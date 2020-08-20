package com.lvshen.demo.enumtest;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-20 8:00
 * @since JDK 1.8
 */
public enum AnimalEnum implements Common {
    PANDA {
        @Override
        public String eat() {
            return "吃竹子";
        }
    },
    CAT {
        @Override
        public String eat() {
            return "吃鱼";
        }
    },
    MONKEY{
        @Override
        public String eat() {
            return "吃香蕉";
        }
    }
}
