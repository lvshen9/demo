package com.lvshen.demo.arithmetic.snowflake;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/10/30 15:57
 * @since JDK 1.8
 */
@Component
public class Test {

    @org.junit.Test
    public void test1() throws ParseException {
        String id = new SnowFlakeGenerator().nextId();
        System.out.println("id生成成功：" + id + ",长度：" + id.length());
    }

    /*@org.junit.Test
    public void test2() {
        Date formatDate = DateTimeUtils.getFormatDate("2081-01-01");
        long time = formatDate.getTime();
        System.out.println(time);
    }*/
}
