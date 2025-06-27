package com.example.bankingjournalservice.infrastructure.dto;

import com.example.bankingjournalservice.domain.model.Feature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO {
    private Long id;
    private String icon;
    private String description;

    public static FeatureDTO fromDomain(Feature feature) {
        FeatureDTO dto = new FeatureDTO();
        dto.setId(feature.getId());
        dto.setDescription(feature.getDescription());
        dto.setIcon(feature.getIcon());
        return dto;
    }

    public Feature toDomain() {
        Feature feature = new Feature();
        feature.setId(this.id);
        feature.setDescription(this.description);
        feature.setIcon(this.icon);
        return feature;
    }
}
