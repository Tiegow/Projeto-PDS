package org.project.easyf1.models.dto;

import java.time.OffsetDateTime;

import org.project.easyf1.models.entity.Meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetingDTO {

    @JsonProperty("meeting_key")
    private Integer meetingKey;

    @JsonProperty("meeting_name")
    private String meetingName;

    @JsonProperty("meeting_official_name")
    private String meetingOfficialName;

    @JsonProperty("location")
    private String location;

    @JsonProperty("country_key")
    private Integer countryKey;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("circuit_key")
    private Integer circuitKey;

    @JsonProperty("circuit_short_name")
    private String circuitShortName;

    @JsonProperty("date_start")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;

    @JsonProperty("year")
    private Integer year;

    public MeetingDTO() {}

    public MeetingDTO(Meeting meeting) {
        this.meetingKey = meeting.getMeetingKey();
        this.meetingName = meeting.getMeetingName();
        this.meetingOfficialName = meeting.getMeetingOfficialName();
        this.location = meeting.getLocation();
        this.countryKey = meeting.getCountryKey();
        this.countryCode = meeting.getCountryCode();
        this.countryName = meeting.getCountryName();
        this.circuitKey = meeting.getCircuitKey();
        this.circuitShortName = meeting.getCircuitShortName();
        this.startDate = meeting.getStartDate();
        this.year = meeting.getYear();
    }

    public Meeting getMeeting() {
        Meeting meeting = new Meeting();
        meeting.setMeetingKey(this.meetingKey);
        meeting.setMeetingName(this.meetingName);
        meeting.setMeetingOfficialName(this.meetingOfficialName);
        meeting.setLocation(this.location);
        meeting.setCountryKey(this.countryKey);
        meeting.setCountryCode(this.countryCode);
        meeting.setCountryName(this.countryName);
        meeting.setCircuitKey(this.circuitKey);
        meeting.setCircuitShortName(this.circuitShortName);
        meeting.setStartDate(this.startDate);
        meeting.setYear(this.year);
        return meeting;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingOfficialName() {
        return meetingOfficialName;
    }

    public void setMeetingOfficialName(String meetingOfficialName) {
        this.meetingOfficialName = meetingOfficialName;
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

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

