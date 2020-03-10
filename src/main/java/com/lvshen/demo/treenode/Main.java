package com.lvshen.demo.treenode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/4/18 17:32
 * @since JDK 1.8
 */
public class Main {

    public static void main(String[] args) {

        List<Node> nodes = new ArrayList<Node>();

        Node p1 = new Node("01", "","01", "");
        Node p6 = new Node("02", "","02", "");
        Node p7 = new Node("0201", "02","0201", "");
        Node p2 = new Node("0101", "01","0101", "");
        Node p3 = new Node("0102", "01","0102", "");
        Node p4 = new Node("010101", "0101","010101", "");
        Node p5 = new Node("010102", "0101","010102", "");
        Node p8 = new Node("03", "","03", "");

        nodes.add(p1);
        nodes.add(p2);
        nodes.add(p3);
        nodes.add(p4);
        nodes.add(p5);
        nodes.add(p6);
        nodes.add(p7);
        nodes.add(p8);

        //解析Node里面的数据
        TreeBuilder treeBuilder = new TreeBuilder(nodes);
        System.out.println(treeBuilder.buildJSONTree());

    }
}
