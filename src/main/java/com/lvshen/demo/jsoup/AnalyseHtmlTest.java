package com.lvshen.demo.jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-7 14:58
 * @since JDK 1.8
 */
public class AnalyseHtmlTest {

    @Test
    public void testCrawlMachinery() throws IOException {
        ProductInfoService service = new ProductInfoService();
        service.crawlMachineryData();
    }

    @Test
    public void test() throws IOException {
        getWukongInfo();
        //getWukongAnswer();
    }

    public void getWukongInfo() {
        // 利用Jsoup获得连接
        Connection connect = Jsoup.connect("https://www.wukong.com/question/6586953004245582083/");
        try {
            // 得到Document对象
            Document document = connect.get();

            Elements elements = document.select(".question-name");
            System.out.println(elements.get(0).text());

            Elements elements2 = document.select(".answer-item");
            for(Element element : elements2)
            {
                Elements elements3  = element.select(".answer-user-avatar img");
                System.out.println(elements3.attr("abs:src"));

                elements3  = element.select(".answer-user-name");
                System.out.println(elements3.text());

                elements3  = element.select(".answer-user-tag");
                System.out.println(elements3.text());

                elements3  = element.select(".answer-text");
                System.out.println(elements3.text());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getWukongAnswer() throws IOException {
        Connection.Response res = Jsoup.connect("https://www.wukong.com/wenda/web/nativefeed/brow/?concern_id=6300775428692904450&t=1533714730319&_signature=DKZ7mhAQV9JbkTPBachKdgyme4")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=GBK")
                .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000).ignoreContentType(true).execute();//.get();
        String body = res.body();
        System.out.println(body);
        JSONObject jsonObject2 = JSON.parseObject(body);
        JSONArray jsonArray = jsonObject2.getJSONArray("data");

        //遍历方式1
        int size = jsonArray.size();
        for (int i = 0; i < size; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.containsKey("question"))
            {
                JSONObject jsonObject3 = jsonObject.getJSONObject("question");
                String qid = jsonObject3.getString("qid");
                System.out.println(qid);
            }

        }
    }
}
