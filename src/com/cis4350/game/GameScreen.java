package com.cis4350.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.graphics.Color;
import android.graphics.Paint;

import com.cis4350.framework.Game;
import com.cis4350.framework.Graphics;
import com.cis4350.framework.Image;
import com.cis4350.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;

	private static Background bg1, bg2;
	private static Bird sBird;
	ArrayList<Pipe> pipes;
	private Image currentSprite, bird, upPipe, downPipe;
	private Animation anim;
	private Rect birdBox;
	public static int score = 0;
	public String scoreString = "";
	private boolean gameOver;

	Paint littleText, bigText, scoreBoard;

	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here
		pipes = new ArrayList<Pipe>();
		gameOver = false;
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		sBird = new Bird();
		score = 0;
		bird = Assets.fBird;
		upPipe = Assets.upPipe;
		downPipe = Assets.downPipe;
		anim = new Animation();
		anim.addFrame(bird, 1550);

		currentSprite = anim.getImage();

		int y1 = (int) (Math.random() * (-450)) - 300;
		pipes.add(new Pipe('d', y1, 500));
		pipes.add(new Pipe('u', y1, 500));
		y1 = (int) (Math.random() * (-450)) - 300;
		pipes.add(new Pipe('d', y1, 900));
		pipes.add(new Pipe('u', y1, 900));
		y1 = (int) (Math.random() * (-450)) - 300;
		pipes.add(new Pipe('d', y1, 1300));
		pipes.add(new Pipe('u', y1, 1300));

		// Defining a paint object
		littleText = new Paint();
		littleText.setTextSize(30);
		littleText.setTextAlign(Paint.Align.CENTER);
		littleText.setAntiAlias(true);
		littleText.setColor(Color.WHITE);

		bigText = new Paint();
		bigText.setTextSize(70);
		bigText.setTextAlign(Paint.Align.CENTER);
		bigText.setAntiAlias(true);
		bigText.setColor(Color.WHITE);

		scoreBoard = new Paint();
		scoreBoard.setTextSize(30);
		scoreBoard.setTextAlign(Paint.Align.CENTER);
		scoreBoard.setAntiAlias(true);
		scoreBoard.setColor(Color.WHITE);
	}

	@Override
	public void update(float deltaTime) {
		List touchEvents = game.getInput().getTouchEvents();

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List touchEvents) {

		// Start with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called.

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List touchEvents, float deltaTime) {

		// Handle touch event input. As most devices only have soft keyboards
		// which would not be displayed during game play there is no need to a
		// keyListener to be implemented here, but if you wished to add one,
		// this would be the appropriate place.

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			sBird.jump();
			currentSprite = anim.getImage();
		}

		if (SensorData.isMoving()) {
			sBird.jump();
			currentSprite = anim.getImage();
		}

		if (gameOver == true) {
			state = GameState.GameOver;
		}

		sBird.update();

		bg1.update();
		bg2.update();
		int y1 = 0;
		for (int pcount = 0; pcount < pipes.size(); pcount++) {
			Pipe p = pipes.get(pcount);
			p.update();
			if (p.getX() <= -300) {
				if (p.getOrientation() == 'd') {
					y1 = (int) (Math.random() * (-450)) - 300;
					pipes.set(pcount, new Pipe('d', y1, 900));
				} else {
					pipes.set(pcount, new Pipe('u', y1, 900));
				}
			}
			if (p.getX() == 50 && p.getOrientation() == 'd') {
				score++;
			}
		}

		birdBox = sBird.getBoundingBox();
		for (int pcount = 0; pcount < pipes.size(); pcount++) {

			if (Rect.intersects(birdBox, pipes.get(pcount).getBoundingBox())) {
				gameOver = true;
			}
		}
		animate();

		if (sBird.getCenterY() > 850) {
			// let the bird completely dip below the horizon before
			// ending the game.
			gameOver = true;
		}
	}

	private void updateGameOver(List touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			nullify();
			game.setScreen(new MainMenuScreen(game));
			return;
		}

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
		g.drawImage(currentSprite, sBird.getCenterX() - 26,
				sBird.getCenterY() - 17);
		for (int pcount = 0; pcount < pipes.size(); pcount++) {
			Pipe p = pipes.get(pcount);
			char o = p.getOrientation();
			if (o == 'd') {
				g.drawImage(downPipe, p.getX(), p.getY());
			} else {
				g.drawImage(upPipe, (int) (p.getBoundingBox().centerX() - p
						.getBoundingBox().width() / 2), (int) (p
						.getBoundingBox().centerY() - p.getBoundingBox()
						.height() / 2));
			}
		}

		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
	}

	public void animate() {
		anim.update(10);
	}

	private void nullify() {
		// Set all variables to null.
		// Not sure if this is necessary since the GC is called next.
		littleText = null;
		bg1 = null;
		bg2 = null;
		sBird = null;
		currentSprite = null;
		bird = null;
		anim = null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start", 240, 420, bigText);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, bigText);
		g.drawString("Menu", 400, 360, bigText);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 490, 810, Color.BLACK);
		g.drawString("GAME OVER", 240, 250, bigText);
		g.drawString("Final Score: " + scoreString.valueOf(score), 240, 350,
				littleText);
		g.drawString("Tap to return", 240, 450, littleText);
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;
	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		game.setScreen(new MainMenuScreen(game));

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Bird getBird() {
		return sBird;
	}
}