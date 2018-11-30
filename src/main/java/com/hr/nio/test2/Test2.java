package com.hr.nio.test2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test2 {
	public static void main(String[] args) throws IOException {
		// 1.分散 scatter
		method1();

		// 2.聚集 gather
		method2();
	}

	/**
	 * gather 聚集
	 * 
	 * 多个buffer写入到channel中
	 * 
	 * buffer0 buffer1 ---->channel buffer2
	 * 
	 * 规则：依次写入
	 * @throws IOException 
	 * 
	 */
	private static void method2() throws IOException {
		// 1.创建管道
		FileOutputStream fis = new FileOutputStream(
				"E:\\\\sts\\\\workspace01\\\\nio\\\\src\\\\main\\\\resources\\\\test1.txt");
		FileChannel channel = fis.getChannel();

		// 2.创建两个buffer
		ByteBuffer header = ByteBuffer.allocate(128);
		ByteBuffer body = ByteBuffer.allocate(1024);
		ByteBuffer[] array = { header, body };

		// 3.写到缓冲区
		channel.write(array);
	}

	/**
	 * scatter 分散 从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
	 * 
	 * buffer0 channel ---> buffer1 buffer2
	 *
	 * read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写。
	 *
	 * @throws IOException
	 */
	private static void method1() throws IOException {
		// 1.创建管道
		FileInputStream fis = new FileInputStream(
				"E:\\\\sts\\\\workspace01\\\\nio\\\\src\\\\main\\\\resources\\\\test1.txt");
		FileChannel channel = fis.getChannel();

		// 2.创建两个buffer
		ByteBuffer header = ByteBuffer.allocate(128);
		ByteBuffer body = ByteBuffer.allocate(1024);
		ByteBuffer[] array = { header, body };

		// 3.读到缓冲区
		channel.read(array);

		fis.close();

	}
}
