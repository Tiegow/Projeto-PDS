package org.project.framework.services;

import org.project.framework.models.dto.SessionDTO;
import org.project.framework.models.entity.Session;
import org.project.framework.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    private final TodaySessionHelper todaySessionProvider;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, TodaySessionHelper todaySessionProvider) {
        this.todaySessionProvider = todaySessionProvider;
        this.sessionRepository = sessionRepository;
    }

    public List<SessionDTO> getSessionsByMeeting(Integer meetingKey) {
        List<Session> sessions = sessionRepository.findAllByMeetingKey(meetingKey);
        return sessions.stream()
                .map(SessionDTO::new)
                .toList();
    }

    public SessionDTO getTodaySession() {
        return todaySessionProvider.getTodaySession();
    }
}