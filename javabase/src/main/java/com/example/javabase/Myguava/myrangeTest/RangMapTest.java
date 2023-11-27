package com.example.javabase.Myguava.myrangeTest;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RangMapTest {
    public static void main(String[] args) {
        List<Range<Integer>> ranges = new ArrayList<>();
        ranges.add(Range.openClosed(1, 5));  // 区间 1-5
        ranges.add(Range.closed(7, 9));  // 区间 7-9

        for (Range<Integer> range : ranges) {
            for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
//                System.out.print(i + " ");
            }
        }

        TreeRangeMap<LocalDate, RangTestOb> map = TreeRangeMap.create();
        map.put(Range.closed(LocalDate.now(),LocalDate.now().plusDays(3)),new RangTestOb(1));
        map.put(Range.closed(LocalDate.now(),LocalDate.now().plusDays(1)),new RangTestOb("qwe"));
        map.put(Range.closed(LocalDate.now().plusDays(1),LocalDate.now().plusDays(2)),new RangTestOb());
//        map.asMapOfRanges().forEach((localDateRange, rangTestOb) -> System.out.println(localDateRange.toString()));
//        System.out.printf(Range.openClosed(1,4).intersection(Range.closed(1,2)).toString());

        MyRangeMap<LocalDate, RangTestOb> map2 = MyRangeMap.create();
        map2.put(Range.closed(LocalDate.now(),LocalDate.now().plusDays(4)),new RangTestOb(1));
        map2.put(Range.closed(LocalDate.now(),LocalDate.now().plusDays(1)),new RangTestOb("qwe"));
        map2.put(Range.closed(LocalDate.now().plusDays(1),LocalDate.now().plusDays(2)),new RangTestOb("asd"));
        map2.asMapOfRanges().forEach((localDateRange, rangTestOb) -> {
            System.out.println(localDateRange.toString());
            System.out.println(rangTestOb);
        });
    }
}
