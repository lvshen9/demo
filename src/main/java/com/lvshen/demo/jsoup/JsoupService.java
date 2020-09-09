package com.lvshen.demo.jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import static com.lvshen.demo.jsoup.Constant.MACHINERY_SOURCE_URL;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-8 14:43
 * @since JDK 1.8
 */
public class JsoupService {

    public JSONObject parseBody(String number) throws IOException {
        Connection.Response res = Jsoup.connect(MACHINERY_SOURCE_URL + number)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=GBK")
                .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000).ignoreContentType(true).execute();
        String body = res.body();

        return JSON.parseObject(body);
    }
}
