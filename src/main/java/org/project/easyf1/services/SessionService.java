package org.project.easyf1.services;

import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.SessionClient;
import org.project.easyf1.exception.NoSessionTodayException;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.MeetingRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Service
public class SessionService {

    private final SessionClient sessionClient;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionClient sessionClient, SessionRepository sessionRepository, MeetingRepository meetingRepository) {
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
    
        List<SessionDTO> newSessions = sessionClient.getSessionsAfter(dateStartParam);
    
        List<Session> sessions = newSessions.stream()
            .map(SessionDTO::getSession) 
            .filter(Objects::nonNull)
            .toList();
    
        sessionRepository.saveAll(sessions);
    }

    public List<SessionDTO> getSessionsByMeeting(Integer meetingKey) {
        List<Session> sessions = sessionRepository.findAllByMeetingKey(meetingKey);
        return sessions.stream()
                .map(SessionDTO::new)
                .toList();
    }

    public SessionDTO getTodaySession() {
        ZoneOffset zoneOffset = ZoneOffset.of("-03:00"); 
        OffsetDateTime now = OffsetDateTime.now(zoneOffset);
        OffsetDateTime startOfDay = now.toLocalDate().atStartOfDay().atOffset(zoneOffset);
        OffsetDateTime startOfNextDay = startOfDay.plusDays(1);

        Session session = sessionRepository.findTodaySession(startOfDay, startOfNextDay, now);

        if (session == null) {
            throw new NoSessionTodayException();
        }

        return new SessionDTO(session);
    }
}