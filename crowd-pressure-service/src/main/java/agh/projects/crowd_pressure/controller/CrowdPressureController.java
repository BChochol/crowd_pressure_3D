package agh.projects.crowd_pressure.controller;

import agh.projects.crowd_pressure.types.response_dto.SimulationDto;
import agh.projects.crowd_pressure.types.request_dto.CreateSimulationRequestDto;
import agh.projects.crowd_pressure.types.http.ErrorResponse;
import agh.projects.crowd_pressure.types.http.HTTPResponse;
import agh.projects.crowd_pressure.service.CrowdPressureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            operationId = "GetSimulationById",
            summary = "Returns the current state of the simulation with given simulation id.",
            description = "Returns the current state of the simulation with given simulation id if the simulation exists. Otherwise returns null.",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "simulationId", description = "The id of the simulation to return.")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK response with the current state of the simulation if it exists."
                    )
            }
    )
    @GetMapping("/simulation/{simulationId}")
    ResponseEntity<HTTPResponse<Optional<SimulationDto>>> getSimulationById(@PathVariable String simulationId) {
        return ResponseEntity.ok(HTTPResponse.ok(service.getSimulationById(simulationId)));
    }

    @Operation(
            operationId = "DeleteSimulationById",
            summary = "Deletes the simulation by the id and return its last state.",
            description = "Deletes the simulation by the id and return its last state. Doesn't have any effect when the simulation doesn't exist.",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "simulationId", description = "The id of the simulation to delete.")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK response with the last state of the simulation if it existed."
                    )
            }
    )
    @DeleteMapping("/simulation/{simulationId}")
    ResponseEntity<HTTPResponse<Optional<SimulationDto>>> deleteSimulationById(@PathVariable String simulationId) {
        return ResponseEntity.ok(HTTPResponse.ok(service.deleteSimulationById(simulationId)));
    }

    @Operation(
            operationId = "CreateSimulation",
            summary = "Creates a new simulation and returns its state.",
            description = "Creates a new simulation and returns its state. The operation may fail if the request contains invalid parameters.",
            requestBody = @RequestBody(
                    description = "The definition of simulation parameters.",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK response with the current state of the simulation if it was created."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "BadRequest response if any of the parameter is invalid. Each parameter has to be non-negative."
                    )
            }
    )
    @PostMapping("/simulation/")
    ResponseEntity<HTTPResponse<SimulationDto>> createSimulation(@RequestBody CreateSimulationRequestDto createSimulationRequestDto) {
        return ResponseEntity.ok(HTTPResponse.ok(service.createSimulation(createSimulationRequestDto)));
    }

    @Operation(
            operationId = "StepSimulationById",
            summary = "Executes given amount of steps in a given simulation.",
            description = "Executes given amount of steps in a given simulation and returns its latest state. Doesn't make any effect if the simulation doesn't exist. The operation is synchronous and may take significant amount of time.",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "simulationId", description = "The id of the simulation to execute steps."),
                    @Parameter(in = ParameterIn.QUERY, name = "steps", description = "The amount of steps to execute.")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK response with the latest state of the simulation if it exist."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "ServerSide error if the simulation cannot execute the given amount of steps."
                    )
            }
    )
    @PatchMapping("/simulation/{simulationId}/step")
    ResponseEntity<HTTPResponse<Optional<SimulationDto>>> stepSimulation(@PathVariable String simulationId, @RequestParam int steps) {
        return ResponseEntity.ok(HTTPResponse.ok(service.stepSimulation(simulationId, steps)));
    }

    @Operation(
            operationId = "ResetSimulationById",
            summary = "Restarts the simulation to its beginning state.",
            description = "Restarts the simulation to its beginning state if the simulation exists.",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "simulationId", description = "The id of the simulation to restart."),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK response with the restarted state of the simulation if it existed."
                    )
            }
    )
    @PatchMapping("/simulation/{simulationId}/reset")
    ResponseEntity<HTTPResponse<Optional<SimulationDto>>> resetSimulation(@PathVariable String simulationId) {
        return ResponseEntity.ok(HTTPResponse.ok(service.resetSimulation(simulationId)));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<HTTPResponse<String>> handleException(Throwable ex) {
        logger.error(String.format("Got error: [%s]", ex.getMessage()), ex);
        if (ex instanceof IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HTTPResponse.error(ex.getMessage(), ErrorResponse.ErrorCode.UNKNOWN_ERROR));
        } else if (ex instanceof IllegalStateException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HTTPResponse.error(ex.getMessage(), ErrorResponse.ErrorCode.SIMULATION_ERROR));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(HTTPResponse.error(ex.getMessage(), ErrorResponse.ErrorCode.CRITICAL_ERROR));
        }
    }


}
