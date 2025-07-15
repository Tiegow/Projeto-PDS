package org.project.framework.providers;

import java.util.List;

import org.project.framework.models.dto.MeetingDTO;
import org.springframework.web.bind.annotation.RequestParam;


public interface MeetingProvider {

    List<MeetingDTO> getMeetingsAfter(@RequestParam("date_start%3E") String dateStart);
}
