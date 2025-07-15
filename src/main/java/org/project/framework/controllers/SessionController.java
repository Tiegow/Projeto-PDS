package org.project.framework.controllers;

import java.util.List;

import org.project.framework.models.dto.SessionDTO;
import org.project.framework.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST responsável por expor endpoints relacionados às sessões de eventos de Fórmula 1 (como treinos, classificações e corridas).
 *
 * <p><strong>Responsabilidades principais:</strong></p>
 * <ul>
 *     <li>Fornecer endpoints públicos da API para recuperar sessões associadas a um determinado evento (meeting).</li>
 *     <li>Expor a sessão que está ocorrendo no dia atual, se houver.</li>
 * </ul>
 *
 * <p><strong>Funcionamento geral:</strong></p>
 * <ul>
 *     <li>O endpoint {@code GET /api/sessions/get?meetingKey=} retorna a lista de sessões relacionadas a um evento específico, identificado pelo {@code meetingKey}.</li>
 *     <li>O endpoint {@code GET /api/sessions/get/today} retorna a sessão em andamento no dia atual, com base no fuso horário de Brasília (-03:00).</li>
 * </ul>
 *
 * <p>Este controlador atua como ponte entre a camada de serviço ({@link SessionService}) e o cliente (frontend ou consumidor externo), delegando as operações para a lógica de negócio apropriada e retornando as respostas formatadas.</p>
 */
@RestController
@RequestMapping("api/sessions")
public class SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping(value = "get", params = "meetingKey")
    public ResponseEntity<List<SessionDTO>> getSessionsByMeeting(@RequestParam Integer meetingKey) {
        List<SessionDTO> sessions = sessionService.getSessionsByMeeting(meetingKey);

        return ResponseEntity.ok(sessions);
    }

    @GetMapping("get/today")
    public ResponseEntity<SessionDTO> getTodaySession() {
        SessionDTO session = sessionService.getTodaySession();

        return ResponseEntity.ok(session);
    }
}
