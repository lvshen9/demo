package com.lvshen.demo.arithmetic.search;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Description:图搜索：广度搜索（BFS）& 深度搜索（DFS）
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/9 11:08
 * @since JDK 1.8
 */
@Slf4j
public class GraphLoopTest {

    private Queue<String> queue = new LinkedList<>();
    private Map<String, Boolean> status = new HashMap<>();
    private Stack<String> stack = new Stack<>();
    private static final String START_POINT = "1";

    public static void main(String[] args){
        GraphLoopTest test = new GraphLoopTest();
        //广度优先算法
        //test.BFSSearch(START_POINT);
        //深度优先算法
        test.DFSSearch(START_POINT);
    }

    public void BFSSearch(String startPoint) {
        queue.add(startPoint);
        status.put(startPoint,false);
        bfsLoop();
    }

    public void DFSSearch(String startPoint) {
        stack.push(startPoint);
        status.put(startPoint, true);
        dfsLoop();
    }

    private void dfsLoop() {
        if (stack.isEmpty()) {
            return;
        }
        String stackTopPoint = stack.peek();
        Map<String, List<String>> graphData = initGraphData();
        List<String> neighborPoints = graphData.get(stackTopPoint);
        neighborPoints.stream().forEach(x -> {
            if (status.getOrDefault(x,false)) {
                stack.push(x);
                status.put(x, true);
                dfsLoop();
            }
        });
        String popPoint = stack.pop();
        System.out.println(popPoint);
    }

    private void bfsLoop() {
        String currentQueueHeader = queue.poll();
        status.put(currentQueueHeader, true);
        System.out.println(currentQueueHeader);

        Map<String, List<String>> graphData = initGraphData();
        List<String> neighborPoints = graphData.get(currentQueueHeader);
        neighborPoints.stream().forEach(x -> {
            if (!status.getOrDefault(x, false)) {
                if (queue.contains(x)) {
                    log.info("the point is in queue!");
                }
                queue.add(x);
                status.put(x, false);
            }
        });
        if (!queue.isEmpty()) {
            bfsLoop();
        }
    }

	private static Map<String, List<String>> initGraphData() {
		Map<String, List<String>> graph = new HashMap<>(16);

        /**
         *  初始化图数据：使用邻居表来表示图数据。
         *  图结构如下
         *         1
         *       /   \
         *      2     3
         *     / \   / \
         *     4  5  6  7
         *     \  | / \ /
         *       8    9
         */
        graph.put("1", Arrays.asList("2", "3"));
        graph.put("2", Arrays.asList("1", "4", "5"));
        graph.put("3", Arrays.asList("1", "6", "7"));
        graph.put("4", Arrays.asList("2", "8"));
        graph.put("5", Arrays.asList("2", "8"));
        graph.put("6", Arrays.asList("3", "8", "9"));
        graph.put("7", Arrays.asList("3", "9"));
        graph.put("8", Arrays.asList("4", "5", "6"));
        graph.put("9", Arrays.asList("6", "7"));

        return graph;
	}
}
