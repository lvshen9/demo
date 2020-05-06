package com.lvshen.demo.test;

import org.junit.Test;

/**
 * Description:给出有序数组(非递减)和闭区间, 找出数组中在区间之内的元素起始位置和结束位置
 * 输入：
 * 1. 有序数组[1,1,2,3,4,5,5]
 * 2. 闭区间[-3,3]
 * 输出：[0,3]
 * 解释：在数组中，前4个元素在区间之内，则起始位置为0，结束位置为3
 * 要求：最坏情况时间复杂度小于O(n)
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/27 18:56
 * @since JDK 1.8
 */

public class TxTest {

    public static void main(String[] args) {
        int[] arrays = {-10, 1, 2, 3, 4, 5, 5};
        fun1(arrays, -3, 3);
    }

    public static void fun1(int[] arrays, int start, int end) {
        int startIndex = startBound(arrays, start);
        int endIndex = endBound(arrays, end);
        System.out.println("[" + startIndex + "," + endIndex + "]");
    }

    private static int startBound(int[] arrays, int key) {
        int start = 0;
        int last = arrays.length;
        while (start < last) {
            int mid = (start + last) / 2;
            if (arrays[mid] >= key) {
                last = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    private static int endBound(int[] arrays, int key) {
        int start = 0;
        int last = arrays.length;
        while (start < last) {
            int mid = (start + last) / 2;
            if (arrays[mid] <= key) {
                start = mid + 1;
            } else {
                last = mid;
            }
        }
        return start - 1;

    }
    //二分法查找 时间复杂度最坏为O(logN)

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
        return start;
    }

    //时间复杂度最坏为O(n)
    public static int inset(int a, int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (a > nums[i]) {
                index++;
            }
        }
        int[] newArray = new int[nums.length + 1];
        newArray[index] = a;
        return index;
    }

    @Test
    public void test() {
        int[] arrays = {1, 1, 2, 3, 4, 5, 5};
        System.out.println(startBound(arrays, 1));
        System.out.println(endBound(arrays, 3));
    }
}

