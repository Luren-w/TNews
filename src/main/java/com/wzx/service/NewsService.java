package com.wzx.service;

import com.wzx.po.News;
import com.wzx.po.NewsQuery;
import com.wzx.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface NewsService {
    News findNewsById(Long id);
    void input(News news);
    Page<News> findByPageable(Pageable pageable);

    Page<News> searchNews(Pageable pageable, NewsQuery newsQuery);

    Page<News> searchNews(Pageable pageable, Long id);

    void deleteById(Long id);

    HashMap<String, List<News>> archiveNews();

    Long countNews();

    Page<News> findNewsByQuery(String query, Pageable pageable);

    List<News> findTop(int i);
}
