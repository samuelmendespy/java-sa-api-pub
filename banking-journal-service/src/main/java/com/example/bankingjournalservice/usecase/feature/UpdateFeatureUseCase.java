package com.example.bankingjournalservice.usecase.feature;

import com.example.bankingjournalservice.domain.model.Feature;
import com.example.bankingjournalservice.domain.service.FeatureService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateFeatureUseCase {

    private final FeatureService featureService;


    public UpdateFeatureUseCase(
            FeatureService featureService
    ) {
        this.featureService = featureService;
    }

    @Transactional
    public Feature execute(
            Long id,
            Feature feature
    ) {
        // Check if Feature exists
        Feature existingFeature = featureService.getFeatureById(id);

        if (existingFeature == null) {
            throw new IllegalStateException("News does not exist! ");
        }

        existingFeature.setId(feature.getId());
        existingFeature.setDescription(feature.getDescription());
        existingFeature.setIcon(feature.getIcon());

        return featureService.updateFeature(existingFeature);
    }
}
