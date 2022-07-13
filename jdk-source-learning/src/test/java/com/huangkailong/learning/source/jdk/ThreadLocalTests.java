package com.huangkailong.learning.source.jdk;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huangkl
 * @since 1.0.0
 */
public class ThreadLocalTests {
    private static final AtomicInteger nextHashCode =
        new AtomicInteger();

    private static final int HASH_INCREMENT = 0x61c88647;

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    @Test
    void test0() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("abc");
        System.out.println(threadLocal.get());
        threadLocal.remove();
        System.out.println(threadLocal.get());
    }


    @Test
    void test1() {
        // 黄金分割点：(√5 - 1) / 2 = 0.6180339887     1.618:1 == 1:0.618
        String x = new DecimalFormat("0.0000000000").format((Math.sqrt(5) - 1) / 2);
        System.out.println(x);
        int hashIncrement = BigDecimal.valueOf(Math.pow(2, 32) * Double.parseDouble(x)).intValue();
        System.out.println(hashIncrement);  //-1640531527
        System.out.println("0x"+Integer.toHexString(Math.abs(hashIncrement)));  //0x61c88647
    }

    /**
     *
     */
    @Test
    void test2() {
        for (int i = 0; i < 20; i++) {
            /**
             * hashCode = h(i) = h(i-1) + HASH_INCREMENT
             * h(0) = 0
             *
             */
            int hashCode = nextHashCode();
            System.out.println(StrUtil.format("第 {} hash code 为 {}, idx(16): {}", i, Integer.toHexString(hashCode), (hashCode & (16-1))));
        }
    }
}
