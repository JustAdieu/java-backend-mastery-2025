package main.java.com.adieu.learning.week2.daily.day3.demo2;


public class Main {
    // 静态（全局）变量
    private static int staticVar = 0;
    // 测试次数（预热+实际测试）
    private static final int WARMUP_ITERATIONS = 1;
    private static final int TEST_ITERATIONS = 100000000;
    private static final int RUNS = 5; // 多次运行取平均

    public static void main(String[] args) {
        // 1. JIT预热：让JVM编译热点代码
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            testStaticVariable();
            testLocalVariable();
        }

        // 2. 实际测试：多次运行取平均
        long staticTotal = 0, localTotal = 0;
        for (int r = 0; r < RUNS; r++) {
            staticTotal += testStaticVariable();
            localTotal += testLocalVariable();
            // 重置静态变量，避免状态干扰
            staticVar = 0;
        }

        // 3. 计算平均耗时
        double staticAvg = (double) staticTotal / RUNS;
        double localAvg = (double) localTotal / RUNS;

        // 4. 输出结果
        System.out.printf("静态变量平均耗时: %.2f 纳秒%n", staticAvg);
        System.out.printf("局部变量平均耗时: %.2f 纳秒%n", localAvg);
        System.out.printf("静态变量比局部变量慢: %.2f%%%n",
                ((staticAvg - localAvg) / localAvg) * 100);
    }

    /**
     * 测试静态变量的访问耗时（核心逻辑：循环读写静态变量）
     */
    private static long testStaticVariable() {
        long start = System.nanoTime();
        int temp = 0; // 临时变量，避免返回值被优化
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            staticVar++; // 写静态变量
            temp = staticVar; // 读静态变量
        }
        // 返回temp，让循环有副作用，防止JIT优化
        return System.nanoTime() - start - (temp * 0); // temp*0不影响结果，仅避免优化
    }

    /**
     * 测试局部变量的访问耗时（核心逻辑：循环读写局部变量）
     */
    private static long testLocalVariable() {
        long start = System.nanoTime();
        int localVar = 0; // 局部变量
        int temp = 0;
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            localVar++; // 写局部变量
            temp = localVar; // 读局部变量
        }
        // 返回temp，让循环有副作用，防止JIT优化
        return System.nanoTime() - start - (temp * 0);
    }

}
