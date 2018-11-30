package com.hr.nio.test6;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class Test2 {

	public static void main(String[] args) throws IOException {
		// exists
		method1();
		// createDirectory
		method2();

		// copy 如果目标文件存在会报错
		method3();

		// copy 允许覆盖操作
		method4();

		// move
		method5();

		// delete
		method6();

		// walkFileTree的语法
		method7();
		
		// walkFileTree的应用-Searching For Files
		method8();
		
		// walkFileTree的应用-Deleting Directories Recursively
		method9();
	}
	
	/**
	 *  这是删除操作,千万别误删文件
	 *  walkFileTree的应用-Deleting Directories Recursively
	 */
	private static void method9() {
		Path rootPath = Paths.get("d:/data/to-delete");

		try {
		  Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
		    @Override
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		      System.out.println("delete file: " + file.toString());
		      Files.delete(file);
		      return FileVisitResult.CONTINUE;
		    }

		    @Override
		    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		      Files.delete(dir);
		      System.out.println("delete dir: " + dir.toString());
		      return FileVisitResult.CONTINUE;
		    }
		  });
		} catch(IOException e){
		  e.printStackTrace();
		}
	}
	/**
	 * walkFileTree的应用-Searching For Files
	 */
	private static void method8() {
		Path rootPath = Paths.get("data");
		String fileToFind = File.separator + "README.txt";

		try {
		  Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
		    
		    @Override
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		      String fileString = file.toAbsolutePath().toString();
		      //System.out.println("pathString = " + fileString);

		      if(fileString.endsWith(fileToFind)){
		        System.out.println("file found at path: " + file.toAbsolutePath());
		        return FileVisitResult.TERMINATE;
		      }
		      return FileVisitResult.CONTINUE;
		    }
		  });
		} catch(IOException e){
		    e.printStackTrace();
		}
	}

	/**
	 * walkFileTree
	 * 
	 * @throws IOException
	 */
	private static void method7() throws IOException {
		// 遍历目录
		Path path = Paths.get("data/subdir/logging-moved.properties");
		Files.walkFileTree(path, new FileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println("pre visit dir:" + dir);
				// CONTINUE
				// TERMINATE
				// SKIP_SIBLINGS
				// SKIP_SUBTREE
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("visit file: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				System.out.println("visit file failed: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				System.out.println("post visit directory: " + dir);

				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * delete
	 */
	private static void method6() {
		Path path = Paths.get("data/subdir/logging-moved.properties");

		try {
			Files.delete(path);
		} catch (IOException e) {
			// deleting file failed
			e.printStackTrace();
		}
	}

	/**
	 * move
	 */
	private static void method5() {
		Path sourcePath = Paths.get("data/logging-copy.properties");
		Path destinationPath = Paths.get("data/subdir/logging-moved.properties");

		try {
			Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// moving file failed.
			e.printStackTrace();
		}
	}

	/**
	 * copy 允许覆盖操作
	 */
	private static void method4() {
		Path sourcePath = Paths.get("data/logging.properties");
		Path destinationPath = Paths.get("data/logging-copy.properties");

		try {
			Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (FileAlreadyExistsException e) {
			// destination file already exists

		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}
	}

	/**
	 * copy 如果目标文件存在会报错
	 * 
	 */
	private static void method3() {
		Path sourcePath = Paths.get("data/logging.properties");
		Path destinationPath = Paths.get("data/logging-copy.properties");

		try {
			Files.copy(sourcePath, destinationPath);
		} catch (FileAlreadyExistsException e) {
			// destination file already exists

		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}
	}

	/**
	 * createDirectory()
	 */
	private static void method2() {
		Path path = Paths.get("data/subdir");
		try {
			Path newDir = Files.createDirectory(path);
		} catch (FileAlreadyExistsException e) {
			// the directory already exists.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * exists()
	 */
	private static void method1() {
		Path path = Paths.get("data/logging.properties");
		// 判断是否存在该文件
		boolean exists = Files.exists(path);
		System.out.println(exists);
		// 判断是否存在该文件.如果该文件是link文件，返回值是false;
		boolean pathExists = Files.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		System.out.println(pathExists);
	}

}
