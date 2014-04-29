package com.cis4350.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.graphics.Color;
import android.graphics.Paint;

import com.cis4350.framework.Game;
import com.cis4350.framework.Graphics;
import com.cis4350.framework.Image;
import com.cis4350.framework.Input.TouchEvent;
import com.cis4350.framework.Screen;
import android.app.Activity;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;

	private static Background bg1, bg2;
	private static Robot robot;

	private Image currentSprite, bird;
	private Animation anim;

	public static int score = 0;
	private boolean gameOver;

	Paint littleText, bigText;

	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here
		gameOver = false;
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		robot = new Robot();

		bird = Assets.fBird;

		anim = new Animation();
		anim.addFrame(bird, 1550);

		currentSprite = anim.getImage();

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

	}

	@Override
	public void update(float deltaTime) {
		List touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List touchEvents, float deltaTime) {

		// Handle touch event input. As most devices only have soft keyboards
		// which
		// would not be displayed during game play there is no need to a
		// keyListener to be implemented here, but if you wished to add one,
		// this
		// would be the appropriate place.

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			robot.jump();
			currentSprite = anim.getImage();
		}

		if (gameOver == true) {
			state = GameState.GameOver;
		}

		robot.update();

		bg1.update();
		bg2.update();
		animate();

		if (robot.getCenterY() > 800) {
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
		g.drawImage(currentSprite, robot.getCenterX(),
				robot.getCenterY() - 63);

		// Example:
		// g.drawImage(Assets.background, 0, 0);
		// g.drawImage(Assets.character, characterX, characterY);

		// Secondly, draw the UI above the game elements.
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
		robot = null;
		currentSprite = null;
		bird = null;
		anim = null;

		// Call garbage collector to clean up memory.
		System.gc();

	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start", 240, 400, bigText);

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
		g.drawString("GAME OVER", 200, 400, bigText);
		g.drawString("Tap to return.", 260, 400, littleText);

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
		// TODO Auto-generated method stub
		game.setScreen(new MainMenuScreen(game));

	}

	public static Background getBg1() {
		// TODO Auto-generated method stub
		return bg1;
	}

	public static Background getBg2() {
		// TODO Auto-generated method stub
		return bg2;
	}

	public static Robot getRobot() {
		// TODO Auto-generated method stub
		return robot;
	}

}