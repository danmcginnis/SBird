package com.cis4350.game;

/*
 * The class deals with reading sensor data. For the purposes of app testing and
 *  demonstration the connections to the actual sensors are severed by commenting
 *  out the code and instead, the sensor data is faked via the SensorSimulator
 *  software available at https://code.google.com/p/openintents/wiki/SensorSimulator
 *  Comments have been added where appropriate, but in short: uncomment everything
 *  related to hardware.Sensor and comment everything from openinternets.sensorsimulator
 *  before deploying on an actual hardware device.
 */

import android.app.Activity;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import com.cis4350.framework.implementation.ShakeListener;

public class SensorData extends Activity implements SensorEventListener {

	private SensorManagerSimulator sensorManager;
	private Sensor moveSensor;
	private float moveX, moveY, moveZ = 0.0f;
	private float lastX, lastY, lastZ;
	private float speed;
	private long diffTime;
	private long lastUpdate = 0;
	private static final int SHAKE_THRESHOLD = 60;
	private boolean moving = false;
	private ShakeListener shakeListener;
	View viewHack = new View(this);

	public SensorData(ShakeListener shakeListener) {
		this.shakeListener = shakeListener;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// for actual sensors
		// sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// for sensorSimulation
		sensorManager = SensorManagerSimulator.getSystemService(this,
				SENSOR_SERVICE);
		sensorManager.connectSimulator();
		moveSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, moveSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, moveSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	protected void onPause() {
		sensorManager.unregisterListener(this);
		super.onPause();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

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
					shakeListener.onShake();
				}

				else {
					resetShakeDetection();
				}

				lastX = moveX;
				lastY = moveY;
				lastZ = moveZ;
			}
			// String moveString = String.valueOf(move);
			// TextView tv = (TextView) findViewById(R.layout.activity_main);
			// tv.setText(moveString);

		}
	}

	public boolean isMoving() {
		return moving;
	}

	private void resetShakeDetection() {
		speed = 0;
		diffTime = 0;
	}

}
