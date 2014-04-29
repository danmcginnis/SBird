package com.cis4350.game;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class SensorData extends Activity implements SensorEventListener{
	
	private SensorManager sensorManager;
	private Sensor moveSensor;
	
	private float move = 0.0f;
	
	
	

	public SensorData() {
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(figure out where activity main is);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		moveSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, moveSensor, SensorManager.SENSOR_DELAY_FASTEST);
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
			move = event.values[0];
			String moveString = String.valueOf(move);
			//TextView tv = (TextView) findViewById(need main activity again);
			//tv.setText(moveString);
		}
	}
	
	

}
