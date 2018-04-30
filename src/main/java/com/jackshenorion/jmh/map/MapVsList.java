package com.jackshenorion.jmh.map;

import it.unimi.dsi.fastutil.chars.Char2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime) @OutputTimeUnit(TimeUnit.MICROSECONDS) @State(Scope.Thread)
public class MapVsList {

    @Param({"6000000"})
    private int recordCount;

    @Param({"16"})
    private int tagCount;
//
//    @Param({"2", "4", "8", "16", "32"})
//    private int queryCount;

    private static char[] tagKeys;
    private static List<List> msgs;

    @Benchmark
    public void testList() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            List<TagField> tagFields = new ArrayList<>(4);
            for (int j = 0; j < tagCount; j ++) {
                Iterator<TagField> it = tagFields.iterator();
                while (it.hasNext()) {
                    TagField f = it.next();
                    if (f.getKey() == tagKeys[j]) {
                        it.remove();
                    }
                }
                tagFields.add(new TagField(tagKeys[j], rd.nextDouble()));
            }
            for (int j = 0; j < tagCount; j ++) {
                for (TagField f : tagFields) {
                    if (f.getKey() == tagKeys[j]) {
                        sum += f.getValue();
                    }
                }
            }
        }
        System.out.println("finalSum is " + sum);
    }

    @Benchmark
    public void testMap() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectOpenHashMap<TagField> tagFields = new Char2ObjectOpenHashMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
            for (int j = 0; j < tagCount; j ++) {
                sum += tagFields.get(tagKeys[j]).getValue();
            }
        }
        System.out.println("finalSum is " + sum);
    }

    @Benchmark
    public void testLinkedMap() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectLinkedOpenHashMap<TagField> tagFields = new Char2ObjectLinkedOpenHashMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
            for (int j = 0; j < tagCount; j ++) {
                sum += tagFields.get(tagKeys[j]).getValue();
            }
        }
        System.out.println("finalSum is " + sum);
    }

    @Benchmark
    public void testAVLTree() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectAVLTreeMap<TagField> tagFields = new Char2ObjectAVLTreeMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
            for (int j = 0; j < tagCount; j ++) {
                sum += tagFields.get(tagKeys[j]).getValue();
            }
        }
        System.out.println("finalSum is " + sum);
    }

    @Benchmark
    public void testRBTree() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectRBTreeMap<TagField> tagFields = new Char2ObjectRBTreeMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
            for (int j = 0; j < tagCount; j ++) {
                sum += tagFields.get(tagKeys[j]).getValue();
            }
        }
        System.out.println("finalSum is " + sum);
    }

    @Benchmark
    public void testListWithoutRead() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            List<TagField> tagFields = new ArrayList<>(4);
            for (int j = 0; j < tagCount; j ++) {
                Iterator<TagField> it = tagFields.iterator();
                while (it.hasNext()) {
                    TagField f = it.next();
                    if (f.getKey() == tagKeys[j]) {
                        it.remove();
                    }
                }
                tagFields.add(new TagField(tagKeys[j], rd.nextDouble()));
            }
        }
    }

    @Benchmark
    public void testMapWithoutRead() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectOpenHashMap<TagField> tagFields = new Char2ObjectOpenHashMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
        }
    }

    @Benchmark
    public void testLinkedMapWithoutRead() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectLinkedOpenHashMap<TagField> tagFields = new Char2ObjectLinkedOpenHashMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
        }
    }

    @Benchmark
    public void testAVLTreeWithoutRead() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectAVLTreeMap<TagField> tagFields = new Char2ObjectAVLTreeMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
        }
    }

    @Benchmark
    public void testRBTreeWithoutRead() {
        System.out.println("recordeCount is " + recordCount);
        initTagKeys();
        double sum = 0;
        Random rd = new Random();
        for (int i = 0; i < recordCount; i ++) {
            Char2ObjectRBTreeMap<TagField> tagFields = new Char2ObjectRBTreeMap<>();
            for (int j = 0; j < tagCount; j ++) {
                tagFields.put(tagKeys[j], new TagField(tagKeys[j], rd.nextDouble()));
            }
        }
    }


    private static void initTagKeys() {
        List<Character> charList = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            charList.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            charList.add(c);
        }
        Collections.shuffle(charList);
        tagKeys = new char[charList.size()];
        for (int i = 0; i < charList.size(); i++) {
            tagKeys[i] = charList.get(i);
        }
    }

    public static void main(String[] args) throws RunnerException {
        // test create list of maps (different size)
        // test create list of lists (different size)

        // test create map and add tags (different number of tags for each)
        // test create list and add tags (different number of tags for each)

        // test create map and add tags and query (different number of tags for each, and different query times)
        // test create list and add tags and query (different number of tags for each)

        Options options = new OptionsBuilder()
                .include(MapVsList.class.getSimpleName())
                .warmupIterations(0)
                .measurementIterations(1)
                .forks(1)
                .build();
        new Runner(options).run();

    }
}
