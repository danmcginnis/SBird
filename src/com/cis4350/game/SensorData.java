package com.cis4350.game;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SensorData extends Activity implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor moveSensor;
	private float moveX, moveY, moveZ = 0.0f;
	private float lastX, lastY, lastZ;
	private long lastUpdate = 0;
	private static final int SHAKE_THRESHOLD = 60;
	private static boolean moving = false;
	View viewHack = new View(this);

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			moveX = event.values[0];
			moveY = event.values[1];
			moveZ = event.values[2];

			long curTime = System.currentTimeMillis();

			// we don't care about updates that happen less than 100
			// milliseconds ago
			if ((curTime - lastUpdate) > 100) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;

				float speed = Math.abs(moveX + moveY + moveZ - lastX - lastY
						- lastZ)
						/ diffTime * 10000;

				if (speed > SHAKE_THRESHOLD) {
					moving = true;
				}

				else {
					moving = false;
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

	public static boolean isMoving() {
		return moving;
	}

}
