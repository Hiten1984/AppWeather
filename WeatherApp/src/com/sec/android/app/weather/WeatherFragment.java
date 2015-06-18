package com.sec.android.app.weather;


import java.util.Observable;
import java.util.Observer;

import com.sec.android.app.weather.observer.WeatherObserver;
import com.sec.android.app.weather.processing.RemoteWeatherClient;
import com.sec.android.app.weather.state.DefaultWeatherState;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment extends Fragment implements Observer {
	private View view;
	private DefaultWeatherState weatherState = new NoWeatherState();

	public WeatherFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WeatherObserver weatherObserver = new WeatherObserver();
		weatherObserver.addObserver(this);
		RemoteWeatherClient.getInstance().init(weatherObserver);
		this.isLastLocationFound();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (this.view == null) {
			this.view = inflater.inflate(R.layout.weather_fragment, container, false);
		}
		return this.view;
	}

	@Override
	public void update(Observable observable, Object data) {
		WeatherObserver weather = RemoteWeatherClient.getInstance().getWeather();
		((TextView) this.view.findViewById(R.id.timezone)).setText(weather.getTimezone());
		((TextView) this.view.findViewById(R.id.weather)).setText(weather.getDescription());
		((TextView) this.view.findViewById(R.id.temperature)).setText(weather.getTemperature());
		((TextView) this.view.findViewById(R.id.humidity)).setText(weather.getHumidity());
		((TextView) this.view.findViewById(R.id.wind)).setText(weather.getWindSpeed());
		((TextView) this.view.findViewById(R.id.pressure)).setText(weather.getPressure());
		this.updateState(weather);
		this.weatherState.updateState(this.getActivity(), this.view);
	}

	private void isLastLocationFound() {
		try {
			WeatherActivity weatherActivity = (WeatherActivity) WeatherFragment.this.getActivity();
			if (weatherActivity.hasLocation()) {
				RemoteWeatherClient.getInstance().updateWeather(weatherActivity, weatherActivity.getLastLocation());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateState(WeatherObserver obsWeather) {
		if (obsWeather.isClear())
			this.weatherState = new WeatherStateClear();
		else if (obsWeather.isRainy())
			this.weatherState = new WeatherStateRain();
		else if (obsWeather.isSunny())
			this.weatherState = new WeatherStateSunny();
		else
			this.weatherState = new NoWeatherState();
	}
}
