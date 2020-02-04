package com.example.es;

import com.example.es.model.Article;
import com.example.es.model.Author;
import com.example.es.service.ArticleService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private ElasticsearchRestTemplate template;

	@Autowired
	private ArticleService articleService;

	Article article;

	@BeforeEach
	void beforeEach() {
		template.deleteIndex(Article.class);
		template.createIndex(Article.class);
	}

	@Test
	void testSave() {
		article = this.saveAndGet();
		Assertions.assertNotNull(article.getId());
	}

	@Test
	void testDel() {
		article = this.saveAndGet();
		Assertions.assertNotNull(article.getId());
		articleService.removeById(article.getId());
	}

	@Test
	void testGetById() {
		article = this.saveAndGet();
		Optional<Article> optionalArticle = articleService.findById(article.getId());
		Assertions.assertTrue(optionalArticle.isPresent());
		System.out.println(optionalArticle.get());
	}

	@Test
	void modifyById() {
		article = this.saveAndGet();
		// springboot 未提供更新方法，调用保存即可
		article.setTitle("我更新了一次title");
		articleService.save(article);
		Assertions.assertNotNull(article.getId());
	}

	@Test
	void findAll() {
		this.saveAndGet();
		Iterable<Article> iterable = articleService.findAll();
		List<Article> list = Lists.newArrayList();
		iterable.forEach(list::add);
		Assertions.assertEquals(1, list.size());
	}

	private Article saveAndGet() {
		Article article = new Article("Spring Data Elasticsearch");
		return articleService.save(article);
	}
}
