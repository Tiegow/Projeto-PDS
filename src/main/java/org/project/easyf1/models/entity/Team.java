package org.project.easyf1.models.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private Integer teamPoints;

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

    public Integer getTeamPoints() {
        return teamPoints;
    }
    public void setTeamPoints(Integer teamPoints) {
        this.teamPoints = teamPoints;
    }



}