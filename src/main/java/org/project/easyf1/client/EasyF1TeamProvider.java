package org.project.easyf1.client;

import java.util.List;

import org.project.framework.models.dto.TeamDTO;
import org.project.framework.providers.TeamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class EasyF1TeamProvider implements TeamProvider{

    private final InnerEasyF1TeamClient client;

    @Autowired
    public EasyF1TeamProvider(InnerEasyF1TeamClient client) {
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
    
    @FeignClient(name = "team", url = "https://api.openf1.org/v1/teams")
    interface InnerEasyF1TeamClient {
        @GetMapping("/{driverNumber}")
        TeamDTO getTeam(@PathVariable("driverNumber") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);

        @GetMapping()
        List<TeamDTO> getTeams(@RequestParam("session_key") Integer sessionKey);
    }
    
}
