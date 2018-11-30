package com.hr.nio.test5;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

public class Test1 {
	public static void main(String[] args) throws IOException {
		// 管道是什么？
		// Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。

		// 创建管道
		method1();

		// 向管道写数据
		method2();

		// 向管道读数据
		method3();
	}

	/**
	 * 向管道读数据 读取管道的数据，需要访问source通道
	 * @throws IOException 
	 */
	private static void method3() throws IOException {
		// 建立管道,获取到sinkChannel
		Pipe pipe = Pipe.open();
		SourceChannel source = pipe.source();

		// 建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 写数据到管道中sinkChannel
		int read = source.read(buffer);
	}

	/**
	 * 向管道写数据 向管道写数据，需要访问sink通道。像这样：
	 * 
	 * @throws IOException
	 */
	private static void method2() throws IOException {
		// 建立管道,获取到sinkChannel
		Pipe pipe = Pipe.open();
		SinkChannel sink = pipe.sink();

		// 建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.clear();
		buffer.put("hello".getBytes());

		// 写数据到管道中sinkChannel
		buffer.flip();
		while(buffer.hasRemaining()) {
			sink.write(buffer);
		}

	}

	/**
	 * 创建管道
	 * 
	 * @throws IOException
	 */
	private static void method1() throws IOException {
		Pipe pipe = Pipe.open();
	}
}
