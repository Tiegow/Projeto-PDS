package org.project.easyf1.models.entity;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entidade JPA que representa uma sessão (como treinos, classificatórias ou corrida) de um evento (meeting).
 *
 * Cada instância desta classe corresponde a um registro na tabela "sessions" no banco de dados,
 * armazenando informações detalhadas como localização, circuito, tipo de sessão e datas.
 *
 * A chave primária é `sessionKey`, e cada sessão está associada a um `meetingKey`.
 */
@Entity
@Table(name = "sessions")
public class Session {

    @Id
    private Integer sessionKey;

    private String location;
    private Integer countryKey;
    private String countryCode;
    private String countryName;
    private Integer circuitKey;
    private String circuitShortName;
    private String sessionType;
    private String sessionName;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    @OneToMany(mappedBy = "session")
    private List<Car> cars;

    @OneToMany(mappedBy = "session")
    private List<Driver> drivers;

    @ManyToOne
    @JoinColumn(name = "meeting_key")
    private Meeting meeting;

    public Session() {}

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

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
