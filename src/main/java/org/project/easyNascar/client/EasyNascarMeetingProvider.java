package org.project.easyNascar.client;

import java.util.List;

import org.project.framework.models.dto.MeetingDTO;
import org.project.framework.providers.MeetingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
public class EasyNascarMeetingProvider implements MeetingProvider {

    private final InnerEasyNascarMeetingClient client;

    @Autowired
    public EasyNascarMeetingProvider(InnerEasyNascarMeetingClient client) {
        this.client = client;
    }

    @Override
    public List<MeetingDTO> getMeetingsAfter(String dateStart) {
        return client.getMeetingsAfter(dateStart);
    }

    @FeignClient(name = "meeting", url = "")
    interface InnerEasyNascarMeetingClient {
        @GetMapping
        List<MeetingDTO> getMeetingsAfter(@RequestParam("date_start%3E") String dateStart);
    }
}
