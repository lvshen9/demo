package com.lvshen.demo.arithmetic.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: 排序算法
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/5 15:27
 * @since JDK 1.8
 */
public class SortTest {
	private static int[] array = { 5, 4, 3, 2, 1 };
	private static int len;

	@Test
	public void test1() {
		// 1.插入排序
		// printArrays(insertionSort(array));
		// 2.冒泡排序
		// printArrays(bubbleSort(array));
		// 3.选择排序
		// printArrays(selectionSort(array));
		// 4.希尔排序
		// printArrays(shellSort(array));
		// 5.快速排序
		printArrays(quickSort(array,0,4));
		/*// 6.归并排序
		printArrays(mergeSort(array));
		// 7.计数排序
		printArrays(countingSort(array));
		// 8.堆排序
		printArrays(heapSort(array));*/
		// 9.桶排序
		// printLists(bucketSort(Ints.asList(array), 5));
		//10.基数排序
		// printArrays(radixSort(array));
	}

	/**
	 * 1. 插入排序
	 * a.从第一个元素开始，该元素可以认为已经被排序；
	 * b.取出下一个元素，在已经排序的元素序列中从后向前扫描；
	 * c.如果该元素（已排序）大于新元素，将该元素移到下一位置；
	 * d.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
	 * e.将新元素插入到该位置后；
	 * f.重复步骤b~e。
	 * 
	 * @param array
	 * @return
	 */
	private static int[] insertionSort(int[] array) {
		if (array.length == 0) {
			return array;
		}
		int current;
		for (int i = 0; i < array.length - 1; i++) {
			current = array[i + 1];
			int preIndex = i;
			while (preIndex >= 0 && current < array[preIndex]) {
				array[preIndex + 1] = array[preIndex];
				preIndex--;
			}
			array[preIndex + 1] = current;
		}
		return array;
	}

