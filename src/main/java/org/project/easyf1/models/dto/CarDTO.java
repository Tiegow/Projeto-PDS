package org.project.easyf1.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.easyf1.models.entity.Car;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;

import java.util.GregorianCalendar;

public class CarDTO {

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

    public CarDTO() {
    }

    public CarDTO(Car car) {
        this.brake = car.getBrake();
        this.date = car.getDate();
        this.drs = car.getDrs();
        this.gear = car.getGear();
        this.rpm = car.getRpm();
        this.speed = car.getSpeed();
        this.throttle = car.getThrottle();

        this.meetingKey = car.getMeeting().getMeetingKey();
        this.sessionKey = car.getSession().getSessionKey();
    }

    public Car getCar(){
        Car car = new Car();
        car.setBrake(this.brake);
        car.setDate(this.date);
        car.setDrs(this.drs);
        car.setGear(this.gear);
        car.setRpm(this.rpm);
        car.setSpeed(this.speed);
        car.setThrottle(this.throttle);

        car.setMeeting(new Meeting());
        car.setSession(new Session());
        car.getSession().setSessionKey(this.sessionKey);
        car.getMeeting().setMeetingKey(this.meetingKey);

        return car;
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
