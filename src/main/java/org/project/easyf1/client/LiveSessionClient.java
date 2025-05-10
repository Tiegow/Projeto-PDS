package org.project.easyf1.client;

import org.project.easyf1.models.dto.PositionDTO;
import org.project.easyf1.models.dto.WeatherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "liveSession", url = "https://api.openf1.org")
public interface LiveSessionClient {

    @GetMapping("/v1/weather")
    WeatherDTO[] getWeather(@RequestParam("session_key") String sessionKey);

    @GetMapping("/v1/position")
    PositionDTO[] getPositions(@RequestParam("session_key") String sessionKey);
}
