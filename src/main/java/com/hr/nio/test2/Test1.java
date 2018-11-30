package com.hr.nio.test2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 创建缓冲区
 * 
 * @author hangjun
 *
 */
public class Test1 {
	public static void main(String[] args) throws IOException {
		// 创建缓冲区
		// method1();

		// 向Buffer中写数据
		// method2();

		// 切换成读模式
		// method3();

		// 向buffer中读数据
		// method4();

		// rewind();
		// method5();

		// clear() 恢复默认值,为写模式使用
		// method6();

		// compact() 在读模式下使用
		// method7();

		// mark()和reset()
//		method8();

		// equals()和compareTo()  都是比较的剩余元素！
//		method9();
		
		// 缓冲区分片
//		method10();
		
		// 只读缓冲区
//		method11();
		
		// 直接缓冲区和间接缓冲区
		method12();
		
	}
	/**
	 * 直接缓冲区和间接缓冲区
	 * 
	 * 直接缓冲区 是为加快 I/O 速度，而以一种特殊的方式分配其内存的缓冲区。
	 */
	private static void method12() {
		//直接缓冲区
		ByteBuffer buffer1 = ByteBuffer.allocateDirect( 10 );
		System.out.println(buffer1.isDirect());
		
		//间接缓冲区
		ByteBuffer buffer2 = ByteBuffer.allocate( 10 );
		System.out.println(buffer2.isDirect());
		
		
	}
	/**
	 * 只读缓冲区
	 * 
	 * 只读缓冲区非常简单 ― 您可以读取它们，但是不能向它们写入。可以通过调用缓冲区的 asReadOnlyBuffer() 方法，将任何常规缓冲区转换为只读缓冲区，这个方法返回一个与原缓冲区完全相同的缓冲区(并与其共享数据)，只不过它是只读的。
	 * 
	 * 作用：只读缓冲区对于保护数据很有用。在将缓冲区传递给某个对象的方法时，您无法知道这个方法是否会修改缓冲区中的数据。创建一个只读的缓冲区可以 保证 该缓冲区不会被修改。不能将只读的缓冲区转换为可写的缓冲区。
	 * 
	 */
	private static void method11() {
		//1.首先创建一个长度为 10 的 ByteBuffer
		ByteBuffer buffer = ByteBuffer.allocate( 10 );
		
		//2.创建一个只读缓冲区
		ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
		
		//这两个不是同一个对象
		System.out.println(buffer.getClass().getName());
		System.out.println(asReadOnlyBuffer.getClass().getName());
		
	}
	/**
	 * slice() 方法根据现有的缓冲区创建一种 子缓冲区 。
	 * 也就是说，它创建一个新的缓冲区，新缓冲区与原来的缓冲区的一部分共享数据。
	 */
	private static void method10() {
		//1.首先创建一个长度为 10 的 ByteBuffer
		ByteBuffer buffer = ByteBuffer.allocate( 10 );
		//2.使用数据来填充这个缓冲区，在第 n 个槽中放入数字 n：
		for (int i=0; i<buffer.capacity(); ++i) {
		     buffer.put( (byte)i );
		}
		//3.缓冲区 分片 ，以创建一个包含槽 3 到槽 6 的子缓冲区。
		buffer.position( 3 );
		buffer.limit( 7 );
		ByteBuffer slice = buffer.slice();
		
		
		
		//子缓冲区是共享数据！！！
		//1.遍历子缓冲区，将每一个元素乘以 11 来改变它。例如，5 会变成 55。
		for (int i=0; i<slice.capacity(); ++i) {
		     byte b = slice.get( i );
		     b *= 11;
		     slice.put( i, b );
		}
		
		//2.查看原缓冲区的内容
		buffer.position( 0 );
		buffer.limit( buffer.capacity() );
		
		while (buffer.remaining()>0) {
		     System.out.println( buffer.get() );
		}
	}
	/**
	 * 当满足下列条件时，表示两个Buffer相等：
		1.有相同的类型（byte、char、int等）。
		2.Buffer中剩余的byte、char等的个数相等。
		3.Buffer中所有剩余的byte、char等都相同。
	 * 实际上，它只比较Buffer中的剩余元素
	 * 
	 * 
	 * 
	 * compareTo()方法比较两个Buffer的剩余元素(byte、char等)， 如果满足下列条件，则认为一个Buffer“小于”另一个Buffer：
		第一个不相等的元素小于另一个Buffer中对应的元素 。
		所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)。
	 * 
	 */
	private static void method9() {
		ByteBuffer buffer1=ByteBuffer.allocate(512);
		buffer1.put("hello".getBytes());
		
		ByteBuffer buffer2=ByteBuffer.allocate(1024);
		buffer2.put("hello".getBytes());
	
		System.out.println(buffer1.equals(buffer2));//设置buffer1初始化大小为512，buffer2初始化大小为1024，比较得到false
		System.out.println(buffer1.compareTo(buffer2));//-522
	
	
		ByteBuffer buffer3=ByteBuffer.allocate(1024);
		buffer3.put("helli".getBytes());
		
		ByteBuffer buffer4=ByteBuffer.allocate(1024);
		buffer4.put("hello".getBytes());
	
		System.out.println(buffer3.equals(buffer4));//true  比较的剩余元素是否相等，类型是否一致
		System.out.println(buffer3.compareTo(buffer4));//0
	
		
	
	}

