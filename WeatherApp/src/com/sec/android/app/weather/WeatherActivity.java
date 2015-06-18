package com.sec.android.app.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.sec.android.app.weather.utils.WeatherUtils;

public class WeatherActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener  {

	private static final long MIN_TIME = 0l;
	private static final float MIN_DISTANCE = 0f;
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		this.buildGoogleApiClient();
	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
		mGoogleApiClient.connect();
	}

	public boolean hasLocation() {
		return this.mLastLocation != null;
	}

	@Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation == null) {
            this.updateCurrentPosition();
        }
    }

	@Override
	protected void onResume() {
		super.onResume();
		this.verifyNetworkConnection();
	}

	private void verifyNetworkConnection() {
		if (!WeatherUtils.isNetworkAvailable(this)) {
			suggestEnableNetwork();
			while (!WeatherUtils.isNetworkAvailable(this)) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void suggestEnableGPS() {
		Toast.makeText(this, R.string.gps_not_enabled, Toast.LENGTH_SHORT).show();
		Intent gpsOptionsIntent = new Intent(
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(gpsOptionsIntent);
	}

	private void suggestEnableNetwork() {
		Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	private void updateCurrentPosition() {
		try {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE,
					this);
			if (!WeatherUtils.isGPSEnabled(this))
				suggestEnableGPS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.w("LOCATION", "Suspended");
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.w("LOCATION", "FAILED");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		try {
			this.mLastLocation = location;
		} catch (Exception e) {
			Log.e("MAIN", "Failed to update location on weather fragment", e);
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	public Location getLastLocation() {
		return mLastLocation;
	}
}