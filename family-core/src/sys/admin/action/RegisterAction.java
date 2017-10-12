package sys.admin.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.UserInfo;
import sys.model.controller.TableRquestData;
import sys.model.form.UserInfoForm;
import sys.set.service.impl.RegisterServiceImpl;
import sys.util.EmailSendHint;

/**
 * 注册审核
 * @author lxr
 *
 */
@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/")
public class RegisterAction extends BaseAction{
	private static final Logger logger=Logger.getLogger(RegisterAction.class.getName());
	@Resource(name=SpringAnnotationDef.SER_REGISTER)
	RegisterServiceImpl registerService;
	/**
	 * 获取注册审核列表的界面
	 */
	@RequestMapping("/admin/menus/register/registerAllPage")
	public ModelAndView usersAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/register/register.vm");  
		return mav;
	}
	/**
	 * 获取注册审核列表及分页信息
	 */
	@RequestMapping("/admin/menus/register/registerList")
	@ResponseBody TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
		return registerService.getUserInfoCheckPagination(tableRquestData,search);
	}
	
	/**
	 * 获取注册详细信息
	 */
	@RequestMapping("/admin/menus/register/registerDetail")
	public ModelAndView  registerDetail(@ModelAttribute("UserInfo")UserInfo userChild){
		 ModelAndView  mav= new ModelAndView();
		 mav.addObject("USERINFOREGISTERREAL", registerService.findOneByUserInfoId(userChild));
         mav.setViewName("admin/menus/set/register/register-check.vm");  
		
		return mav;
	}
	/**
	 *测试邮箱是否有效
	 */
	@RequestMapping("/admin/menus/email/TEST")
	@ResponseBody Map<String,Object> registerEmailTest(HttpServletRequest request,@RequestParam("email")String email,@RequestParam("title")String title,@RequestParam("content")String content){
		try{
			new EmailSendHint(request).send(title,email,content);
			ht.put(BaseHint.MESSAGE, "已成功发送");
		}catch(AddressException ae){
			ht.put(BaseHint.MESSAGE, ae.getMessage());
			logger.info(ae.getMessage());
		}catch( MessagingException me){
			ht.put(BaseHint.MESSAGE, me.getMessage());
			logger.info(me.getMessage());
		}catch(Exception e){
			ht.put(BaseHint.MESSAGE, e.getMessage());
			logger.info(e.getMessage());
		}
		return ht;
	}
	/**
	 *审核通过
	 */
	@RequestMapping("/admin/menus/register/checkRealName")
	@ResponseBody Map<String,Object> checkRealName(HttpServletRequest request,@ModelAttribute("UserInfoForm") UserInfoForm userInfoForm){
		try{
			ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
			registerService.checkUserInfo(ht, request, userInfoForm);
		}catch(BusinessException be){
			ht.put(BaseHint.MESSAGE, be.getMessage());
			logger.info(be.getMessage());
		}catch(AddressException ae){
			ht.put(BaseHint.MESSAGE, "发送失败，地址错误");
			logger.info(ae.getMessage());
		}catch( MessagingException me){
			ht.put(BaseHint.MESSAGE, "发送失败，内容格式错误");
			logger.info(me.getMessage());
		}catch(Exception e){
			ht.put(BaseHint.MESSAGE, e.getMessage());
			logger.info(e.getMessage());
		}
		return ht;
	}
	/**
	 *审核不通过
	 */
	@RequestMapping("/admin/menus/register/checkNotThroughUserInfo")
	@ResponseBody Map<String,Object> checkNotThroughUserInfo(HttpServletRequest request,@ModelAttribute("UserInfo") UserInfo userInfo){
		try{
			registerService.checkNotThroughUserInfo(ht, request, userInfo);
		
		}catch(AddressException ae){
			ht.put(BaseHint.MESSAGE, "发送失败，地址错误");
			logger.info(ae.getMessage());
		}catch( MessagingException me){
			ht.put(BaseHint.MESSAGE, "发送失败，内容格式错误");
			logger.info(me.getMessage());
		}catch(Exception e){
			ht.put(BaseHint.MESSAGE, e.getMessage());
			logger.info(e.getMessage());
		}
		return ht;
	}
	
}
