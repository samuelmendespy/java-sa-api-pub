package com.example.bankingjournalservice.domain.service;

import com.example.bankingjournalservice.domain.model.Feature;
import com.example.bankingjournalservice.domain.repository.FeatureRepository;
import com.example.bankingjournalservice.infrastructure.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Transactional(readOnly = true)
    public Feature getFeatureById(Long id) {
        return featureRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Feature> getAllFeatures(Pageable pageable) {
        return featureRepository.findAll(pageable);
    }

    @Transactional
    public Feature createFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    @Transactional
    public Feature updateFeature(Feature feature) {
        if (!featureRepository.existsById(feature.getId())) {
            throw new NotFoundException("Feature not found with ID: " + feature.getId());
        }
        return featureRepository.save(feature);
    }

    @Transactional
    public void deleteFeature(Long id) {
        if (!featureRepository.existsById(id)) {
            throw new NotFoundException("Feature not found with ID: " + id);
        }
        featureRepository.deleteById(id);
    }
}
