package com.example.es.service;

import com.example.es.model.Article;

import java.util.Optional;

/**
 * <p></p>
 *
 * @author jiuhua.xu
 * @version 1.0
 * @since JDK 1.8
 */
public interface ArticleService {

    Article save(Article article);

    void removeById(String id);

    Optional<Article> findById(String id);

    Iterable<Article> findAll();
}
