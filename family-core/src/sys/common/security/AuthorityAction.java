package sys.common.security;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.admin.action.BaseAction;
import sys.exception.handing.BusinessException;
import sys.family.annotation.SystemControllerLog;
import sys.model.PubAuthority;
import sys.model.PubAuthorityGroup;
import sys.set.service.impl.AuthorityServiceGroupImpl;
import sys.set.service.impl.AuthorityServiceImpl;
import sys.set.service.impl.IMenuServiceImpl;

/**
 * 权限控制中心
 * 
 * @author lxr
 * 
 */
@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/authority")
public class AuthorityAction extends BaseAction{

	protected final Log _Log = LogFactory.getLog(getClass());
	@Resource(name = SpringAnnotationDef.SER_AUTHORITY)
	AuthorityServiceImpl authorityService;
	@Resource(name=SpringAnnotationDef.SER_MENU)
	private IMenuServiceImpl menuService;
	@Resource(name=SpringAnnotationDef.SER_AUTHORITY_GROUP)
	private AuthorityServiceGroupImpl authorityServiceGroupImpl;
	/**
	 * 获取权限列表的界面
	 */
	@RequestMapping("/authorityAllPage")
	public ModelAndView authorityAllPage(ModelAndView modelAndView){
		_Log.debug(">>>>>>>>>>>>>>>>>authorityAllPage()<<<<<<<<<<<<<<<<<");
		_Log.debug("GET authority.vm");
		modelAndView.setViewName("admin/menus/set/authority/authority.vm");
		return modelAndView;
	}
	/**
	 * 获取权限列表数据
	 */
	@RequestMapping("/authorityList")
	@ResponseBody Map<String,Object> authorityList(ModelAndView modelAndView){
		_Log.debug(">>>>>>>>>>>>>>>>>authorityList()<<<<<<<<<<<<<<<<<");
		return authorityService.getAuthorityCenter();
	}
	/**
	 *保存权限
	 */
	@RequestMapping(value = "/COM_AUTHORITY_SAVE", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> addNew(@ModelAttribute("PubAuthority") PubAuthority pubAuthority) {
		try {
		    _Log.debug(">>>>>>>>>>>>>>>>>addNew()<<<<<<<<<<<<<<<<<");
		    _Log.debug(">parameter1[pubAuthority]:" + pubAuthority);
		    authorityService.addNew(pubAuthority);
		    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
		    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		} catch (BusinessException be) {
		    ht.put(BaseHint.MESSAGE, be.getMessage());
		    _Log.error("Error occur while addNew()", be);
		} catch (Exception e) {
		    ht.put(BaseHint.MESSAGE, e.getMessage());
		    _Log.error("Error occur while addNew()", e);
		}
		return ht;
    }
	/**
	 *保存权限
	 */
	@RequestMapping(value = "/COM_AUTHORITY_GROUP_SAVE", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> addAuthorityGroup(@ModelAttribute("PubAuthorityGroup") PubAuthorityGroup pubAuthorityGroup) {
		try {
		    _Log.debug(">>>>>>>>>>>>>>>>>pubAuthorityGroup()<<<<<<<<<<<<<<<<<");
		    _Log.debug(">parameter1[pubAuthorityGroup]:" + pubAuthorityGroup);
		    authorityService.addAuthorityGroup(pubAuthorityGroup);
		    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
		    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		} catch (BusinessException be) {
		    ht.put(BaseHint.MESSAGE, be.getMessage());
		    _Log.error("Error occur while addNew()", be);
		} catch (Exception e) {
		    ht.put(BaseHint.MESSAGE, e.getMessage());
		    _Log.error("Error occur while addNew()", e);
		}
		return ht;
    }
    /**
     * 获取新增权限组页面 
     * @param familyNewsId
     * @throws BusinessException
     */
    @RequestMapping("/get/authorityGroup/page")
    @ResponseBody
    public ModelAndView getAuthorityGroups(ModelAndView modelAndView) {
		_Log.debug("**********已有权限组选择**********");
		modelAndView.setViewName("admin/menus/set/authority/authority-group-add.vm");
		return modelAndView;
    }
    /**
     * 获取新增权限页面 
     * @param familyNewsId
     * @throws BusinessException
     */
    @RequestMapping("/get/add/authorityPage")
    @ResponseBody
    public ModelAndView getAddAuthorityPage(ModelAndView modelAndView) {
		_Log.debug("**********已有权限组选择**********");
		modelAndView.setViewName("admin/menus/set/authority/authority-add.vm");
		modelAndView.addObject("AUTHORITYGROUPNAME",JSONArray.fromObject(authorityServiceGroupImpl.finAllAuthorityGroup()));
		return modelAndView;
    }
    /**
     * 获取权限组名称 
     * @param familyNewsId
     * @throws BusinessException
     */
    @RequestMapping("/get/add/authorityGroup/names")
    @ResponseBody
    List<Map<String,Object>> getAuthorityGroupNames(ModelAndView modelAndView) {
		return authorityServiceGroupImpl.finAllAuthorityGroup();
    }
    
	
	/**
	 *分配资源
	 */
	@RequestMapping(value = "/COM_AUTHORITY_ALLOCATION", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> allocationResource(String authorityId,String type,Integer resourceId) {
		try {
		    _Log.debug(">>>>>>>>>>>>>>>>>allocationResource()<<<<<<<<<<<<<<<<<");
		    authorityService.allocation(authorityId,type,resourceId);
		    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
		    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		} catch (BusinessException be) {
		    ht.put(BaseHint.MESSAGE, be.getMessage());
		    _Log.error("Error occur while addNew()", be);
		} catch (Exception e) {
		    ht.put(BaseHint.MESSAGE, e.getMessage());
		    _Log.error("Error occur while addNew()", e);
		}
		return ht;
    }
	

	/**
	 * 获取菜单信息及分页信息
	 */
	@RequestMapping("/get/menuItemList")
	@SystemControllerLog(description = Sys_Const.META_LOG_ROLELIST)
	@ResponseBody Map<String,Object> menuList(){
		return menuService.getMenuPagination();
	}
	@RequestMapping("/delete/authority")
	@ResponseBody Map<String,Object> deleteAuthority(@ModelAttribute("PubAuthority") PubAuthority pubAuthority){
		try {
			authorityService.delAuthority(pubAuthority);
		    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
		    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		} catch (BusinessException be) {
		    ht.put(BaseHint.MESSAGE, be.getMessage());
		    _Log.info(be.getMessage());
		} catch (Exception e) {
		    ht.put(BaseHint.MESSAGE, e.getMessage());
		    _Log.info(e.getMessage());
		}
		return ht;
	}
	
	
	 /**
     * 判断权限名称唯一
     * 
     * @param pubAuthority
     * @return
     */
    @RequestMapping(value = "/checkAuthorityName", method = RequestMethod.POST)
    @ResponseBody
    boolean isAuthorityName(@ModelAttribute("PubAuthority") PubAuthority pubAuthority) {
	_Log.debug(">>>>>>>>>>>>>>>>>isAuthorityName()<<<<<<<<<<<<<<<<<");
	_Log.debug("parameter1[pubAuthority]:" + pubAuthority);
	return authorityService.isAuthorityName(pubAuthority.getAuthorityId());
    }

}
