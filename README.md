## Elasticsearch SpringBoot 示例

两种方式操作elasticsearch数据
- [Elasticsearch Repositories](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.repositories)
- [Elasticsearch Operations](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.operations)

### Spring Data Repositories

#### Query Method
> Deriving the query from the method name is not always sufficient and/or may result in unreadable `method names`. In this case one might make either use of `@Query` annotation 
- 1.Query creation

```java
interface BookRepository extends Repository<Book, String> {
  List<Book> findByNameAndPrice(String name, Integer price);
}
```
The method name above will be translated into the following Elasticsearch json query
```json
{ "bool" :
    { "must" :
        [
            { "field" : {"name" : "?"} },
            { "field" : {"price" : "?"} }
        ]
    }
}
```



| keyword | Sample             | Elasticsearch Query String                                   |
| ------- | ------------------ | ------------------------------------------------------------ |
| And     | findByNameAndPrice | {"bool" : {"must" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}} |
| Or      | findByNameOrPrice  | {"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}} |
| Is      | findByName         | {"bool" : {"must" : {"field" : {"name" : "?"}}}}             |
| e.g     | e.g                | e.g                                                          |

缺点：可能存在一些不可读的方法名


- 2.Using @Query Annotation
```java
interface BookRepository extends ElasticsearchRepository<Book, String> {
    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"name\" : \"?0\"}}}}")
    Page<Book> findByName(String name,Pageable pageable);
}
```



**注意事项**

1. createIndex方法，并不会产生`mapping`；当执行save document时会根据传入的参数生成mapping,

   如果参数author值是字符串，则mapping中的author(type=text)，不是keyword; 

```java
@Data
@Document(indexName = "article", type = "_doc")
public class Article {

    private String id;

    private String title;

    @Field(type = FieldType.Keyword)
    private String author;
```

```java
template.createIndex(Article.class);
```

```json
{

    "state": "open",
    "settings": {
        "index": {
            "refresh_interval": "1s",
            "number_of_shards": "5",
            "provided_name": "article",
            "creation_date": "1580829766935",
            "store": {
                "type": "fs"
            },
            "number_of_replicas": "1",
            "uuid": "0KjtD5LZTRGF4V3zT04U3g",
            "version": {
                "created": "7050299"
            }
        }
    },
    "mappings": { },
  ...
```

执行`template.putMapping`方法后author(type=keyword)，但是另外一个问题产生，其他字段无type

```java
 template.putMapping(Article.class);
```

```json
"mappings": {

    "_doc": {
        "properties": {
            "author": {
                "type": "keyword"
            }
        }
    }
```

执行save document时会根据传入的参数更新mapping

```java
 Article article = new Article("Spring Data Elasticsearch", "tom");
 articleService.index(article);
```

```json
"mappings": {

    "_doc": {
        "properties": {
            "author": {
                "type": "keyword"
            },
            "title": {
                "type": "text",
                "fields": {
                    "keyword": {
                        "ignore_above": 256,
                        "type": "keyword"
                    }
                }
            }
        }
    }
}
```

