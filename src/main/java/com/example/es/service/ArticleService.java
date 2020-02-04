package com.example.es.service;

import com.example.es.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
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

    List<Article> findByTitleLike(String keyword);

    List<Article> findByTitle(String keyword);

    List<Article> queryByTitle(String keyword);

    Page<Article> findByTitle(String elasticsearch, Pageable pageable);

    List<Article> findByTitle(String elasticsearch, Sort sort);

    Article index(Article article);
}
