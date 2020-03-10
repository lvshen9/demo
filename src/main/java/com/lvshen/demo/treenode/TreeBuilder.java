package com.lvshen.demo.treenode;

import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/4/18 17:14
 * @since JDK 1.8
 */
public class TreeBuilder {

	List<Node> nodes = new ArrayList<Node>();

	public TreeBuilder(List<Node> nodes) {

		super();

		this.nodes = nodes;

	}

	/**
	 * 构建JSON树形结构
	 * 
	 * @return
	 */
	public String buildJSONTree() {

		List<Node> nodeTree = buildTree();

		String s = JSONArray.toJSONString(nodeTree);

		return s;

	}

	/**
	 * 构建树形结构
	 * 
	 * @return
	 */
	public List<Node> buildTree() {

		List<Node> treeNodes = new ArrayList<Node>();

		List<Node> rootNodes = getRootNodes();

		for (Node rootNode : rootNodes) {

			buildChildNodes(rootNode);

			treeNodes.add(rootNode);

		}

		return treeNodes;

	}

	/**
	 * 递归子节点
	 * 
	 * @param node
	 */
	public void buildChildNodes(Node node) {

		List<Node> children = getChildNodes(node);

		if (!children.isEmpty()) {

			for (Node child : children) {

				buildChildNodes(child);

			}

			node.setMenus(children);

		}

	}

	/**
	 * 获取父节点下所有的子节点
	 * 
	 * @param pnode
	 * @return
	 */
	public List<Node> getChildNodes(Node pnode) {

		List<Node> childNodes = new ArrayList<Node>();

		for (Node n : nodes) {

			if (pnode.getId().equals(n.getPid())) {

				childNodes.add(n);

			}

		}

		return childNodes;

	}

	/**
	 * 判断是否为根节点
	 * 
	 * @param node
	 * @return
	 */
	public boolean rootNode(Node node) {

		boolean isRootNode = true;

		for (Node n : nodes) {

			if (node.getPid().equals(n.getId())) {
				isRootNode = false;
				break;
			}
		}
		return isRootNode;

	}

	/**
	 * 获取集合中所有的根节点
	 * 
	 * @return
	 */
	public List<Node> getRootNodes() {

		List<Node> rootNodes = new ArrayList<Node>();

		for (Node n : nodes) {

			if (rootNode(n)) {
				rootNodes.add(n);
			}
		}
		return rootNodes;
	}

}