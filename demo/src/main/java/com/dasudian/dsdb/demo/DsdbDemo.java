package com.dasudian.dsdb.demo;

import com.dasudian.dsdb.client.DsdbClient;
import com.dasudian.dsdb.client.DsdbException;
import com.dasudian.dsdb.client.DsdbSearch;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by bob on 17-4-24.
 */

public class DsdbDemo {
    private static String index = "people";
    private static String bucketType = "people";
    private static String bucket = "people_20170525";
    private static String key = "key";
    private static String value = "{\"name\":\"Jack_bb\",\"age\":24,\"leader\":false}";

    public static void main(String[] args) throws Exception {

        /**
         *  创建客户端
         *  @param remoteAddress DSDB地址
         *  @param port          DSDB端口
         */
        DsdbClient dsdb = new DsdbClient.Builder("192.168.1.206", 8087).build();

        /**
         * 客户端建立连接
         */
        dsdb.connect();

        /**
         * 将数据存储到指定的bucket和bucketType中
         *
         * @param bucketType 这是一个命名空间
                             数据存放的bucket type。可以为null，如果传null，则将数据保存到默认的bucketType中。
         * @param bucket     这是bucketType子一层命名空间
                             比如：bucketType代表一个脊椎动物类，那么bucket就可以是鱼类,爬行类,鸟类,两栖类,哺乳类
                                  bucketType代表一个汽车,那么bucket就可以是跑车，拖拉机等。
                             数据存放的bucket。
         * @param key        数据的key，必须是唯一的标识符。
         * @param value      要存储的数据内容。
         * @throws DsdbException 失败时抛出异常
         */
        dsdb.put(bucketType, bucket, key, value);

        /**
         * 读取数据
         *
         * @param bucketType 数据所在的bucket type。可以为null，如果传null，则从默认的bucket type中读取数据。
         * @param bucket     数据所在的bucket
         * @param key        数据对应的key
         * @return 返回读到的数据。如果该key没有数据，则返回null。
         * @throws DsdbException 失败时抛出异常
         */
        String res = dsdb.get(bucketType, bucket, key);

        System.out.println(res);

        /**
         * 刪除数据
         *
         * @param bucketType 数据所在的bucket type。可以为null，如果传null，则从默认的bucket type中删除数据。
         * @param bucket     数据所在的bucket
         * @param key        数据对应的key
         * @throws DsdbException 失败时抛出异常
         */
//        dsdb.delete(bucketType, bucket, key);

        /**
         * 创建查询类
         */
        DsdbSearch ds = new DsdbSearch();

        /**
         * 建立索引
         * @param index
         */
        ds.setIndex(index);

        /**
         * 建立查询条件
         * @param condtion
         */
        ds.setCondtion("*:*");

        /**
         * 得到一个SearchResult对象
         */
        DsdbClient.SearchResult sr = dsdb.search(ds);

        System.out.println(sr.results);

        /**
         * 断开连接
         */
        dsdb.disconnect();
    }

}