package main.java.com.adieu.learning.week2.daily.day5.demo1;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

public class Main {
    private static final int TEST_TIMES = 100000;

    public static void main(String[] args) {
        // 1. JVM预热：避免首次执行因类加载、JIT编译导致的误差
        warmUpJVM();

        // 2. 初始化对象（保持你原有代码的对象定义逻辑）
        String s1 = new String();
        StringBuilder s2 = new StringBuilder();
        StringBuffer s3 = new StringBuffer();

        // 3. 打印测试说明
        System.out.println("===== 字符串拼接性能测试（" + TEST_TIMES + "次）=====");
        System.out.println("测试指标：执行耗时（纳秒）、堆内存增量（KB）\n");

        // 4. 测试String拼接（保留你的核心逻辑，新增内存统计）
        testStringConcat(s1);

        // 5. 测试StringBuilder拼接
        testStringBuilderConcat(s2);

        // 6. 测试StringBuffer拼接
        testStringBufferConcat(s3);

        // 7. 补充GC次数/耗时（可选，需JVM参数配合）
        System.out.println("\n===== 测试补充说明 =====");
        System.out.println("若需查看GC详情，启动时添加JVM参数：-XX:+PrintGC -XX:+PrintGCTimeStamps");
    }

    /**
     * JVM预热方法：提前执行少量拼接，触发类加载和JIT编译
     */
    private static void warmUpJVM() {
        String temp = "";
        StringBuilder tempSb = new StringBuilder();
        StringBuffer tempSbf = new StringBuffer();
        for (int i = 0; i < 1000; i++) {
            temp += "1";
            tempSb.append("1");
            tempSbf.append("1");
        }
    }

    /**
     * 测试String直接拼接：保留原有耗时逻辑，新增内存统计
     */
    private static void testStringConcat(String s1) {
        // 记录初始内存状态
        MemoryUsage initialHeap = getHeapMemoryUsage();
        long start = System.nanoTime();

        // 核心拼接逻辑（与你的代码一致）
        for (int i = 0; i < TEST_TIMES; i++) {
            s1 += "1";
        }

        // 计算耗时和内存增量
        long costTime = System.nanoTime() - start;
        MemoryUsage endHeap = getHeapMemoryUsage();
        long heapIncrement = (endHeap.getUsed() - initialHeap.getUsed()) / 1024; // 转换为KB

        // 格式化输出
        System.out.printf("String拼接：\n  耗时：%d ns（≈%.2f ms）\n  堆内存增量：%d KB\n",
                costTime, costTime / 1_000_000.0, heapIncrement);
    }

    /**
     * 测试StringBuilder拼接：对齐格式，新增内存统计
     */
    private static void testStringBuilderConcat(StringBuilder s2) {
        MemoryUsage initialHeap = getHeapMemoryUsage();
        long start = System.nanoTime();

        for (int i = 0; i < TEST_TIMES; i++) {
            s2.append("1");
        }
        // 模拟实际使用：触发最终字符串生成
        String result = s2.toString();

        long costTime = System.nanoTime() - start;
        MemoryUsage endHeap = getHeapMemoryUsage();
        long heapIncrement = (endHeap.getUsed() - initialHeap.getUsed()) / 1024;

        System.out.printf("StringBuilder拼接：\n  耗时：%d ns（≈%.2f ms）\n  堆内存增量：%d KB\n",
                costTime, costTime / 1_000_000.0, heapIncrement);
    }

    /**
     * 测试StringBuffer拼接：对齐格式，新增内存统计
     */
    private static void testStringBufferConcat(StringBuffer s3) {
        MemoryUsage initialHeap = getHeapMemoryUsage();
        long start = System.nanoTime();

        for (int i = 0; i < TEST_TIMES; i++) {
            s3.append("1");
        }
        String result = s3.toString();

        long costTime = System.nanoTime() - start;
        MemoryUsage endHeap = getHeapMemoryUsage();
        long heapIncrement = (endHeap.getUsed() - initialHeap.getUsed()) / 1024;

        System.out.printf("StringBuffer拼接：\n  耗时：%d ns（≈%.2f ms）\n  堆内存增量：%d KB\n",
                costTime, costTime / 1_000_000.0, heapIncrement);
    }

    /**
     * 工具方法：获取当前堆内存使用情况（新生代+老年代）
     */
    private static MemoryUsage getHeapMemoryUsage() {
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    }
}
