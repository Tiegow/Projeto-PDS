package org.project.easyNascar.models.dto.liveSession;

import org.project.framework.models.dto.RaceControlDTO;

public class RaceControlDTOImpl extends RaceControlDTO {

    RaceControlDTOImpl() {
        super(); 
    }

    @Override
    public String resolveSubtitle(String flag) {
        if (flag == null) return "";

        return switch (flag) {
            case "GREEN" -> "Bandeira verde: pista liberada";
            case "YELLOW" -> "Bandeira amarela: cautela, incidente na pista";
            case "RED" -> "Bandeira vermelha: corrida interrompida";
            case "WHITE" -> "Bandeira branca: última volta";
            case "BLACK" -> "Bandeira preta: desclassificação do piloto";
            case "BLUE" -> "Bandeira azul: ceder passagem ao líder";
            case "CHECKERED" -> "Bandeira quadriculada: fim da corrida";
            case "BLACK WITH WHITE CROSS" -> "Bandeira preta com cruz branca: piloto ignorou penalidade";
            case "YELLOW WITH RED STRIPES" -> "Bandeira amarela com listras vermelhas: detritos na pista";
            default -> "Bandeira";
        };
    }
    
}
