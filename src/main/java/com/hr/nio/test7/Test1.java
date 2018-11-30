package com.hr.nio.test7;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class Test1 {
	public static void main(String[] args) throws IOException {
		// AsynchronousFileChannel是什么？
		// 使用AsynchronousFileChannel可以实现异步地读取和写入文件数据。

		// 创建一个AsynchronousFileChannel
		// method1();

		// 读取数据-方式一Future
		// method2();

		// 读取数据-方式二使用CompletionHandler读取数据
		// method3();

		// 写入数据-方式一Future
		// method4();

		// 写入数据-方式二使用CompletionHandler写入数据
		method5();

	}

	/**
	 * 写入数据-方式二使用CompletionHandler写入数据
	 * @throws IOException 
	 */
	private static void method5() throws IOException {
		// 1.建立管道
		Path path = Paths.get("test-write.txt");
		System.out.println(path.toAbsolutePath());
		// 写入目标文件要提前创建好，如果它不存在的话，write() 方法会抛出一个 java.nio.file.NoSuchFileException。
		if (!Files.exists(path)) {// 判断当前文件是否存在
			Files.createFile(path);// 不存在创建该文件
		}
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		// 2.建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("test data".getBytes());

		// 3.写入通道中
		buffer.flip();
		long position = 0;
		Future<Integer> operation = fileChannel.write(buffer, position);
		
		fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
		    @Override
		    public void completed(Integer result, ByteBuffer attachment) {
		        System.out.println("bytes written: " + result);
		        
		    }
		    @Override
		    public void failed(Throwable e, ByteBuffer attachment) {
		        System.out.println("Write failed");
		        e.printStackTrace();
		    }
		});
		
	}

	/**
	 * 写入数据-方式一Future
	 * 
	 * @throws IOException
	 */
	private static void method4() throws IOException {
		// 1.建立管道
		Path path = Paths.get("test-write.txt");
		System.out.println(path.toAbsolutePath());
		// 写入目标文件要提前创建好，如果它不存在的话，write() 方法会抛出一个 java.nio.file.NoSuchFileException。
		if (!Files.exists(path)) {// 判断当前文件是否存在
			Files.createFile(path);// 不存在创建该文件
		}
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		// 2.建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("test data".getBytes());

		// 3.写入通道中
		buffer.flip();
		long position = 0;
		Future<Integer> operation = fileChannel.write(buffer, position);
		buffer.clear();
		while (!operation.isDone())
			;

		System.out.println("Write done");

	}

	/**
	 * 读取数据-方式二使用CompletionHandler读取数据
	 */
	private static void method3() throws IOException {
		// 1.建立管道
		Path path = Paths.get("E:\\sts\\workspace01\\nio\\fromFile.txt");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

		// 2.建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		// 3.读到缓冲区
		fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				System.out.println("result = " + result);
				attachment.flip();
				byte[] data = new byte[attachment.limit()];
				attachment.get(data);
				System.out.println(new String(data));
				attachment.clear();
				System.out.println("执行成功");
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				System.out.println("执行失败");
			}
		});

		// 说明：
		// 一旦读取操作完成，CompletionHandler的 complete() 方法将会被调用。
		// 它的第一个参数是个Integer类型，表示读取的字节数。第二个参数 attachment 是 ByteBuffer 类型的，用来存储读取的数据。它其实也是
		// read()方法的第三个参数
		// 当前示例中，我们选用 ByteBuffer 来存储数据，其实我们也可以选用其他的类型。

		// 读取失败的时候，CompletionHandler的 failed()方法会被调用。
	}

	/**
	 * 读取数据-方式一
	 * 
	 * @throws IOException
	 */
	private static void method2() throws IOException {
		// 1.建立管道
		Path path = Paths.get("E:\\sts\\workspace01\\nio\\fromFile.txt");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

		// 2.建立buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		// 3.读到缓冲区
		Future<Integer> operation = fileChannel.read(buffer, position);

		// 说明：
		// 第一个参数是ByteBuffer，从 AsynchronousFileChannel 中读取的数据先写入这个 ByteBuffer 。
		// 第二个参数表示从文件读取数据的开始位置。
		// 此 read() 方法会立即返回，即使整个读的过程还没有完全结束。我们可以通过operation.isDone()来检查读取是否完成。这里的
		// operation 是上面调用 read() 方法返回的 Future 类型的实例。

		while (!operation.isDone())
			;

		buffer.flip();
		byte[] data = new byte[buffer.limit()];
		buffer.get(data);
		System.out.println(new String(data));
		buffer.clear();

	}

	/**
	 * 创建一个AsynchronousFileChannel
	 * 
	 * @throws IOException
	 */
	private static void method1() throws IOException {
		Path path = Paths.get("E:\\sts\\workspace01\\nio\\fromFile.txt");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

		// 说明：
		// 第一个参数是一个 PATH 的对像实例，它指向了那个与 AsynchronousFileChannel 相关联的文件。
		// 第二个参数是一个或多个操作选项，它决定了 AsynchronousFileChannel 将对目标文件做何种操作。示例代码中我们使用了
		// StandardOpenOption.READ ，它表明我们将要对目标文件进行读操作。
	}
}
