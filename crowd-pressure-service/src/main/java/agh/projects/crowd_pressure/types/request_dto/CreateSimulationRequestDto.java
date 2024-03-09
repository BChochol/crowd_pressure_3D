package agh.projects.crowd_pressure.types.request_dto;

import java.util.List;

public record CreateSimulationRequestDto(
        List<RoadDto> roads,
        int simulationWidth,
        int simulationHeight,
        int agentCount,
        double scaleCoefficient,
        double destinationRadius,
        double timeQuantum

) {


}
