package org.project.easyf1.services;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.project.easyf1.exception.NoSessionTodayException;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.stereotype.Component;

@Component
public class TodaySessionProvider {
    private final SessionRepository sessionRepository;

    public TodaySessionProvider(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionDTO getTodaySession() throws NoSessionTodayException {
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
