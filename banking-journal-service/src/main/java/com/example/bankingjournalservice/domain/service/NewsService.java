package com.example.bankingjournalservice.domain.service;

import com.example.bankingjournalservice.domain.model.News;
import com.example.bankingjournalservice.domain.repository.NewsRepository;
import com.example.bankingjournalservice.infrastructure.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional(readOnly = true)
    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<News> getAllNews(
    @PageableDefault(size = 10, page = 0, sort = "publishedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Transactional
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    @Transactional
    public News updateNews(News news) {
        if (!newsRepository.existsById(news.getId())) {
            throw new NotFoundException("User not found with ID: " + news.getId());
        }
        return newsRepository.save(news);
    }

    @Transactional
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new NotFoundException("News not found with ID: " + id);
        }
        newsRepository.deleteById(id);
    }
}