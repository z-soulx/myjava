package com.example.javabase.Myguava.myrangeTest;

public interface MyMerge<T extends  MyMerge> {
    T merge(T o);
}
