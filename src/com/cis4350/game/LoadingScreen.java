package com.cis4350.game;

import com.cis4350.framework.Game;
import com.cis4350.framework.Graphics;
import com.cis4350.framework.Graphics.ImageFormat;
import com.cis4350.framework.Screen;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("menu.png", ImageFormat.RGB565);
        Assets.background = g.newImage("emulatorBackground.jpg", ImageFormat.RGB565);
        Assets.background2 = g.newImage("background2.png", ImageFormat.ARGB4444);
        Assets.fBird = g.newImage("smallFlappyBird.png", ImageFormat.ARGB4444);
        Assets.upPipe  = g.newImage("upPipe.png", ImageFormat.ARGB4444);
        Assets.downPipe = g.newImage("downPipe.png", ImageFormat.ARGB4444);
        //Assets.button = g.newImage("button.jpg", ImageFormat.RGB565);

        //This is how you would load a sound if you had one.
        //Assets.click = game.getAudio().createSound("explode.ogg");
      
        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.splash, 0, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}