package org.project.framework.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.project.framework.providers.MeetingProvider;
import org.project.framework.controllers.MeetingController;
import org.project.framework.models.dto.MeetingDTO;
import org.project.framework.models.entity.Meeting;
import org.project.framework.models.entity.Session;
import org.project.framework.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar as operações relacionadas a Meetings (corridas ou eventos).
 * 
 * <p><strong>Responsabilidades principais:</strong></p>
 * <ul>
 *     <li>Buscar e armazenar novos meetings obtidos da API externa OpenF1 através do {@link MeetingProvider}.</li>
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

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<MeetingDTO> getMeetingsByYear(Integer year) {
        List<Meeting> meetings = meetingRepository.findAllByYear(year).stream().filter(meeting -> meeting.getSessions() != null).toList();
        
        // Mapeia para DTO
        Stream<MeetingDTO> dtos = meetings.stream()
            .map(meeting -> {
                MeetingDTO dto = new MeetingDTO(meeting);
                List<Session> sessions = meeting.getSessions();

                if (sessions != null && !sessions.isEmpty()) {
                    Session lastSession = sessions.getLast(); // ou sessions.get(sessions.size() - 1)
                    dto.setEndDate(lastSession.getEndDate());
                }

                return dto;
            });

        List<MeetingDTO> listDTOS = new java.util.ArrayList<>(dtos.toList());

        // Mais recentes para o início
        Collections.reverse(listDTOS);

        return listDTOS;
    }
}
