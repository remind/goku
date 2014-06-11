package org.remind.goku;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.Callable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.remind.goku.cache.Cache;
import org.remind.goku.cache.imp.DefaultCache;
import org.remind.goku.context.HttpContext;
import org.remind.goku.context.ThreadLocalContext;
import org.remind.goku.db.sql.SqlConnection;
import org.remind.goku.internal.action.ActionInvoke;
import org.remind.goku.internal.action.ActionResult;
import org.remind.goku.route.RouteInfo;
import org.remind.goku.route.RouteResult;


/**
 * 利用Filter来拦截
 * 
 * @author remind
 * 
 */
public class GokuFilter implements Filter {

	private static Logger logger = Logger.getLogger(GokuFilter.class);
	private static final ActionInvoke actionInvoke = new ActionInvoke();
	private Cache<String, RouteResult> routeResultCache = new DefaultCache<String, RouteResult>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("filter init");
		try {
			GlobalConfig.init(filterConfig.getServletContext());
			Goku.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest re = (HttpServletRequest) request;
		
		HttpContext httpContext = HttpContext.getCurrent();
		httpContext.setRequest((HttpServletRequest) request);
		httpContext.setResponse((HttpServletResponse) response);
		httpContext.getRequest().setCharacterEncoding(GlobalConfig.ENCODING);
		RouteResult routeResult;
		
		try {
			
			routeResult = routeResultCache.get(String.valueOf(HttpContext.getCurrent().getMatchPath().hashCode()), 
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
				SqlConnection.getConnection().close();
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ThreadLocalContext.remove();
	}

	@Override
	public void destroy() {
	}

}
