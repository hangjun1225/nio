package com.hr.nio.test9.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
	private static final int BUF_SIZE = 1024;
	private static final int PORT = 10086;
	private static final int TIMEOUT = 3000;

	public static void main(String[] args) {
		selector();
	}

	public static void selector() {
		
		Selector selector = null;
		ServerSocketChannel ssc = null;
		try {
			//1.创建一个selector
			selector = Selector.open();
			//2.创建一个serverSocketChannel,同时配置非阻塞,绑定端口
			ssc = ServerSocketChannel.open();
			ssc.socket().bind(new InetSocketAddress(PORT));
			ssc.configureBlocking(false);
			//3.channel注册到selector中
			ssc.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
//				if (selector.select(TIMEOUT) == 0) {
//					System.out.println("==");
//					continue;
//				}
				int num = selector.select();
				
				if(num<=0) {
					continue;
				}
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					if (key.isAcceptable()) {
						handleAccept(key);
					}
					if (key.isReadable()) {
						handleRead(key);
					}
					if (key.isWritable() && key.isValid()) {
						handleWrite(key);
					}
					if (key.isConnectable()) {
						System.out.println("isConnectable = true");
					}
					iter.remove();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (selector != null) {
					selector.close();
				}
				if (ssc != null) {
					ssc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void handleAccept(SelectionKey key) throws IOException {
		ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
		SocketChannel sc = ssChannel.accept();
		sc.configureBlocking(false);
		sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
	}

	public static void handleRead(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		long bytesRead = sc.read(buf);
		while (bytesRead > 0) {
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			System.out.println();
			buf.clear();
			bytesRead = sc.read(buf);
		}
		if (bytesRead == -1) {
			sc.close();
		}
	}

	public static void handleWrite(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip();
		SocketChannel sc = (SocketChannel) key.channel();
		while (buf.hasRemaining()) {
			sc.write(buf);
		}
		buf.compact();
	}

	
}
