package org.project.easyf1.controllers.rest;

import java.util.List;

import org.project.easyf1.models.dto.MeetingDTO;
import org.project.easyf1.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("get")
    public ResponseEntity<List<MeetingDTO>> getByYear(@RequestParam Integer year) {
        List<MeetingDTO> meetings = meetingService.getMeetingsByYear(year);

        return ResponseEntity.ok(meetings);
    }
}
