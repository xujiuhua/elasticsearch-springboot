package com.example.es.repository;

import com.example.es.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p></p>
 *
 * @author jiuhua.xu
 * @version 1.0
 * @since JDK 1.8
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    List<Article> findByTitleLike(String keyword);

    List<Article> findByTitle(String keyword);

    @Query("{\"bool\" : {\"must\" : {\"match\" : {\"title\" : \"?0\"}}}}")
    List<Article> queryByTitle(String keyword);

    Page<Article> findByTitle(String keyword, Pageable pageable);

    List<Article> findByTitle(String keyword, Sort sort);
}
