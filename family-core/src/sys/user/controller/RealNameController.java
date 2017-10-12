package sys.user.controller;

import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.exception.handing.BusinessException;
import sys.model.UserInfo;
import sys.model.form.UserInfoForm;
import sys.user.front.service.imp.RegisterServiceImpl;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping(value = "/")
public class RealNameController extends BaseAction{
	private static final Logger logger = Logger.getLogger(RealNameController.class.getName());
	@Resource(name=SpringAnnotationDef.SER_REGISTER_FRONT)
	RegisterServiceImpl registerService;
		/**
		 * 获取实名认证界面
		 * @param request
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/user/front/real-name.html")
		public String realNamePage(Model model){
			 model.addAttribute("USERINFOSELF", UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo());
			return "/user/personal/real/real-name.jsp";
		}
		/**
		 * 实名认证保存
		 * @param request
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/front/REAL_NAME_SAVE")
		@ResponseBody Map<String,Object> realNameSave(UserInfoForm userInfoList){
			try{
				ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
				registerService.addRealName(ht, userInfoList);
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
		@RequestMapping(value="/front/checkUserName", method = RequestMethod.POST)
		@ResponseBody boolean isUserName(@ModelAttribute("UserInfo")UserInfo ui){
			return registerService.isUserName(ui.getUserName(),ui.getUserInfoId());
		}
		@RequestMapping(value="/front/findUserName", method = RequestMethod.GET)
		@ResponseBody Map<String,Object> findUserName(@RequestParam("query")String userName){
			return registerService.familyByUserNameColumn(userName);
		}
		
		@RequestMapping(value="/front/getFatherPage", method = RequestMethod.GET)
		public ModelAndView fatherPage(@RequestParam("id")String id){
			id=StringUtil.getEncoding(id);
			 ModelAndView  mav= new ModelAndView(); 
			 if(StringUtil.isEmpty(id)){
				 mav.setViewName("user/initFatherName.vm"); 
			 }else{
				 mav.setViewName("user/fatherName.vm"); 
		         mav.addObject("USERFATHER",registerService.getUserInfo(Integer.parseInt(id)));
			 }
			return mav;
		} 
		
}
