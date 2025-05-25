package org.project.easyf1.configuration;

import org.project.easyf1.client.MeetingClient;
import org.project.easyf1.client.SessionClient;
import org.project.easyf1.models.dto.MeetingDTO;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.MeetingRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MeetingInitializer {

    private MeetingRepository meetingRepository;

    private SessionRepository sessionRepository;

    private MeetingClient meetingClient;

    private SessionClient sessionClient;

    public MeetingInitializer(MeetingRepository meetingRepository, SessionRepository sessionRepository, MeetingClient meetingClient, SessionClient sessionClient) {
        this.meetingRepository = meetingRepository;
        this.sessionRepository = sessionRepository;
        this.meetingClient = meetingClient;
        this.sessionClient = sessionClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        Meeting lastMeeting = meetingRepository.findFirstByOrderByStartDateDesc();

        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastMeeting != null) {
            startDate = lastMeeting.getStartDate();
        }

        String dateStartParam = startDate.toString();

        List<MeetingDTO> newMeetings = meetingClient.getMeetingsAfter(dateStartParam);
        List<Meeting> meetings = newMeetings.stream().map(MeetingDTO::getMeeting).toList();

        List<Session> sessions = getAllSessions();

        meetingRepository.saveAll(meetings);
        sessionRepository.saveAll(sessions);
    }


    private List<Session> getAllSessions(){
        Session lastSession = sessionRepository.findFirstByOrderByEndDateDesc();

        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastSession != null) {
            startDate = lastSession.getStartDate();
        }

        String dateStartParam = startDate.toString();

        try {
            List<SessionDTO> newSessions = sessionClient.getSessionsAfter(dateStartParam);

            List<Session> sessions = newSessions.stream()
                    .map(SessionDTO::getSession)
                    .filter(Objects::nonNull)
                    .toList();

            return sessions;
        } catch (Exception e) {
            System.err.println("Erro ao buscar novas sessoes na inicialização");
        }

        return null;
    }
}