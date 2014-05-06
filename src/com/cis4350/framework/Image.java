package com.cis4350.framework;

import com.cis4350.framework.Graphics.ImageFormat;

public interface Image {

	public int getWidth();

	public int getHeight();

	public ImageFormat getFormat();

	public void dispose();
}