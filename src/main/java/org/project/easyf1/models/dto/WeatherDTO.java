package org.project.easyf1.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDTO {
    @JsonProperty("air_temperature")
    private double airTemperature; // (°C)

    @JsonProperty("humidity")
    private int humidity; // (%)

    @JsonProperty("pressure")
    private int pressure; // (mbar)

    @JsonProperty("rainfall")
    private int rainfall; 

    @JsonProperty("track_temperature")
    private double trackTemperature; // (°C)

    @JsonProperty("wind_speed")
    private double windSpeed; // (m/s)

    @JsonProperty("session_key")
    private int sessionKey;

    @JsonProperty("meeting_key")
    private int meetingKey;

    public double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getRainfall() {
        return rainfall;
    }

    public void setRainfall(int rainfall) {
        this.rainfall = rainfall;
    }

    public double getTrackTemperature() {
        return trackTemperature;
    }

    public void setTrackTemperature(double trackTemperature) {
        this.trackTemperature = trackTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(int sessionKey) {
        this.sessionKey = sessionKey;
    }

    public int getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(int meetingKey) {
        this.meetingKey = meetingKey;
    }
}
