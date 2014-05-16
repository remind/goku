package com.remind.rmvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.remind.rmvc.test.controller.blog.PageController;

public class RouterTest {

	private WebClient webClient = new WebClient();

	public void test() {
		String url = "http://localhost";
		try {
			HtmlPage page = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRefection() {
		System.out.println(System.currentTimeMillis());
		Class<?> cls = PageController.class;
		
		Object[] NO_ARGS_OBJECT = new Object[1];
		Method method;
		try {
			Object instance = cls.newInstance();
			//System.out.println(System.currentTimeMillis());
			method = cls.getDeclaredMethod("tag", String.class);
			NO_ARGS_OBJECT[0] = "aa";
			method.invoke(instance, NO_ARGS_OBJECT);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
	}
}
