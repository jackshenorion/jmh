package com.jackshenorion.jmh;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@State(Scope.Thread)
public class LargeListProvider {

    @Param({"1000", "10000", "100000"})
    private int count;

    private List<Integer> largeList = new ArrayList<>();

    public List<Integer> getLargeList() {
        return largeList;
    }

    @Setup
    public void prepare() {
        Random rd = new Random();
        for (int i = 0; i < count; i++) {
            largeList.add(rd.nextInt());
        }
    }
}
