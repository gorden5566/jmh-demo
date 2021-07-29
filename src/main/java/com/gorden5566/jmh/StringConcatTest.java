package com.gorden5566.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author gorden5566
 * @date 2021/07/30
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Threads(1)
@Fork(1)
@State(value = Scope.Benchmark)
public class StringConcatTest {
    private static final String COMMA = ",";
    private static List<String> list = Arrays.asList("groupA=DENY", "groupB=PUB|SUB", "groupC=SUB");

    @Benchmark
    public void testStringBufferAdd(Blackhole blackhole) {
        blackhole.consume(list2StringBuffer(list, COMMA));
    }

    @Benchmark
    public void testStringBuilderAdd(Blackhole blackhole) {
        blackhole.consume(list2StringBuilder(list, COMMA));
    }

    public static String list2StringBuffer(List<String> list, String splitor) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i));
            if (i == list.size() - 1) {
                break;
            }
            str.append(splitor);
        }
        return str.toString();
    }

    public static String list2StringBuilder(List<String> list, String splitor) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i));
            if (i == list.size() - 1) {
                break;
            }
            str.append(splitor);
        }
        return str.toString();
    }

    public static void main(String[] args) throws RunnerException {
        String classPath = StringConcatTest.class.getClassLoader().getResource("").getPath();
        Options opt = new OptionsBuilder()
                .include(StringConcatTest.class.getSimpleName())
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
