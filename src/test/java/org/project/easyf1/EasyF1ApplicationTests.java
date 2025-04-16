package org.project.easyf1;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.MeetingRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EasyF1ApplicationTests {

	@Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    void testFindLastSession() {
        Session session = sessionRepository.findFirstByOrderByEndDateDesc();
        System.out.println("Sessão retornada no teste: " + session.getSessionKey());
        assertNotNull(session);
    }

    @Test
    void testMeetingRelation() {
        Session session = sessionRepository.findFirstByOrderByEndDateDesc();
        Meeting meeting = session.getMeeting();
        String key = meeting.getMeetingKey().toString();
        System.out.println("Sessao " + session.getSessionKey() + " tem a reunião " + meeting.getMeetingKey());
    }
}
