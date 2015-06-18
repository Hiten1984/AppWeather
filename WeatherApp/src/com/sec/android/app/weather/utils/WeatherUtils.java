package com.sec.android.app.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WeatherUtils {
	
	/**
     * @param context
     * @return true - if network is connected
     *         false- if not connected
     */    
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) 
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		boolean isAvailable = false;
		if(networkInfo != null && networkInfo.isConnected()){
			isAvailable = true;
		}
		return isAvailable;
	}

    /**
     * @param context
     * @return true  if WI-FI connected
     *         false if not connected        
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo != null) {
            return wifiNetworkInfo.isConnected();
        }         
        return false;
    }

    /**
     * @param context
     * @return true  if GPS is enabled
     *         false if not enabled 
     */
    public static boolean isGPSEnabled(Context mContext) {
        
    	LocationManager locManager = (LocationManager) mContext
                .getSystemService(Activity.LOCATION_SERVICE);

        // getting GPS status
        boolean isGPSEnabled = locManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

    	return isGPSEnabled;
    }
}
