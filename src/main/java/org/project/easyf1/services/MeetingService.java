package org.project.easyf1.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.project.easyf1.client.MeetingClient;
import org.project.easyf1.models.dto.MeetingDTO;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MeetingService {

    private final MeetingClient meetingClient;
    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingClient meetingClient, MeetingRepository meetingRepository) {
        this.meetingClient = meetingClient;
        this.meetingRepository = meetingRepository;
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
}
