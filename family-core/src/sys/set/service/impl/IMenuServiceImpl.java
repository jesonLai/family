package sys.set.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.family.annotation.SystemServiceLog;
import sys.model.PubAuthoritiesMenu;
import sys.model.PubAuthority;
import sys.model.PubMenu;
import sys.model.PubRole;
import sys.model.PubRolesAuthority;
import sys.model.PubUser;
import sys.model.PubUsersRole;
import sys.model.controller.TableRquestData;
import sys.set.service.AuthoritiesMenuService;
import sys.set.service.AuthorityService;
import sys.set.service.MenuService;
import sys.set.service.UsersRolesService;
import sys.util.JSONUtils;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 菜单
 * 
 */
@Service(SpringAnnotationDef.SER_MENU)
@Transactional(readOnly = true)
public class IMenuServiceImpl{

	private static final Logger _log = Logger.getLogger(IMenuServiceImpl.class.getName());
	private static final String DATA_NOT_NULL = "参数错误";
	private static final String MENU_NAME_IN = "菜单名称已存在";
	private static final String[][] MENU_HINT = { { "菜单名称不能为空" } };
	private static final String DATA_MISSING = "参数丢失！";
	@Resource
	private MenuService menuService;
	@Resource
	private AuthorityService authorityService;
	@Resource
	private AuthoritiesMenuService authoritiesMenuService;
	@Resource
	private UsersRolesService usersRolesService;

	@Transactional
	@SystemServiceLog(description = Sys_Const.META_LOG_SERVICE_SAVENEWMENU)
	public void addNewMenu(PubMenu pubMenu) throws BusinessException{
		PubUser currUser = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		if (StringUtil.isEmpty(pubMenu) || StringUtil.isEmpty(pubMenu.getMenuName()))
			throw new BusinessException(DATA_NOT_NULL);
		Integer menuId = pubMenu.getMenuId();
		PubMenu pubMenuOld = null;
		if (!StringUtil.isEmpty(menuId))
			pubMenuOld = menuService.findOne(menuId);
		if (StringUtil.isEmpty(pubMenu.getMenuName()))
			throw new BusinessException(MENU_HINT[0][0]);
		if (!isMenuName(pubMenu.getMenuName(),pubMenu.getMenuId()))
			throw new BusinessException(MENU_NAME_IN);
		Integer parentId = StringUtil.isEmpty(pubMenu.getPubMenuParent()) ? null : pubMenu.getMenuId() == null ? null : pubMenu.getPubMenuParent().getMenuId();
		PubMenu pMenuParent = null;
		if (parentId != null){
			PubMenu pMenu = menuService.findOne(parentId);
			if (!StringUtil.isEmpty(pMenu) && pMenu.getMenuId() != null)
				pMenuParent = pMenu;
		}
		if (StringUtil.isEmpty(pubMenuOld) || StringUtil.isEmpty(pubMenuOld.getMenuId()) || pubMenuOld.getMenuId() == 0){
			pubMenu.setCreateDate(new Date());
			pubMenu.setMenuOrder(menuService.findAll().size() + 1);
			pubMenu.setPubUser(currUser);
			pubMenu.setPubMenuParent(pMenuParent);;
			menuService.save(pubMenu);
		}else{
			pubMenuOld.setUpdateDate(new Date());
			pubMenuOld.setUpdatePubUser(currUser);
			pubMenuOld.setMenuName(pubMenu.getMenuName());
			pubMenuOld.setItemIcon(pubMenu.getItemIcon());
			pubMenuOld.setMenuUrl(pubMenu.getMenuUrl());
			pubMenuOld.setPubMenuParent(pMenuParent);
		}
	}


