package com.lvshen.demo.sms;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/9/18 16:59
 * @since JDK 1.8
 */
public class ConvertToCapitalUsingStreams {
    // collection holds all the words that are not to be capitalized
    private static final List<String> EXCLUSION_LIST = Arrays.asList(new String[]{"or"});

    public String convertToInitCase(final String data) {
        String[] words = data.split("\\s+");
        List<String> initUpperWords = Arrays.stream(words).map(word -> {
            //first make it lowercase
            return word.toLowerCase();
        }).collect(Collectors.toList());

        // convert back the list of words into a single string
        String finalWord = String.join(" ", initUpperWords);

        return finalWord;
    }

    public static void main(String[] a) {
        System.out.println(new ConvertToCapitalUsingStreams().convertToInitCase("TAXI or bus driver"));

    }
}
