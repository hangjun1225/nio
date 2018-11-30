package com.hr.nio.test4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Test3 {

	public static void main(String[] args) throws IOException {
		// socketChannel开启非阻塞模式
		// method1();

		// 非阻塞模式下 建立连接
		// method2();

		// 非阻塞模式下 read()
		// method3();

		// 非阻塞模式下write();
		 method4();

	}

	/**
	 * 阻塞模式下，write()方法在尚未写出任何内容时可能就返回了。所以需要在循环中调用write()
	 * 
	 * @throws IOException
	 */
	private static void method4() throws IOException {
		// 1.新建一个管道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);// 设置非阻塞模式
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));

		while (!socketChannel.finishConnect()) {//如果没有建立连接 一直循环执行
			// wait, or do something else...
			System.out.println("还未连接到服务器...");
		}

		// 2.新建一个buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("hello".getBytes());

		// 3.写入管道中
		buffer.flip();
		// 这里需要循环写到管道中
		while (buffer.hasRemaining()) {
			socketChannel.write(buffer);
		}

		socketChannel.close();

	}

	/**
	 * 非阻塞模式下,read()方法在尚未读取到任何数据时可能就返回了。所以需要关注它的int返回值，它会告诉你读取了多少字节。
	 * 
	 * @throws IOException
	 */
	private static void method3() throws IOException {
		// 1.新建一个管道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);// 设置非阻塞模式
		socketChannel.connect(new InetSocketAddress("http://www.baidu.com", 10086));

		while (!socketChannel.finishConnect()) {
			// wait, or do something else...

		}

		// 2.新建一个buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 3.读到缓冲区
		int read = socketChannel.read(buffer);

		System.out.println(read);

		// 4.关闭channel
		socketChannel.close();

	}

	/**
	 * 如果SocketChannel在非阻塞模式下，此时调用connect()，该方法可能在连接建立之前就返回了。为了确定连接是否建立，可以调用finishConnect()的方法。像这样：
	 * 
	 * @throws IOException
	 */
	private static void method2() throws IOException {
		// 1.新建一个管道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);// 设置非阻塞模式
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));

		while (socketChannel.finishConnect()) {
			// wait, or do something else...

		}

	}

	/**
	 * 可以设置 SocketChannel 为非阻塞模式（non-blocking mode）.设置之后，就可以在异步模式下调用connect(),
	 * read() 和write()了。
	 * 
	 * @throws IOException
	 */
	private static void method1() throws IOException {
		// 1.新建一个管道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);// 设置非阻塞模式
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));
	}
}
