package agh.projects.crowd_pressure.engine.simulation.model;

import agh.projects.crowd_pressure.types.request_dto.AgentGroupDto;
import lombok.Getter;

@Getter
public class AgentGroup {

    private final int count;
    private final Point startCenter;
    private final double startRadius;
    private final Point destinationCenter;
    private final double destinationRadius;

    public AgentGroup(int count, Point startCenter, double startRadius, Point destinationCenter, double destinationRadius) {
        this.count = count;
        this.startCenter = startCenter;
        this.startRadius = startRadius;
        this.destinationCenter = destinationCenter;
        this.destinationRadius = destinationRadius;
    }

    public AgentGroup(AgentGroupDto dto) {
        this(dto.groupSize(), new Point(dto.startCenter()), dto.startRadius(), new Point(dto.destination()), dto.destinationRadius());
    }


}
