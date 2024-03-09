package agh.projects.crowd_pressure.service;

import agh.projects.crowd_pressure.repository.CrowdPressureRepository;
import agh.projects.crowd_pressure.types.domain.Simulation;
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


}
