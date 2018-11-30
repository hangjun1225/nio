package com.hr.nio.test1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 写文件
 * @author hangjun
 *
 */
public class Test2 {

	public static void main(String[] args) throws IOException {

		// method1();
		method2();
	}

	private static void method2() throws IOException {
		// 1.创建channel
		RandomAccessFile file =new RandomAccessFile("E:\\\\sts\\\\workspace01\\\\nio\\\\src\\\\main\\\\resources\\\\test1.txt", "rw");
		FileChannel fc = file.getChannel();
		// 2.创建缓冲区
		ByteBuffer buffer =ByteBuffer.allocate(1024);
		// 3.写入管道中 (数据从缓冲区写到管道中)
		byte[] message = "杭军".getBytes();
		buffer.put(message);
		
		buffer.flip();//切换到度模式
		int writeCount = fc.write(buffer);
		System.out.println("写到管道中,写入数据的字节数="+writeCount);
		
		file.close();
		
			
	}

	private static void method1() throws IOException {
		// 1.创建channel
		FileOutputStream fos = new FileOutputStream("E:\\sts\\workspace01\\nio\\src\\main\\resources\\test1.txt");
		FileChannel fc = fos.getChannel();

		// 2.创建缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 3.写入管道中 (数据从缓冲区写到管道中)
		// 3.1先自己放一些数据到缓冲区
		byte[] message = "杭军".getBytes();
		for (int i = 0; i < message.length; i++) {
			buffer.put(message[i]);
		}
		// 3.2
		buffer.flip();// 切换到读模式
		fc.write(buffer);

		fos.close();
	}

}
