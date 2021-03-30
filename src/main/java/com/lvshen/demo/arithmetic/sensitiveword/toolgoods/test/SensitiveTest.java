package com.lvshen.demo.arithmetic.sensitiveword.toolgoods.test;

import com.lvshen.demo.arithmetic.sensitiveword.toolgoods.StringSearch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-3-30 11:00
 * @since JDK 1.8
 */
public class SensitiveTest {


    @Test
    public void test1() {
        String test = "我是中国人";
        List<String> list = new ArrayList<>();

        list.add("中国");
        list.add("国人");
        list.add("zg人");
        System.out.println("StringSearch run Test.");

        StringSearch iwords = new StringSearch();
        iwords.SetKeywords(list);

        boolean b = iwords.ContainsAny(test);

        String f = iwords.FindFirst(test);

        List<String> all = iwords.FindAll(test);
        System.out.println(f);
        System.out.println(b);
    }

    @Test
    public void test2() {

        String test = "韩立被村里人叫作“二愣子”，可人并不是真愣真傻，反而是村中首屈一指的聪明孩子，但" +
                "就像其他村中的孩子一样，除了家里人外，他就很少听到有人正式叫他名字“韩立”，倒是“二愣子”“二愣子”的称呼一直伴随" +
                "至今。而之所以被人起了个“二愣子”的绰号，也只不过是因为村里已有一个叫“愣子”的孩子了。这也没啥，村里" +
                "·的其他孩子也是“狗娃”“二蛋”之类的被人一直称呼着，这些名字也不见得比“二愣子”好听了哪里去。";
        System.out.println("待检测语句字数：" + test.length());
        List<String> list = new ArrayList<>();

        list.add("二愣子");
        list.add("二蛋");
        list.add("狗娃");
        StringSearch iwords = new StringSearch();
        iwords.SetKeywords(list);
        List<String> all = iwords.FindAll(test);

        long beginTime = System.currentTimeMillis();
        String str = iwords.Replace(test, '*');
        long endTime = System.currentTimeMillis();

        System.out.println("总共消耗时间为：" + (endTime - beginTime));
        System.out.println(str);
    }
}