	/**
	 * 获取全部菜单信息
	 */
	@SystemServiceLog(description = Sys_Const.META_LOG_SERVICE_MENUPAGINATION)
	public Map<String, Object> getMenuPagination(){
		List<PubMenu> pubMenuList = menuService.findAll();
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		Map<String, Object> maps = new HashMap<String, Object>();
		HashMap menuList = new HashMap();
		// 根节点  
		List<PushMenu> menuRoot = new ArrayList<PushMenu>();
		// 根据结果集构造节点列表（存入散列表）  
		for (Iterator it = pubMenuList.iterator();it.hasNext();){
			PubMenu pm = (PubMenu) it.next();
			PushMenu pushMenu = new PushMenu();
			pushMenu.menuId = ObjectUtils.toString(StringUtil.isEmpty(pm.getMenuId()) ? 0 : pm.getMenuId());
			pushMenu.menuName = StringUtil.isEmpty(pm.getMenuName()) ? "" : pm.getMenuName();
			pushMenu.menuItemIcon = StringUtil.isEmpty(pm.getItemIcon()) ? "" : pm.getItemIcon();
			pushMenu.menuUrl = StringUtil.isEmpty(pm.getMenuUrl()) ? "" : pm.getMenuUrl();
			pushMenu.menuCreateDate = StringUtil.isEmpty(pm.getCreateDate()) ? "" : UtilFactory.getSysDate().DateToString(pm.getCreateDate());
			pushMenu.menuCreatePerson = StringUtil.isEmpty(pm.getPubUser()) ? "" : pm.getPubUser().getUserAccount();
			pushMenu.menuUpdateDate = StringUtil.isEmpty(pm.getUpdateDate()) ? "" : UtilFactory.getSysDate().DateToString(pm.getUpdateDate());
			pushMenu.menuUpdatePerson = StringUtil.isEmpty(pm.getPubUser()) ? "" : pm.getPubUser().getUserAccount();
			pushMenu.menuParentId = ObjectUtils.toString(StringUtil.isEmpty(pm.getPubMenuParent()) ? "" : pm.getPubMenuParent().getMenuId());
			pushMenu.menuParentName = ObjectUtils.toString(StringUtil.isEmpty(pm.getPubMenuParent()) ? "" : StringUtil.isEmpty(pm.getPubMenuParent().getMenuName()) ? "" : pm.getPubMenuParent()
					.getMenuName());
			pushMenu.menuOrder = StringUtil.isEmpty(pm.getMenuOrder()) ? 0 : pm.getMenuOrder();
			menuList.put(pushMenu.menuId,pushMenu);
		}
		// 构造无序的多叉树  
		Set entrySet = menuList.entrySet();
		for (Iterator it = entrySet.iterator();it.hasNext();){
			PushMenu pushMenu = (PushMenu) ((Map.Entry) it.next()).getValue();
			if (pushMenu == null || StringUtil.isEmpty(pushMenu.menuParentId)){
				menuRoot.add(pushMenu);
			}else{
				((PushMenu) menuList.get(pushMenu.menuParentId)).addChild(pushMenu);
			}
		}
		Collections.sort(menuRoot,new NodeIDComparator());
		List<Map<String, Object>> menuRoots = new ArrayList<Map<String, Object>>();
		for (PushMenu pushMenu:menuRoot){
			pushMenu.sortChildren();
			menuRoots.add(JSONUtils.toJSONObject(pushMenu.toString()));
		}
		maps.put("data",menuRoots);
		return maps;
	}

	private void getchild(Set<PubMenu> pm){
		for (PubMenu children:pm){
			System.out.println("-----------" + children.getMenuName());
			getchild(children.getChildren());
		}
	}

