package sys.admin.action;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.family.annotation.SystemControllerLog;
import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.controller.TableRquestData;
import sys.model.controller.UserInfoContext;
import sys.set.service.impl.UserLoginServiceImpl;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("admin/menus/users/login")
public class UserLoginInfoAction extends UsersInfoAction{
	private static final Logger logger = Logger.getLogger(UserLoginInfoAction.class.getName());
	@Resource(name=SpringAnnotationDef.SER_USERSLOGIN)
	UserLoginServiceImpl userLoginService;
	/**
	 * 获取全部登录信息用户的界面
	 */
	@RequestMapping("/usersLoginAllPage")
	@SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERLOGININFOMAINPAGE)
	public ModelAndView userLoginInfoMainPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/usersLogin/userLogin.vm");  
         
		return mav;
	}
	/**
	 * 获取用户登录及分页信息
	 */
	@RequestMapping("/usersLoginList")
	@SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERLOGININFOMAINLIST)
	@ResponseBody TableRquestData userLoginInfoMainList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
		UserInfoContext userInfoContext= UtilFactory.getSpringContext().getUserInfoContext();
		List<PubRole> pubRoles=userInfoContext.getPubRoles(); 
		List<Integer> roleIds=Lists.newArrayList();
		for (PubRole pubRole : pubRoles) {
			roleIds.add(pubRole.getRoleId());
		}
		return userLoginService.getUserPagination(tableRquestData,search,roleIds);
	}
	/**
	 * 获取全部登录账号用户的界面
	 */
	@RequestMapping("/usersLoginAddPage")
	@SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERLOGININFOADDNEWPAGE)
	public ModelAndView userLoginInfoAddNewPage(@RequestParam("uId")String uId){
		PubUser pu=null;
		if(!StringUtil.isEmpty(uId)&&uId.matches("[0-9]+")){
			pu=userLoginService.getOnePubUser(Integer.parseInt(uId));
		}
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/usersLogin/userLogin-add.vm");  
         mav.addObject("USERS",pu);
		return mav;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("/COM_UPDTAE_PWD")
	@SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERLOGININFOUPDATEPASSWORD)
	@ResponseBody Map<String,Object> userInfoUpdatePassWord(
						@RequestParam("oldPwd") String oldPwd,
						@RequestParam("newOnePwd") String newOnePwd,
						@RequestParam("newTowPwd") String newTowPwd){
		try{
			userLoginService.updateUserPassWord(oldPwd, newOnePwd, newTowPwd);
			ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
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
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping("/COM_RESET_PWD")
	@SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERLOGININFORESETPASSWORD)
	@ResponseBody Map<String,Object> userLoginInfoResetPassWord(@RequestParam("uId")Integer uId){
		try{
			userLoginService.resetPwd(uId);
			ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
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
	/**
	 * 删除账号
	 * @return
	 */
	@RequestMapping("/COM_LOGIN_ACCOUNT_REMOVE")
	@SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERLOGININFREMOVE)
	@ResponseBody Map<String,Object> userLoginInfoRemove(@RequestParam("uId")Integer uId){
		try{
			userLoginService.removeAccount(uId);
			ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		}catch(BusinessException be){
			ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
			ht.put(BaseHint.MESSAGE, be.getMessage());
			logger.info(be.getMessage());
		}catch(Exception e){
			if(e.getCause() instanceof ConstraintViolationException){
				ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
				ht.put(BaseHint.MESSAGE, "无法删除，该账号已有关联的信息，请先删除");
			}else{
				ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
				ht.put(BaseHint.MESSAGE, e.getMessage());
				e.printStackTrace();
			}
			logger.info(e.getMessage());
			
			
			
		}
		return ht;
	}
	/**
	 * 获取全部用户信息的界面
	 */
	@RequestMapping("/userInfosLoginAllPage")
	public ModelAndView userInfosAllPage(ModelAndView modelAndView){
		 modelAndView.setViewName("admin/menus/set/usersLogin/user-list-login.vm");  
		return modelAndView;
	}
	/**
	 * 设置绑定用户信息
	 */
	@RequestMapping("/userInfosLoginSet")
	@ResponseBody Map<String,Object> userInfosLoginSet(@RequestParam("uId")Integer uId,@RequestParam("uiId")Integer uiId){
		try{
			userLoginService.updateUser_UserInfo(uId,uiId);
			ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
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

	/**
	 * 新增账号
	 */
	@RequestMapping("/COM_CHANGE_FLAG")
	@ResponseBody Map<String,Object> usesChangFlag(@ModelAttribute("PubUser")PubUser pubUser){
		try{
			userLoginService.changeUserFlag(pubUser);
			ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		}catch(Exception e){
			ht.put(BaseHint.MESSAGE, e.getMessage());
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}
	
	/**
	 * 新增账号
	 */
	@RequestMapping("/COM_USERSLOGIN_SAVE")
	@ResponseBody Map<String,Object> userInfosLoginSave(@ModelAttribute("PubUser")PubUser pu,@RequestParam(value="insRoleIds",defaultValue="")Integer[] insRoleIds,@RequestParam(value="delRoleIds",defaultValue="")Integer[] delRoleIds){
		try{
			userLoginService.addNew(pu,delRoleIds,insRoleIds);
			ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
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
	
	/**
	 * 检验账号的唯一性
	 * @param pu
	 * @return
	 */
	@RequestMapping(value="/checkUserAccount", method = RequestMethod.POST)
	@ResponseBody boolean isUserAccount(@ModelAttribute("Pu")PubUser pu){
		return userLoginService.isUserAccount(pu.getUserAccount(),pu.getUId());
	}
}
