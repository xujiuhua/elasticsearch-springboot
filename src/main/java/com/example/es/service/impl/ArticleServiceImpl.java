package com.example.es.service.impl;

import com.example.es.model.Article;
import com.example.es.repository.ArticleRepository;
import com.example.es.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p></p>
 *
 * @author jiuhua.xu
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void removeById(String id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Optional<Article> findById(String id) {
        return this.articleRepository.findById(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }
}
