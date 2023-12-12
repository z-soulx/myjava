package com.example.spring.netty;

/**
 * @program: mini-tomcat
 * @description:  java实现但线程绑定多个端口
 * @author: soulx
 * @create: 2023-12-12 19:33
 **/

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiPortEcho {
	private int ports[];
	private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

	public MultiPortEcho(int ports[]) throws Exception {
		this.ports = ports;

		configure_selector();
	}

	private void configure_selector() throws Exception {
		// create a new selector
		Selector selector = Selector.open();

		// Open a listener on each port, and register each one
		// with the selector
		for (int i = 0; i < ports.length; ++i) {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket ss = ssc.socket();
			InetSocketAddress address = new InetSocketAddress(ports[i]);
			ss.bind(address);

			SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

			System.out.println("Going to listen on " + ports[i]);
		}

		while (true) {
			int num = selector.select();

			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectedKeys.iterator();

			while (it.hasNext()) {
				SelectionKey key = it.next();

				if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					// 对Accept操作进行处理,可以进一步添加自己的逻辑
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					SocketChannel sc = ssc.accept();

					sc.configureBlocking(false);

					sc.register(selector, SelectionKey.OP_READ);
					it.remove();
					System.out.println("New Connection");

				} else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					// 对Read操作进行处理,可以进一步添加自己的逻辑
					SocketChannel sc = (SocketChannel) key.channel();
					while (true) {
						echoBuffer.clear();

						int n = sc.read(echoBuffer);

						if (n <= 0) {
							break;
						}
						echoBuffer.flip();
						sc.write(echoBuffer);
					}
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {

		int ports[] = {
				8080, 8081
		};

		new MultiPortEcho(ports);
	}
}
