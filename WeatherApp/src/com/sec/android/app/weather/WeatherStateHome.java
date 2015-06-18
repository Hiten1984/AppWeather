package com.sec.android.app.weather;

import com.sec.android.app.weather.state.DefaultWeatherState;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public abstract class WeatherStateHome implements DefaultWeatherState{


	protected void changeWeatherSummary(View view, int color) {
		TextView weatherView = ((TextView) view.findViewById(R.id.weather));
		weatherView.setTextColor(color);
	}

	protected int getColor(Context context, int clear) {
		return context.getResources().getColor(R.color.clear);
	}
}


