package com.example.bankingjournalservice.usecase.feature;

import com.example.bankingjournalservice.domain.model.Feature;
import com.example.bankingjournalservice.domain.service.FeatureService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetFeatureByIdUseCase {

    private final FeatureService featureService;

    public GetFeatureByIdUseCase(FeatureService featureService) {
        this.featureService = featureService;
    }

    @Transactional
    public Feature execute(
            Long id
    ) {
        Feature existingFeature = featureService.getFeatureById(id);

        if (existingFeature == null) {
            throw new IllegalStateException("News with ID " + id + " does not exist");
        }

        return existingFeature;
    }
}
