package org.remind.goku.internal;

/**
 * 文件上传实体
 * @author remind
 *
 */
public class UploadFile {

	/**
	 * 文件路径
	 */
	private String fileUrl;
	
	/**
	 * 上传是否成功
	 */
	private boolean success;
	
	/**
	 * 上传不成功的错误消息
	 */
	private String errorMsg;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
