package com.jackshenorion.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BenchMarkUsingSeparatedState {

    @Benchmark
    public double sumByIterator(LargeListProvider largeListProvider) {
        double sum = 0;
        for (Integer i : largeListProvider.getLargeList()) {
            sum += i;
        }
        return sum;
    }

    @Benchmark
    public double sumByStreamReduce(LargeListProvider largeListProvider) {
        return largeListProvider.getLargeList().stream().map(d -> (double) d).reduce(0.0, Double::sum);
    }

    @Benchmark
    public double sumByStream(LargeListProvider largeListProvider) {
        final double[] sum = {0};
        largeListProvider.getLargeList().stream().forEach(i -> sum[0] += i);
        return sum[0];
    }

    @Benchmark
    public double sumByLambda(LargeListProvider largeListProvider) {
        final double[] sum = {0};
        largeListProvider.getLargeList().forEach(i -> sum[0] += i);
        return sum[0];
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchMarkUsingSeparatedState.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();
        new Runner(options).run();
    }
}
