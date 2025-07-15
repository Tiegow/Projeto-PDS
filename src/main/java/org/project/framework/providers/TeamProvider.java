package org.project.framework.providers;

import java.util.List;

import org.project.framework.models.dto.TeamDTO;

public interface TeamProvider {
    TeamDTO getTeam(Integer driverNumber, Integer sessionKey);

    List<TeamDTO> getTeams(Integer sessionKey);
}
