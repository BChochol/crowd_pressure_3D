package agh.projects.crowd_pressure.service;

import agh.projects.crowd_pressure.repository.CrowdPressureRepository;
import agh.projects.crowd_pressure.types.domain.Simulation;
import agh.projects.crowd_pressure.types.request_dto.CreateSimulationRequestDto;
import agh.projects.crowd_pressure.types.response_dto.CreateSimulationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrowdPressureServiceImpl implements CrowdPressureService {

    private final CrowdPressureRepository repository;

    @Override
    public Optional<Simulation> getSimulationById(String simulationId) {
        return repository.getSimulation(simulationId);
    }

    @Override
    public Optional<Simulation> deleteSimulationById(String simulationId) {
        return repository.deleteSimulation(simulationId);
    }

    @Override
    public CreateSimulationResponseDto createSimulation(CreateSimulationRequestDto createSimulationRequestDto) {
        return null;
    }


}
