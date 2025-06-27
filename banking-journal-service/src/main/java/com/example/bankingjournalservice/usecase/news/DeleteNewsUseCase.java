package com.example.bankingjournalservice.usecase.news;

import com.example.bankingjournalservice.domain.model.News;
import com.example.bankingjournalservice.domain.service.NewsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteNewsUseCase {
    private final NewsService newsService;

    public DeleteNewsUseCase(
            NewsService newsService
    ) {
        this.newsService = newsService;
    }

    @Transactional
    public void execute(
            Long id
    ) {
        News existingNews = newsService.getNewsById(id);

        // Check if news exists
        if (existingNews == null) {
            throw new IllegalStateException("News with ID: "+ id +" does not exist! ");
        }

        newsService.deleteNews(id);

        // Return nothing
        return;
    }
}