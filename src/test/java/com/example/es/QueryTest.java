package com.example.es;

import com.example.es.model.Article;
import com.example.es.service.ArticleService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * <p></p>
 *
 * @author jiuhua.xu
 * @version 1.0
 * @since JDK 1.8
 */
@SpringBootTest
public class QueryTest {

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    void beforeEach() throws IOException {
        template.deleteIndex(Article.class);

        template.createIndex(Article.class);
        template.putMapping(Article.class);

        Article article = new Article("Spring Data Elasticsearch", "tom");
        articleService.index(article);

        article = new Article("SpringBoot", "jack");
        articleService.save(article);

        article = new Article("Java", "eric");
        articleService.save(article);

        article = new Article("Linux", "jack");
        articleService.save(article);

        article = new Article("ElasticSearch", "lucy");
        articleService.save(article);
    }

    @Test
    void findByTitleLike() {

        List<Article> list = articleService.findByTitleLike("spring");
        Assertions.assertEquals(1, list.size());

        list = articleService.findByTitleLike("data");
        Assertions.assertEquals(1, list.size());

        list = articleService.findByTitleLike("elasticsearch");
        Assertions.assertEquals(2, list.size());

        list = articleService.findByTitleLike("other");
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void findByTitle() {

        List<Article> list = articleService.findByTitle("spring");
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void queryByTitle() {
        List<Article> list = articleService.queryByTitle("data");
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void findByTitlePage() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Article> page = articleService.findByTitle("elasticsearch", pageable);
        Assertions.assertEquals(1L, page.getNumberOfElements());

        pageable = PageRequest.of(0, 2);
        page = articleService.findByTitle("elasticsearch", pageable);
        Assertions.assertEquals(2L, page.getNumberOfElements());

        // 不分页Pageable.unpaged()
        page = articleService.findByTitle("elasticsearch", Pageable.unpaged());
        Assertions.assertEquals(2L, page.getTotalElements());
    }

    @Test
    void findByTitleSort() {
        Sort.TypedSort<Article> article = Sort.sort(Article.class);
        Sort sort = article.by(Article::getAuthor).ascending();

        List<Article> list = articleService.findByTitle("elasticsearch", sort);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("lucy", list.get(0).getAuthor());
        Assertions.assertEquals("tom", list.get(1).getAuthor());
    }

}
