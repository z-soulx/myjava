package com.example.javabase.java.base;

public  enum   Testenum {
	 ONE{
		@Override
		void absFind() {

		}
	},
	TWO("ss"){
		@Override
		void absFind() {

		}
	};
    private String a;

	Testenum(String a) {
		this.a = a;
	}

	Testenum() {
	}

	abstract void absFind();
}
