package org.project.easyf1.services;

import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.SessionClient;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.MeetingRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionClient sessionClient;
    private final SessionRepository sessionRepository;
    private final MeetingRepository meetingRepository;

    @Autowired
    public SessionService(SessionClient sessionClient, SessionRepository sessionRepository, MeetingRepository meetingRepository) {
        this.sessionClient = sessionClient;
        this.sessionRepository = sessionRepository;
        this.meetingRepository = meetingRepository;
    }

    @PostConstruct
    public void getNewSessions() {
        Session lastSession = sessionRepository.findFirstByOrderByEndDateDesc();

        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastSession != null) {
            startDate = lastSession.getStartDate();
        }

        String dateStartParam = startDate.toString();

        List<SessionDTO> newSessions = sessionClient.getSessionsAfter(dateStartParam);
        
        // Cache de meetings para evitar várias queries
        Map<Integer, Meeting> meetingMap = meetingRepository.findAll().stream()
            .collect(Collectors.toMap(Meeting::getMeetingKey, m -> m));

        List<Session> sessions = newSessions.stream()
            .map(dto -> {
                Meeting meeting = meetingMap.get(dto.getMeetingKey());
                return dto.getSession(meeting);
            })
            .filter(Objects::nonNull)
            .toList();

        sessionRepository.saveAll(sessions);
    }
}