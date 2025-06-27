package com.example.bankingjournalservice.domain.repository;

import com.example.bankingjournalservice.domain.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
}