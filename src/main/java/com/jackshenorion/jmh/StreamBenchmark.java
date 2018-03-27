package com.jackshenorion.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class StreamBenchmark {

    private List<Integer> largeList = new ArrayList<>();

    @Param({"1000", "10000", "100000"})
    private int count;

    @Setup
    public void prepare() {
        Random rd = new Random();
        for (int i = 0; i < count; i++) {
            largeList.add(rd.nextInt());
        }
    }
    @Benchmark
    public double sumByIterator() {
        double sum = 0;
        for (Integer i : largeList) {
            sum += i;
        }
        return sum;
    }

    @Benchmark
    public double sumByStreamReduce() {
        return largeList.stream().map(d -> (double) d).reduce(0.0, Double::sum);
    }

    @Benchmark
    public double sumByStream() {
        final double[] sum = {0};
        largeList.stream().forEach(i -> sum[0] += i);
        return sum[0];
    }

    @Benchmark
    public double sumByLambda() {
        final double[] sum = {0};
        largeList.forEach(i -> sum[0] += i);
        return sum[0];
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(StreamBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();
        new Runner(options).run();
    }

}
