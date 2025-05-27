package org.project.easyf1.models.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    private Integer firstDriverNumber;
    private Integer secondDriverNumber;

    private Integer teamPoints;
    private String teamColor;

    public Team() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


    public Integer getFirstDriverNumber() {
        return firstDriverNumber;
    }
    public void setFirstDriverNumber(Integer firstDriverNumber) {
        this.firstDriverNumber = firstDriverNumber;
    }

    public Integer getSecondDriverNumber() {
        return secondDriverNumber;
    }
    public void setSecondDriverNumber(Integer secondDriverNumber) {
        this.secondDriverNumber = secondDriverNumber;
    }

    public Integer getTeamPoints() {
        return teamPoints;
    }
    public void setTeamPoints(Integer teamPoints) {
        this.teamPoints = teamPoints;
    }

    public String getTeamColor() {
        return teamColor;
    }
    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }


}