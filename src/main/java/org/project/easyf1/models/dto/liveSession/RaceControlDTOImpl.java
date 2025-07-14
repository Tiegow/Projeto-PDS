package org.project.easyf1.models.dto.liveSession;

import org.project.framework.models.dto.RaceControlDTO;

public class RaceControlDTOImpl extends RaceControlDTO {

    RaceControlDTOImpl() {
        super(); 
    }

    @Override
    public String resolveSubtitle(String flag) {
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
