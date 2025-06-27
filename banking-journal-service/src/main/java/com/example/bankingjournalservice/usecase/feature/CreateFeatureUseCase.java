package com.example.bankingjournalservice.usecase.feature;

import com.example.bankingjournalservice.domain.model.Feature;
import com.example.bankingjournalservice.domain.service.FeatureService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateFeatureUseCase {

    private final FeatureService featureService;

    public CreateFeatureUseCase(FeatureService featureService) {
        this.featureService = featureService;
    }

    @Transactional
    public Feature execute(
            Feature feature
    ) {
        return featureService.createFeature(feature);
    }
}