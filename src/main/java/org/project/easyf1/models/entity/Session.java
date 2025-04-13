package org.project.easyf1.models.entity;

import jakarta.persistence.*;

import java.util.GregorianCalendar;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private Integer countryKey;
    private String countryCode;
    private String countryName;
    private Integer circuitKey;
    private String circuitShortName;
    private String sessionType;
    private String sessionName;
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;
    private Integer sessionKey;
    private Integer meetingKey;

    public Session() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCountryKey() {
        return countryKey;
    }

    public void setCountryKey(Integer countryKey) {
        this.countryKey = countryKey;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCircuitKey() {
        return circuitKey;
    }

    public void setCircuitKey(Integer circuitKey) {
        this.circuitKey = circuitKey;
    }

    public String getCircuitShortName() {
        return circuitShortName;
    }

    public void setCircuitShortName(String circuitShortName) {
        this.circuitShortName = circuitShortName;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public Integer getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }
}
