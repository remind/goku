package com.remind.rmvc.test.controller.blog;

import com.remind.rmvc.annotations.Get;
import com.remind.rmvc.annotations.Path;

/**
 * 这个是用来测试的
 * tag类最好放在第一个，或者这个类就只有他一个方法
 * @author remind
 *
 */
@Path("/blog/page")
@Get
public class PageController {

	@Path("/tag")
	@Get
	public void tag(String name) {
		//System.out.println("hello " + name);
	}
}
