package org.project.easyf1.services;

import org.project.easyf1.models.entity.Ranking;
import org.project.easyf1.repositories.RankingRepository;
import org.project.easyf1.services.Interfaces.LLMService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RankingService {

    private RankingRepository rankingRepository;

    private LLMService llmService;

    public RankingService(RankingRepository rankingRepository, LLMService llmService) {
        this.rankingRepository = rankingRepository;
        this.llmService = llmService;
    }

    public List<Ranking> getLastRanking(){

        Date gc = Date.valueOf(LocalDate.now());

        List<Ranking> rankings = rankingRepository.findAllByDateOrderByPoints(gc);

        if(!rankings.isEmpty()){
            return rankings;
        }

        HashMap<String, Integer> rankingLLM = llmService.RankingDrivers();

        for(String driver : rankingLLM.keySet()){
            Ranking ranking = new Ranking();
            ranking.setDriverName(driver);
            ranking.setPoints(rankingLLM.get(driver));
            ranking.setDate(gc);
            rankingRepository.save(ranking);
        }

        return rankingRepository.findAllByDateOrderByPoints(gc);
    }
}
