package org.project.framework.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;

/**
 * Data Transfer Object (DTO) que representa os dados de uma sessão de corrida (Session).
 * 
 * Esta classe é usada para transferir os dados de uma sessão, como localização, país, circuito,
 * tipo de sessão, nome da sessão e as datas de início e fim. Além disso, ela inclui métodos para
 * converter entre a entidade `Session` e o DTO `SessionDTO`.
 * 
 * A classe utiliza a biblioteca Jackson para mapeamento de JSON, com anotações como `@JsonProperty` 
 * e `@JsonFormat` para garantir que os campos do DTO sejam corretamente mapeados para os campos 
 * do JSON com os respectivos nomes e formatos desejados.
 */
public class SessionDTO {

    @JsonProperty("id")
    @JsonAlias("id")
    private Long id;

    @JsonProperty("location")
    @JsonAlias("location")
    private String location;

    @JsonProperty("country_key")
    @JsonAlias("countryKey")
    private Integer countryKey;

    @JsonProperty("country_code")
    @JsonAlias("countryCode")
    private String countryCode;

    @JsonProperty("country_name")
    @JsonAlias("countryName")
    private String countryName;

    @JsonProperty("circuit_key")
    @JsonAlias("circuitKey")
    private Integer circuitKey;

    @JsonProperty("circuit_short_name")
    @JsonAlias("circuitShortName")
    private String circuitShortName;

    @JsonProperty("session_type")
    @JsonAlias("sessionType")
    private String sessionType;

    @JsonProperty("session_name")
    @JsonAlias("sessionName")
    private String sessionName;

    @JsonProperty("date_start")
    @JsonAlias("startDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;

    @JsonProperty("date_end")
    @JsonAlias("endDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endDate;

    @JsonProperty("session_key")
    @JsonAlias("sessionKey")
    private Integer sessionKey;

    @JsonProperty("meeting_key")
    @JsonAlias("meetingKey")
    private Integer meetingKey;

    public SessionDTO() {}

    public SessionDTO(Session session) {
        this.location = session.getLocation();
        this.countryKey = session.getCountryKey();
        this.countryCode = session.getCountryCode();
        this.countryName = session.getCountryName();
        this.circuitKey = session.getCircuitKey();
        this.circuitShortName = session.getCircuitShortName();
        this.sessionType = session.getSessionType();
        this.sessionName = session.getSessionName();
        this.startDate = session.getStartDate();
        this.endDate = session.getEndDate();
        this.sessionKey = session.getSessionKey();
        this.meetingKey = session.getMeeting().getMeetingKey();
    }

    public Session getSession(){
        Session session = new Session();
        session.setLocation(this.location);
        session.setCountryKey(this.countryKey);
        session.setCountryCode(this.countryCode);
        session.setCountryName(this.countryName);
        session.setCircuitKey(this.circuitKey);
        session.setCircuitShortName(this.circuitShortName);
        session.setSessionType(this.sessionType);
        session.setSessionName(this.sessionName);
        session.setStartDate(this.startDate);
        session.setEndDate(this.endDate);
        session.setSessionKey(this.sessionKey);
        session.setMeeting(new Meeting());
        session.getMeeting().setMeetingKey(this.meetingKey);

        return session;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCountryKey() {
        return countryKey;
    }

    public void setCountryKey(Integer countryKey) {
        this.countryKey = countryKey;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCircuitKey() {
        return circuitKey;
    }

    public void setCircuitKey(Integer circuitKey) {
        this.circuitKey = circuitKey;
    }

    public String getCircuitShortName() {
        return circuitShortName;
    }

    public void setCircuitShortName(String circuitShortName) {
        this.circuitShortName = circuitShortName;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(Integer sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(Integer meetingKey) {
        this.meetingKey = meetingKey;
    }
}
