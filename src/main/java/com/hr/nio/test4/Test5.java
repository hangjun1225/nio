package com.hr.nio.test4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * ava
 * NIO中的DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包。
 * 
 * @author hangjun
 *
 */
public class Test5 {
	public static void main(String[] args) throws IOException {
		// 打开 DatagramChannel
		method1();

		// 接收数据
		method2();

		// 发送数据
		method3();

	}

	/**
	 * 发送数据
	 * @throws IOException 
	 */
	private static void method3() throws IOException {
		// 建立连接
		DatagramChannel channel = DatagramChannel.open();
		// 2.建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		String newData = "New String to write to file..." + System.currentTimeMillis();
		buffer.clear();
		buffer.put(newData.getBytes());

		// 3.接受数据包
		buffer.flip();
		int bytesSent = channel.send(buffer, new InetSocketAddress("www.baidu.com", 10086));
	
		//这个例子发送一串字符到”jenkov.com”服务器的UDP端口80。 因为服务端并没有监控这个端口，所以什么也不会发生。也不会通知你发出的数据包是否已收到，因为UDP在数据传送方面没有任何保证
	}

	/**
	 * 接收数据
	 * 
	 * @throws IOException
	 */
	private static void method2() throws IOException {
		// 建立连接
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(10086));
		// 2.建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 3.接受数据包
		channel.receive(buffer);

	}

	/**
	 * 打开 DatagramChannel
	 * 
	 * @throws IOException
	 */
	private static void method1() throws IOException {
		// DatagramChannel可以在UDP端口9999上接收数据包。
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(10086));
	}
}
