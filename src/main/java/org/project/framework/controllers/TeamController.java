package org.project.framework.controllers;

import org.project.easyf1.models.entity.Team; 
import org.project.framework.services.TeamService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams() {
        try {
            List<Team> teams = teamService.getAllTeams();
            if (teams == null || teams.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(teams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/details")
    public ResponseEntity<Team> getTeamDetails(@RequestParam("team_name") String teamName) {
        System.err.println("Endpoint /api/teams/details chamado para: " + teamName + " - Implementação pendente no TeamService/Repository");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
