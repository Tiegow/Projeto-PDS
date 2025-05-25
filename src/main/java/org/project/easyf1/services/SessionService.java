package org.project.easyf1.services;

import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.SessionClient;
import org.project.easyf1.controllers.rest.SessionController;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Serviço responsável por gerenciar as operações relacionadas a sessões (treinos, classificações e corridas).
 *
 * <p><strong>Responsabilidades principais:</strong></p>
 * <ul>
 *     <li>Buscar e armazenar sessões atualizadas da API externa OpenF1 através do {@link SessionClient}.</li>
 *     <li>Persistir as sessões no banco de dados usando o {@link SessionRepository}.</li>
 *     <li>Fornecer métodos para consultar sessões por evento (meeting) ou pela data atual.</li>
 * </ul>
 *
 * <p><strong>Funcionamento geral:</strong></p>
 * <ul>
 *     <li>O método {@code getNewSessions()} é executado automaticamente ao iniciar a aplicação, buscando todas as sessões ocorridas após a última registrada.</li>
 *     <li>Os dados retornados da API são convertidos em entidades e persistidos localmente.</li>
 *     <li>O método {@code getSessionsByMeeting(Integer)} retorna todas as sessões vinculadas a um determinado evento (meeting).</li>
 *     <li>O método {@code getTodaySession()} busca a sessão que está ocorrendo no dia atual, considerando o fuso horário de Brasília (-03:00), e lança uma exceção personalizada se nenhuma sessão estiver ocorrendo hoje.</li>
 * </ul>
 *
 * <p>Esta classe atua como ponte entre o {@link SessionController} e a camada de dados.</p>
 */
@Service
public class SessionService {
    private final TodaySessionProvider todaySessionProvider;
    private final SessionClient sessionClient;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionClient sessionClient, SessionRepository sessionRepository, TodaySessionProvider todaySessionProvider) {
        this.todaySessionProvider = todaySessionProvider;
        this.sessionClient = sessionClient;
        this.sessionRepository = sessionRepository;
    }

    @PostConstruct
    public void getNewSessions() {
        Session lastSession = sessionRepository.findFirstByOrderByEndDateDesc();
    
        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastSession != null) {
            startDate = lastSession.getStartDate();
        }
    
        String dateStartParam = startDate.toString();
        
        try {
            List<SessionDTO> newSessions = sessionClient.getSessionsAfter(dateStartParam);
    
            List<Session> sessions = newSessions.stream()
                .map(SessionDTO::getSession) 
                .filter(Objects::nonNull)
                .toList();
        
            sessionRepository.saveAll(sessions);
        } catch (Exception e) {
            System.err.println("Erro ao buscar novas sessoes na inicialização");
        }
    }

    public List<SessionDTO> getSessionsByMeeting(Integer meetingKey) {
        List<Session> sessions = sessionRepository.findAllByMeetingKey(meetingKey);
        return sessions.stream()
                .map(SessionDTO::new)
                .toList();
    }

    public SessionDTO getTodaySession() {
        return todaySessionProvider.getTodaySession();
    }
}