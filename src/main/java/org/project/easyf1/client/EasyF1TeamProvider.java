package org.project.easyf1.client;


import org.project.framework.providers.TeamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
public interface EasyF1TeamProvider /*implements TeamProvider*/{
/*
    private final EasyF1TeamClient easyf1Team;

    @Autowired
    public EasyF1TeamProvider(EasyF1TeamClient easyf1Team)  {this.easyf1Team = easyf1Team;}

*/
    @FeignClient(name = "team", url = "https://api.openf1.org/v1/drivers")
    interface EasyF1TeamClient {


    }
}
