package com.cis4350.game;

import java.util.ArrayList;

import android.graphics.Rect;

public class Robot {

	// Constants are Here
	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	private int centerX = 100;
	private int centerY = 377;

	private int speedY = 0;
	public static Rect boundingBox = new Rect(0, 0, 0, 0);

	public void update() {
		// Moves Character or Scrolls Background accordingly.

		// Updates Y Position
		centerY += speedY;
		if (centerY < 30)
			centerY = 30;
		// Handles Jumping
		if (speedY <= 15)
			speedY += 1;

		// Prevents going beyond X coordinate of 0

		boundingBox.set(centerX - 25, centerY - 17, centerX + 25, centerY + 17);
	}

	private void stop() {

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