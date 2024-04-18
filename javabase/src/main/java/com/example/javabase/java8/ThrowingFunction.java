package com.example.javabase.java8;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingFunction<T, R> {
    R apply(T t) throws IOException;
//    R apply(T t);
}
