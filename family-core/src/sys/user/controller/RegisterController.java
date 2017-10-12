package sys.user.controller;



import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.admin.action.UserLoginInfoAction;
import sys.exception.handing.BusinessException;
import sys.model.PubUser;
import sys.user.front.service.imp.RegisterServiceImpl;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping(value = "/")
public class RegisterController extends BaseAction{
	private static final Logger logger = Logger.getLogger(RegisterController.class.getName());
	@Resource(name=SpringAnnotationDef.SER_REGISTER_FRONT)
	RegisterServiceImpl registerService;
	/**
	 * 前台注册
	 * 邮箱返回需要把之前填写的信息读取出来
	 */
	@RequestMapping("user/register.html")
	public ModelAndView registerPage(ModelAndView mdv){
		mdv.setViewName("user/register.jsp");
		return mdv;
	}
	/**
	 * 前台注册
	 * 后续需要实名认证才可以查看家族树状图
	 */
	@RequestMapping("/front/COM_REGISTER_SAVE")
	@ResponseBody Map<String, Object> addUsers(PubUser pu){
		try{
			registerService.addNew(ht, pu);
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
	@RequestMapping("/front/CHECK_USERACCOUNT")
	@ResponseBody Boolean checkUserAccount(String userAccount){
		
		return registerService.checkUserAccount(userAccount);
	}
	
}
