package com.lvshen.demo.yjh.java8.sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description: 运动员
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-12-29 10:26
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sportsman {
    private String name;
    /**
     * 身高 cm
     */
    private Integer height;

    public static int compareByNameThenHigt(Sportsman s1, Sportsman s2) {
        if (s1.name.equals(s2.name)) {
            return Integer.compare(s1.height, s2.height);
        } else {
            return s1.name.compareTo(s2.name);
        }
    }
}