	/**
	 * authority_type:1 菜单
	 * 
	 * @return
	 */
	@SystemServiceLog(description = Sys_Const.META_LOG_SERVICE_MENUSLIST)
	public List<PubMenu> findAllByMenus(){
		List<PubMenu> pubMenus = Lists.newArrayList();
		PubUser currUser = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		List<PubUsersRole> pubUsersRoles = usersRolesService.findByPubUser(currUser);
		for (PubUsersRole pubUsersRole:pubUsersRoles){
			PubRole pubRole = pubUsersRole.getPubRole();
			if (!StringUtil.isEmpty(pubRole)){
				List<PubRolesAuthority> pubRolesAuthorities = pubRole.getPubRolesAuthorities();
				for (PubRolesAuthority pubRolesAuthority:pubRolesAuthorities){
					PubAuthority pubAuthority = pubRolesAuthority.getPubAuthority();
					if (!StringUtil.isEmpty(pubAuthority)){
						int authorityType = pubAuthority.getAuthorityType();
						if (authorityType == 1){
							List<PubAuthoritiesMenu> pubAuthoritiesMenus = pubAuthority.getPubAuthoritiesMenu();
							for (PubAuthoritiesMenu pubAuthoritiesMenu:pubAuthoritiesMenus){
								PubMenu pubMenu = pubAuthoritiesMenu.getPubMenu();
								if (!StringUtil.isEmpty(pubMenu))
									if (!pubMenus.contains(pubMenu)){
										boolean bl = false;
										if (pubMenus.size() > 0){
											for (int i = 0;i < pubMenus.size();i++ ){
												PubMenu nPubMenu = pubMenus.get(i);
												if (nPubMenu.getMenuOrder() < pubMenu.getMenuOrder()){
													pubMenus.add(i,pubMenu);
													bl = true;
													break;
												}
											}
										}
										if (pubMenus.size() <= 0 || !bl){
											pubMenus.add(pubMenu);
										}
									}
							}
						}
					}
				}
			}
		}
		return pubMenus;
	}

	@Transactional
	@SystemServiceLog(description = Sys_Const.META_LOG_SERVICE_REMOVEMENU)
	public void removeMenu(Integer menuId){
		PubMenu pubMenu = findOneByMenuId(menuId);
		if (!StringUtil.isEmpty(pubMenu)){
			menuService.delete(pubMenu);
		}
	}

	@SystemServiceLog(description = Sys_Const.META_LOG_SERVICE_ONEMENU)
	public PubMenu findOneByMenuId(Integer menuId){
		return menuService.findOne(menuId);
	}

	@Transactional
	@SystemServiceLog(description = Sys_Const.META_LOG_SERVICE_UPMOVE)
	public void upMove(Integer menuId){
		PubUser currUser = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		PubMenu pubMenu = findOneByMenuId(menuId);
		if (!StringUtil.isEmpty(pubMenu) && !StringUtil.isEmpty(pubMenu.getMenuId())){
			PubMenu pubMenuPrev = menuService.findOneByPrev(pubMenu.getMenuOrder());
			if (!StringUtil.isEmpty(pubMenuPrev) && !StringUtil.isEmpty(pubMenuPrev.getMenuId())){
				int menuOrder = pubMenu.getMenuOrder();
				int menuOrderParent = pubMenuPrev.getMenuOrder();
				pubMenu.setMenuOrder(menuOrderParent);
				pubMenu.setUpdateDate(new Date());
				pubMenu.setUpdatePubUser(currUser);
				pubMenu.setUpdatePubUser(pubMenu.getPubUser());
				pubMenuPrev.setMenuOrder(menuOrder);
				pubMenuPrev.setUpdateDate(new Date());
				pubMenuPrev.setUpdatePubUser(currUser);
			}
		}
	}

	@Transactional
	@SystemServiceLog(description = Sys_Const.META_LOG_SERVICE_DOWNMOVE)
	public void downMove(Integer menuId){
		PubUser currUser = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		PubMenu pubMenu = findOneByMenuId(menuId);
		if (!StringUtil.isEmpty(pubMenu)){
			PubMenu pubMenuNext = menuService.findOneByPrevNext(pubMenu.getMenuOrder());
			if (!StringUtil.isEmpty(pubMenuNext)){
				int menuOrder = pubMenu.getMenuOrder();
				int menuOrderParent = pubMenuNext.getMenuOrder();
				pubMenu.setMenuOrder(menuOrderParent);
				pubMenu.setUpdateDate(new Date());
				pubMenu.setUpdatePubUser(currUser);
				pubMenuNext.setMenuOrder(menuOrder);
				pubMenuNext.setUpdateDate(new Date());
				pubMenuNext.setUpdatePubUser(currUser);
			}
		}
	}

