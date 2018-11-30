package com.hr.nio.test8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerTest {

	public static void main(String[] args) throws Exception {
		// 1.创建serverSocketChannel,配置非阻塞,配置绑定端口
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		serverChannel.socket().bind(new InetSocketAddress(10086));
		// 2.创建一个selector
		Selector selector = Selector.open();
		// 3.加入到selector中
		SelectionKey serverKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("server starting.......");

		// 4.获取就绪channel
		while (true) {
			int count = selector.select();// 这个是阻塞的方法
			if (count > 0) {
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey next = iterator.next();
					if (next.isAcceptable()) {
						// 获得和客户端连接的通道
						SocketChannel socketChannel = ((ServerSocketChannel) next.channel()).accept();
						// 设置成非阻塞
						socketChannel.configureBlocking(false);

						// 在这里可以发送消息给客户端
						// socketChannel.write(ByteBuffer.wrap(new String("hello client").getBytes()));

						// 将选择器注册到连接到的客户端信道，
						// 并指定该信道key值的属性为OP_READ，
						socketChannel.register(next.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));

					} else if (next.isConnectable()) {

					} else if (next.isReadable()) {
						// a channel is ready for reading
						read(next);
					} else if (next.isWritable()) {
						// a channel is ready for writing
						write(next);
					}
					iterator.remove();
				}
			}
		}
	}

	private static void write(SelectionKey next) throws IOException {
		SocketChannel channel = (SocketChannel) next.channel();

		ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
		channel.write(buffer);

	}

	private static void read(SelectionKey key) throws Exception {
		//  服务器可读消息，得到事件发生的socket通道  
		SocketChannel channel = (SocketChannel) key.channel();
		//  穿件读取的缓冲区  
		ByteBuffer buffer = ByteBuffer.allocate(10);
		channel.read(buffer);
		byte[] array = buffer.array();
		String msg = new String(array).trim();
		System.out.println("server receive from client: " + msg);

		// 设置key的兴趣流： 读,写
		key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
	}

}
