package org.project.easyf1.client;

import java.util.List;

import org.project.easyf1.models.dto.SessionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "session", url = "https://api.openf1.org")
public interface SessionClient {

    @GetMapping("/v1/sessions")
    List<SessionDTO> getSessionsAfter(@RequestParam("date_start%3E") String dateStart);
}
