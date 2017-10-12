package sys.model.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpSession;

import sys.model.PubMenu;
import sys.model.PubRole;
import sys.model.PubUser;

/**
 * 用户上下文信息[session]
 * @author lxr
 *
 */
public class UserInfoContext implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户基本信息
	 */
	private PubUser user;
	/**
	 * 用户登录信息
	 */
	/**
	 * 用户session
	 */
	private HttpSession userSession;
	/**
	 * 前后台区分
	 */
	private String frontOrBack;
	/**
	 * 导入的数据
	 * @return
	 */
	private List list;
	/**
	 * 客户端ip、浏览器、操作系统、ip地址
	 */
	private String clientIP;
	private String clientBrowser;
	private String clientSystem;
	private String clientAddress;
	/**
	 * 服务段ip
	 */
	private String serviceIP;
	

	/**
	 * 项目访问地址
	 */
	private String basePath;
	/**
	 * 用户菜单
	 */
	private List<PubMenu> pubMenus;
	/**
	 * 用户角色
	 */
	private List<PubRole> pubRoles;
	/**
	 * 格式化好的树形菜单
	 */
	private String formattMenu;
	public PubUser getUser() {
		return user;
	}
	public void setUser(PubUser user) {
		this.user = user;
	}
	public HttpSession getUserSession() {
		return userSession;
	}
	public void setUserSession(HttpSession userSession) {
		this.userSession = userSession;
	}
	public String getFrontOrBack() {
		return frontOrBack;
	}
	public void setFrontOrBack(String frontOrBack) {
		this.frontOrBack = frontOrBack;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public List<PubMenu> getPubMenus() {
		return pubMenus;
	}
	public void setPubMenus(List<PubMenu> pubMenus) {
		this.pubMenus = pubMenus;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getFormattMenu() {
		return formattMenu;
	}
	public void setFormattMenu(String formattMenu) {
		this.formattMenu = formattMenu;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getClientBrowser() {
		return clientBrowser;
	}
	public void setClientBrowser(String clientBrowser) {
		this.clientBrowser = clientBrowser;
	}
	public String getClientSystem() {
		return clientSystem;
	}
	public void setClientSystem(String clientSystem) {
		this.clientSystem = clientSystem;
	}
	public String getServiceIP() {
		return serviceIP;
	}
	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public List<PubRole> getPubRoles() {
		return pubRoles;
	}
	public void setPubRoles(List<PubRole> pubRoles) {
		this.pubRoles = pubRoles;
	}
	
}
