package agh.projects.crowd_pressure.repository;

import agh.projects.crowd_pressure.types.domain.Simulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class CrowdPressureRepositoryImpl implements CrowdPressureRepository {

    private final ConcurrentHashMap<String, Simulation> simulations = new ConcurrentHashMap<>();

    @Override
    public Optional<Simulation> getSimulation(String simulationId) {
        return Optional.ofNullable(simulations.get(simulationId));
    }

    @Override
    public Optional<Simulation> deleteSimulation(String simulationId) {
        return Optional.ofNullable(simulations.remove(simulationId));
    }
}
