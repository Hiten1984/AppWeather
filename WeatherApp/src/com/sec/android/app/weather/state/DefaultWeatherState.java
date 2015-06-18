package com.sec.android.app.weather.state;

import android.content.Context;
import android.view.View;

public interface DefaultWeatherState {

	/*  
	 * Updates the default state as per the weather 
	 * conditions
	 *  
	 */
	
	void updateState(Context ctx, View view);
	
}
