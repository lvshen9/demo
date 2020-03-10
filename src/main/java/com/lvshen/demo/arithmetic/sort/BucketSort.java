package com.lvshen.demo.arithmetic.sort;

import org.junit.Test;

/**
 * Description: 桶排序简化版
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/8/1 17:28
 * @since JDK 1.8
 */
public class BucketSort {

	@Test
    public void test1(){
        int[] t = {5 ,3 ,5 ,2 ,8};
        bucketSort(t);
    }

	private void bucketSort(int[] t) {
		int a[] = new int[11];
		for (int i = 0; i < a.length; i++) {
			a[i] = 0;
		}

		for (int i = 0; i < t.length; i++) {
			int index = t[i];
			if (a[index] != 0) {
				a[index]++;
			} else {
				a[index] = 1;
			}
		}

		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				continue;
			}
			for (int j = 0; j < a[i]; j++) {
				System.out.println(i);
			}
		}

	}
}
