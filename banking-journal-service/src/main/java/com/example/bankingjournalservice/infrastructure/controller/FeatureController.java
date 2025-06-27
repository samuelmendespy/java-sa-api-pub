package com.example.bankingjournalservice.infrastructure.controller;

import com.example.bankingjournalservice.domain.model.Feature;
import com.example.bankingjournalservice.infrastructure.dto.FeatureDTO;
import com.example.bankingjournalservice.infrastructure.exception.NotFoundException;
import com.example.bankingjournalservice.usecase.feature.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/features")
public class FeatureController {

    private final GetFeatureByIdUseCase getFeatureByIdUseCase;
    private final GetAllFeaturesUseCase getAllFeaturesUseCase;
    private final CreateFeatureUseCase createFeatureUseCase;
    private final UpdateFeatureUseCase updateFeatureUseCase;
    private final DeleteFeatureUseCase deleteFeatureUseCase;

    public FeatureController(
            GetFeatureByIdUseCase getFeatureByIdUseCase,
            GetAllFeaturesUseCase getAllFeaturesUseCase,
            CreateFeatureUseCase createFeatureUseCase,
            UpdateFeatureUseCase updateFeatureUseCase,
            DeleteFeatureUseCase deleteFeatureUseCase
    ) {
        this.getFeatureByIdUseCase = getFeatureByIdUseCase;
        this.getAllFeaturesUseCase = getAllFeaturesUseCase;
        this.createFeatureUseCase = createFeatureUseCase;
        this.updateFeatureUseCase = updateFeatureUseCase;
        this.deleteFeatureUseCase = deleteFeatureUseCase;
    }

    @Operation(summary = "Create a new feature", description = "Creates a new bank feature.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeatureDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<FeatureDTO> createFeature(
            @RequestBody FeatureDTO FeatureDTO
    ) {
        try {
            Feature feature = FeatureDTO.toDomain();
            Feature createdFeature = createFeatureUseCase.execute(feature);
            return ResponseEntity.status(HttpStatus.CREATED).body(FeatureDTO.fromDomain(createdFeature));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get an feature by ID", description = "Retrieve the details of an existing feature by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeatureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FeatureDTO> getFeatureById(@PathVariable Long id) {
        try {
            Feature feature = getFeatureByIdUseCase.execute(id);
            return ResponseEntity.ok(FeatureDTO.fromDomain(feature));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all feature", description = "Retrieve a list of all features.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of feature retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeatureDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<FeatureDTO>> getAllFeatures(Pageable pageable) {
        Page<Feature> feature = getAllFeaturesUseCase.execute(pageable);
        Page<FeatureDTO> featureDTOS = feature.map(FeatureDTO::fromDomain);
        return ResponseEntity.ok(featureDTOS);
    }

    @Operation(summary = "Update an existing feature", description = "Update the details of an existing feature.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeatureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FeatureDTO> updateFeature(@PathVariable Long id, @RequestBody FeatureDTO featureDTO) {
        try {
            Feature feature = featureDTO.toDomain();
            Feature updatedAccount = updateFeatureUseCase.execute(id, feature);
            return ResponseEntity.ok(FeatureDTO.fromDomain(updatedAccount));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete an feature by ID", description = "Delete an feature and its associated cards by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Account deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        try {
            deleteFeatureUseCase.execute(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}