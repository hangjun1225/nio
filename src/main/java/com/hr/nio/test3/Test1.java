package com.hr.nio.test3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Test1 {
	public static void main(String[] args) throws IOException {
	
		// 1.1selector是什么？
		// 简单版：一个selector管理多个channel,而且是在一个线程中的。
		// Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。
		
		// 1.2为什么要用selector?
		// 只需要更少的线程来处理通道. 线程的切换开销大！！
		// 可以只用一个线程处理所有的通道。对于操作系统来说，线程之间上下文切换的开销很大，而且每个线程都要占用系统的一些资源（如内存
		// 1.创建selector
//		method1();
		
		
		// 2.channel注册到selector中 (渠道必须是非阻塞的模式,fileChannel不是！)
		method2();
	}
	/**
	 * channel注册到selector中 (渠道必须是非阻塞的模式,fileChannel不是！)
	 * @throws IOException 
	 */
	private static void method2() throws IOException {
		
		//1.创建selector
		Selector selector=Selector.open();
		
		//2.打开一个ServerSocketChannel
		//2.1创建一个ServerSocketChannel
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking( false );//配置非阻塞模式
		
		//2.2得到一个socket，bind定端口10086
		ServerSocket ss = ssc.socket();//得到一个socket
		InetSocketAddress address = new InetSocketAddress(10086);
		ss.bind( address );
		
		//3.channel注册到selector中
		ssc.register(selector,SelectionKey.OP_ACCEPT); //selector监听这个channel的accept事件
		
		//说明：
		//1.register() 的第一个参数总是这个 Selector。第二个参数是 OP_ACCEPT，这里它指定我们想要监听 accept 事件，也就是在新的连接建立时所发生的事件。这是适用于 ServerSocketChannel 的唯一事件类型。
		//2.请注意对 register() 的调用的返回值。 SelectionKey 代表这个通道在此 Selector 上的这个注册。当某个 Selector 通知您某个传入事件时，它是通过提供对应于该事件的 SelectionKey 来进行的。SelectionKey 还可以用于取消通道的注册。
		
		
		//4.通过selector判断是否有准备就绪的通道 
		int num = selector.select();//这个方法返回你所感兴趣的事件（如连接、接受、读或写）已经准备就绪的那些通道。 一个selector中可能有多个通道
		
		
		//4.通过selector得到SelectionKey的set集合.遍历这个set集合
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		Iterator<SelectionKey> iterator = selectedKeys.iterator();
		while(iterator.hasNext()) {
			SelectionKey next = iterator.next();
			if(next.isAcceptable()) {
				// a connection was accepted by a ServerSocketChannel.
			}else if(next.isConnectable()) {
				// a connection was established with a remote server.
			}else if(next.isReadable()) {
				// a channel is ready for reading
			}else if(next.isWritable()) {
				// a channel is ready for writing
			}
			iterator.remove();
		}
		
		
		
	}

	/**
	 * 创建selector
	 * 
	 * @throws IOException
	 */
	private static void method1() throws IOException {
		// 1.创建selector
		Selector selector = Selector.open();
	}
}
