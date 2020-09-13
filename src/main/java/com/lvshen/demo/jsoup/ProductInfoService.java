package com.lvshen.demo.jsoup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lvshen.demo.jsoup.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.lvshen.demo.jsoup.Constant.MACHINERY_URL;

/**
 * Description: 三个网站数据爬取
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-7 17:43
 * @since JDK 1.8
 */
@Slf4j
public class ProductInfoService {

    /**
     * 爬取118机械网的数据
     */
    public void crawlMachineryData() throws IOException {
        List<ProductInfo> infoList = Lists.newArrayList();
        int count = 1;
        /*for(;;) {
            count ++;
            if() {

                break;
            }
        }*/
        String number = "1";
        JSONObject jsonBody = new JsoupService().parseBody(number);
        JSONArray jsonItems = jsonBody.getJSONArray("items");
        Date nowDate = DateUtils.getCurrentDate();
        int size = jsonItems.size();
        for (int i = 0; i < size; i++){
            JSONObject jsonItem = jsonItems.getJSONObject(i);
            String title = jsonItem.getString("title");
            String priceStr = jsonItem.getString("price");
            BigDecimal price = new BigDecimal(priceStr);
            String manufactureDateStr = jsonItem.getString("manufactureDate");


            ProductInfo productInfo = ProductInfo.builder()
                    .sourceUrl(MACHINERY_URL)
                    .collectDate(nowDate)
                    .title(title)
                    .price(price)
                    .deliveryDate(manufactureDateStr)
                    .build();
            productInfo.setSourceUrl(MACHINERY_URL);
            infoList.add(productInfo);
        }
        System.out.println(infoList);
    }
}
