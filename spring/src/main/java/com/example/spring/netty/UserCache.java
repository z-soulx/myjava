package com.example.spring.netty;


import io.netty.util.Recycler;

/**
 * @program: myjava
 * @description: https://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/Netty%20%E6%A0%B8%E5%BF%83%E5%8E%9F%E7%90%86%E5%89%96%E6%9E%90%E4%B8%8E%20RPC%20%E5%AE%9E%E8%B7%B5-%E5%AE%8C/15%20%20%E8%BD%BB%E9%87%8F%E7%BA%A7%E5%AF%B9%E8%B1%A1%E5%9B%9E%E6%94%B6%E7%AB%99%EF%BC%9ARecycler%20%E5%AF%B9%E8%B1%A1%E6%B1%A0%E6%8A%80%E6%9C%AF%E8%A7%A3%E6%9E%90.md
 * @author: soulx
 * @create: 2023-09-11 11:42
 **/
public class UserCache {

	private static final Recycler<User> userRecycler = new Recycler<User>() {

		@Override

		protected User newObject(Handle<User> handle) {

			return new User(handle);

		}

	};

	static final class User {

		private String name;

		private Recycler.Handle<User> handle;

		public void setName(String name) {

			this.name = name;

		}

		public String getName() {

			return name;

		}

		public User(Recycler.Handle<User> handle) {

			this.handle = handle;

		}

		public void recycle() {

			handle.recycle(this);

		}

	}

	public static void main(String[] args) {

		User user1 = userRecycler.get(); // 1、从对象池获取 User 对象

		user1.setName("hello"); // 2、设置 User 对象的属性

		user1.recycle(); // 3、回收对象到对象池

		User user2 = userRecycler.get(); // 4、从对象池获取对象

		System.out.println(user2.getName());

		System.out.println(user1 == user2);

	}

}
