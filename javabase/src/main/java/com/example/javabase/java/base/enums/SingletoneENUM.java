package com.example.javabase.java.base.enums;

/**
 * @author soulx
 */

public  enum SingletoneENUM {
	INSTANCE{
		@Override
		void s() {

		}
	};
	static {
		System.out.println("enum loading...");
	}
	 abstract void s();
}
