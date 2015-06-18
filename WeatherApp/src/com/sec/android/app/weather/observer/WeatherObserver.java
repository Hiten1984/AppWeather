package com.sec.android.app.weather.observer;

import java.util.Observable;

public class WeatherObserver extends Observable {

	private static final String CLEAR = "Clear";
	private static final String PARTLY_CLOUD = "PartlyCloudy";
	private static final String RAINY = "Rain";
	private static final String SUNNY = "Sunny";

	private String timezone = "";
	private String description = "";
	private String temperature = "";
	private String humidity = "";
	private String windSpeed = "";
	private String pressure = "";

	public String getTimezone() {
		return timezone;
	}

	public String getDescription() {
		return description;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
		this.setChanged();
	}

	public void setDescription(String description) {
		this.description = description;
		this.setChanged();
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
		this.setChanged();
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
		this.setChanged();
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
		this.setChanged();
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
		this.setChanged();
	}

	public String getHumidity() {
		return humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public boolean isClear() {
		return CLEAR.equals(this.description);
	}

	public boolean isRainy() {
		return RAINY.equals(this.description);
	}

	public boolean isPartlyCloudy() {
		return PARTLY_CLOUD.equals(this.description);
	}
	
	public boolean isSunny() {
		return SUNNY.equals(this.description);
	}
}
