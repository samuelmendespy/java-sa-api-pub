package com.example.bankingjournalservice.usecase.feature;

import com.example.bankingjournalservice.domain.model.Feature;
import com.example.bankingjournalservice.domain.service.FeatureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAllFeaturesUseCase {
    private final FeatureService featuresService;

    public GetAllFeaturesUseCase(FeatureService featureService) {
        this.featuresService = featureService;
    }

    @Transactional
    public Page<Feature> execute(
            Pageable pageable
    ) {
        return featuresService.getAllFeatures(pageable);
    }
}
