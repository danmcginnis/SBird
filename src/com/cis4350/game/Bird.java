package com.cis4350.game;

import android.graphics.Rect;

public class Bird {

	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	private int centerX = 100;
	private int centerY = 377;

	private int speedY = 0;
	public static Rect boundingBox = new Rect(0, 0, 0, 0);

	public void update() {
		centerY += speedY;

		if (centerY < 30)
			centerY = 30;

		if (speedY <= 15)
			speedY += 1;

		boundingBox.set(centerX - 25, centerY - 17, centerX + 25, centerY + 17);
	}

	public void jump() {
		speedY = -12;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public Rect getBoundingBox() {
		return boundingBox;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
}