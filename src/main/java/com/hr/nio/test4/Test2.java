package com.hr.nio.test4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Test2 {

	public static void main(String[] args) throws IOException {
		// 打开 SocketChannel
		// method1();

		// 关闭 SocketChannel
		// method02();

		// 从 SocketChannel 读取数据
		// method3();

		// 写入 SocketChannel
		method4();
	}

	/**
	 * 写入 SocketChannel
	 * 
	 * @throws IOException
	 */
	private static void method4() throws IOException {
		// 1.新建一个管道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));
		// 2.新建一个buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("hello".getBytes());
		// 3.写到管道中
		buffer.flip();
		socketChannel.write(buffer);

		socketChannel.close();
	}

	/**
	 * 从 SocketChannel 读取数据
	 * 
	 * @throws IOException
	 */
	private static void method3() throws IOException {
		// 1.新建一个管道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));
		// 2.新建一个buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		// 3.读到缓冲区
		socketChannel.read(buffer);// 如果没有响应,会一直阻塞

		socketChannel.close();
	}

	private static void method02() throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));

		socketChannel.close();
	}

	private static void method1() throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));

	}
}
