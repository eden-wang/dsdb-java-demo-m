package com.dasudian.dsdb.demo;

import com.dasudian.dsdb.client.*;

import java.util.Date;

import static com.dasudian.dsdb.client.DsdbFilter.*;
import static com.dasudian.dsdb.client.DsdbUpdates.*;

public class DsdbDemo {
    final static String USER = "user"; /* 登录数据库的用户*/
    final static String PASSWD = "passwd"; /* 对应用户的密码 */
    final static String REMOTE_ADDR = "remote_address"; /* 服务器地址 */
    final static int PORT = 27017; /* 服务器端口号 */
    final static String AUTH_DB = "test"; /* 验证用户密码的数据库 */
    final static String DATA_DB = "test"; /* 存放数据的数据库 */
    final static String DATA_COLL = "people"; /* 存放数据的集合 */

    public static void main(String[] args) throws Exception {
        try {
            /*
             * 填写用户名, 密码, 服务器地址和端口号, 认证数据库名
             */
            DsdbClient client = new DsdbClient.Builder(USER, PASSWD,
                    REMOTE_ADDR, PORT, AUTH_DB).build();
            /* 连接服务器 */
            client.connect();
            /* 向某个数据库的某个集合中写入数据 */
            client.put(DATA_DB, DATA_COLL,
                    new DsdbDocument("createTime", new Date()) /* date类型的数据 */
                            .append("name", "mike") /* 字符串类型的数据 */
                            .append("age", 5) /* 整型数据 */
                            .append("male", true)); /* 布尔型数据 */
            client.put(DATA_DB, DATA_COLL,
                    new DsdbDocument("createTime", new Date())
                    .append("name", "jack")
                    .append("age", 10)
                    .append("male", true));
            client.put(DATA_DB, DATA_COLL,
                    new DsdbDocument("createTime", new Date())
                    .append("name", "maria")
                    .append("age", 2)
                    .append("male", false));

            /* 默认查询所有数据 */
            DsdbSearchOption searchOption = new DsdbSearchOption();
            /* 返回所有数据 */
            searchOption.rows = 0;

            DsdbSearchResult searchResult = client.search(DATA_DB, DATA_COLL, searchOption);
            /* 打印查询结果 */
            System.out.println("插入结果:");
            printSearchResult(searchResult);

            /* 更新male为true的age字段为20 */
            client.updateMany(DATA_DB, DATA_COLL, eq("male", true), set("age", 20));

            searchResult = client.search(DATA_DB, DATA_COLL, searchOption);
            /* 打印查询结果 */
            System.out.println("更新结果:");
            printSearchResult(searchResult);

            /* 断开连接 */
            client.disconnect();
        } catch (DsdbException e) {
            System.out.println(e.getMessage());
        }
    }
    /* 按照Json格式输出查询结果 */
    public static void printSearchResult(DsdbSearchResult searchResult) {
        try {
            if (searchResult != null)
                while (searchResult.hasNext())
                    System.out.println(searchResult.next().toJson());
        } catch (DsdbException e) {
            System.out.println("print error,error message:  " + e.getMessage());
        }
    }


}
