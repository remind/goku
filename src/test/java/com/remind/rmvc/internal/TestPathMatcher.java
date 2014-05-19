package com.remind.rmvc.internal;

import org.junit.Assert;
import org.junit.Test;

public class TestPathMatcher {

	private PathMatcher matcher;
	private String pattern;
	private String path;
	
	@Test
	public void testMatch() {
		pattern = "/blog";
		path = "/blog";
		matcher = new PathMatcher(pattern, path);
		Assert.assertTrue(matcher.doMatch());
		
		pattern = "/bloga";
		path = "/blog";
		matcher = new PathMatcher(pattern, path);
		Assert.assertFalse(matcher.doMatch());
		
		
		pattern = "/blog";
		path = "/bloga";
		matcher = new PathMatcher(pattern, path);
		Assert.assertFalse(matcher.doMatch());
		
		pattern = "/blog/{author}";
		path = "/blog/me";
		matcher = new PathMatcher(pattern, path);
		Assert.assertTrue(matcher.doMatch());
		
		
		pattern = "{author}/blog";
		path = "me/blog";
		matcher = new PathMatcher(pattern, path);
		Assert.assertTrue(matcher.doMatch());
	}
	
	@Test
	public void testHandle() {
		Assert.assertEquals("/blog/aa", PathMatcher.filter("/blog/aa/"));
		Assert.assertEquals("/blog/aa", PathMatcher.filter("/blog//aa"));
		Assert.assertEquals("/blog/aa", PathMatcher.filter("/blog//aa/"));
	}
}
