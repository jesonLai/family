package sys.model.controller;

import java.io.Serializable;


/**
 * 菜单
 * @author lxr
 *
 */
public class MenuForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 菜单对应的controller层
	 */
	private String action;
	/**
	 * 刷新controller对应view层的内容
	 */
	private String refreshAction;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单的地址编号值
	 */
	private String urlCode;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getRefreshAction() {
		return refreshAction;
	}
	public void setRefreshAction(String refreshAction) {
		this.refreshAction = refreshAction;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUrlCode() {
		return urlCode;
	}
	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}
	
}
