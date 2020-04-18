package com.lvshen.demo.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/18 15:44
 * @since JDK 1.8
 */
public class Main {

    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        //String s = scanner.nextLine();
        String s = "ABCA";
        System.out.println(mainFun(s));
    }

    public static int mainFun(String S) {
        int length = S.length();
        int parentResult = arithMetic(length);
        int childResult = 1;
        HashSet<String> sets = fun(S);

        if (CollectionUtils.isEmpty(sets)) {
            return parentResult / childResult;
        }

        for(String set : sets){
            int length1 = set.length();
            childResult = childResult * length1;
        }

        return parentResult / childResult;
    }


    public static HashSet<String> fun(String S) {
        List<String> list = Lists.newArrayList();
        StringBuffer sb = new StringBuffer();
        char[] chars = S.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j <= chars.length - 1; j++) {
                if (chars[i] == chars[j]) {
                    sb.append(chars[i]).append(chars[j]);
                }
            }
            list.add(sb.toString());
        }
        HashSet<String> sets = Sets.newHashSet(list);
        return sets;
    }

    public static int arithMetic(int num) {
        int add = 1;
        for (int i = 1; i <= num; i++) {
            add = add * i;
        }
        return add;
    }
}
