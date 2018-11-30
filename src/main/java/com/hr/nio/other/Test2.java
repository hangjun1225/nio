package com.hr.nio.other;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Test2 {
	/**
	 * 感觉太啃爹,不好用！！！
	 * @param args
	 * @throws CharacterCodingException
	 */
	public static void main(String[] args) throws CharacterCodingException {
		//1.得到字符集
		Charset latin1 = Charset.forName( "UTF-8" );
		//2.得到解码器和编码器
		CharsetDecoder decoder = latin1.newDecoder();
		CharsetEncoder encoder = latin1.newEncoder();
		
		//解码成字符
		ByteBuffer buffer1=ByteBuffer.allocate(1024);
		buffer1.put("杭军".getBytes());
		
		buffer1.flip();
		CharBuffer cb = decoder.decode( buffer1 );

		//编码成字节
		ByteBuffer buffer2 = encoder.encode(cb);
		
		while(buffer2.hasRemaining()) {
			System.out.println(buffer2.get());
		}
		
		
		
		
	}
}
