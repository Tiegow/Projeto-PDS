package org.project.easyf1.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "team", url = "https://api.openf1.org/v1/drivers")
public interface TeamClient {

}
