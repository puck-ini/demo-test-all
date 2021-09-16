package org.zchzh.javademo.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zengchzh
 * @date 2021/9/6
 *
 * 使用 LinkedHashMap 实现， 重写 removeEldestEntry()
 */
public class LRU1<K, V> extends LinkedHashMap<K, V> {

    private int capacity;

    public LRU1(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LRU1 lru = new LRU1(3);
        lru.put(1,"a");
        lru.put(2,"b");
        lru.put(3,"c");
        System.out.println(lru.keySet());
        lru.put(4,"d");
        System.out.println(lru.keySet());
        lru.put(3,"c");
        System.out.println(lru.keySet());
        lru.put(3,"c");
        System.out.println(lru.keySet());
        lru.put(3,"c");
        System.out.println(lru.keySet());
        lru.put(5,"e");
        System.out.println(lru.keySet());
    }
}
