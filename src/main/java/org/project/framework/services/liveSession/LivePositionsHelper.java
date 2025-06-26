package org.project.framework.services.liveSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.project.easyf1.models.dto.PositionDTO;
import org.springframework.stereotype.Component;

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