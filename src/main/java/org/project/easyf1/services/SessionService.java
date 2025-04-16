package org.project.easyf1.services;

import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.SessionClient;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

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
    public void getNewSessions() {
        Session lastSession = sessionRepository.findFirstByOrderByEndDateDesc();

        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastSession != null) {
            startDate = lastSession.getStartDate();
        }

        String dateStartParam = startDate.toString();
        System.out.println("Date Start: " + dateStartParam);

        List<SessionDTO> newSessions = sessionClient.getSessionsAfter(dateStartParam);
        System.out.println("Sessions returned: " + newSessions.size());

        List<Session> sessions = newSessions.stream()
                .map(SessionDTO::getSession)
                .toList();

        sessionRepository.saveAll(sessions);
    }
}