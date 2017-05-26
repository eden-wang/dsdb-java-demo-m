# Dasudian dsdb-java-sdk

1. [创建](#create)
2. [连接](#connect)
3. [断开连接](#disconnect)
4. [存储](#put)
5. [删除](#delete)
6. [读取数据](#get)
7. [查询数据](#search)
8. [查询类](#DsdbSearch)
9. [查询结果类](#SearchResult)
10. [DSDB异常类](#DsdbException)

## <a name="create">创建</a>

通过DsdbClient.Builder创建DsdbClient实例。

```
public static class Builder {

    /**
     * 设置DSDB的地址和端口
     *
     * @param remoteAddress DSDB地址
     * @param port          DSDB端口
     */
    public Builder(String remoteAddress, int port) {
        ...
    }

    /**
     * 设置连接池的最大最小值
     * 不填默认值为 10 , 50
     * @param min 最小值
     * @param max 最大值
     */
    public Builder withConnectionPooling(int min, int max) {
        ...
    }

    /**
     * 生成DSDB客户端实例
     *
     * @return DsdbClient
     */

    public DsdbClient build() {
        ...
    }

}
```

## <a name="connect">连接</a>

```
/**
 * 连接DSDB
 *
 * @throws DsdbException 失败时抛出异常
 */
public void connect() throws DsdbException{
    ...
}
```

## <a name="disconnect">断开连接</a>

```
/**
 * 断开与DSDB的连接
 */
public void disconnect() {
    ...
}
```

## <a name="put">存储</a>

```
/**
 * 将数据存储到指定的bucket和bucket type中
 *
 * @param bucketType 这是一个命名空间
                     数据存放的bucket type。可以为null，如果传null，则将数据保存到默认的bucket type中。
 * @param bucket     这是bucketType子一层命名空间
                     比如：bucketType代表一个脊椎动物类，那么bucket就可以是鱼类,爬行类,鸟类,两栖类,哺乳类
                          bucketType代表一个汽车,那么bucket就可以是跑车
                     数据存放的bucket。
 * @param key        数据的key，必须是唯一的标识符。
 * @param value      要存储的数据内容。
 * @throws DsdbException 失败时抛出异常
 */

public void put(String bucketType, String bucket, String key, String value) throws DsdbException {
    ...
}
```

## <a name="delete">删除</a>

```
/**
 * 刪除数据
 *
 * @param bucketType 数据所在的bucket type。可以为null，如果传null，则从默认的bucket type中删除数据。
 * @param bucket     数据所在的bucket
 * @param key        数据对应的key
 * @throws DsdbException 失败时抛出异常
 */
public void delete(String bucketType, String bucket, String key) throws DsdbException {
    ...
}
```

## <a name="get">读取数据</a>

```
/**
 * 读取数据
 *
 * @param bucketType 数据所在的bucket type。可以为null，如果传null，则从默认的bucket type中读取数据。
 * @param bucket     数据所在的bucket
 * @param key        数据对应的key
 * @return 返回读到的数据。如果该key没有数据，则返回null。
 * @throws DsdbException 失败时抛出异常
 */

public String get(String bucketType, String bucket, String key) throws DsdbException {

}
```

## <a name="search">查询数据</a>

```
/**
 * 查询数据
 * @param DsdbSearch
 */
public DsdbClient.SearchResult search(DsdbSearch ds) throws DsdbException {
    ....
}

```

## <a name="DsdbSearch">查询类</a>

```
/**
 * 查询类
 */
public class DsdbSearch {

    /**
     * 建立索引
     * @param index
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * 建立查询条件
     * @param condtion
     */
    public void setCondtion(String condtion) {
        ...
    }
    public DsdbSearch() {
        ...
    }

}
```

## <a name="SearchResult">查询结果类</a>

```
/**
 * 查询结果类
 */
public class SearchResult {

    /**
     * 建立查询条件
     * @param this$0
     * @param numResults
     */
    public SearchResult(int this$0, List<Map<String, List<String>>> numResults) {
        ...
    }
}
```

## <a name="DsdbException">DSDB异常类</a>

```
public class DsdbException extends Exception {
    ...
}
```
