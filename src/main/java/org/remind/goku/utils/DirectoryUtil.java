package org.remind.goku.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 文件夹相关操作工具类
 * @author remind
 *
 */
public class DirectoryUtil {

	/**
	 * 循环获取一个目录下面的所有文件
	 * @param dir
	 * @param filter
	 * @return 只返回文件，不包括文件夹
	 */
	public static Set<File> getFileByRecursive(File dir, FileFilter filter) {
		if (!dir.exists() || !dir.isDirectory()) {
			return null;
		}
		Set<File> fileSet = new LinkedHashSet<File>();
		Stack<File> tempDir = new Stack<File>();
		tempDir.push(dir);
		while(!tempDir.empty()) {
			File[] dirFiles = tempDir.pop().listFiles(filter);
			for (int i = 0; i < dirFiles.length; i++) {
				File file = dirFiles[i];
				if (file.isFile()) {
					fileSet.add(file);
				}
				if (file.isDirectory()) {
					tempDir.push(file);
				}
			}
		}
		return fileSet;
	}
	
	/**
	 * 循环获取一个目录下面的所有文件及文件夹
	 * @param dir
	 * @param filter
	 * @return 返回所有文件及文件夹
	 */
	public static Set<File> getAllFileByRecursive(File dir, FileFilter filter) {
		if (!dir.exists() || !dir.isDirectory()) {
			return null;
		}
		Set<File> fileSet = new LinkedHashSet<File>();
		Stack<File> tempDir = new Stack<File>();
		tempDir.push(dir);
		while(!tempDir.empty()) {
			File[] dirFiles = tempDir.pop().listFiles(filter);
			for (int i = 0; i < dirFiles.length; i++) {
				File file = dirFiles[i];
				fileSet.add(file);
				if (file.isDirectory()) {
					tempDir.push(file);
				}
			}
		}
		return fileSet;
	}
}
