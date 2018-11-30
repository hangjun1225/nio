package com.hr.nio.test4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * fileChannel的使用
 * 
 * @author hangjun
 *
 */
public class Test1 {

	public static void main(String[] args) throws IOException {
		// 创建管道-FileChannel
		// method1();

		// 从fileChannel读取数据
		// method2();

		// 向FileChannel写数据
		method3();

		// 关闭fileChannel
		// method4();

		// fileChannel的position
		// method5();

		// fileChannel的size方法
		// method6();

		// fileChannel的truncate方法
//		method7();

		// fileChannel的force方法
		mehtod8();

	}

	/**
	 * FileChannel.force()方法将通道里尚未写入磁盘的数据强制写到磁盘上。出于性能方面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。
	 * 要保证这一点，需要调用force()方法。
	 * @throws IOException 
	 */
	private static void mehtod8() throws IOException {
		// 1.创建管道
		RandomAccessFile file = new RandomAccessFile("tofile.txt", "rw");
		FileChannel outChannel = file.getChannel();
		
		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("hellowasdfasdfasdf".getBytes());
		
		// 3.写入到管道中
		buffer.flip();
		outChannel.write(buffer);
		
		outChannel.force(true);//即时写入到文件中
		
		outChannel.close();
	}

	/**
	 * 以使用FileChannel.truncate()方法截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除。如：
	 * 
	 * @throws IOException
	 */
	private static void method7() throws IOException {
		// 1.创建管道
		RandomAccessFile file = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel inChannel = file.getChannel();

		System.out.println(inChannel.size());// 文件的大小为10个字节
		inChannel.truncate(7);// 截取文件的前7个字节

		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 3.读到缓冲区
		inChannel.read(buffer);

		System.out.println(buffer.position());// 7

	}

	/**
	 * size()方法将返回该实例所关联文件的大小。
	 * 
	 * @throws IOException
	 */
	private static void method6() throws IOException {
		// 1.创建管道
		RandomAccessFile file = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel inChannel = file.getChannel();

		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 3.读到缓冲区
		inChannel.read(buffer);

		// 打印出channel的size 等价于文件的大小
		System.out.println(inChannel.size());

	}

	/**
	 * fileChannel的position
	 * 作用：有时可能需要在FileChannel的某个特定位置进行数据的读/写操作。可以通过调用position()方法获取FileChannel的当前位置。
	 * 
	 * 设置位置超过文件结束符 long pos = channel.position(); channel.position(pos +123);
	 * 读-(这个会造成数据丢失) 如果将位置设置在文件结束符之后，然后试图从文件通道中读取数据，读方法将返回-1 —— 文件结束标志。
	 * 写-(这个会造成数据中间有空格)
	 * 如果将位置设置在文件结束符之后，然后向通道中写数据，文件将撑大到当前位置并写入数据。这可能导致“文件空洞”，磁盘上物理文件中写入的数据间有空隙。
	 * 
	 * @throws IOException
	 */
	private static void method5() throws IOException {
		// 1.创建管道
		RandomAccessFile file = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel outChannel = file.getChannel();

		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		String msg = "word";
		buffer.put(msg.getBytes());

		// 3.写到管道中
		buffer.flip();// 如果没有这句话，那么写的数据还是position到limit的数据.不过现在的position=4,limit=1024.这里面的数据都是空的
		int bytesWrite = outChannel.write(buffer);

		// 3.查看channel当前的位置
		long position = outChannel.position();
		System.out.println(position);

		// 现在的position=4
		// 有时可能需要在FileChannel的某个特定位置进行数据的读/写操作
		// 模拟对channel再写入4个数据.(模拟数据空洞)
		// 设置positon=6
		outChannel.position(6);

		ByteBuffer buffer2 = ByteBuffer.allocate(1024);
		buffer2.put("test".getBytes());

		buffer2.flip();
		outChannel.write(buffer2);
		// 查看现在的位置
		position = outChannel.position();
		System.out.println(position);// 现在position=10

		// 4.关闭channel
		outChannel.close();

	}

	/**
	 * 关闭fileChannel
	 * 
	 * @throws IOException
	 */
	private static void method4() throws IOException {
		// 1.创建管道
		RandomAccessFile aFile = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel channel = aFile.getChannel();

		channel.close();

	}

	/**
	 * 向FileChannel写数据
	 * 
	 * @throws IOException
	 */
	private static void method3() throws IOException {
		// 1.创建管道
		RandomAccessFile aFile = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel outChannel = aFile.getChannel();

		// 2.创建buffer
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put("hello".getBytes());// 放的数据不能超过48

		buf.flip();
		while (buf.hasRemaining()) {
			int num = outChannel.write(buf);
		}

	}

	/**
	 * 从fileChannel读取数据
	 * 
	 * @throws IOException
	 */
	private static void method2() throws IOException {
		// 1.创建管道
		RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();

		// 2.创建buffer
		ByteBuffer buf = ByteBuffer.allocate(48);

		// 3.读到缓冲区
		int bytesRead = inChannel.read(buf);

	}

	/**
	 * 创建管道-FileChannel
	 * 
	 * @throws FileNotFoundException
	 */
	private static void method1() throws FileNotFoundException {
		// 方式一：创建管道-FileChannel
		RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();

		// 方式二：通过输入流，输出流
		FileInputStream fis = new FileInputStream("");
		FileChannel channel = fis.getChannel();

	}
}
