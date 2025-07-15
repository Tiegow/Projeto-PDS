package org.project.easyf1.client;


import org.project.easyf1.models.dto.TeamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "team", url = "https://api.openf1.org/v1/drivers")
public interface TeamClient {

}
