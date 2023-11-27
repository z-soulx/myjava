package com.example.javabase.Myguava.myrangeTest;



public class RangTestOb implements MyMerge<RangTestOb> {
    private String name;
    private int age = -1;

    public RangTestOb(String name) {
        this.name = name;
    }

    public RangTestOb(int age) {
        this.age = age;
    }

    public RangTestOb() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "RangTestOb{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public RangTestOb merge(RangTestOb o) {
        RangTestOb r = new RangTestOb();

           r.name = null != o.name ? o.name : this.name;


            r.age = -1 != o.age ? o.age : this.age;

        return r;
    }
}
