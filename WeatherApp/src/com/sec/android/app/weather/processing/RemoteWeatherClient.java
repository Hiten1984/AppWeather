package com.sec.android.app.weather.processing;

import android.content.Context;
import android.location.Location;

import com.sec.android.app.weather.observer.WeatherObserver;

public class RemoteWeatherClient {

	//String API_KEY = "1510db66112dbf39acd9e2071d7e9768";
	private static String WEATHER_URL = "http://api.forecast.io/forecast/1510db66112dbf39acd9e2071d7e9768/{LAT},{LON}"; //"https://api.forecast.io/forecast/1510db66112dbf39acd9e2071d7e9768/37.8136,144.9631";	

	    private static final RemoteWeatherClient instance = new RemoteWeatherClient();
	    private WeatherObserver obsWeather;

	    public static RemoteWeatherClient getInstance() {
	        return instance;
	    }

	    public void init(WeatherObserver obsWeather) {
	        this.obsWeather = obsWeather;
	    }

	    private RemoteWeatherClient() {
	    }

	    public WeatherObserver getWeather() {
	        this.checkInit();
	        return this.obsWeather;
	    }

	    public void updateWeather(final Context context, final Location location) {
	        new Thread(new Runnable() {
	            @Override
	            public void run() {	    
	                new WeatherRequestProcessor(context, obsWeather).execute(WEATHER_URL.replaceAll("{LAT}", String.valueOf(location.getLatitude())).replaceAll("{LON}", String.valueOf(location.getLongitude())));
	            }
	        }).start();
	    }

	    private void checkInit() throws IllegalArgumentException {
	        if (this.obsWeather == null) 
	            throw new IllegalStateException("Service was not initiated");
	    }

	    @Override
	    protected Object clone() throws CloneNotSupportedException {
	        throw new CloneNotSupportedException("cannot clone singleton");
	    }
	}
