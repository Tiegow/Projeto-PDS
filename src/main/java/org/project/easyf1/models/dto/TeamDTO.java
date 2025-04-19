package org.project.easyf1.models.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.Team;

import java.util.List;

public class TeamDTO {

    @JsonProperty("team_name")
    @JsonAlias("teamName")
    private String teamName;

    @JsonProperty("first_driver_number")
    @JsonAlias("firstDriverNumber")
    private Integer firstDriverNumber;

    @JsonProperty("second_driver_number")
    @JsonAlias("secondDriverNumber")
    private Integer secondDriverNumber;

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
        team.setFirstDriverNumber(firstDriverNumber);
        team.setSecondDriverNumber(secondDriverNumber);
        team.setTeamPoints(teamPoints);
        return team;
    }


    public String getTeamName() {return teamName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    public Integer getFirstDriverNumber() {return firstDriverNumber;}
    public void setFirstDriverNumber(Integer firstDriverNumber) {this.firstDriverNumber = firstDriverNumber;}

    public Integer getSecondDriverNumber() {return secondDriverNumber;}
    public void setSecondDriverNumber(Integer secondDriverNumber) {this.secondDriverNumber = secondDriverNumber;}

    public Integer getTeamPoints() {return teamPoints;}
    public void setTeamPoints(Integer teamPoints) {this.teamPoints = teamPoints;}

}
