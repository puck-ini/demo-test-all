package org.zchzh.bloomfilter;

import cn.hutool.bloomfilter.BitMapBloomFilter;

import java.util.Arrays;

/**
 * @author zengchzh
 * @date 2021/8/16
 */
public class HutoolBloomFilterTest {

    private BitMapBloomFilter filter = new BitMapBloomFilter(10);

    public HutoolBloomFilterTest() {
        filter.add("abc");
        filter.add("123");
        filter.add("qwe");
    }

    public boolean contains(String s) {
        return filter.contains(s);
    }

    public static void main(String[] args) {
        HutoolBloomFilterTest filterTest = new HutoolBloomFilterTest();
        System.out.println(filterTest.contains("123"));
        System.out.println(filterTest.contains("111"));
        System.out.println(filterTest.contains("abc"));
        System.out.println(filterTest.contains("cba"));
    }
}
