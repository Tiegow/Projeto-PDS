package org.project.easyf1.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionDTO {

    @JsonProperty("position")
    private int position;

    @JsonProperty("driver_number")
    private int driverNumber;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(int driverNumber) {
        this.driverNumber = driverNumber;
    }

}
