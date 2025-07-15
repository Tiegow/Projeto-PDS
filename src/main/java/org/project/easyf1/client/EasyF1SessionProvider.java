package org.project.easyf1.client;

import java.util.List;

import org.project.framework.models.dto.SessionDTO;
import org.project.framework.providers.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class EasyF1SessionProvider implements SessionProvider{

    private final InnerEasyF1SessionClient client;

    @Autowired
    public EasyF1SessionProvider(InnerEasyF1SessionClient client) {
        this.client = client;
    }

    @Override
    public List<SessionDTO> getSessionsAfter(String dateStart) {
        return client.getSessionsAfter(dateStart);
    }

    @Override
    public List<SessionDTO> getSessionByMeeting(Integer meetingKey) {
        return client.getSessionByMeeting(meetingKey);
    }

    @FeignClient(name = "session", url = "https://api.openf1.org/v1/sessions")
    interface InnerEasyF1SessionClient {
        @GetMapping
        List<SessionDTO> getSessionsAfter(@RequestParam("date_start%3E") String dateStart);

        @GetMapping
        List<SessionDTO> getSessionByMeeting(@RequestParam("meeting_key") Integer meetingKey);
    }
}