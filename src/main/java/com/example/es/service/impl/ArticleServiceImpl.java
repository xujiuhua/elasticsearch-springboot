package com.example.es.service.impl;

import com.example.es.model.Article;
import com.example.es.repository.ArticleRepository;
import com.example.es.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Article> findByTitleLike(String keyword) {
        return articleRepository.findByTitleLike(keyword);
    }

    @Override
    public List<Article> findByTitle(String keyword) {
        return articleRepository.findByTitle(keyword);
    }

    @Override
    public List<Article> queryByTitle(String keyword) {
        return articleRepository.queryByTitle(keyword);
    }

    @Override
    public Page<Article> findByTitle(String keyword, Pageable pageable) {
        return articleRepository.findByTitle(keyword, pageable);
    }

    @Override
    public List<Article> findByTitle(String keyword, Sort sort) {
        return articleRepository.findByTitle(keyword, sort);
    }

    @Override
    public Article index(Article article) {
        return articleRepository.index(article);
    }
}
