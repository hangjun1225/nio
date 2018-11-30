package com.hr.nio.other;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Test1 {

	public static void main(String[] args) throws IOException {
		// 文件锁定
		// 要获取文件的一部分上的锁，您要调用一个打开的 FileChannel 上的 lock() 方法。注意，如果要获取一个排它锁，您必须以写方式打开文件。
		RandomAccessFile raf = new RandomAccessFile("fromFile.txt", "rw");
		FileChannel fc = raf.getChannel();
		// FileLock lock = fc.lock( start, end, false );
		FileLock lock = fc.lock(0, 6, false);

		// do something
		// 在拥有锁之后，您可以执行需要的任何敏感操作，然后再释放锁：

		lock.release();
	}

}
