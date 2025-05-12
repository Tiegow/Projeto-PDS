package org.project.easyf1.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.project.easyf1.client.MeetingClient;
import org.project.easyf1.client.SessionClient;
import org.project.easyf1.controllers.rest.MeetingController;
import org.project.easyf1.models.dto.MeetingDTO;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.MeetingRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Meetings (corridas ou eventos).
 * 
 * <p><strong>Responsabilidades principais:</strong></p>
 * <ul>
 *     <li>Buscar e armazenar novos meetings obtidos da API externa OpenF1 através do {@link MeetingClient}.</li>
 *     <li>Persistir os dados obtidos no banco de dados usando {@link MeetingRepository}.</li>
 *     <li>Fornecer listas de meetings filtradas por ano, convertendo entidades para DTOs com informações complementares.</li>
 * </ul>
 *
 * <p><strong>Funcionamento geral:</strong></p>
 * <ul>
 *     <li>Ao inicializar a aplicação, o método {@code getNewMeetings()} é executado automaticamente, buscando novos meetings após a data do último armazenado.</li>
 *     <li>Os dados recebidos da API são convertidos em entidades e salvos no banco.</li>
 *     <li>O método {@code getMeetingsByYear(Integer year)} permite recuperar os meetings de um determinado ano, incluindo a data da última sessão associada, se disponível.</li>
 * </ul>
 * 
 * <p>Esta classe atua como ponte entre o {@link MeetingController} e a camada de dados.</p>
 */
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, SessionRepository sessionRepository) {
        this.meetingRepository = meetingRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<MeetingDTO> getMeetingsByYear(Integer year) {
        List<Meeting> meetings = meetingRepository.findAllByYear(year);
        
        // Mapeia para DTO
        List<MeetingDTO> dtos = meetings.stream()
            .map(meeting -> {
                MeetingDTO dto = new MeetingDTO(meeting);
                Session lastSession = meeting.getSessions().getLast();

                if (lastSession != null) {
                    dto.setEndDate(lastSession.getEndDate());
                }

                return dto;
            })
            .collect(Collectors.toList());

        // Mais recentes para o início
        Collections.reverse(dtos);

        return dtos;
    }
}
