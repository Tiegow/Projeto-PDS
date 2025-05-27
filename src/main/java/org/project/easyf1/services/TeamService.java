package org.project.easyf1.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.project.easyf1.models.dto.TeamDTO;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.models.entity.Team;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

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