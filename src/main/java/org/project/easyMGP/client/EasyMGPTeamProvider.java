package org.project.easyMGP.client;

import java.util.List;

import org.project.framework.models.dto.TeamDTO;
import org.project.framework.providers.TeamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class EasyMGPTeamProvider implements TeamProvider{

    private final InnerEasyMGPTeamClient client;

    @Autowired
    public EasyMGPTeamProvider(InnerEasyMGPTeamClient client) {
        this.client = client;
    }

    @Override
    public TeamDTO getTeam(Integer driverNumber, Integer sessionKey) {
        return client.getTeam(driverNumber, sessionKey);
    }

    @Override
    public List<TeamDTO> getTeams(Integer sessionKey) {
        return client.getTeams(sessionKey);
    }
    
    @FeignClient(name = "team", url = "")
    interface InnerEasyMGPTeamClient {
        @GetMapping("/{driverNumber}")
        TeamDTO getTeam(@PathVariable("driverNumber") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);

        @GetMapping()
        List<TeamDTO> getTeams(@RequestParam("session_key") Integer sessionKey);
    }
    
}
