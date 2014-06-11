package org.remind.goku;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.remind.goku.context.HttpContext;
import org.remind.goku.context.ThreadLocalContext;
import org.remind.goku.internal.ModelMap;
import org.remind.goku.internal.action.ActionResult;
import org.remind.goku.utils.DateUtil;
import org.remind.goku.utils.HtmlUtil;
import org.remind.goku.view.View;
import org.remind.goku.view.impl.JsonView;
import org.remind.goku.view.impl.TextView;
import org.remind.goku.view.impl.VelocityView;

/**
 * 基本controller，其它所有controller都需要继承它
 * @author remind
 *
 */
public abstract class BaseController {
	
	private Logger log = Logger.getLogger(BaseController.class);
	
	/**
	 * 由于controller是单列的
	 * 所以要获取ActionResult都从这个方法获得
	 * @return
	 */
	private ActionResult getActionResult() {
		return (ActionResult) ThreadLocalContext.get().getSingleThreadLocalVar(ActionResult.class);
	}
	
	/**
	 * 获得model
	 * @return
	 */
	protected ModelMap getModel() {
		return getActionResult().getModel();
	}
	
	/**
	 * 添加cookie
	 * @param name
	 * @param value
	 */
	public void addCookie(String name, String value) {
		HttpContext.getCurrent().getResponse().addCookie(new Cookie(name, value));
	}
	
	/**
	 * 直接输出文本到页面
	 * @param content
	 * @return
	 */
	protected ActionResult write(String content) {
		getActionResult().setView(new TextView(content));
		return getActionResult();
	}
	
	/**
	 * 输出json串到页面
	 * @param o	object对象，会采用fastjson转换成json串
	 * @return
	 */
	protected ActionResult writeJson(Object o) {
		getActionResult().setView(new JsonView(o));
		return getActionResult();
	}
	
	/**
	 * velocity view
	 * @param path
	 * @return
	 */
	protected ActionResult view(String path) {
		VelocityView view = new VelocityView();
		view.setPath(path);
		getActionResult().setView(view);
		return getActionResult();
	}
	
	/**
	 * 重定向
	 * @param location
	 * @return
	 */
	protected ActionResult redirect(String location) {
		ActionResult ar = new ActionResult();
		if (location.indexOf("://") > 0) {
			ar.getModel().add("location", location);
		} else {
			ar.getModel().add("location", HttpContext.getCurrent().getBasePath() + location);
		}
		ar.setView(new View() {
			@Override
			public void render(ActionResult actionResult) {
				try {
					HttpContext.getCurrent().getResponse().sendRedirect(actionResult.getModel().get("location").toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return ar;
	}
	
	/**
	 * 上传文件
	 * @param maxSize	最大大小
	 * @param enableFileExt		允许的文件后缀
	 */
	protected List<Map<String, String>> uploadFile(int maxSize, String enableFileExt) {
		String savePath = GlobalConfig.getUploadPath();
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(GlobalConfig.ENCODING);
		List<FileItem> items;
		try {
			items = upload.parseRequest(HttpContext.getCurrent().getRequest());
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				Map<String, String> map = new HashMap<String, String>();
				FileItem item = itr.next();
				String fileName = item.getName();
				if (!item.isFormField()) {
					//检查文件大小
					if (fileName == null || fileName.equals("")) {
						continue;
					}
					if(item.getSize() > maxSize) {
						log.debug("上传文件太大");
						map.put("errorMsg", "文件太大");
						map.put("success", "0");
						result.add(map);
						return result;
					}
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(enableFileExt.indexOf(fileExt) < 0){
						log.error("上传文件格式错误");
						map.put("errorMsg", "文件格式错误");
						map.put("success", "0");
						result.add(map);
						return result;
					}
					
					String dirUrl = DateUtil.getCurrentByFormat("yyyy/MM/");
					
					File dir = new File(savePath, dirUrl);
					if (!dir.exists()) {
						dir.mkdirs();
					}

					String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExt;
					try{
						File uploadedFile = new File(dir.getPath(), newFileName);
						item.write(uploadedFile);
						map.put("imgUrl", GlobalConfig.getUploadHttpUrl() + dirUrl + newFileName);
						map.put("success", "1");
					}catch(Exception e) {
						e.printStackTrace();
						map.put("success", "0");
					}
					result.add(map);
				} else {
					HttpContext.getCurrent().getContextMap().put(item.getFieldName(), new String(item.get(), GlobalConfig.ENCODING));
				}
			}
		} catch (FileUploadException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 返回表单中的值
	 * @param name
	 * @return
	 */
	protected Object getFormData(String name) {
		Object value = "";
		if (HttpContext.getCurrent().getRequest().getParameter(name) != null) {
			value = HttpContext.getCurrent().getRequest().getParameter(name);
		} else if (HttpContext.getCurrent().getContextMap().containsKey(name)){
			value = HttpContext.getCurrent().getContextMap().get(name).toString();
		} else {
			return "";
		}
		if (value instanceof String) {
			value = HtmlUtil.escapeHtml(value.toString());
		}
		return value;
	}
	
	/**
	 * 对表单项做html转义
	 * @param name
	 * @return
	 */
	protected String getHtmlFormData(String name) {
		String value = "";
		if (HttpContext.getCurrent().getRequest().getParameter(name) != null) {
			value = HttpContext.getCurrent().getRequest().getParameter(name).toString();
		} else if (HttpContext.getCurrent().getContextMap().containsKey(name)){
			value = HttpContext.getCurrent().getContextMap().get(name).toString();
		} else {
			return "";
		}
		return HtmlUtil.escapeHtml(value.toString());
	}
}
