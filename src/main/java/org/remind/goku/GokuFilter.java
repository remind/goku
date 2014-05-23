package org.remind.goku;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.remind.goku.context.HttpContext;
import org.remind.goku.context.ThreadLocalContext;
import org.remind.goku.internal.action.ActionInvoke;
import org.remind.goku.internal.action.ActionResult;
import org.remind.goku.route.RouteInfo;
import org.remind.goku.route.RouteResult;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 利用Filter来拦截
 * 
 * @author remind
 * 
 */
public class GokuFilter implements Filter {

	private static Logger logger = Logger.getLogger(GokuFilter.class);
	private static final ActionInvoke actionInvoke = new ActionInvoke();
	private Cache<String, RouteResult> routeResultCache = CacheBuilder.newBuilder().build();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("filter init");
		try {
			Goku.start();
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
		try {
			routeResult = routeResultCache.get(HttpContext.getCurrent().getMatchPath(), 
				new Callable<RouteResult>() {
					@Override
					public RouteResult call() {
						return GlobalConfig.getMvcRoute().route(
								new RouteInfo(HttpContext.getCurrent()
										.getMatchPath(), HttpContext.getCurrent()
										.getRequest().getMethod().equalsIgnoreCase("GET")));
					}
			});
			if (routeResult.isSuccess()) { // 路由匹配成功
				ActionResult result = actionInvoke.invoke(
						routeResult.getAction(),
						routeResult.getUriPatternVariable());
				if (result != null) {
					result.render();
				} else {
					// #TODO
				}
			} else {
				chain.doFilter(request, response);
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		ThreadLocalContext.remove();
	}

	@Override
	public void destroy() {
	}

}
