package com.lvshen.demo.dynamicstree;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Date;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/5/9 9:30
 * @since JDK 1.8
 */
public class Branch {
	DefaultMutableTreeNode r; // DefaultMutableTreeNode是树的数据结构中的通用节点,节点也可以有多个子节点。

	public Branch(String[] data) {
		r = new DefaultMutableTreeNode(data[0]);
		for (int i = 1; i < data.length; i++) {
			r.add(new DefaultMutableTreeNode(data[i]));
		}
		// 给节点r添加多个子节点
	}

	public DefaultMutableTreeNode node() {// 返回节点
		return r;
	}

}
