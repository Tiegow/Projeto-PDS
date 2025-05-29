package org.project.easyf1.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.easyf1.models.entity.Team;
import org.project.easyf1.models.entity.TeamDetail;

public class TeamDetailDTO {

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_base")
    private String teamBase;

    @JsonProperty("team_principal")
    private String teamPrincipal;

    @JsonProperty("team_full_name")
    private String TeamFullName;

    @JsonProperty("team_championship_position")
    private Integer teamChampionshipPosition;

    @JsonProperty("victories")
    private Integer victories;

    @JsonProperty("team_colour")
    private String teamColour;

    @JsonProperty("team_points")
    private Integer teamPoints;

    @JsonProperty("driver1")
    private DriverDTO driver1;
    @JsonProperty("driver2")
    private DriverDTO driver2;

    public TeamDetailDTO() {}

    public TeamDetailDTO(String teamName, String teamBase, String teamPrincipal,
                          Integer teamChampionshipPosition, Integer victories, String teamColour, Integer teamPoints
    , DriverDTO driver1, DriverDTO driver2) {
        this.teamName = teamName;
        this.teamBase = teamBase;
        this.teamPrincipal = teamPrincipal;
        this.teamChampionshipPosition = teamChampionshipPosition;
        this.victories = victories;
        this.teamColour = teamColour;
        this.teamPoints = teamPoints;


        this.driver1 = driver1;
        this.driver2 = driver2;
    }

    public TeamDetailDTO(TeamDetail team){
        this( team.getTeamName(),
                team.getTeamBase(),
                team.getTeamPrincipal(),
                team.getTeamChampionshipPosition(),
                team.getVictories(),
                team.getTeamColour(),
                team.getTeamPoints()
                ,team.getDriver1(),
                team.getDriver2());
    }

    public TeamDetail getTeam() {
        TeamDetail team = new TeamDetail();
        team.setTeamName(this.getTeamName());
        team.setTeamBase(this.getTeamBase());
        team.setTeamPrincipal(this.getTeamPrincipal());
        team.setTeamChampionshipPosition(this.getTeamChampionshipPosition());
        team.setVictories(this.getVictories());
        team.setTeamColour(this.getTeamColour());
        team.setTeamPoints(this.getTeamPoints());
        return team;
    }

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

    public String getTeamFullName() {
        return TeamFullName;
    }

    public void setTeamFullName(String teamFullName) {
        TeamFullName = teamFullName;
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
