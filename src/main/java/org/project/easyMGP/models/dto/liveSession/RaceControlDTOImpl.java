package org.project.easyMGP.models.dto.liveSession;

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
            case "YELLOW" -> "Atenção: perigo localizado";
            case "DOUBLE YELLOW" -> "Atenção: perigo maior";
            case "RED" -> "Corrida interrompida";
            case "BLACK" -> "Desclassificação de piloto";
            case "BLACK WITH ORANGE DISC" -> "Problema mecânico";
            case "BLUE" -> "Ceder passagem";
            case "WHITE" -> "Veículo lento na pista";
            case "BLACK AND WHITE" -> "Advertência por conduta antidesportiva";
            case "CHEQUERED" -> "Corrida finalizada";
            default -> "Bandeira";
        };
    }
    
}
