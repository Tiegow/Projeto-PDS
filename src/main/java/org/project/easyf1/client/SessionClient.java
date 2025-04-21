package org.project.easyf1.client;

import java.util.List;

import org.project.easyf1.models.dto.SessionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Client Feign responsável por consumir a API externa do OpenF1 para recuperar sessões de corridas.
 *
 * Este client envia requisições HTTP para o endpoint remoto e retorna os dados mapeados como {@link SessionDTO}.
 */
@FeignClient(name = "session", url = "https://api.openf1.org")
public interface SessionClient {

    @GetMapping("/v1/sessions")
    List<SessionDTO> getSessionsAfter(@RequestParam("date_start%3E") String dateStart);
}
