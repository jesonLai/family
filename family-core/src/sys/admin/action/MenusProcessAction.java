package sys.admin.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.family.annotation.SystemControllerLog;
import sys.model.PubMenu;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.IMenuServiceImpl;


@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus/menuItem")
/**
 * 菜单管理
 * @author lxr
 *
 */
public class MenusProcessAction extends BaseAction {
	private Logger _log=Logger.getLogger(MenusProcessAction.class);
	@Resource(name=SpringAnnotationDef.SER_MENU)
	private IMenuServiceImpl menuService;
	
	/**
	 * 获取菜单界面
	 */
	@RequestMapping("/menuItemAllPage")
	@SystemControllerLog(description =Sys_Const.META_LOG_USERALLPAGE)    
	public ModelAndView usersMainPage(ModelAndView modelAndView){
		modelAndView.setViewName("admin/menus/set/menuItems/menuItem.vm");  
		return modelAndView;
	}
	/**
	 * 获取菜单信息及分页信息
	 */
	@RequestMapping("/menuItemList")
	@SystemControllerLog(description = Sys_Const.META_LOG_ROLELIST)
	@ResponseBody Map<String,Object> usersList(){
		return menuService.getMenuPagination();
	}
	/**
	 * 获取新增页面
	 */
	@RequestMapping("/menuItemAddPage")
	@SystemControllerLog(description =Sys_Const.META_LOG_USERADDPAGE)
	public ModelAndView usersAddPage(ModelAndView modelAndView,@RequestParam("menuId")Integer menuId){
		modelAndView.addObject("MENUITEM",menuService.findOneByMenuId(menuId));
		modelAndView.addObject("MENUITEMNAMES",new JSONArray().fromObject(menuService.findByMenuNameColumn(menuId)));
		
		modelAndView.setViewName("admin/menus/set/menuItems/menuItem-add.vm");  
		return modelAndView;
	}
	/**
	 * 获取权限列表的界面
	 */
//	@RequestMapping("/rolesAllPage")
//	@SystemControllerLog(description =Sys_Const.META_LOG_ROLEMAINPAGE)
//	public ModelAndView rolesMainPage(ModelAndView modelAndView){
//		 modelAndView.setViewName("admin/menus/set/menuItems/menu-role.vm");  
//		return modelAndView;
//	}
	/**
	 * 功能赋予角色
	 * @param editorValue
	 * @return
	 */
//	@RequestMapping(value="/COM_AUTHORITY_FUNCTION_GRANT_ROLES",method=RequestMethod.POST)
//	@SystemControllerLog(description = Sys_Const.META_LOG_ROLESELECT)
//	@ResponseBody Map<String,Object> roleSelect(@RequestParam(value="menuId",defaultValue="0")int menuId,@RequestParam(value="delRoleIds[]",defaultValue="")String[] delRoleIds,@RequestParam(value="insRoleIds[]",defaultValue="")String[] insRoleIds){
//		try {
//			 ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
//			 menuService.grantRole(menuId,delRoleIds,insRoleIds);
//			 ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
//			 ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
//		}catch(BusinessException be){
//			ht.put(BaseHint.MESSAGE, be.getMessage());
//			_log.info(be.getMessage());
//		}catch(Exception e){
//			ht.put(BaseHint.MESSAGE, e.getMessage());
//			_log.info(e.getMessage());
//			e.printStackTrace();
//		}
//		return ht;
//	}
//	/**
//	 * 获取角色列表及分页信息
//	 */
//	@RequestMapping("/RolesList")
//	@SystemControllerLog(description = Sys_Const.META_LOG_ROLELIST)
//	@ResponseBody TableRquestData rolesList(@RequestParam(value="menuId",defaultValue="0")String menuId,@ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
//		return menuService.getRolesPagination(menuId,tableRquestData,search);
//	}
	/**
	 * 添加菜单
	 * @param familyNew
	 * @param editorValue
	 * @return
	 */
	@RequestMapping("/COM_MENUITEM_SAVE")
	@SystemControllerLog(description = Sys_Const.META_LOG_SAVEMENU)
	@ResponseBody Map<String,Object> saveMenu(@ModelAttribute("PubMenu")PubMenu pubMenu){
		try{
			menuService.addNewMenu(pubMenu);
			ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
			ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		}catch(Exception e){
			ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
			ht.put(BaseHint.MESSAGE, e.getMessage());
			_log.error(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}
	/**
	 * 删除菜单
	 * @param familyNew
	 * @param editorValue
	 * @return
	 */
	@RequestMapping("/COM_MENUITEM_REMOVE")
	@SystemControllerLog(description = Sys_Const.META_LOG_REMOVEMENU)
	@ResponseBody Map<String,Object> removeMenu(@RequestParam(value="menuId",defaultValue="0")Integer menuId){
		try{
			menuService.removeMenu(menuId);
			ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
			ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		}catch(Exception e){
			ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
			ht.put(BaseHint.MESSAGE, e.getMessage());
			_log.error(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}
	/**
	 * 保存关系
	 * @param authorityId
	 * @param functionIds
	 * @return
	 */
//	@RequestMapping(value="/COM_AUTHORITY_MENUS_RELATIONSHIP",method=RequestMethod.POST)
//	@SystemControllerLog(description=Sys_Const.META_LOG_REQ_SAVEPUBAUTHORITIESFUNCTION)
//	@ResponseBody Map<String,Object> relationshipFunction(@RequestParam("authorityId")Integer authorityId,@RequestParam(value="delMenuIds[]",defaultValue="")Integer[] delMenuIds,@RequestParam(value="insMenuIds[]",defaultValue="")Integer[] insMenuIds){
//		try {
//			 ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
//			 menuService.addNewAuthorityMenus(authorityId,delMenuIds,insMenuIds);
//			 ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
//			 ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
//		}catch(BusinessException be){
//			ht.put(BaseHint.MESSAGE, be.getMessage());
//			_log.info(be.getMessage());
//		}catch(Exception e){
//			ht.put(BaseHint.MESSAGE, e.getMessage());
//			_log.info(e.getMessage());
//			e.printStackTrace();
//		}
//		return ht;
//	}
	/**
	 * 菜单排序
	 * @param familyNew
	 * @param editorValue
	 * @return
	 */
	@RequestMapping("/COM_MENUITEM_UPMOVE")
	@ResponseBody Map<String,Object> upmoveMenu(@RequestParam(value="menuId",defaultValue="0")Integer menuId){
		try{
			menuService.upMove(menuId);
			ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
			ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		}catch(Exception e){
			ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
			ht.put(BaseHint.MESSAGE, e.getMessage());
			_log.error(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}
	/**
	 * 菜单排序
	 * @param familyNew
	 * @param editorValue
	 * @return
	 */
	@RequestMapping("/COM_MENUITEM_DOWNMOVE")
	@ResponseBody Map<String,Object> downMoveMenu(@RequestParam(value="menuId",defaultValue="0")Integer menuId){
		try{
			menuService.downMove(menuId);
			ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
			ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		}catch(Exception e){
			ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
			ht.put(BaseHint.MESSAGE, e.getMessage());
			_log.error(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}
	
}
