package com.example.bankingjournalservice.usecase.news;

import com.example.bankingjournalservice.domain.model.News;
import com.example.bankingjournalservice.domain.service.NewsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetNewsByIdUseCase {

    private final NewsService newsService;

    public GetNewsByIdUseCase(NewsService newsService) {
        this.newsService = newsService;
    }

    @Transactional
    public News execute(
            Long id
    ) {
        News existingNews = newsService.getNewsById(id);

        if (existingNews == null) {
            throw new IllegalStateException("News with ID " + id + " does not exist");
        }
        return existingNews;
    }
}
