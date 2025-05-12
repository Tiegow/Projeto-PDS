package org.project.easyf1.models.entity;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entidade JPA que representa um evento de corrida (Meeting).
 *
 * Cada instância desta classe corresponde a um registro na tabela "meetings" no banco de dados,
 * armazenando dados relacionados ao evento, como local, país, circuito, nome oficial e data de início.
 *
 * A chave primária é `meetingKey`, que identifica unicamente cada evento.
 */
@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @Column(name = "meeting_key")
    private Integer meetingKey;

    private String meetingName;
    private String meetingOfficialName;
    private String location;
    private Integer countryKey;
    private String countryCode;
    private String countryName;
    private Integer circuitKey;
    private String circuitShortName;
    private OffsetDateTime startDate;
    private Integer year;

    @OneToMany(mappedBy = "meeting")
    private List<Car> cars;

    @OneToMany(mappedBy = "meeting")
    private List<Session> sessions;

    public Meeting() {}

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

    public void setStartDate(OffsetDateTime dateStart) {
        this.startDate = dateStart;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
