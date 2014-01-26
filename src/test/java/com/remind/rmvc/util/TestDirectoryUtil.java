package com.remind.rmvc.util;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.remind.rmvc.utils.DirectoryUtil;

public class TestDirectoryUtil {

	@Test
	public void testGetFileByRecursive() {
		File dir;
		Set<File> fileSet = null;
		try {
			dir = new File(TestDirectoryUtil.class.getResource("/").toURI());
			fileSet = DirectoryUtil.getFileByRecursive(dir, new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory() || pathname.getName().equals("TestDirectoryUtil.class")) {
						return true;
					}
					return false;
				}
			});
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(fileSet.iterator().next().getName().equals("TestDirectoryUtil.class") && fileSet.size() == 1);
	}
	
	@Test
	public void testGetAllFileByRecursive() {
		File dir;
		Set<File> fileSet = null;
		File sourceFile = null;
		try {
			sourceFile = new File(TestDirectoryUtil.class.getResource("/").getPath(),"com/remind/rmvc/util/TestDirectoryUtil.class");
			dir = new File(TestDirectoryUtil.class.getResource("/").toURI());
			fileSet = DirectoryUtil.getAllFileByRecursive(dir, new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory() || pathname.getName().equals("TestDirectoryUtil.class")) {
						return true;
					}
					return false;
				}
			});
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(fileSet.contains(sourceFile) && fileSet.size() > 1);
	}
}
