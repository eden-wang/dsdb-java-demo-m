package com.dasudian.dsdb.demo;

import com.dasudian.dsdb.client.DsdbClient;
import com.dasudian.dsdb.client.DsdbException;
import com.dasudian.dsdb.client.DsdbSearch;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by bob on 17-4-24.
 */

public class DsdbDemo {

    private static String index = "TestingDeviceConfig";
    private static String bucketType = "TestingDeviceConfig";
    private static String bucket = "TestingDeviceConfig_0707";
    private static String key = "key01";
    private static String value = "{\"TestDeviceID\":1,\"TestDeviceName\":\"test_GO\",\"TestDeviceIPAddress\":\"test05\",\"TestDeviceIPPort\":1,\"Baudrate\":1,\"StopBit\":1,\"DataBit\":1,\"ParityCheckBit\":1}";

    public static void main(String[] args) throws Exception {
        String host = "yourServerUrl";  //服务器地址
        int port = 8087;   //设置您的端口号
        int min = 10;      //设置连接池最小值
        int max = 50;      //设置连接池最大值
        DsdbClient dsdb = new DsdbClient.Builder(host, port).withConnectionPooling(min, max).build();//创建客户端

        dsdb.connect();  //客户端建立连接

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
       // dsdb.delete(bucketType, bucket, key);


        DsdbSearch dsdbSearch = new DsdbSearch();       //创建查询类

        dsdbSearch.setIndex("TestingDeviceConfig");     //建立索引
        dsdbSearch.setCondtion("*:*");                  //建立查询条件
        dsdbSearch.setSortFiled("TestDeviceID desc");   //结果排序
        dsdbSearch.setStart(0);                         //查出的数据，从第几条开始显示
        dsdbSearch.setRows(50);                         //每页输出的结果数量

        List<String> returnFields = new ArrayList<String>();
        returnFields.add("TestDeviceID");               //设置返回的字段
        returnFields.add("TestDeviceName");
        dsdbSearch.setReturnFields(returnFields);

        dsdbSearch.setFacet("facet=true&facet.field=ParityCheckBit");                 //垂直查询

        List<Map<String, Object>> facet = dsdb.facet(dsdbSearch);
        System.out.println("facet:" + facet);

        dsdbSearch.setStats("stats=true&stats.field=Baudrate");                       //统计查询

        List<Map<String, Object>> stats = dsdb.stats(dsdbSearch);
        System.out.println("stats:" + stats);

    //    dsdbSearch.setGroup("group=true&group.field=ParityCheckBit&group.limit=100"); //分组查询

        DsdbClient.SearchResult searchResult = dsdb.search(dsdbSearch);
        for (Map<String, List<String>> r : searchResult.results) {
            System.out.println("search result:" + r);
        }

        dsdb.disconnect();
    }

}
