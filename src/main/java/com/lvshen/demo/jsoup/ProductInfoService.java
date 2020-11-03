package com.lvshen.demo.jsoup;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lvshen.demo.arithmetic.snowflake.SnowFlakeGenerator;
import com.lvshen.demo.jsoup.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.lvshen.demo.jsoup.Constant.ENGINEERING_URL;
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
            String price = jsonItem.getString("price");
            String deliveryDateStr = jsonItem.getString("manufactureDate");
            String dischargeStandard = jsonItem.getString("emissionStandard");
            String brand = jsonItem.getString("brandName");
            String transfer = jsonItem.getString("transferType");
            transfer = "transfer";
            String imgUrl = jsonItem.getString("thumbnail");
            String code = jsonItem.getString("code");
            String refreshTimeStr = jsonItem.getString("refreshTime");
            Date refreshTime = DateUtils.String2Date(refreshTimeStr);
            String id = String.valueOf(new Snowflake(1,1).nextId());

            ProductInfo productInfo = ProductInfo.builder()
                    .id(id)
                    .sourceUrl(MACHINERY_URL)
                    .collectDate(nowDate)
                    .title(title)
                    .price(price)
                    .deliveryDate(deliveryDateStr)
                    .dischargeStandard(dischargeStandard)
                    .brand(brand)
                    .transferStatus(transfer)
                    .imgUrl(imgUrl)
                    .code(code)
                    .refreshTime(refreshTime)
                    .build();
            infoList.add(productInfo);

        }
        System.out.println(infoList);
    }
}
