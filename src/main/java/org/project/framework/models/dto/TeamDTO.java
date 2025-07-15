package org.project.framework.models.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.framework.models.entity.Team;

public class TeamDTO {

    private Long id;

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

    @JsonProperty("team_color")
    @JsonAlias("teamColor")
    private String teamColor;

    public TeamDTO() {
        
    }

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.firstDriverNumber = team.getFirstDriverNumber();
        this.secondDriverNumber = team.getSecondDriverNumber();
        this.teamName = team.getTeamName();
        this.teamPoints = team.getTeamPoints();
        this.teamColor = team.getTeamColor();
    }

    public Team getTeam() {

        Team team = new Team();

        team.setId(id);
        team.setTeamName(teamName);
        team.setFirstDriverNumber(firstDriverNumber);
        team.setSecondDriverNumber(secondDriverNumber);
        team.setTeamPoints(teamPoints);
        team.setTeamColor(teamColor);
        return team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {return teamName;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    public Integer getFirstDriverNumber() {return firstDriverNumber;}
    public void setFirstDriverNumber(Integer firstDriverNumber) {this.firstDriverNumber = firstDriverNumber;}

    public Integer getSecondDriverNumber() {return secondDriverNumber;}
    public void setSecondDriverNumber(Integer secondDriverNumber) {this.secondDriverNumber = secondDriverNumber;}

    public Integer getTeamPoints() {return teamPoints;}
    public void setTeamPoints(Integer teamPoints) {this.teamPoints = teamPoints;}

    public String getTeamColor() {return teamColor;}
    public void setTeamColor(String teamColor) {this.teamColor = teamColor;}
}
