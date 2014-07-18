package com.example.kilometer;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener,
		Runnable {

	// private LocationManager locationManager;
	private String provider;

	boolean isStart = false;
	Button playPauseBtn;
	int count = 0;
	private double currentLong = 0, lastLong = 0;
	private double currentLat = 0, lastLat = 0;
	String distance;
	boolean isPlay = false;
	private Location currentLocation, lastLocation;
	private EditText kmEt, durationEt;
	Location location;

	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in
																		// Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 30000;

	protected LocationManager locationManager;
	static double n = 0;
	Long s1, r1;
	double plat, plon, clat, clon, dis;
	MyCount counter;
	Thread t1;
	EditText e1;
	boolean bool = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}

	private void initView() {
		playPauseBtn = (Button) findViewById(R.id.play_pause_btn);
		playPauseBtn.setOnClickListener(this);
		kmEt = (EditText) findViewById(R.id.km_et);
		durationEt = (EditText) findViewById(R.id.duration_et);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		locationManager.requestLocationUpdates(provider,
				MINIMUM_TIME_BETWEEN_UPDATES,
				MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener(
						MainActivity.this));
		showCurrentLocation();
	}

	private void getCurrentLocation() {
		// TODO Auto-generated method stub
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			// onLocationChanged(location);
		} else {
			// latituteField.setText("Location not available");
			// longitudeField.setText("Location not available");
		}
	}

	/*
	 * @Override public void onLocationChanged(Location location) { int lat =
	 * (int) (location.getLatitude()); int lng = (int)
	 * (location.getLongitude()); Toast.makeText(MainActivity.this,
	 * "Onlocation Change", Toast.LENGTH_SHORT).show(); //
	 * latituteField.setText(String.valueOf(lat)); //
	 * longitudeField.setText(String.valueOf(lng));
	 * 
	 * Toast.makeText( this, "latitude " + location.getLatitude() +
	 * "\nlongitude " + location.getLongitude(), Toast.LENGTH_SHORT).show(); if
	 * (isStart) { currentLat = location.getLatitude(); currentLong =
	 * location.getLongitude(); currentLocation = location; Log.v("",
	 * "currentLat:::" + currentLat + "currentLong:::" + currentLong +
	 * currentLocation);
	 * 
	 * } else { lastLocation = location; lastLat = 18.5583214; lastLong
	 * =73.8860099; lastLocation.setLatitude(lastLat);
	 * lastLocation.setLongitude(lastLong); // lastLat = location.getLatitude();
	 * // lastLong = location.getLongitude();
	 * 
	 * Log.v("", "lastLat:" + lastLat + "lastLong::" + lastLong);
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	/*
	 * @Override public void onStatusChanged(String provider, int status, Bundle
	 * extras) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onProviderEnabled(String provider) {
	 * Toast.makeText(this, "Enabled new provider " + provider,
	 * Toast.LENGTH_SHORT).show();
	 * 
	 * }
	 * 
	 * @Override public void onProviderDisabled(String provider) {
	 * Toast.makeText(this, "Disabled provider " + provider,
	 * Toast.LENGTH_SHORT).show(); }
	 */

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.play_pause_btn:
			if (isStart) {
				playPauseBtn.setText("START");
				// getCurrentLocation();
				// if (count != 0)
				// getDisatnce();
				// pause
				counter.cancel();

				bool = false;
				double time = n * 30 + r1;
				Toast.makeText(
						MainActivity.this,
						"distance in metres:" + String.valueOf(dis)
								+ "Velocity in m/sec :"
								+ String.valueOf(dis / time), Toast.LENGTH_LONG)
						.show();
				durationEt.setText(String.valueOf(time));
			} else {

				playPauseBtn.setText("PAUSE");
				if (count == 0) {
					// getCurrentLocation();
					// start moving.. calculates distance on clicking this
					showCurrentLocation();
					t1 = new Thread();
					t1.start();
					counter = new MyCount(30000, 1000);
					counter.start();
				} else {
					// resume
					counter = new MyCount(s1, 1000);
					counter.start();
					bool = true;
				}

			}
			count++;
			isStart = !isStart;

			break;

		default:
			break;
		}
	}

	/*
	 * private void getDisatnce() { // TODO Auto-generated method stub float
	 * dist = lastLocation.distanceTo(currentLocation) / 1000; Log.v("",
	 * "dist::" + dist); String disatance = Float.toString(dist);
	 * kmEt.setText(disatance);
	 * 
	 * }
	 */

	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			counter = new MyCount(30000, 1000);
			counter.start();
			n = n + 1;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			s1 = millisUntilFinished;
			r1 = (30000 - s1) / 1000;
			// e1.setText(String.valueOf(r1));
			Log.v("", "e1::" + String.valueOf(r1));

		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (bool) {
			Toast.makeText(MainActivity.this, "inside While",
					Toast.LENGTH_SHORT).show();
			clat = location.getLatitude();
			clon = location.getLongitude();
			if (clat != plat || clon != plon) {
				
				dis += getDistance(plat, plon, clat, clon);
				Toast.makeText(MainActivity.this, "calulating distance"+dis,
						Toast.LENGTH_SHORT).show();
				plat = clat;
				plon = clon;

			}

		}
	}

	protected void showCurrentLocation() {
		// Define the criteria how to select the locatioin provider -> use
		// default

		location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			String message = String.format(
					"Current Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude());
			clat = location.getLatitude();
			clon = location.getLongitude();
			Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(MainActivity.this, "null location",
					Toast.LENGTH_LONG).show();
		}
	}

	public double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double latA = Math.toRadians(lat1);
		double lonA = Math.toRadians(lon1);
		double latB = Math.toRadians(lat2);
		double lonB = Math.toRadians(lon2);
		double cosAng = (Math.cos(latA) * Math.cos(latB) * Math
				.cos(lonB - lonA)) + (Math.sin(latA) * Math.sin(latB));
		double ang = Math.acos(cosAng);
		double dist = ang * 6371;
		return dist;
	}

}
