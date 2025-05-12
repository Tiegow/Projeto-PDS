package org.project.easyf1.models.entity;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "drivers")
public class Driver implements Comparable<Driver> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String broadcastName;
    private String countryCode;
    private Integer driverNumber;

    private String firstName;
    private String lastName;

    private String headshotUrl;

    private String nameAcronym;

    @ManyToOne
    @JoinColumn(name = "meeting_key")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "session_key")
    private Session session;

    private String teamColour;

    private String teamName;

    public Driver() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public String getNameAcronym() {
        return nameAcronym;
    }

    public void setNameAcronym(String nameAcronym) {
        this.nameAcronym = nameAcronym;
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

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public int compareTo(Driver o) {
        if (this.id != 0) {
            if (this.id > o.id) {
                return 1;
            } else if (this.id < o.id) {
                return -1;
            }
            return 0;
        } else {
            if(this.broadcastName.equals(o.broadcastName) && this.driverNumber.equals(o.driverNumber)) {
                return 0;
            }
        }
        return 0;
    }
}
