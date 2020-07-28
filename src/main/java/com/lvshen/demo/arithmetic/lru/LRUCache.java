package com.lvshen.demo.arithmetic.lru;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/7/27 21:23
 * @since JDK 1.8
 */
public class LRUCache<K,V> {
    private int size;
    private ConcurrentHashMap<K, LinkedNode> map;
    private LinkedNode head;
    private LinkedNode tail;

    LRUCache(int size) {
        this.size = size;
        map = new ConcurrentHashMap<>();
    }

    /**
     * 添加元素
     * 1.元素存在，将元素移动到队尾
     * 2.不存在，判断链表是否满。
     * 如果满，则删除队首元素，放入队尾元素，删除更新哈希表
     * 如果不满，放入队尾元素，更新哈希表
     */
    public void put(K key, V value) {
        LinkedNode node = map.get(key);
        if (node != null) {
            //更新值
            node.v = value;
            moveNodeToTail(node);
        } else {
            LinkedNode newNode = new LinkedNode(key, value);
            //链表满，需要删除首节点
            if (map.size() == size) {
                LinkedNode delHead = removeHead();
                map.remove(delHead.k);
            }
            addLast(newNode);
            map.put(key, newNode);
        }
    }

    public V get(K key) {
        LinkedNode node = map.get(key);
        if (node != null) {
            moveNodeToTail(node);
            return (V) node.v;
        }
        return null;
    }

    public void addLast(LinkedNode newNode) {
        if (newNode == null) {
            return;
        }
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            //连接新节点
            tail.next = newNode;
            newNode.pre = tail;
            //更新尾节点指针为新节点
            tail = newNode;
        }
    }

    public void moveNodeToTail(LinkedNode node) {
        if (tail == node) {
            return;
        }
        if (head == node) {
            head = node.next;
            head.pre = null;
        } else {
            //调整双向链表指针
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        node.pre = tail;
        node.next = null;
        tail.next = node;
        tail = node;
    }

    public LinkedNode removeHead() {
        if (head == null) {
            return null;
        }
        LinkedNode res = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = res.next;
            head.pre = null;
            res.next = null;
        }
        return res;
    }
    public void getMap() {
        map.forEach((key,value) -> {
            System.out.print("["+key+":"+value.v+"];");
        });
    }



}
