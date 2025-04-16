package org.project.easyf1.services;

import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.SessionClient;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

@Service
public class SessionService {

    private final SessionClient sessionClient;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionClient sessionClient, SessionRepository sessionRepository) {
        this.sessionClient = sessionClient;
        this.sessionRepository = sessionRepository;
    }

    @PostConstruct
    public void getNewsSessions() throws UnsupportedEncodingException {
        Session session = sessionRepository.findFirstByOrderByEndDateDesc();

        GregorianCalendar startDate = new GregorianCalendar(2000, GregorianCalendar.JANUARY, 1);
        GregorianCalendar endDate = new GregorianCalendar();
        SimpleDateFormat dataFormater = new SimpleDateFormat("yyyy-MM-dd");

        if(session != null){
            startDate = session.getStartDate();
        }

        String url = "https://api.openf1.org/v1/sessions?date_start>=" + dataFormater.format(startDate.getTime()) + "&date_end<=" + dataFormater.format(endDate.getTime());

        RestTemplate restTemplate = new RestTemplate();

        List<Session> sessions =  Objects.requireNonNull(restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SessionDTO>>() {
                }
        ).getBody()).stream().map(SessionDTO::getSession).toList();

        sessionRepository.saveAll(sessions);
    }
}
