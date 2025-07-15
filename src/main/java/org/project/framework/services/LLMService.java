package org.project.framework.services;

public interface LLMService {

    String WhosGonnaWin(Integer meetingID);

    public String callGeminiAPI(String prompt);
}
