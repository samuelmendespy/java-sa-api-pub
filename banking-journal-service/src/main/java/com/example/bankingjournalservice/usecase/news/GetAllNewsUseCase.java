package com.example.bankingjournalservice.usecase.news;

import com.example.bankingjournalservice.domain.model.News;
import com.example.bankingjournalservice.domain.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAllNewsUseCase {
    private final NewsService newsService;

    public GetAllNewsUseCase(NewsService newsService) {
        this.newsService = newsService;
    }

    @Transactional
    public Page<News> execute(
            Pageable pageable
    ) {
        return newsService.getAllNews(pageable);
    }
}
