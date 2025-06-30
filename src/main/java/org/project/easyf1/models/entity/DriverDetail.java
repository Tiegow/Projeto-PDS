package org.project.easyf1.models.entity;


import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "driver_details")
public class DriverDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int driverNumber;
    private String countryCode;
    private String headshotUrl;

    // Dados da equipe
    private String teamName;
    private String teamColour;
    private String teamFullName;
    private String teamBase;
    private String teamPrincipal;
    private int teamChampionshipPosition;

    // Desempenho
    private int championshipPosition;
    private int points;
    private int bestPosition;
    private int worstPosition;
    private int victories;

    // Carro
    private String carModel;
    private String engine;
    private int powerHp;
    private int weightKg;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(int driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getHeadshotUrl() {
        return headshotUrl;
    }

    public void setHeadshotUrl(String headshotUrl) {
        this.headshotUrl = headshotUrl;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamColour() {
        return teamColour;
    }

    public void setTeamColour(String teamColour) {
        this.teamColour = teamColour;
    }

    public String getTeamFullName() {
        return teamFullName;
    }

    public void setTeamFullName(String teamFullName) {
        this.teamFullName = teamFullName;
    }

    public String getTeamBase() {
        return teamBase;
    }

    public void setTeamBase(String teamBase) {
        this.teamBase = teamBase;
    }

    public String getTeamPrincipal() {
        return teamPrincipal;
    }

    public void setTeamPrincipal(String teamPrincipal) {
        this.teamPrincipal = teamPrincipal;
    }

    public int getTeamChampionshipPosition() {
        return teamChampionshipPosition;
    }

    public void setTeamChampionshipPosition(int teamChampionshipPosition) {
        this.teamChampionshipPosition = teamChampionshipPosition;
    }

    public int getChampionshipPosition() {
        return championshipPosition;
    }

    public void setChampionshipPosition(int championshipPosition) {
        this.championshipPosition = championshipPosition;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(int bestPosition) {
        this.bestPosition = bestPosition;
    }

    public int getWorstPosition() {
        return worstPosition;
    }

    public void setWorstPosition(int worstPosition) {
        this.worstPosition = worstPosition;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getPowerHp() {
        return powerHp;
    }

    public void setPowerHp(int powerHp) {
        this.powerHp = powerHp;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
