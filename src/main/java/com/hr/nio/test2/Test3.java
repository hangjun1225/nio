package com.hr.nio.test2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class Test3 {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		//transferFrom()
		method1();
		//transferTo();
		method2();
	}

	private static void method2() throws IOException {
		// 通道之间的数据传输
		RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 0;
		long count = fromChannel.size();

		toChannel.transferFrom(fromChannel, position, count);
		// 1.方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。
		// 如果源通道的剩余空间小于 count个字节，则所传输的字节数要小于请求的字节数。 例如：磁盘空间不够了,那么传输的字节就是磁盘剩余的大小 <count

		// 2.此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
	}

	private static void method1() throws IOException {
		// 通道之间的数据传输
		RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 0;
		long count = fromChannel.size();

		fromChannel.transferTo(position,count,toChannel);
		// 1.方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。
		// 如果写入通道的剩余空间小于 count个字节，则所传输的字节数要小于请求的字节数。 例如：磁盘空间不够了,那么传输的字节就是磁盘剩余的大小 <count

		// 2.此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
	}
}
