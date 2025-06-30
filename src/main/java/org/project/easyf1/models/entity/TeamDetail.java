package org.project.easyf1.models.entity;

import jakarta.persistence.*;
import org.project.easyf1.models.dto.DriverDTO;

import java.sql.Date;

@Entity
@Table(name = "team_detail")
public class TeamDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private String teamBase;
    private String teamPrincipal;
    private Integer teamChampionshipPosition;
    private Integer victories;
    private String teamColour;

    private Integer teamPoints;

    @Transient
    private DriverDTO driver1;
    @Transient
    private DriverDTO driver2;

    private Date date;

    public TeamDetail() {}

    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
    public Integer getTeamChampionshipPosition() {
        return teamChampionshipPosition;
    }
    public void setTeamChampionshipPosition(Integer teamChampionshipPosition) {
        this.teamChampionshipPosition = teamChampionshipPosition;
    }
    public Integer getVictories() {
        return victories;
    }
    public void setVictories(Integer victories) {
        this.victories = victories;
    }
    public String getTeamColour() {
        return teamColour;
    }
    public void setTeamColour(String teamColour) {
        this.teamColour = teamColour;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTeamPoints() {
        return teamPoints;
    }

    public void setTeamPoints(Integer teamPoints) {
        this.teamPoints = teamPoints;
    }

    public DriverDTO getDriver1() {
        return driver1;
    }

    public void setDriver1(DriverDTO driver1) {
        this.driver1 = driver1;
    }

    public DriverDTO getDriver2() {
        return driver2;
    }

    public void setDriver2(DriverDTO driver2) {
        this.driver2 = driver2;
    }
}
