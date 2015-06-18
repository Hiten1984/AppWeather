/**
 * 
 */
package com.sec.android.app.weather;

import android.content.Context;
import android.view.View;

/**
 * @author Hiten
 *
 */
public class WeatherStateClear extends WeatherStateHome {

	@Override
	public void updateState(Context ctx, View view) {
		changeWeatherSummary(view, this.getColor(ctx, R.color.clear));

	}

}
