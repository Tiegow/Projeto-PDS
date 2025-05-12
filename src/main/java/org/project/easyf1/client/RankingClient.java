package org.project.easyf1.client;


import org.project.easyf1.models.dto.RankingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ranking", url = "https://v1.formula-1.api-sports.io/")
public interface RankingClient {

    @GetMapping(value = "/rankings/drivers", headers = {
            "x-rapidapi-host=v1.formula-1.api-sports.io",
            "x-rapidapi-key=${rapidapi.key}"
    })
    RankingDTO getDriversRankings();

    @GetMapping(value = "/rankings/teams", headers = {
            "x-rapidapi-host=v1.formula-1.api-sports.io",
            "x-rapidapi-key=${rapidapi.key}",
            "x-apisports-key=${apisports.key}"
    })
    String getTeamRankings();
}
