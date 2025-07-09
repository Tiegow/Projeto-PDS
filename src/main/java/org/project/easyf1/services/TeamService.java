package org.project.easyf1.services;

import org.project.easyf1.models.entity.Team;
import org.project.framework.repositories.SessionRepository;
import org.project.framework.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {

    private final SessionRepository sessionRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(SessionRepository sessionRepository, TeamRepository teamRepository) {
        this.sessionRepository = sessionRepository;
        this.teamRepository = teamRepository;
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

}