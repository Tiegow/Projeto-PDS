package org.project.framework.services;

import org.project.framework.models.entity.Team;
import org.project.framework.models.dto.TeamDTO;
import org.project.framework.repositories.DriverRepository;
import org.project.framework.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final DriverRepository driverRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository,  DriverRepository driverRepository) {
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Team getTeamByName(String name) {
        return teamRepository.findAll().stream()
                           .filter(t -> t.getTeamName().equalsIgnoreCase(name))
                           .findFirst()
                           .orElse(null);
    }

    public TeamDTO detailTeam(String teamName) {
        TeamDTO team = new TeamDTO(getTeamByName(teamName));
        List<Integer> ids = driverRepository.findByTeamName(teamName);

        if(ids != null && ids.size() == 1) {
            team.setFirstDriverNumber(ids.getFirst());
        } else if (ids != null && ids.size() == 2)  {
            team.setFirstDriverNumber(ids.get(0));
            team.setSecondDriverNumber(ids.get(1));
        }
        return team;
    }
}