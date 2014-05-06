package com.cis4350.framework.implementation;

/*
 * The class deals with reading sensor data. For the purposes of app testing and
 *  demonstration the connections to the actual sensors are severed by commenting
 *  out the code and instead, the sensor data is faked via the SensorSimulator
 *  software available at https://code.google.com/p/openintents/wiki/SensorSimulator
 *  Comments have been added where appropriate, but in short: uncomment everything
 *  related to hardware.Sensor and comment everything from openinternets.sensorsimulator
 *  before deploying on an actual hardware device.
 */

import com.cis4350.game.SampleGame;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//import org.openintents.sensorsimulator.hardware.Sensor;
//import org.openintents.sensorsimulator.hardware.SensorEvent;
//import org.openintents.sensorsimulator.hardware.SensorEventListener;
//import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

public class AccelerometerHandler implements SensorEventListener {
	private static float screenX;
	static float screenY;
	static float screenZ;
	private SensorManager sensorManager;
	private Sensor moveSensor;
	private float moveX, moveY, moveZ = 0.0f;
	private long lastUpdate = 0;
	private long diffTime;
	private float speed;
	private float lastX, lastY, lastZ;
	private static final int SHAKE_THRESHOLD = 60;
	private static boolean isMoving = false;

	public AccelerometerHandler(Context context) {
		sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			moveSensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER)
					.get(0);
			sensorManager.registerListener(this, moveSensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// nothing to do here
	}

//	public void onPause() {
//		sensorManager.unregisterListener(this);
//		//super.onPause();
//	}
//
//	protected void onResume() {
//		//super.onPause();
//		sensorManager.registerListener(this, moveSensor,
//				SensorManager.SENSOR_DELAY_FASTEST);
//	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.equals(Sensor.TYPE_ACCELEROMETER)) {
			moveX = event.values[0];
			moveY = event.values[1];
			moveZ = event.values[2];

			long curTime = System.currentTimeMillis();

			// we don't care about updates that happen less than 100
			// milliseconds ago
			if ((curTime - lastUpdate) > 100) {
				diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;

				speed = Math.abs(moveX + moveY + moveZ - lastX - lastY - lastZ)
						/ diffTime * 10000;

				if (speed > SHAKE_THRESHOLD) {
					// It's a shake! Notify the listener.
					// shakeListener.onShake();
					isMoving = true;

				}

				else {
					isMoving = false;
				}

				lastX = moveX;
				lastY = moveY;
				lastZ = moveZ;
			}
		}
	}

	public static float getAccelX() {
		return screenX;
	}

	public static float getAccelY() {
		return screenY;
	}

	public static float getAccelZ() {
		return screenZ;
	}

	public void setScreenX(float screenX) {
		this.screenX = screenX;
	}

	public static boolean isMoving() {
		return isMoving;
	}
}