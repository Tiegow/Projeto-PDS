package org.project.easyf1.client;

import java.util.List;

import org.project.framework.models.dto.MeetingDTO;
import org.project.framework.providers.MeetingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
public class EasyF1MeetingProvider implements MeetingProvider{

    private final InnerEasyF1MeetingClient client;

    @Autowired
    public EasyF1MeetingProvider(InnerEasyF1MeetingClient client) {
        this.client = client;
    }

    @Override
    public List<MeetingDTO> getMeetingsAfter(String dateStart) {
        return client.getMeetingsAfter(dateStart);
    }

    @FeignClient(name = "meeting", url = "https://api.openf1.org/v1/meetings")
    interface InnerEasyF1MeetingClient {
        @GetMapping
        List<MeetingDTO> getMeetingsAfter(@RequestParam("date_start%3E") String dateStart);
    }
}
