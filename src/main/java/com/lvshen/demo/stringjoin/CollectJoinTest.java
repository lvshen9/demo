package com.lvshen.demo.stringjoin;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-11-28 16:14
 * @since JDK 1.8
 */
public class CollectJoinTest {
    // 定义人名数组
    private static final String[] names = {"我", "大意了啊", "年轻人", "不讲武德", "来骗"};

    @Test
    public void test1() {
        Stream<String> stream1 = Stream.of(names);
        Stream<String> stream2 = Stream.of(names);
        Stream<String> stream3 = Stream.of(names);
        // 拼接成 [x, y, z] 形式
        String result1 = stream1.collect(Collectors.joining(", ", "[", "]"));
        // 拼接成 x | y | z 形式
        String result2 = stream2.collect(Collectors.joining(" | ", "", ""));
        // 拼接成 x -> y -> z] 形式
        String result3 = stream3.collect(Collectors.joining(" -> ", "", ""));
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    @Test
    public void test2() {
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        for (String name : names) {
            stringJoiner.add(name);
        }
        System.out.println(stringJoiner.toString());
    }

    @Test
    public void test3() {
        String str = String.join(",", names[0], names[1], names[2], names[3], names[4]);
        System.out.println(str);
    }

    @Test
    public void test4() {
        StringBuilder sb = new StringBuilder();
        sb.append(names[0]);
        sb.append(",");
        sb.append(names[1]);
        sb.append(",");
        sb.append(names[2]);
        sb.append(",");
        sb.append(names[3]);
        sb.append(",");
        sb.append(names[4]);
        System.out.println(sb.toString());

        StringBuilder sb2 = new StringBuilder();
        int length = names.length;
        for (int i = 0; i < length; i++) {
            sb2.append(names[i]);
            if (i != length - 1) {
                sb2.append(",");
            }
        }
        System.out.println(sb2.toString());
    }

    @Test
    public void test5() {
        String str = names[0] +","+ names[1] +","+ names[2] +","+ names[3] +","+ names[4];
        System.out.println(str);
    }

    @Test
    public void test6() {
        Joiner joiner = Joiner.on(",").skipNulls();
        String join = joiner.join("公众号", null, "Lvshen的技术小屋");
        System.out.println("join:" + join);

        Joiner joiner1 = Joiner.on(",").useForNull("");
        String content1 = joiner1.join("公众号", null, "Lvshen的技术小屋", "欢迎订阅");
        System.out.println("content1:" + content1);

        System.out.println(Joiner.on("-").join("a", "b", "c", "d"));
        System.out.println(Joiner.on("-").join(Lists.newArrayList("a", "b", "c", "d")));
    }
}
