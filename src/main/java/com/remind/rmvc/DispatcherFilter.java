package com.remind.rmvc;

import java.io.IOException;

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
import com.remind.rmvc.internal.action.ActionInvoke;
import com.remind.rmvc.internal.action.ActionResult;
import com.remind.rmvc.internal.cache.RouteResultCache;
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
	private static final ActionInvoke actionInvoke = new ActionInvoke();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("filter init");
		try {
			Application.start();
		} catch (Exception e) {
			System.exit(0);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpContext httpContext = HttpContext.getCurrent();
		httpContext.setRequest((HttpServletRequest) request);
		httpContext.setResponse((HttpServletResponse) response);
		httpContext.getRequest().setCharacterEncoding(GlobalConfig.ENCODING);
		RouteResult routeResult;
		if (RouteResultCache.get(HttpContext.getCurrent().getMatchPath()) != null) {
			routeResult = RouteResultCache.get(HttpContext.getCurrent().getMatchPath());
		}
		else {
			routeResult = GlobalConfig.getMvcRoute().route(
				new RouteInfo(HttpContext.getCurrent().getMatchPath(),
						HttpContext.getCurrent().getRequest().getMethod()
								.equalsIgnoreCase("GET")));
		}
		if (routeResult.isSuccess()) { // 路由匹配成功
			ActionResult result = actionInvoke.invoke(routeResult.getAction(), routeResult.getUriPatternVariable());
			if (result != null) {
				result.render();
			} else {
				//#TODO
			}
		} else {
			chain.doFilter(request, response);
		}
		ThreadLocalContext.remove();
	}

	@Override
	public void destroy() {
	}

}
