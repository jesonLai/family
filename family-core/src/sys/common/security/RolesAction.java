package sys.common.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import sys.admin.action.BaseAction;
import sys.exception.handing.BusinessException;
import sys.family.annotation.SystemControllerLog;
import sys.model.PubRole;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.RoleServiceImpl;

/**
 * 角色管理
 * @author lxr
 *
 */
@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus/roles")
public class RolesAction extends BaseAction{
	private static final Logger logger=Logger.getLogger(RolesAction.class.getName());
	@Resource(name=SpringAnnotationDef.SER_ROLES)
	RoleServiceImpl rolesService;
	/**
	 * 获取权限列表的界面
	 */
	@RequestMapping("/rolesAllPage")
	public ModelAndView usersAllPage(ModelAndView modelAndView){
		 modelAndView.setViewName("admin/menus/set/roles/role.vm");  
		return modelAndView;
	}
	/**
	 * 获取权限资源列表及分页信息
	 */
	@RequestMapping("/rolesList")
	@ResponseBody TableRquestData usersList(@RequestParam(value="authorityId",defaultValue="0") String authorityId, @ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
		return rolesService.getRolesPagination(authorityId,tableRquestData,search);
	}
	/**
	 * 获取权限列表的界面
	 */
	@RequestMapping("/rolesAddAllPage")
	public ModelAndView rolesAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/roles/role-add.vm");  
		return mav;
	}
	
	@RequestMapping("/get/AuthorityCenter")
	@ResponseBody List<Map<String,Object>> getAuthorityCenterAction(@ModelAttribute("PubRole")PubRole role){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		try {
			resultList=rolesService.getAuthorityCenter(role);
		}catch(BusinessException be){
			logger.info(be.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return resultList;
	}
	
	@RequestMapping("/roleAddNew")
	@ResponseBody Map<String,Object> addNew(@ModelAttribute("PubRole")PubRole role){
		try {
			 ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
			 PubRole pubRole= rolesService.addNew(role);
			 ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
			 ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		}catch(BusinessException be){
			ht.put(BaseHint.MESSAGE, be.getMessage());
			logger.info(be.getMessage());
		}catch(Exception e){
			ht.put(BaseHint.MESSAGE, e.getMessage());
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}
}
