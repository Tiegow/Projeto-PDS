package org.project.easyf1.controllers.rest;

import java.util.List;

import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sessions")
public class SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping(value = "get", params = "meetingKey")
    public ResponseEntity<List<SessionDTO>> getSessionsByMeeting(@RequestParam Integer meetingKey) {
        List<SessionDTO> sessions = sessionService.getSessionsByMeeting(meetingKey);

        return ResponseEntity.ok(sessions);
    }

    @GetMapping("get/today")
    public ResponseEntity<SessionDTO> getTodaySession() {
        SessionDTO session = sessionService.getTodaySession();

        return ResponseEntity.ok(session);
    }
}