	/**
	 * 查询已有的菜单作为上级不能自己
	 * 
	 * @param menuId
	 * @return
	 */
	public List<Object> findByMenuNameColumn(Integer menuId){
		List<PubMenu> pubMenus = menuService.findByMenuIdNot(menuId);
		List<Object> list = Lists.newArrayList();
		for (PubMenu pubMenu:pubMenus){
			Map<String, String> map = Maps.newHashMap();
			map.put("data",String.valueOf(pubMenu.getMenuId()));
			map.put("value",pubMenu.getMenuName());
			list.add(map);
		}
		return list;
	}

	/**
	 * 检查微信号的唯一
	 * 
	 * @param userWeixin
	 * @param userInfoId
	 * @return
	 */
	public boolean isMenuName(String menuName, Integer menuId){
		if (StringUtil.isEmpty(menuId) || menuId == 0){
			return StringUtil.isEmpty(menuService.findOneByMenuName(menuName));
		}else{
			menuId = StringUtil.isEmpty(menuId) ? 0 : menuId;
			return StringUtil.isEmpty(menuService.findOneByMenuNameAndMenuIdNot(menuName,menuId));
		}
	}

	class PushMenu{

		public String menuId;
		public String menuName;
		public String menuItemIcon;
		public String menuUrl;
		public String menuCreateDate;
		public String menuCreatePerson;
		public String menuUpdateDate;
		public String menuUpdatePerson;
		public String menuParentId;
		public String menuParentName;
		public int menuOrder;
		private Children children = new Children();

		public String toString(){
			String result = "{MenuId:'" + menuId + "', MenuName:'" + menuName + "', MenuItemIcon:'" + menuItemIcon + "', MenuUrl:'" + menuUrl + "', MenuCreateDate:'" + menuCreateDate
							+ "', MenuCreatePerson:'" + menuCreatePerson + "', MenuUpdateDate:'" + menuUpdateDate + "', MenuUpdatePerson:'" + menuUpdatePerson + "',MenuParent:'" + menuParentName
							+ "'";
			if (children != null && children.getSize() != 0){
				result += ", children : " + children.toString();
			}else{
				result += ", leaf : true";
			}
			return result + "}";
		}

		/**
		 * 
		 * @param pushMenu void
		 */
		public void addChild(PushMenu pushMenu){
			this.children.addChild(pushMenu);
		}

		// 兄弟节点横向排序  
		public void sortChildren(){
			if (children != null && children.getSize() != 0){
				children.sortChildren();
			}
		}
	}

	class Children{

		private List list = new ArrayList();

		public int getSize(){
			return list.size();
		}

		public void addChild(PushMenu pushMenu){
			list.add(pushMenu);
		}

		// 拼接孩子节点的JSON字符串  
		public String toString(){
			String result = "[";
			for (Iterator it = list.iterator();it.hasNext();){
				result += ((PushMenu) it.next()).toString();
				result += ",";
			}
			result = result.substring(0,result.length() - 1);
			result += "]";
			return result;
		}

		// 孩子节点排序  
		public void sortChildren(){
			// 对本层节点进行排序  
			// 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器  
			Collections.sort(list,new NodeIDComparator());
			// 对每个节点的下一层节点进行排序  
			for (Iterator it = list.iterator();it.hasNext();){
				((PushMenu) it.next()).sortChildren();
			}
		}
	}

	class NodeIDComparator implements Comparator{

		// 按照节点编号比较  
		public int compare(Object o1, Object o2){
			int j1 = ((PushMenu) o1).menuOrder;
			int j2 = ((PushMenu) o2).menuOrder;
			return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
		}
	}
}
