package org.me.helloandroid;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorHandler implements SensorEventListener {
	private static final String TAG = "SensorActivity";

	private SensorManager sensormanager;
	private Sensor sensor;
	private SensorEvent sensorevent;

	public SensorHandler(Context context) {
		Log.i(TAG, "SensorHandler -> SensorHandler");
		sensormanager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		Log.i(TAG, "SensorHandler -> onAccuracyChanged");
		Log.d(TAG, String.format("\t sensor.getName()=%s", sensor.getName()));
		Log.d(TAG, String.format("\t sensor.getType()=%d", sensor.getType()));
		Log.d(TAG, String.format("\t accuracy=%d", accuracy));
	}
	@Override
	public void onSensorChanged(SensorEvent sensorevent) {
		Log.i(TAG, "SensorHandler -> onSensorChanged");
		this.sensorevent = sensorevent;
		Log.d(TAG, GetSensorEventString());
	}
	public void RegisterSensors() {
		Log.i(TAG, "SensorHandler -> RegisterSensors");
		List<Sensor> sensorslist = sensormanager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensorslist.size() > 0) {
			if (sensor == null) {
				sensor = sensorslist.get(0);
				sensormanager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
			} else
				Log.i(TAG, "sensor is already registered");
		}
	}
	public void UnregisterSensors() {
		Log.i(TAG, "SensorHandler -> UnregisterSensors");
		if (sensor != null) {
			sensormanager.unregisterListener(this, sensor);
			sensor = null;
		} else
			Log.i(TAG, "sensor is already null");
	}
	public SensorEvent getSensorevent() {
		return sensorevent;
	}
	public static void PrintSensorDetails(Sensor sensor) {
		Log.i(TAG, "SensorHandler -> PrintSensorDetails");
		Log.d(TAG, String.format("\t getName()=%s", sensor.getName()));
		Log.d(TAG, String.format("\t getMaximumRange()=%f", sensor.getMaximumRange()));
		Log.d(TAG, String.format("\t getPower()=%fmA", sensor.getPower()));
		Log.d(TAG, String.format("\t getResolution()=%f", sensor.getResolution()));
		Log.d(TAG, String.format("\t sensor.getType()=%d", sensor.getType()));
		Log.d(TAG, String.format("\t getVendor()=%s", sensor.getVendor()));
		Log.d(TAG, String.format("\t getVersion()=%d", sensor.getVersion()));
	}
	public String GetSensorEventString() { 
		String str =  String.format(
			"\t sensor.getName()=%s\n" +
			"\t sensor.getType()=%d\n" +
			"\t accuracy=%d\n" +
			"\t timestamp=%d\n" +
			"\t values.length=%d\n",
			sensorevent.sensor.getName(),
			sensorevent.sensor.getType(),
			sensorevent.accuracy,
			sensorevent.timestamp,
			sensorevent.values.length
			);
		for (int i = 0; i < sensorevent.values.length; i++) {
			str += String.format("\t\t values[%d]=%f\n", i, sensorevent.values[i]);
		}
		return str;
	}
}
