package com.hr.nio.test9.demo3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TestMappedByteBuffer {
	/**
	 * 测试内存映射
	 * @param args
	 */
	public static void main(String[] args) {
		method1();
		System.out.println("=============");
		method2();
		
		//5M文件的结果
		// Read time: 1ms
		// =============
		// Read time: 9ms
		
		//400M文件的结果
		// Read time: 2ms
		// =============
		// Read time: 8103ms
		
		
		//当然也有一个缺陷问题
		//以后仔细研究
		//https://www.cnblogs.com/yanglonggang/p/6879646.html
	}

	public static void method1() {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		try {
//			aFile = new RandomAccessFile("src/main/resources/Redis-x64-3.2.100.zip", "rw");
			aFile = new RandomAccessFile("src/main/resources/spring-tool-suite.zip", "rw");
			fc = aFile.getChannel();
			long timeBegin = System.currentTimeMillis();
			MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
			// System.out.println((char)mbb.get((int)(aFile.length()/2-1)));
			// System.out.println((char)mbb.get((int)(aFile.length()/2)));
			// System.out.println((char)mbb.get((int)(aFile.length()/2)+1));
			long timeEnd = System.currentTimeMillis();
			System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
				if (fc != null) {
					fc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void method2() {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		try {
//			aFile = new RandomAccessFile("src/main/resources/Redis-x64-3.2.100.zip", "rw");
			aFile = new RandomAccessFile("src/main/resources/spring-tool-suite.zip", "rw");
			fc = aFile.getChannel();

			long timeBegin = System.currentTimeMillis();
			ByteBuffer buff = ByteBuffer.allocate((int) aFile.length());
			buff.clear();
			fc.read(buff);
			// System.out.println((char)buff.get((int)(aFile.length()/2-1)));
			// System.out.println((char)buff.get((int)(aFile.length()/2)));
			// System.out.println((char)buff.get((int)(aFile.length()/2)+1));
			long timeEnd = System.currentTimeMillis();
			System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
				if (fc != null) {
					fc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
}
