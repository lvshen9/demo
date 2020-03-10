package com.lvshen.demo.arithmetic.queue;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Description:算法 新学期开始了，小哈是小哼的新同桌（小哈是个小美女哦~），小哼向小哈询问 QQ 号， 小哈当然不会直接告诉小哼啦，原因嘛你懂的。所以小哈给了小哼一串加密过的数字，同
 * 时小哈也告诉了小哼解密规则。规则是这样的：首先将第 1 个数删除，紧接着将第 2 个数放到这串数的末尾，再将第 3个数删除并将第 4 个数再放到这串数的末尾，再将第 5
 * 个数删除……直到剩下最后一个数，将最后一个数也删除。按照刚才删除的顺序，把这些 删除的数连在一起就是小哈的 QQ 啦。现在你来帮帮小哼吧。小哈给小哼加密过的一串数 是“6 3 1 75 8 9 2 4”。
 * 
 * @author yuange
 * @version 1.0
 * @date: 2019/11/4 14:23
 * @since JDK 1.8
 */
public class QueueArithmetic {

    /**
     * 采用集合方法
     */
    @Test
	public void test1() {
		Integer[] q = { 0, 6, 3, 1, 7, 5, 8, 9, 2, 4 };
		List<Integer> list = Lists.newArrayList(Arrays.asList(q));
		List<Integer> result = Lists.newArrayList();
		int head;
		int tail;

		head = 0;
		tail = list.size();
		while (head < tail - 2) {
			System.out.println(list.get(head));
			head = head + 2;

			list.add(list.get(head));
			tail++;
		}
		System.out.println(list.toString());

		for (int i = 1; i < list.size(); i += 2) {
			result.add(list.get(i));
		}
		System.out.println(result.toString());
	}

    /**
     * 采用队列方法
     */
	@Test
	public void test2() {
		Queue<Integer> queue = new LinkedList<>();
        Integer[] q = { 6, 3, 1, 7, 5, 8, 9, 2, 4 };
		List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < q.length; i++) {
            queue.offer(q[i]);
        }

		while (queue.element() != null) {
			list.add(queue.poll());
			queue.offer(queue.poll());
            System.out.println(queue);
        }
        System.out.println(list.toString());
	}

}
