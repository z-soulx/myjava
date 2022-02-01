package com.example.javabase.java8;

/**
 * @program: myjava
 * @description:
 * [2] 的结果有点像脑筋急转弯。 我接受未绑定的引用并对其调用 transform()，
 * 将其传递给 X，并以某种方式导致对 x.f() 的调用。
 * Java 知道它必须采用第一个参数，这实际上就是 this
 * 未绑定方法本来就是 void call2(This athis, int i, double d); 第一个参数。
 * @author: soulx
 * @create: 2022-02-01 15:20
 **/
class X {
	String f() { return "X::f()"; }
}

interface MakeString {
	String make();
}

interface TransformX {
	String transform(X x);
}

public class UnboundMethodReference {
	public static void main(String[] args) {
		// MakeString ms = X::f; // [1]  不行，没有对象
		TransformX sp = X::f; // 生成的类就是 UnboundMethodReference$Lambda@xx
		X x = new X();
		System.out.println(sp.transform(x)); // [2]
		System.out.println(x.f()); // 同等效果
	}
}
