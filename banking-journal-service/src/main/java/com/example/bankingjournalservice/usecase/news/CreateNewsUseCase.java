package com.example.bankingjournalservice.usecase.news;

import com.example.bankingjournalservice.domain.model.News;
import com.example.bankingjournalservice.domain.service.NewsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateNewsUseCase {

    private final NewsService newsService;

    public CreateNewsUseCase(NewsService newsService) {
        this.newsService = newsService;
    }

    @Transactional
    public News execute(
            News news
    ) {
        return newsService.createNews(news);
    }
}