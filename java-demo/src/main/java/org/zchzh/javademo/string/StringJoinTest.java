package org.zchzh.javademo.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author zengchzh
 * @date 2021/8/12
 */
public class StringJoinTest {

    public String stringBufferJoin(List<String> stringList, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for (String s : stringList) {
            if (first) {
                first = false;
            } else {
                buffer.append(delimiter);
            }
            buffer.append(s);
        }
        return buffer.toString();
    }


    public String stringJoinJoin(List<String> stringList, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (String string : stringList) {
            joiner.add(string);
        }
        return joiner.toString();
    }

    public String streamJoin(List<String> stringList, String delimiter) {
        return stringList.stream().map(String::valueOf).collect(Collectors.joining(delimiter));
    }

    public String stringJoin(List<String> stringList, String delimiter) {
        return String.join(delimiter, stringList);
    }

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add(String.valueOf(i));
        }
        String delimiter = "-";
        StringJoinTest joinTest = new StringJoinTest();
        System.out.println(joinTest.stringBufferJoin(stringList, delimiter));
        System.out.println(joinTest.stringJoinJoin(stringList, delimiter));
        System.out.println(joinTest.streamJoin(stringList, delimiter));
        System.out.println(joinTest.stringJoin( stringList, delimiter));
    }
}