package com.sec.android.app.weather.processing;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.sec.android.app.weather.observer.WeatherObserver;

import android.content.Context;
import android.os.AsyncTask;

public class WeatherRequestProcessor extends AsyncTask<String, String, JSONObject> {
	
	private final WeatherObserver weather;

    public WeatherRequestProcessor(Context context, WeatherObserver weather) {
        if (weather == null)
            throw new IllegalArgumentException("Weather cannot be null!");
        this.weather = weather;
    }

    @Override
    protected JSONObject doInBackground(String... uri) {
        JSONObject json = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(uri[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());            
            json = this.extractJson(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return json;
    }

    private JSONObject extractJson(InputStream in) throws IOException, JSONException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while (null != (inputStr = streamReader.readLine()))
            responseStrBuilder.append(inputStr);
        return new JSONObject(responseStrBuilder.toString());
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);        
        try {
            JSONObject currentWeather = json.getJSONObject("currently");
            weather.setTimezone(json.getString("timezone"));
            weather.setDescription(currentWeather.getString("summary"));
            weather.setTemperature(currentWeather.getString("temperature"));
            weather.setHumidity(currentWeather.getString("humidity"));
            weather.setWindSpeed(currentWeather.getString("windSpeed"));
            weather.setPressure(currentWeather.getString("pressure"));

            this.weather.notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
