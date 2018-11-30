package com.hr.nio.test4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Test4 {

	public static void main(String[] args) throws IOException {

		// 1.建立连接并绑定端口
//		method1();

		// 2.关闭连接
//		method2();

		// 3.监听新进来的连接
//		method3();

		// 4.非阻塞模式下,accept()
		method4();

	}

	/**
	 * 在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null。
	 * 因此，需要检查返回的SocketChannel是否是null.
	 * @throws IOException 
	 */
	private static void method4() throws IOException {
		// 建立连接-得到serverSocketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",10086));
		System.out.println("服务端启动成功！");
		
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			System.out.println(socketChannel);
			if(socketChannel!=null) {
				// wait,do something
				System.out.println(socketChannel);
			}
		}
	}

	/**
	 * 监听端口
	 * 
	 * @throws IOException
	 */
	private static void method3() throws IOException {
		// 建立连接-得到serverSocketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 建立连接-得到ServerSocket并绑定端口
		serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",10086));
		System.out.println("服务端启动成功！");
		
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			// wait,do something
			System.out.println(socketChannel);
		}
	}

	/**
	 * 关闭
	 * 
	 * @throws IOException
	 */
	private static void method2() throws IOException {
		// 建立连接-得到serverSocketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 建立连接-得到ServerSocket并绑定端口
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));

		// 关闭连接
		serverSocketChannel.close();
	}

	private static void method1() throws IOException {
		// 建立连接-得到serverSocketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 建立连接-得到ServerSocket并绑定端口
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
	}
}
