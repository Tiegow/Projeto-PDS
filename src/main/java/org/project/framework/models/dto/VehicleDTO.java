package org.project.framework.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.framework.models.entity.Vehicle;
import org.project.framework.models.entity.Meeting;
import org.project.framework.models.entity.Session;

import java.util.GregorianCalendar;

public class VehicleDTO {

    @JsonProperty(value = "brake")
    private Integer brake;

    @JsonProperty(value = "date")
    private GregorianCalendar date;

    @JsonProperty(value = "drs")
    private Integer drs;

    @JsonProperty(value = "meetingKey")
    @JsonAlias("meeting_key")
    private Integer meetingKey;

    @JsonProperty(value = "gear")
    private Integer gear;

    @JsonProperty(value = "rpm")
    private Integer rpm;

    @JsonProperty(value = "session")
    @JsonAlias("session_key")
    private Integer sessionKey;

    @JsonProperty(value = "speed")
    private Double speed;

    @JsonProperty(value = "throttle")
    private Double throttle;

    @JsonProperty(value = "points")
    private Integer points;

    @JsonProperty(value = "position")
    private Integer position;

    public VehicleDTO() {
    }

    public VehicleDTO(Vehicle vehicle) {
        this.brake = vehicle.getBrake();
        this.date = vehicle.getDate();
        this.drs = vehicle.getDrs();
        this.gear = vehicle.getGear();
        this.rpm = vehicle.getRpm();
        this.speed = vehicle.getSpeed();
        this.throttle = vehicle.getThrottle();

        this.meetingKey = vehicle.getMeeting().getMeetingKey();
        this.sessionKey = vehicle.getSession().getSessionKey();
    }

    public Vehicle getCar(){
        Vehicle vehicle = new Vehicle();
        vehicle.setBrake(this.brake);
        vehicle.setDate(this.date);
        vehicle.setDrs(this.drs);
        vehicle.setGear(this.gear);
        vehicle.setRpm(this.rpm);
        vehicle.setSpeed(this.speed);
        vehicle.setThrottle(this.throttle);

        vehicle.setMeeting(new Meeting());
        vehicle.setSession(new Session());
        vehicle.getSession().setSessionKey(this.sessionKey);
        vehicle.getMeeting().setMeetingKey(this.meetingKey);

        return vehicle;
    }

    public Integer getBrake() {
        return brake;
    }

    public void setBrake(Integer brake) {
        this.brake = brake;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public Integer getDrs() {
        return drs;
    }

    public void setDrs(Integer drs) {
        this.drs = drs;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }

    public Integer getGear() {
        return gear;
    }

    public void setGear(Integer gear) {
        this.gear = gear;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public Integer getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getThrottle() {
        return throttle;
    }

    public void setThrottle(Double throttle) {
        this.throttle = throttle;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
