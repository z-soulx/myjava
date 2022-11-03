package com.example.javabase.java.base;

public  enum   Testenum  implements InFTest{
	 ONE{
		 @Override
		 public String tt() {
			 return null;
		 }

		 @Override
		void absFind() {

		}
	},
	TWO("ss"){
		@Override
		public String tt() {
			return null;
		}

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
