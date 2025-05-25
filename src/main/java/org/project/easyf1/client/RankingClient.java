package org.project.easyf1.client;


import org.project.easyf1.configuration.CustomFeignConfig;
import org.project.easyf1.models.dto.RankingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ranking", url = "https://v1.formula-1.api-sports.io/", configuration =  CustomFeignConfig.class)
public interface RankingClient {

    @GetMapping(value = "/rankings/drivers")
    String getDriversRankings();

    @GetMapping(value = "/rankings/teams")
    String getTeamRankings();
}
