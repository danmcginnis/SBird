package com.cis4350.game;

import android.graphics.Rect;

public class Pipe {
	private char orientation;
	private int x = 500;
	private int y;
	private int speedX = -5;
	private Rect boundingBox = new Rect(0, 0, 0, 0);

	public Pipe(char ori, int yVal) {
		orientation = ori;
		y = yVal;
		if (orientation == 'd') {
			boundingBox.set(x, y, x + 120, y + 800);
		} else {
			boundingBox.set(x, y + 1000, x + 120, y + 1800);
		}
	}

	public Pipe(char ori, int yVal, int xVal) {
		orientation = ori;
		y = yVal;
		x = xVal;
		if (orientation == 'd') {
			boundingBox.set(x, y, x + 120, y + 800);
		} else {
			boundingBox.set(x, y + 1000, x + 120, y + 1800);
		}
	}

	public Rect getBoundingBox() {
		return boundingBox;
	}

	public void update() {
		x += speedX;
		if (orientation == 'd') {
			boundingBox.set(x, y, x + 120, y + 800);
		} else {
			boundingBox.set(x, y + 1000, x + 120, y + 1800);
		}
	}

	public char getOrientation() {
		return orientation;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}