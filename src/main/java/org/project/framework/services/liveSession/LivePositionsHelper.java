package org.project.framework.services.liveSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.project.framework.models.dto.PositionDTO;
import org.springframework.stereotype.Component;

/*
 * Classe que ajuda a manter um mapa atualizado com a ultima conhecida de cada 
 * piloto (mapeando o número do piloto à sua posição no grid). É útil quando os dados 
 * de posição chegam em lotes ou de forma esparsa, permitindo que outros componentes do sistema,
 * como os broadcasters, tenham acesso rápido a uma visão consolidada e completa do grid a qualquer
 * momento.
 * 
 * O seu uso dessa classe pelos Broadcasters é totalmente opcional.
 */
@Component
public class LivePositionsHelper {
    private Map<Integer, Integer> driversPositions = new HashMap<>(); // (numero piloto, posicao)

    public void updatePositions(List<PositionDTO> positions) {
        for (PositionDTO positionDTO : positions) {
            driversPositions.put(positionDTO.getDriverNumber(), positionDTO.getPosition());
        }
    }

    public Map<Integer, Integer> getDriversPositions() {
        return driversPositions;
    }
}