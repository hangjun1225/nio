package com.hr.nio.test1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读写文件
 * @author hangjun
 *
 */
public class Test3 {
	public static void main(String[] args) throws IOException {
		//1.创建管道
		FileInputStream fis=new FileInputStream("E:\\sts\\workspace01\\nio\\src\\main\\resources\\test1.txt");
		FileChannel fcin = fis.getChannel();
		
		FileOutputStream fos=new FileOutputStream("E:\\\\sts\\\\workspace01\\\\nio\\\\src\\\\main\\\\resources\\\\test2.txt");
		FileChannel fcout = fos.getChannel();
		
		//2.创建缓冲区
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		
		//3.读到缓冲区,写到管道中
		while(true) {
			buffer.clear();
			int readCount = fcin.read(buffer);
			if(readCount==-1) {
				break;
			}
			buffer.flip();
			fcout.write(buffer);
		}
		//或者
//		while((fcin.read(buffer)!=-1)) {
//			//1.切换读模式
//			buffer.flip();
//			fcout.write(buffer);
//			
//			//2.为下一次读到缓冲区做准备
//			buffer.clear();
//		}
		
		
		fcin.close();
		fcout.close();
		
		fis.close();
		fos.close();
		System.out.println("文件拷贝成功");
		
		
		
	}
}
