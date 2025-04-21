package org.project.easyf1.client;

import java.util.List;

import org.project.easyf1.models.dto.MeetingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Client Feign responsável por consumir a API externa do OpenF1 para recuperar dados de meetings.
 *
 * Este client envia requisições HTTP para o endpoint remoto e retorna os dados como {@link MeetingDTO}.
 */
@FeignClient(name = "meeting", url = "https://api.openf1.org")
public interface MeetingClient {

    @GetMapping("/v1/meetings")
    List<MeetingDTO> getMeetingsAfter(@RequestParam("date_start%3E") String dateStart);
}
