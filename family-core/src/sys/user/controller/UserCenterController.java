package sys.user.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.admin.action.BaseAction;
import sys.exception.handing.BusinessException;
import sys.model.PubUser;
import sys.model.UserInfo;
import sys.set.service.impl.IUsersServiceImpl;
import sys.user.front.service.imp.WebUserCenterServiceImpl;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping(value = "/user")
public class UserCenterController extends BaseAction{

	private static final Logger logger = Logger.getLogger(UserCenterController.class.getName());
	/***
	 * 个人中心
	 */
	@Resource(name = SpringAnnotationDef.SER_USERS)
	private IUsersServiceImpl usersService;
	@Resource(name = SpringAnnotationDef.Web_SER_USERS)
	private WebUserCenterServiceImpl userCenterService;

	// 查询宗族关系、个人信息
	@RequestMapping(value = "center.html")
	public String findNewsList(HttpServletRequest request, Model model, HttpSession session){
		PubUser userContext = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		if (!StringUtil.isEmpty(userContext) && !StringUtil.isEmpty(userContext.getUserInfo()))
			session.setAttribute("USERINFOPERSON",userContext.getUserInfo());
		// 个人信息
		UserInfo userInfo = UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo();
		// 登陆信息
		// PubUser user =
		// UtilFactory.getSpringContext().getUserInfoContext().getUser();
		Object headImageNameObject = userInfo == null ? "" : userInfo.getHeadImageName();
		String imgSrc = MessageFormat.format(Sys_Const.FAMILY_HEAD_IMAGE_FILE_FOLDER_PATH,"/") + "/" + headImageNameObject;
		session.setAttribute("HEANDIMAGESRC",imgSrc);
		// return "/user/userInfo.jsp";
		model.addAttribute("user",userInfo);
		return "/user/personal/userInfo.jsp";
	}

	// 跳转修改密码页面
	@RequestMapping(value = "updatepwd.html")
	public String updatepwd(HttpServletRequest request, Model model){
		return "/user/personal/updatePassword.jsp";
	}

	// 验证新密码与原密码是否相同
	@RequestMapping(value = "validatePassWord")
	@ResponseBody
	Boolean validatePass(String oldPass){
		return userCenterService.passWordSame(oldPass);
	}

	// 修改密码,成功后跳转登陆页面
	@RequestMapping(value = "updatePassWord.html")
	public String updatePassWord(HttpServletRequest request, String newOnePwd){
		userCenterService.updatePassWord(newOnePwd);
		// 修改完密码后清除会话数据
		// logout
		// 最后跳转登陆页面
		return "redirect:/logout";
	}

	// 修改密码,成功后跳转登陆页面
	@RequestMapping(value = "uploadHeadImg.html")
	public String uploadHeadImg(HttpServletRequest request){
		// 最后跳转登陆页面
		return "/user/personal/uploadHeadImg.jsp";
	}

	// 修改头像
	@RequestMapping(value = "saveUploadHeadImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUploadHeadImg(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		try{
			UserInfo ui = UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo();
			usersService.updateCurrHeadImg(request,ht,ui.getHeadImageName());
			UserInfo userInfo = UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo();
			session.setAttribute("USERINFOPERSON",userInfo);
		}catch (BusinessException e){
			// TODO Auto-generated catch block
			ht.put(BaseHint.MESSAGE,e.getMessage());
			// logger.info(e.getMessage());
		}catch (IllegalStateException e){
			ht.put(BaseHint.MESSAGE,e.getMessage());
		}catch (IOException e){
			ht.put(BaseHint.MESSAGE,e.getMessage());
		}catch (Exception e){
			ht.put(BaseHint.MESSAGE,e.getMessage());
		}
		return ht;
	}

	// 个人信息修改
	@RequestMapping(value = "/front/userAddNew")
	@ResponseBody
	Map<String, Object> updatePersonInfo(@ModelAttribute("UserInfo") UserInfo userInfo){
		try{
			ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
			userCenterService.updatePersonInfo(userInfo);
			ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
			ht.put(BaseHint.MESSAGE,BaseHint.MESSAGE_SUCCESS);
		}catch (BusinessException be){
			ht.put(BaseHint.MESSAGE,be.getMessage());
			logger.info(be.getMessage());
		}catch (Exception e){
			ht.put(BaseHint.MESSAGE,e.getMessage());
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}

	/**
	 * 检验用户信息 邮箱、手机号、QQ、微信是否唯一
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/front/checkUserName", method = RequestMethod.POST)
	@ResponseBody
	boolean isUserName(@ModelAttribute("UserInfo") UserInfo ui){
		return usersService.isUserName(ui.getUserName(),ui.getUserInfoId());
	}

	@RequestMapping(value = "/front/checkUserEmail", method = RequestMethod.POST)
	@ResponseBody
	boolean isUserEmail(@ModelAttribute("UserInfo") UserInfo ui){
		return usersService.isUserEmail(ui.getUserEmail(),ui.getUserInfoId());
	}

	@RequestMapping(value = "/front/checkUserPhone", method = RequestMethod.POST)
	@ResponseBody
	boolean isUserPhone(@ModelAttribute("UserInfo") UserInfo user){
		return usersService.isUserPhone(user.getUserPhone(),user.getUserInfoId());
	}

	@RequestMapping(value = "/front/checkUserQq", method = RequestMethod.POST)
	@ResponseBody
	boolean isUserQq(@ModelAttribute("UserInfo") UserInfo user){
		return usersService.isUserQq(user.getUserQq(),user.getUserInfoId());
	}

	@RequestMapping(value = "/front/checkUserWeixin", method = RequestMethod.POST)
	@ResponseBody
	boolean isUserWeixin(@ModelAttribute("UserInfo") UserInfo user){
		return usersService.isUserWeixin(user.getUserWeixin(),user.getUserInfoId());
	}
}
