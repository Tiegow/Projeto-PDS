package org.project.framework.providers;

import java.util.List;

import org.project.framework.models.dto.MeetingDTO;
import org.project.framework.models.dto.SessionDTO;
import org.springframework.web.bind.annotation.RequestParam;


public interface SessionProvider {

    List<SessionDTO> getSessionsAfter(@RequestParam("date_start%3E") String dateStart);

    List<SessionDTO> getSessionByMeeting(@RequestParam("meeting_key")Integer meetingKey);
}
