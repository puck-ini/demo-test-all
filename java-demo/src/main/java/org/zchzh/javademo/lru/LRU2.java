package org.zchzh.javademo.lru;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author zengchzh
 * @date 2021/9/6
 *
 * LinkedList HashMap
 */
public class LRU2<K, V> {
    int capacity;
    Map<K, V> map;
    LinkedList<K> list;

    public LRU2(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.list = new LinkedList<>();
    }

    /**
     * 添加元素
     * 1.元素存在，放到队尾
     * 2.不存在，判断链表是否满。
     * 如果满，则删除队首元素，放入队尾元素，删除更新哈希表
     * 如果不满，放入队尾元素，更新哈希表
     */
    public void put(K key, V value) {
        V v = map.get(key);
        if (v != null) {
            list.remove(key);
            list.addLast(key);
            map.put(key, value);
            return;
        }

        //队列未满，添加到尾部
        if (list.size() < capacity) {
            list.addLast(key);
            map.put(key, value);
        } else {
            //队列已满，移除队首
            K firstKey = list.removeFirst();
            map.remove(firstKey);
            list.addLast(key);
            map.put(key, value);
        }
    }

    /**
     * 访问元素
     * 元素存在，放到队尾
     */
    public V get(K key) {
        V v = map.get(key);
        if (v != null) {
            list.remove(key);
            list.addLast(key);
            return v;
        }
        return null;
    }

    public static void main(String[] args) {
        LRU2 lru = new LRU2(3);
        lru.put(1,"a");
        lru.put(2,"b");
        lru.put(3,"c");
        System.out.println(lru.list);
        lru.put(4,"d");
        System.out.println(lru.list);
        lru.put(3,"c");
        System.out.println(lru.list);
        lru.put(3,"c");
        System.out.println(lru.list);
        lru.put(3,"c");
        System.out.println(lru.list);
        lru.put(5,"e");
        System.out.println(lru.list);
    }
}
