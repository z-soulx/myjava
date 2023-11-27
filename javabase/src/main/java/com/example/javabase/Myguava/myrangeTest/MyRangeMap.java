package com.example.javabase.Myguava.myrangeTest;


import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class MyRangeMap<K extends Comparable, V extends MyMerge>  {
    TreeRangeMap<K, MyMerge> map;

    public MyRangeMap(TreeRangeMap<K, MyMerge> map) {
        this.map = map;
    }

    public static <K extends Comparable, V extends MyMerge>  MyRangeMap<K, V> create() {

        return new MyRangeMap<K,V>(TreeRangeMap.create());
    }

    public void put(Range<K> range, V value) {
        RangeMap<K, MyMerge> kvRangeMap = map.subRangeMap(range);
        TreeRangeMap<K, MyMerge> tmp = TreeRangeMap.<K, MyMerge>create();
        tmp.putAll(kvRangeMap);
        map.put(range,value);
        tmp.asMapOfRanges().forEach((kRange, v) -> {
               map.put(kRange,v.merge(value));
            System.out.println(kRange.toString());
        } );

    }
    Map<Range<K>, MyMerge>  asMapOfRanges(){
        Map<Range<K>, MyMerge> rangeMyMergeMap = map.asMapOfRanges();
        return rangeMyMergeMap;
    }
}
