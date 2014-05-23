package org.remind.goku.route;

/**
 * 路由信息
 * 把路由时需要的信息拿出来更利于单元测试
 * @author remind
 *
 */
public class RouteInfo {

	/**
	 * 精简过后请求的路径
	 */
	private String path;
	
	/**
	 * 是否为get
	 */
	private boolean get;
	
	/**
	 * 是否为post
	 */
	private boolean post;
	
	public RouteInfo(String path, boolean get) {
		this.path = path;
		this.get = get;
		this.post = !get;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isGet() {
		return get;
	}

	public void setGet(boolean get) {
		this.get = get;
	}

	public boolean isPost() {
		return post;
	}

	public void setPost(boolean post) {
		this.post = post;
	}
	
}
