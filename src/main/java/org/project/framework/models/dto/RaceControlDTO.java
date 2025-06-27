package org.project.framework.models.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RaceControlDTO {
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;

    @JsonProperty("category")
    private String category;

    @JsonIgnore
    private String subTitle;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("message")
    private String message;

    RaceControlDTO(){};

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("subTitle")
    public String getSubTitle() {
        return resolveSubtitle(flag);
    }

    private String resolveSubtitle(String flag) {
        if (flag == null) return "";

        return switch (flag) {
            case "GREEN" -> "Pista livre";
            case "BLUE" -> "Ceder passagem";
            case "CHEQUERED" -> "Corrida finalizada";
            case "YELLOW" -> "Atenção: perigo localizado";
            case "DOUBLE YELLOW" -> "Atenção: perigo maior";
            case "RED" -> "Sessão interrompida";
            case "BLACK" -> "Desclassificação de piloto";
            case "BLACK AND WHITE" -> "Má conduta";
            case "CLEAR" -> "Situação normalizada";
            default -> "Bandeira";
        };
    }
}
