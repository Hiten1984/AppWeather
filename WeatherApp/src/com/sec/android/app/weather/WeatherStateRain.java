package com.sec.android.app.weather;

import android.content.Context;
import android.view.View;

public class WeatherStateRain extends WeatherStateHome{


	/**
	 * @author Hiten
	 *
	 */
	@Override
	public void updateState(Context ctx, View view) {
		changeWeatherSummary(view, getColor(ctx, R.color.rainy));
	}
}
