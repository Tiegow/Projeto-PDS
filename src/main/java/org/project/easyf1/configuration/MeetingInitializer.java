package org.project.easyf1.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.project.framework.models.dto.GeminiResponse;
import org.project.framework.models.dto.MeetingDTO;
import org.project.framework.models.dto.SessionDTO;
import org.project.framework.models.entity.Meeting;
import org.project.framework.models.entity.Session;
import org.project.framework.repositories.MeetingRepository;
import org.project.framework.repositories.SessionRepository;
import org.project.framework.services.LLMService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MeetingInitializer {

    private MeetingRepository meetingRepository;

    private SessionRepository sessionRepository;

    private LLMService llmService;

    public MeetingInitializer(MeetingRepository meetingRepository, SessionRepository sessionRepository,  LLMService llmService) {
        this.meetingRepository = meetingRepository;
        this.sessionRepository = sessionRepository;
        this.llmService = llmService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        Meeting lastMeeting = meetingRepository.findFirstByOrderByStartDateDesc();

        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastMeeting != null) {
            startDate = lastMeeting.getStartDate();
        }

        List<MeetingDTO> newMeetings = this.getAllMeetingDTOS();
        List<Meeting> meetings = newMeetings.stream().map(MeetingDTO::getMeeting).toList();

        List<String> idsMeetings = pegarTodosIds(meetings);

        List<Session> sessions = getAllSessions(idsMeetings);

        try{
            meetingRepository.saveAll(meetings);
            sessionRepository.saveAll(sessions);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<String> pegarTodosIds(List<Meeting> meetings) {
        List<String> idsMeetings = new ArrayList<>();

        for (Meeting meeting : meetings) {
            idsMeetings.add(String.valueOf(meeting.getMeetingKey()));
        }

        return idsMeetings;
    }


    private List<Session> getAllSessions(List<String> idsMeetings) {
        Session lastSession = sessionRepository.findFirstByOrderByEndDateDesc();

        OffsetDateTime startDate = OffsetDateTime.parse("2000-01-01T00:00:00Z");
        if (lastSession != null) {
            startDate = lastSession.getStartDate();
        }

        try {
            List<SessionDTO> newSessions = this.getAllSessionsDTOS(idsMeetings);

            List<Session> sessions = newSessions.stream()
                    .map(SessionDTO::getSession)
                    .filter(Objects::nonNull)
                    .toList();

            return sessions;
        } catch (Exception e) {
            System.err.println("Erro ao buscar novas sessoes na inicialização");
        }

        return null;
    }

    private List<MeetingDTO> getAllMeetingDTOS() {

        StringBuilder s = new StringBuilder("Quero que você me retorne um array JSON contendo 20 objetos representando Grandes Prêmios aleatórios de corrida da MotoGP. ");
        s.append("Cada objeto deve conter os seguintes campos: ");
        s.append("meeting_key (número inteiro), ");
        s.append("meeting_name (nome do evento), ");
        s.append("meeting_official_name (nome oficial do evento), ");
        s.append("location (local da corrida), ");
        s.append("country_key (número inteiro do país), ");
        s.append("country_code (código do país em padrao alpha tres), ");
        s.append("country_name (nome do país), ");
        s.append("circuit_key (número inteiro do circuito), ");
        s.append("circuit_short_name (nome curto do circuito), ");
        s.append("date_start (data e hora de início no formato ISO 8601, ex: 2025-08-03T14:00:00-03:00), ");
        s.append("date_end (data e hora de término no mesmo formato), ");
        s.append("year (ano da corrida). ");
        s.append("Todos os dados devem parecer plausíveis e realistas. Retorne apenas o JSON, sem explicações ou comentários.");

        String retorno = llmService.callGeminiAPI(s.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        GeminiResponse geminiResponse = null;
        try{
            geminiResponse = objectMapper.readValue(retorno, GeminiResponse.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String rawJson = geminiResponse.getCandidates()
                .getFirst()
                .getContent()
                .getParts()
                .getFirst()
                .getText();

        String cleanJson = rawJson.replaceAll("(?s)```json\\s*", "")
                .replaceAll("```", "")
                .trim();

        try{
           return objectMapper.readValue(cleanJson, new TypeReference<List<MeetingDTO>>() {});
        } catch (Exception e){
            return null;
        }
    }

    private List<SessionDTO> getAllSessionsDTOS(List<String> idsMeetings) {

        StringBuilder s = new StringBuilder("Quero que você me retorne um array JSON contendo 20 objetos representando sessões de corrida da MotoGP. ");
        s.append("Cada objeto deve conter os seguintes campos: ");
        s.append("id (número inteiro único), ");
        s.append("location (cidade e estado onde ocorre a sessão), ");
        s.append("country_key (número inteiro do país), ");
        s.append("country_code (código do país no padrão alpha-3, ex: 'USA'), ");
        s.append("country_name (nome completo do país), ");
        s.append("circuit_key (número inteiro representando o circuito), ");
        s.append("circuit_short_name (nome curto do circuito), ");
        s.append("session_type (tipo da sessão, como 'Practice', 'Qualifying' ou 'Race'), ");
        s.append("session_name (nome oficial da sessão), ");
        s.append("date_start (data e hora de início no formato ISO 8601, ex: 2025-08-03T14:00:00-03:00), ");
        s.append("date_end (data e hora de término no mesmo formato), ");
        s.append("session_key (número inteiro identificando unicamente a sessão), ");
        s.append("meeting_key (chave estrangeira que referencia o evento principal de corrida). ");
        s.append("Os dados devem parecer plausíveis e realistas, incluindo horários coerentes com os tipos de sessão. ");
        s.append("Considere apenas os seguintes valores possíveis para meeting_key Liste 4 sessions para cada meeting_key: " + String.join(", ", idsMeetings) + ". ");
        s.append("Retorne apenas o JSON, sem explicações ou comentários.");

        String retorno = llmService.callGeminiAPI(s.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        GeminiResponse geminiResponse = null;
        try{
            geminiResponse = objectMapper.readValue(retorno, GeminiResponse.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String rawJson = geminiResponse.getCandidates()
                .getFirst()
                .getContent()
                .getParts()
                .getFirst()
                .getText();

        String cleanJson = rawJson.replaceAll("(?s)```json\\s*", "")
                .replaceAll("```", "")
                .trim();

        try{
            return objectMapper.readValue(cleanJson, new TypeReference<List<SessionDTO>>() {});
        } catch (Exception e){
            return null;
        }
    }
}