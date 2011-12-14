package org.me.helloandroid;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SensorActivity extends Activity {
	private static final String TAG = "SensorActivity";

	private SensorHandler sensorhandler;
	private TextView status_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onConfigurationChanged(null);
		setContentView(R.layout.main);

		status_tv = (TextView)findViewById(R.id.status_tv);
		sensorhandler = new SensorHandler(this);
		PritnSensorsList();

		((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "SensorActivity -> button -> onClick");
				startSensors();
			}
		});
		((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "SensorActivity -> button2 -> onClick");
				stopSensors();
			}
		});
		((Button)findViewById(R.id.update_button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "SensorActivity -> update_button -> onClick");
				status_tv.setText(sensorhandler.GetSensorEventString());
			}
		});
	}
	private void startSensors() {
		sensorhandler.RegisterSensors();
	}
	private void stopSensors() {
		sensorhandler.UnregisterSensors();
	}
	private void PritnSensorsList() {
		List<Sensor> sensorslist = ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : sensorslist) {
			SensorHandler.PrintSensorDetails(sensor);
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopSensors();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}