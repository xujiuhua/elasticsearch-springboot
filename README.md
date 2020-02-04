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

