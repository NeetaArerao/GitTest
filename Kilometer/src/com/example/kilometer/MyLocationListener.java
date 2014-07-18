package com.example.kilometer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {
	Context con;

	MyLocationListener(Context context) {
		con = context;
	}

	public void onLocationChanged(Location location) {
		String message = String.format(
				"New Location \n Longitude: %1$s \n Latitude: %2$s",
				location.getLongitude(), location.getLatitude());

		Toast.makeText(con, message, Toast.LENGTH_LONG).show();
	}

	public void onStatusChanged(String s, int i, Bundle b) {
		Toast.makeText(con, "Provider status changed", Toast.LENGTH_LONG)
				.show();
	}

	public void onProviderDisabled(String s) {
		Toast.makeText(con, "Provider disabled by the user. GPS turned off",
				Toast.LENGTH_LONG).show();
	}

	public void onProviderEnabled(String s) {
		Toast.makeText(con, "Provider enabled by the user. GPS turned on",
				Toast.LENGTH_LONG).show();
	}

}
