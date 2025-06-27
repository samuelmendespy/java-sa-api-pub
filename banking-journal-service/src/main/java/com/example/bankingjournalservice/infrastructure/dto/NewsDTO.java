package com.example.bankingjournalservice.infrastructure.dto;


import com.example.bankingjournalservice.domain.model.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private Long id;
    private String icon;
    private String description;

    public static NewsDTO fromDomain(News news) {
        NewsDTO dto = new NewsDTO();
        dto.setId(news.getId());
        dto.setDescription(news.getDescription());
        dto.setIcon(news.getIcon());
        return dto;
    }

    public News toDomain() {
        News news = new News();
        news.setId(this.id);
        news.setDescription(this.description);
        news.setIcon(this.icon);
        return news;
    }
}
