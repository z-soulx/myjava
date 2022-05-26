package com.example.javabase.test;

import com.example.javabase.design_patterns.my_code.chapter_6.NoCommand;
import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.beans.BeanUtils;


import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @program: java-base
 * @description:
 * @author: soulx
 * @create: 2019-08-11 18:29
 **/
public class T {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        BitSet s = new BitSet();
   s.set(5);
        System.out.println(s.get(5));
        System.out.println(s.get(6));
    }
    class BitMap {
        Integer age;
        Integer id;

        public BitMap() {
        }

        public BitMap(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"age\":")
                    .append(age);
            sb.append(",\"id\":")
                    .append(id);
            sb.append('}');
            return sb.toString();
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }


}
