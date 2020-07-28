package com.lvshen.demo.arithmetic.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/7/27 22:25
 * @since JDK 1.8
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private int capacity;

    LRULinkedHashMap(int capacity) {
        // 初始大小，0.75是装载因子，true是表示按照访问时间排序
        super(capacity, 0.75f, true);
        //传入指定的缓存最大容量
        this.capacity = capacity;
    }

    /**
     * 实现LRU的关键方法，如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;

    }
}
