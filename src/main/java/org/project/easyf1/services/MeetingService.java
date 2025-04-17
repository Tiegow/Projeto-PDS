package org.project.easyf1.services;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.project.easyf1.client.MeetingClient;
import org.project.easyf1.models.dto.MeetingDTO;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.MeetingRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MeetingService {

    private final MeetingClient meetingClient;
    private final MeetingRepository meetingRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public MeetingService(MeetingClient meetingClient, MeetingRepository meetingRepository, SessionRepository sessionRepository) {
        this.meetingClient = meetingClient;
        this.meetingRepository = meetingRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostConstruct
    public void getNewMeetings() {
        Meeting lastMeeting = meetingRepository.findFirstByOrderByStartDateDesc();

        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastMeeting != null) {
            startDate = lastMeeting.getStartDate();
        }

        String dateStartParam = startDate.toString();

        List<MeetingDTO> newMeetings = meetingClient.getMeetingsAfter(dateStartParam);

        List<Meeting> meetings = newMeetings.stream()
                .map(MeetingDTO::getMeeting)
                .toList();

        meetingRepository.saveAll(meetings);
    }

    public List<MeetingDTO> getMeetingsByYear(Integer year) {
        List<Meeting> meetings = meetingRepository.findAllByYear(year);
        
        // Mapeia para DTO
        List<MeetingDTO> dtos = meetings.stream()
            .map(meeting -> {
                MeetingDTO dto = new MeetingDTO(meeting);
                Session lastSession = sessionRepository.findFirstByMeetingKeyOrderByEndDateDesc(meeting.getMeetingKey());

                if (lastSession != null) {
                    dto.setEndDate(lastSession.getEndDate());
                }

                return dto;
            })
            .collect(Collectors.toList());

        // Mais recentes para o início
        Collections.reverse(dtos);

        return dtos;
    }
}
