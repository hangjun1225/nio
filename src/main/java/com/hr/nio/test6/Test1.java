package com.hr.nio.test6;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test1 {
	public static void main(String[] args) {
		// 创建绝对路径
//		method1();

		// 创建相对路径Path
//		method2();

		// 标准化路径；Path.normalize()
		method3();

	}

	/**
	 * 标准化路径
	 */
	private static void method3() {
		String originalPath = "d:\\data\\projects\\a-project\\..\\another-project";

		Path path1 = Paths.get(originalPath);
		System.out.println("path1 = " + path1);

		Path path2 = path1.normalize();
		System.out.println("path2 = " + path2);
	}

	/**
	 * 创建相对路径
	 */
	private static void method2() {
		// 方式一：
		// 相对于d:\\data的 projects目录
		Path projects = Paths.get("d:\\data", "projects");
		// 相对于d:\\data的 projects目录下myfile.txt文件
		Path file = Paths.get("d:\\data", "projects\\myfile.txt");

		// 方式二：
		Path currentDir1 = Paths.get(".");
		System.out.println(currentDir1.toAbsolutePath());

		Path currentDir2 = Paths.get("..");
		System.out.println(currentDir1.toAbsolutePath());

		// 方式三：结合上面两种写法
		Path path1 = Paths.get("d:\\data", ".\\projects");
		System.out.println(path1.toAbsolutePath());

		Path path2 = Paths.get("d:\\data\\projects", "..\\projects\\a.txt");
		System.out.println(path2.toAbsolutePath());
	}

	/**
	 * 创建Path实例
	 */
	private static void method1() {
		// 绝对路径
		// window系统
		Path path1 = Paths.get("c:\\data\\myfile.txt");
		// linux系统
		Path path2 = Paths.get("/home/hangjun/myfile.txt");

		// 注意：如果把以/开头path的格式运行在windows系统中，系统会将其解析为相对路径。例如：
		// /home/hangjun/myfile.txt
		// c:/home/hangjun/myfile.txt
	}
}
