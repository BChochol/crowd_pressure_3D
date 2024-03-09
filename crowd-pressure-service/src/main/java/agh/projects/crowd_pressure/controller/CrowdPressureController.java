package agh.projects.crowd_pressure.controller;

import agh.projects.crowd_pressure.types.domain.Simulation;
import agh.projects.crowd_pressure.types.request_dto.CreateSimulationRequestDto;
import agh.projects.crowd_pressure.types.http.ErrorResponse;
import agh.projects.crowd_pressure.types.http.HTTPResponse;
import agh.projects.crowd_pressure.service.CrowdPressureService;
import agh.projects.crowd_pressure.types.response_dto.CreateSimulationResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CrowdPressureController {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final CrowdPressureService service;

    @GetMapping("/simulation/{simulationId}")
    ResponseEntity<HTTPResponse<Optional<Simulation>>> getSimulationById(@PathVariable String simulationId) {
        return ResponseEntity.ok(HTTPResponse.ok(service.getSimulationById(simulationId)));
    }

    @DeleteMapping("/simulation/{simulationId}")
    ResponseEntity<HTTPResponse<Optional<Simulation>>> deleteSimulationById(@PathVariable String simulationId) {
        return ResponseEntity.ok(HTTPResponse.ok(service.deleteSimulationById(simulationId)));
    }

    @PostMapping("/simulation/")
    ResponseEntity<HTTPResponse<CreateSimulationResponseDto>> createSimulation(@RequestBody CreateSimulationRequestDto createSimulationRequestDto) {
        return ResponseEntity.ok(HTTPResponse.ok(service.createSimulation(createSimulationRequestDto)));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<HTTPResponse<String>> handleException(Throwable ex) {
        logger.error(String.format("Got error: [%s]", ex.getMessage()), ex);
        if (ex instanceof IllegalArgumentException || ex instanceof IllegalStateException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HTTPResponse.error(ex.getMessage(), ErrorResponse.ErrorCode.UNKNOWN_ERROR));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HTTPResponse.error(ex.getMessage(), ErrorResponse.ErrorCode.CRITICAL_ERROR));
        }
    }


}
