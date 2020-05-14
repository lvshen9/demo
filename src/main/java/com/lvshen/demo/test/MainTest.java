package com.lvshen.demo.test;

/**
 * Description: 二分查找
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/12 20:26
 * @since JDK 1.8
 */
public class MainTest {
    public static void main(String[] args) {
        //有序的数组
        int[] arr = {1,2,4,5,6};
        //返回索引
        int index = getIndex(arr, 4);
        System.out.println(index);
    }


    /**
     *  二分查找返回索引下标
     * @param arr   数组
     * @param key   查找的值
     * @return      返回数组索引下标
     */
    public static int getIndex(int[] arr, int key) {
        int mid = arr.length / 2;
        if (arr[mid] == key) {
            return mid;
        }
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            mid = (end - start) / 2 + start;
            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] > key) {
                end = mid - 1;
            } else {
                start = start + 1;
            }
        }
        //找不到，返回-1
        return -1;


    }
}