package org.project.framework.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.project.framework.services.LLMService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/llm")
public class LLMController {

    private LLMService llmService;

    public LLMController(LLMService llmService) {
        this.llmService = llmService;
    }

    @GetMapping("who")
    public ResponseEntity<String> getWhosGonnaWin(@RequestParam Integer meetingKey){
        return ResponseEntity.ok(llmService.WhosGonnaWin(meetingKey));
    }
}
