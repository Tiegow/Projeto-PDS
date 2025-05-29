package org.project.easyf1.services.Interfaces;

import java.util.HashMap;

public interface LLMService {

    String WhosGonnaWin(Integer meetingID);

    HashMap<String, Integer> RankingDrivers();

    HashMap<String, Integer> RankingTeams();

    String detailsDriver(Integer driverNumber);

    String DetailsTeam(String teamName);
}