	/**
	 * 通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。之后可以通过调用Buffer.reset()方法恢复到这个position。
	 */
	private static void method8() {
		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("hello".getBytes());// 先放一些数据进去作为测试

		// 第一次读取数据
		buffer.flip();
		buffer.mark();// 设置标记点
		byte b = buffer.get();
		System.out.println(buffer.position());

		buffer.reset();// 回滚标记点
		System.out.println(buffer.position());
	}

	/**
	 * compact() 如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。
	 * 
	 * compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
	 */
	private static void method7() {
		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("hello".getBytes());// 先放一些数据进去作为测试

		// 第一次读取数据
		buffer.flip();
		byte b = buffer.get();// 如果没有切换到读模式下，那么get()取得是第6个元素,但是第六个元素为空。导致逻辑有问题的。
		System.out.println(buffer.position());

		// 使用compact()方法之后
		buffer.compact();// 这个只能在读模式下使用. 必须之前调用buffer.flip();
		System.out.println(buffer.position());

	}

	/**
	 * 旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成。
	 * 
	 * @throws IOException
	 */
	private static void method6() throws IOException {
		// 1.创建管道
		FileInputStream fis = new FileInputStream("E:\\sts\\workspace01\\nio\\src\\main\\resources\\test1.txt");
		FileChannel fcin = fis.getChannel();

		FileOutputStream fos = new FileOutputStream(
				"E:\\\\sts\\\\workspace01\\\\nio\\\\src\\\\main\\\\resources\\\\test2.txt");
		FileChannel fcout = fos.getChannel();

		// 2.创建缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 3.读到缓冲区,写到管道中
		while (true) {
			buffer.clear();
			int readCount = fcin.read(buffer);
			if (readCount == -1) {
				break;
			}
			buffer.flip();
			fcout.write(buffer);
		}
		// 或者
		// while((fcin.read(buffer)!=-1)) {
		// //1.切换读模式
		// buffer.flip();
		// fcout.write(buffer);
		//
		// //2.为下一次读到缓冲区做准备
		// buffer.clear();
		// }

		fis.close();
		fos.close();
		System.out.println("文件拷贝成功");
	}

	/**
	 * Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
	 */
	private static void method5() {
		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("hello".getBytes());// 先放一些数据进去作为测试
		// 第一次读取数据
		buffer.flip();
		byte b = buffer.get();

		// 重置position=0
		buffer.rewind();
		// 第二次读取数据
		byte c = buffer.get();

	}

	/**
	 * 向buffer中读数据
	 * 
	 * @throws IOException
	 */
	private static void method4() throws IOException {
		// 方式一：通过channel.write();
		// 1.创建管道
		FileOutputStream fos = new FileOutputStream(
				"E:\\\\sts\\\\workspace01\\\\nio\\\\src\\\\main\\\\resources\\\\test1.txt");
		FileChannel channel = fos.getChannel();
		// 2.创建buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put("hello".getBytes());// 先放一些数据进去作为测试

		// 3.写入到管道中
		buffer.flip();
		channel.write(buffer);

		fos.close();

		// 方式二：通过get的方式
		// 注意：前三种的位置会不会发生改变呢？会! 第四种位置肯定不会改变的
		// 得到一个字节
		buffer.rewind();// 重置到起始位置
		byte b = buffer.get();

		// 得到一个字节数组中,注意这个字节数据的长度可能不够用？
		buffer.rewind();// 重置到起始位置
		byte[] dst = new byte[buffer.remaining()]; // 初始化的数组的长度<=remaining;如果数组的长度小于remaining,那么只会拷贝数据长度的数据，buffer中还有数据需要读取
		// byte[] dst=new byte[3];
		buffer.get(dst);

		// 得到一个字节数组中,数据是原buffer的起始位置3，长度4的数据
		buffer.rewind();// 重置到起始位置
		buffer.get(dst, 1, 4);

		// 得到原buffer第1个位置的字节
		byte c = buffer.get(1);

	}

	/**
	 * 切换成读模式
	 */
	private static void method3() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.flip();
	}

	/**
	 * 向Buffer中写数据
	 * 
	 * @throws IOException
	 */
	private static void method2() throws IOException {
		// 方式一:通过channel写到缓冲区
		FileOutputStream fos = new FileOutputStream("");
		FileChannel channel = fos.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		channel.write(buffer);

		// 方式二：通过put()方法放入数据
		byte b = 'a';
		byte[] message = "hello".getBytes();
		ByteBuffer buffer2 = ByteBuffer.allocate(1024);
		// put有四种方式：前三种方式会改变内部的position,第四种不会
		buffer.put(b);
		buffer.put(message);
		buffer.put(buffer2);
		buffer.put(1, b);// 这个要特别注意

	}

	/**
	 * 创建缓冲区
	 */
	private static void method1() {
		// 1.方式一：创建缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 2.方式二：缓冲区的包装
		byte[] array = new byte[1024];
		ByteBuffer buffer2 = ByteBuffer.wrap(array);

		// 对比两者的区别,其实没啥子区别
		// ByteBuffer自己内部有一个数组,第一种方式自己创建一个数据,第二种方式使用用户自己创建的数组

	}
}