	/**
	 * 2. 冒泡排序
	 * a.比较相邻的元素。如果第一个比第二个大，就交换它们两个；
	 * b.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
	 * c.针对所有的元素重复以上的步骤，除了最后一个；
	 * d.重复步骤a~c，直到排序完成。
	 * @param array
	 * @return
	 */
	public static int[] bubbleSort(int[] array) {
		if (array.length == 0) {
			return array;
		}
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j + 1] < array[j]) {
					int temp = array[j + 1];
					array[j + 1] = array[j];
					array[j] = temp;
				}
			}
		}
		return array;
	}

	/**
	 * 3. 选择排序（性能最稳定的排序算法之一）
	 * 初始状态：无序区为R[1..n]，有序区为空；
	 * 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录 R[k]，
	 * 将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
	 * n-1趟结束，数组有序化了
	 * @param array
	 * @return
	 */
	public static int[] selectionSort(int[] array) {
		if (array.length == 0) {
			return array;
		}
		for (int i = 0; i < array.length; i++) {
			int minIndex = i;
			for (int j = i; j < array.length; j++) {
				// 找到最小的数
				if (array[j] < array[minIndex]) {
					// 将最小数的索引保存
					minIndex = j;
				}
			}
			int temp = array[minIndex];
			array[minIndex] = array[i];
			array[i] = temp;
		}
		return array;
	}

	/**
	 * 4.希尔排序
	 *
	 * @param array
	 * @return
	 */
	public static int[] shellSort(int[] array) {
		int len = array.length;
		int temp, gap = len / 2;
		while (gap > 0) {
			for (int i = gap; i < len; i++) {
				temp = array[i];
				int preIndex = i - gap;
				while (preIndex >= 0 && array[preIndex] > temp) {
					array[preIndex + gap] = array[preIndex];
					preIndex -= gap;
				}
				array[preIndex + gap] = temp;
			}
			gap /= 2;
		}
		return array;
	}

	/**
	 * 5.归并排序
	 *
	 * @param array
	 * @return
	 */
	public static int[] mergeSort(int[] array) {
		if (array.length < 2) {
			return array;
		}
		int mid = array.length / 2;
		int[] left = Arrays.copyOfRange(array, 0, mid);
		int[] right = Arrays.copyOfRange(array, mid, array.length);
		return merge(mergeSort(left), mergeSort(right));
	}

	/**
	 * 6.快速排序方法
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	public static int[] quickSort(int[] array, int start, int end) {
		if (array.length < 1 || start < 0 || end >= array.length || start > end) {
			return null;
		}
		int smallIndex = partition(array, start, end);
		if (smallIndex > start) {
			quickSort(array, start, smallIndex - 1);
		}
		if (smallIndex < end) {
			quickSort(array, smallIndex + 1, end);
		}
		return array;
	}
	/**
	 * 7.堆排序算法
	 *
	 * @param array
	 * @return
	 */
	public static int[] heapSort(int[] array) {
		len = array.length;
		if (len < 1) {
			return array;
		}
		//1.构建一个最大堆
		buildMaxHeap(array);
		//2.循环将堆首位（最大值）与末位交换，然后在重新调整最大堆
		while (len > 0) {
			swap(array, 0, len - 1);
			len--;
			adjustHeap(array, 0);
		}
		return array;
	}
	/**
	 * 8.计数排序
	 *
	 * @param array
	 * @return
	 */
	public static int[] countingSort(int[] array) {
		if (array.length == 0) {
			return array;
		}
		int bias, min = array[0], max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}
		bias = 0 - min;
		int[] bucket = new int[max - min + 1];
		Arrays.fill(bucket, 0);
		for (int i = 0; i < array.length; i++) {
			bucket[array[i] + bias]++;
		}
		int index = 0, i = 0;
		while (index < array.length) {
			if (bucket[i] != 0) {
				array[index] = i - bias;
				bucket[i]--;
				index++;
			} else {
				i++;
			}
		}
		return array;
	}

	/**
	 * 9.桶排序
	 * 人为设置一个BucketSize，作为每个桶所能放置多少个不同数值（例如当BucketSize==5时，该桶可以存放｛1,2,3,4,5｝这几种数字，但是容量不限，即可以存放100个3）；
	 * 遍历输入数据，并且把数据一个一个放到对应的桶里去；
	 * 对每个不是空的桶进行排序，可以使用其它排序方法，也可以递归使用桶排序；
	 * 从不是空的桶里把排好序的数据拼接起来。
	 *
	 * @param array
	 * @param bucketSize
	 * @return
	 */
	public static List<Integer> bucketSort(List<Integer> array, int bucketSize) {
		if (array == null || array.size() < 2) {
			return array;
		}
		int max = array.get(0), min = array.get(0);
		// 找到最大值最小值
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i) > max) {
				max = array.get(i);
			}
			if (array.get(i) < min) {
				min = array.get(i);
			}
		}
		int bucketCount = (max - min) / bucketSize + 1;
		List<List<Integer>> bucketArr = new ArrayList<>(bucketCount);
		List<Integer> resultArr = new ArrayList<>();
		for (int i = 0; i < bucketCount; i++) {
			bucketArr.add(new ArrayList<>());
		}
		for (int i = 0; i < array.size(); i++) {
			bucketArr.get((array.get(i) - min) / bucketSize).add(array.get(i));
		}
		for (int i = 0; i < bucketCount; i++) {
			// 如果带排序数组中有重复数字时
			if (bucketSize == 1) {
				for (int j = 0; j < bucketArr.get(i).size(); j++) {
					resultArr.add(bucketArr.get(i).get(j));
				}
			} else {
				if (bucketCount == 1) {
					bucketSize--;
				}
				List<Integer> temp = bucketSort(bucketArr.get(i), bucketSize);
				for (int j = 0; j < temp.size(); j++) {
					resultArr.add(temp.get(j));
				}
			}
		}
		return resultArr;
	}

	/**
	 * 10.基数排序
	 * 取得数组中的最大数，并取得位数；
	 * arr为原始数组，从最低位开始取每个位组成radix数组；
	 * 对radix进行计数排序（利用计数排序适用于小范围数的特点）；
	 * @param array
	 * @return
	 */
	public static int[] radixSort(int[] array) {
		if (array == null || array.length < 2) {
			return array;
		}
		// 1.先算出最大数的位数；
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			max = Math.max(max, array[i]);
		}
		int maxDigit = 0;
		while (max != 0) {
			max /= 10;
			maxDigit++;
		}
		int mod = 10, div = 1;
		ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 10; i++) {
			bucketList.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < maxDigit; i++, mod *= 10, div *= 10) {
			for (int j = 0; j < array.length; j++) {
				int num = (array[j] % mod) / div;
				bucketList.get(num).add(array[j]);
			}
			int index = 0;
			for (int j = 0; j < bucketList.size(); j++) {
				for (int k = 0; k < bucketList.get(j).size(); k++) {
					array[index++] = bucketList.get(j).get(k);
				}
				bucketList.get(j).clear();
			}
		}
		return array;
	}

	/**
	 * 建立最大堆
	 *
	 * @param array
	 */
	private static void buildMaxHeap(int[] array) {
		//从最后一个非叶子节点开始向上构造最大堆
		//感谢 @让我发会呆 网友的提醒，此处应该为 i = (len/2 - 1)
		for (int i = (len/2 - 1); i >= 0; i--) {
			adjustHeap(array, i);
		}
	}
	/**
	 * 调整使之成为最大堆
	 *
	 * @param array
	 * @param i
	 */
	private static void adjustHeap(int[] array, int i) {
		int maxIndex = i;
		//如果有左子树，且左子树大于父节点，则将最大指针指向左子树
		if (i * 2 < len && array[i * 2] > array[maxIndex]) {
			maxIndex = i * 2;
		}
		//如果有右子树，且右子树大于父节点，则将最大指针指向右子树
		if (i * 2 + 1 < len && array[i * 2 + 1] > array[maxIndex]) {
			maxIndex = i * 2 + 1;
		}
		//如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
		if (maxIndex != i) {
			swap(array, maxIndex, i);
			adjustHeap(array, maxIndex);
		}
	}
	/**
	 * 快速排序算法——partition
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	private static int partition(int[] array, int start, int end) {
		int pivot = (int) (start + Math.random() * (end - start + 1));
		int smallIndex = start - 1;
		swap(array, pivot, end);
		for (int i = start; i <= end; i++) {
			if (array[i] <= array[end]) {
				smallIndex++;
				if (i > smallIndex) {
					swap(array, i, smallIndex);
				}
			}
		}
		return smallIndex;
	}

	/**
	 * 交换数组内两个元素
	 * @param array
	 * @param i
	 * @param j
	 */
	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	/**
	 * 归并排序——将两段排序好的数组结合成一个排序数组
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	private static int[] merge(int[] left, int[] right) {
		int[] result = new int[left.length + right.length];
		for (int index = 0, i = 0, j = 0; index < result.length; index++) {
			if (i >= left.length) {
				result[index] = right[j++];
			} else if (j >= right.length) {
				result[index] = left[i++];
			} else if (left[i] > right[j]) {
				result[index] = right[j++];
			} else {
				result[index] = left[i++];
			}
		}
		return result;
	}

	private void printArrays(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	private void printLists(List<Integer> arrays) {
		arrays.stream().forEach(x -> {
			System.out.print(x + " ");
		});
		System.out.println();
	}
}
