package com.example.bankingjournalservice.usecase.news;

import com.example.bankingjournalservice.domain.model.News;
import com.example.bankingjournalservice.domain.service.NewsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateNewsUseCase {

    private final NewsService newsService;


    public UpdateNewsUseCase(
            NewsService newsService
    ) {
        this.newsService = newsService;
    }

    @Transactional
    public News execute(
            Long id,
            News news
    ) {
        // Check if News exist
        News existingNews = newsService.getNewsById(id);

        if (existingNews == null) {
            throw new IllegalStateException("News does not exist! ");
        }

        existingNews.setId(news.getId());
        existingNews.setDescription(news.getDescription());
        existingNews.setIcon(news.getIcon());

        return newsService.updateNews(existingNews);
    }
}
