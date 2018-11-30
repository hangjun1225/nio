package com.hr.nio.test8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ClientTest {

	public static void main(String[] args) throws IOException {
		// 1.新建一个管道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);// 设置非阻塞模式
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));
		
		//2.新建一个selector
		Selector selector = Selector.open();
		
		//3.将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		
		while(true) {
			int num = selector.select();
			if(num>=1) {
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while(it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					if(key.isConnectable()) {
						SocketChannel channel = (SocketChannel)key.channel();
						if (channel.isConnectionPending()) {
							channel.finishConnect();
						}
						// 设置成非阻塞
						channel.configureBlocking(false);
						// 在这里可以给服务端发送信息哦
						channel.write(ByteBuffer.wrap(new String("hello server!").getBytes()));
						// 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
						channel.register(selector, SelectionKey.OP_READ); // 获得了可读的事
					}else if(key.isReadable()) {
						read(key);
					}else if(key.isWritable()) {
						write(key);
					}
					
				}
			}
		}
		
		
	}
	private static void write(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel(); 
		
		ByteBuffer buffer=ByteBuffer.wrap("hello".getBytes());
		channel.write(buffer);
		
	}
	/**
	 * 
	 * @param key
	 * @throws IOException
	 */
	private static void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel(); 
		ByteBuffer buffer = ByteBuffer.allocate(10);
		channel.read(buffer);
		
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("client receive msg from server:" + msg);
		
		//设置key的兴趣流： 读,写
		key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
		
	}

}
