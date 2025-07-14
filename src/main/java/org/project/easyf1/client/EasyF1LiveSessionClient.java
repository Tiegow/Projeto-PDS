package org.project.easyf1.client;

import java.util.List;

import org.project.easyf1.models.dto.liveSession.RaceControlDTOImpl;
import org.project.framework.models.dto.PositionDTO;
import org.project.framework.models.dto.WeatherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "liveSession", url = "https://api.openf1.org")
public interface EasyF1LiveSessionClient {

    @GetMapping("/v1/weather")
    List<WeatherDTO> getWeather(@RequestParam("session_key") Integer sessionKey);

    @GetMapping("/v1/position")
    List<PositionDTO> getPositions(@RequestParam("session_key") Integer sessionKey);

    @GetMapping("/v1/race_control")
    List<RaceControlDTOImpl> getRaceEvents(@RequestParam("session_key") Integer sessionKey);
}
