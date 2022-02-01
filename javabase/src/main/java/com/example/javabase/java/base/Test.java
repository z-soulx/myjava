package com.example.javabase.java.base;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @program: javabase
 * @description:
 * @author: soulx
 * @create: 2020-01-31 14:51
 **/


public class Test {

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        Path p = Paths.get("src").toAbsolutePath();
        System.out.println(Files.exists(p));
      Arrays.asList();
    }

  public <T>void Fx(T t){
        if( t instanceof Test){
            System.out.println("test");
        }
      System.out.println("ss");
  }

@org.junit.Test
/**
 *  编译期常量 初始化问题
 */
  public void t(){

//  System.out.println(A.ii);
  System.out.println(A.a);
  System.out.println();

//  System.out.println(A.i);
}


}


