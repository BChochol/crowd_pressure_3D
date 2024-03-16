package agh.projects.crowd_pressure.types.request_dto;

import java.util.List;

public record CreateSimulationRequestDto(
        List<RoadDto> roads,
        List<AgentGroupDto> agentGroups,
        int simulationWidth,
        int simulationHeight,
        double scaleCoefficient,
        double destinationRadius,
        double timeQuantum

) {


}
