package com.gorden5566.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author gorden5566
 * @date 2021/07/23
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Threads(4)
@Fork(4)
@State(value = Scope.Thread)
public class NewCapacityTest {
    private static final int MAX_SIZE = 16 * 1024 * 1024;

    @Benchmark
    public void measureTableSizeFor(Blackhole blackhole) {
        for (int i = 0; i < MAX_SIZE; i++) {
            int newSize = MathUtils.tableSizeFor(i);
            blackhole.consume(newSize);
        }
    }

    @Benchmark
    public void measureNewCapacity(Blackhole blackhole) {
        for (int i = 0; i < MAX_SIZE; i++) {
            int newSize = MathUtils.newCapacity(i);
            blackhole.consume(newSize);
        }
    }

    public static void main(String[] args) throws RunnerException {
        String classPath = NewCapacityTest.class.getClassLoader().getResource("").getPath();
        Options opt = new OptionsBuilder()
                .include(NewCapacityTest.class.getSimpleName())
                .result(classPath + "result.json")
                .jvmArgs(getJvmArgs())
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    private static String[] getJvmArgs() {
        String[] jvmArgs = new String[]{"-Xms768m", "-Xmx768m", "-XX:MaxDirectMemorySize=768m",
                "-XX:BiasedLockingStartupDelay=0"};
        return jvmArgs;
    }
}
