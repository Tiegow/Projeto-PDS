package org.project.easyf1.controllers.rest;

import org.project.easyf1.services.LiveSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/liveSession")
public class LiveSessionController {
    private final LiveSessionService liveSessionService;

    public LiveSessionController(LiveSessionService liveSessionService) {
        this.liveSessionService = liveSessionService;
    }

    @PostMapping("/sendLatest")
    public ResponseEntity<Void> sendLatest() {
        liveSessionService.sendWeather();
        liveSessionService.sendPositions();
        
        return ResponseEntity.ok().build();
    }
}