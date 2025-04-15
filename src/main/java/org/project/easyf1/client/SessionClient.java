package org.project.easyf1.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "session", url = "https://api.openf1.org/v1/sessions")
public interface SessionClient {

}
