package org.project.framework.controllers;

import java.util.List;

import org.project.framework.models.dto.MeetingDTO;
import org.project.framework.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST responsável por expor endpoints relacionados aos eventos (meetings) de Fórmula 1.
 *
 * <p><strong>Responsabilidades principais:</strong></p>
 * <ul>
 *     <li>Oferecer um ponto de acesso externo para recuperar os eventos de Fórmula 1 organizados por ano.</li>
 * </ul>
 *
 * <p><strong>Funcionamento geral:</strong></p>
 * <ul>
 *     <li>O endpoint {@code GET /api/meetings/get?year=} permite obter todos os eventos realizados no ano informado, juntamente com informações complementares como a data da última sessão associada.</li>
 * </ul>
 *
 * <p>Este controlador atua como ponte entre a camada de serviço ({@link MeetingService}) e o cliente (frontend ou consumidor externo), delegando as operações para a lógica de negócio apropriada e retornando as respostas formatadas.</p>
 */
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
