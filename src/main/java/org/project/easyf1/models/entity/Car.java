package org.project.easyf1.models.entity;


import jakarta.persistence.*;

import java.util.GregorianCalendar;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer driverNumber;

    private Integer brake;

    private GregorianCalendar date;

    private Integer drs;

    @ManyToOne
    @JoinColumn(name = "meeting_key", nullable = false)
    private Meeting meeting;

    private Integer gear;
    private Integer rpm;

    @ManyToOne
    @JoinColumn(name = "session_key", nullable = false)
    private Session session;

    private Double speed;
    private Double throttle;

    public Car() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }
}
