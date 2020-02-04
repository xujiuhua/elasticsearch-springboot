package com.example.es.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;


/**
 * <p></p>
 *
 * @author jiuhua.xu
 * @version 1.0
 * @since JDK 1.8
 */
@Data
@Document(indexName = "article", type = "_doc")
public class Article {

    private String id;

    private String title;

    @Field(type = FieldType.Keyword)
    private String author;

    public Article() {
    }

    public Article(String title) {
        this.title = title;
    }

    public Article(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
