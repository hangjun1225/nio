package com.hr.nio.test1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读文件
 * 
 * @author hangjun
 *
 */
public class Test1 {
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//method1();
		method2();
		method3();
		
	}

	/**
	 * 个人练习使用
	 * @throws IOException
	 */
	private static void method3() throws IOException {
		//1.创建管道
		RandomAccessFile file=new RandomAccessFile("E:\\sts\\workspace01\\nio\\src\\main\\resources\\test1.txt", "rw");
		FileChannel fc = file.getChannel();
		//2.创建缓冲区
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		//3.读到缓冲区
		int readCount = fc.read(buffer);
		while(readCount!=-1) {
			buffer.flip();
			while(buffer.hasRemaining()) {
				System.out.println(buffer.get());
			}
			
			//再把数据读到缓冲区
			buffer.clear();
			readCount=fc.read(buffer);
		}
		
		file.close();
	}

	private static void method2() throws IOException {
		//1.创建管道
		RandomAccessFile file=new RandomAccessFile("E:\\sts\\workspace01\\nio\\src\\main\\resources\\test1.txt", "rw");
		FileChannel fc = file.getChannel();
		
		//2.创建缓冲区
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		
		//3.读到缓冲区
		fc.read(buffer);
		
		file.close();
	}
		

	private static void method1() throws IOException {
		// 1.创建管道
		FileInputStream fis = new FileInputStream("E:\\sts\\workspace01\\nio\\src\\main\\resources\\test1.txt");
		FileChannel fc = fis.getChannel();

		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 3.读到缓冲区 (数据从通道读到缓冲区)
		int readCount = fc.read(buffer);
		System.out.println("读到缓冲区,读取到数据的个数=" + readCount);

		fis.close();

	}
	
	
	

}
