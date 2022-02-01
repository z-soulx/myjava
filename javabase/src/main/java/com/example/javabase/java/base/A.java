package com.example.javabase.java.base;

/**
 * @program: javabase
 * @description:
 * @author: soulx
 * @create: 2020-01-31 14:51
 **/
 public class A{
 public static final String a ="ss";
 public static final int i = 1;
 public static final Integer ii = 1;
 private final String name = "Clive";
 static {
  System.out.println("static");
 }
 public void printName() {
  System.out.println(name);
 }

}
