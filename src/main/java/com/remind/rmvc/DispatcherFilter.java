package com.remind.rmvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.context.ThreadLocalContext;
import com.remind.rmvc.internal.ActionInfo;
import com.remind.rmvc.internal.ActionResult;
import com.remind.rmvc.route.RouteInfo;
import com.remind.rmvc.route.RouteResult;

/**
 * 利用Filter来拦截
 * 
 * @author remind
 * 
 */
public class DispatcherFilter implements Filter {

	private static Logger logger = Logger.getLogger(DispatcherFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("filter init");
		Application.start();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpContext httpContext = HttpContext.getCurrent();
		httpContext.setRequest((HttpServletRequest) request);
		httpContext.setResponse((HttpServletResponse) response);
		httpContext.getRequest().setCharacterEncoding(GlobalConfig.ENCODING);
		if (!doDispatch()) {
			chain.doFilter(request, response);
		}
		ThreadLocalContext.remove();
	}

	@Override
	public void destroy() {
	}

	/**
	 * 进行分发
	 * 
	 * @return
	 */
	protected boolean doDispatch() {
		RouteResult routeResult = GlobalConfig.getMvcRoute().route(
				new RouteInfo(HttpContext.getCurrent().getMatchPath(),
						HttpContext.getCurrent().getRequest().getMethod()
								.equalsIgnoreCase("GET")));
		if (routeResult.isSuccess()) { // 路由匹配成功
			ActionInfo action = routeResult.getAction();
			try {
				ActionResult result = (ActionResult) action.getMethod().invoke(
						action.getControllerClass(),
						routeResult.getVariable().values().toArray());
				result.render();
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

}
