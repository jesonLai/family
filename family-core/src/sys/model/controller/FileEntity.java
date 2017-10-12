package sys.model.controller;

import java.util.Date;

public class FileEntity {
	/**
	 * 文件编号
	 */
	private String id;
	/**
	 * 文件名称
	 */
	private String name;
	/**
	 * 文件初始名称
	 */
	private String initName;
	/**
	 * 文件浏览器地址
	 */
	private String httpUrl;
	/**
	 * 文件跳转
	 */
	private String href;
	/**
	 * 文件系统地址
	 */
	private String systemUrl;
	/**
	 * 文件处理时间
	 */
	private Date dateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getInitName() {
		return initName;
	}
	public void setInitName(String initName) {
		this.initName = initName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getSystemUrl() {
		return systemUrl;
	}
	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
