package com.example.bankingjournalservice.domain.repository;

import com.example.bankingjournalservice.domain.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}