package com.huangkailong.learning.source.jdk;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author huangkl
 * @since 1.0.0
 */
public class ArrayListTests {
    @Test
    void test0() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        for (String item : list) {
            System.out.println(item);
        }
    }
}
