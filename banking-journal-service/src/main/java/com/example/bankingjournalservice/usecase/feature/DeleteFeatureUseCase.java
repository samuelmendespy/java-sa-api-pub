package com.example.bankingjournalservice.usecase.feature;

import com.example.bankingjournalservice.domain.model.Feature;
import com.example.bankingjournalservice.domain.service.FeatureService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteFeatureUseCase {
    private final FeatureService featureService;

    public DeleteFeatureUseCase(
            FeatureService featureService
    ) {
        this.featureService = featureService;
    }

    @Transactional
    public void execute(
            Long id
    ) {
        Feature existingNews = featureService.getFeatureById(id);

        // Check if feature exists
        if (existingNews == null) {
            throw new IllegalStateException("News with ID: "+ id +" does not exist! ");
        }

        featureService.deleteFeature(id);

        // Return nothing
        return;
    }
}