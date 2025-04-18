package org.project.easyf1.models.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.Team;

import java.util.List;

public class TeamDTO {

    @JsonProperty("team_name")
    @JsonAlias("teamName")
    private String teamName;

    @JsonProperty("drivers_list")
    @JsonAlias("driversList")
    private List<Driver> driversList;

    // @JsonProperty("cars_list")
    // @JsonAlias("carsList")
    // private List<Car> carsList;

    @JsonProperty("team_points")
    @JsonAlias("teamPoints")
    private Integer teamPoints;

    public TeamDTO() {

    }

    public Team getTeam() {

        Team team = new Team();

        team.setTeamName(teamName);
        team.setTeamPoints(teamPoints);
        return team;
    }


    public String getTeamName() {return teamName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    public Integer getTeamPoints() {return teamPoints;}
    public void setTeamPoints(Integer teamPoints) {this.teamPoints = teamPoints;}

}
