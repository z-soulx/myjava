package com.example.javabase.java.base;

/**
 * @program: myjava
 * @description:  父类
 * @author: soulx
 * @create: 2022-02-01 13:59
 **/
public  class Father {
  public  final int num = 1;
  public  String id = "1";
  public static String name = "father";
  private int sid = 100;
  public void say(){
    holle();
    System.out.println("haha");
  }
  static {
    System.out.println("static-father");
  }
//  private Father() {
     //私有构造不能被继承
//  }

  private  void finalFf(){
    System.out.println("father");
  }
  public static void staticFf(){
    System.out.println("father");
  }

  public void holle(){
    System.out.println("haha2");
  }
}
