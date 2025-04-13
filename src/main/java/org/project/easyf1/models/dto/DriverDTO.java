package org.project.easyf1.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.easyf1.models.entity.Driver;

public class DriverDTO {

    @JsonProperty("broadcast_name")
    @JsonAlias("broadcastName")
    private String broadcastName;

    @JsonProperty("country_code")
    @JsonAlias("countryCode")
    private String countryCode;

    @JsonProperty("driver_number")
    @JsonAlias("driverNumber")
    private Integer driverNumber;

    @JsonProperty("first_name")
    @JsonAlias("firstName")
    private String firstName;

    @JsonProperty("last_name")
    @JsonAlias("lastName")
    private String lastName;

    @JsonProperty("headshot_url")
    @JsonAlias("headshotUrl")
    private String headshotUrl;

    @JsonProperty("meeting_key")
    @JsonAlias("meetingKey")
    private Integer meetingKey;

    @JsonProperty("name_acronym")
    @JsonAlias("nameAcronym")
    private String nameAcronym;

    @JsonProperty("session_key")
    @JsonAlias("sessionKey")
    private Integer sessionKey;

    @JsonProperty("team_colour")
    @JsonAlias("teamColour")
    private String teamColour;

    @JsonProperty("team_name")
    @JsonAlias("teamName")
    private String teamName;

    public DriverDTO() {
    }

    public Driver getDriver() {
        Driver driver = new Driver();
        driver.setBroadcastName(broadcastName);
        driver.setCountryCode(countryCode);
        driver.setDriverNumber(driverNumber);
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setHeadshotUrl(headshotUrl);
        driver.setMeetingKey(meetingKey);
        driver.setNameAcronym(nameAcronym);
        driver.setSessionKey(sessionKey);
        driver.setTeamColour(teamColour);
        driver.setTeamName(teamName);
        return driver;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public void setBroadcastName(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHeadshotUrl() {
        return headshotUrl;
    }

    public void setHeadshotUrl(String headshotUrl) {
        this.headshotUrl = headshotUrl;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }

    public String getNameAcronym() {
        return nameAcronym;
    }

    public void setNameAcronym(String nameAcronym) {
        this.nameAcronym = nameAcronym;
    }

    public Integer getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getTeamColour() {
        return teamColour;
    }

    public void setTeamColour(String teamColour) {
        this.teamColour = teamColour;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
