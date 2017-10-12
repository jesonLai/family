package sys.admin.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sys.SpringAnnotationDef;
import sys.model.PubUser;
import sys.model.controller.MenuForm;
import sys.set.service.impl.IMenuServiceImpl;
import sys.util.func.UtilFactory;


@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus")
/**
 * 菜单跳转
 * @author lxr
 *
 */
public class MenuAction extends BaseAction {
	private Logger _log=Logger.getLogger(MenuAction.class);
	@Resource(name=SpringAnnotationDef.SER_MENU)
	private IMenuServiceImpl menuService;
	@RequestMapping(value="/sysMenu",method=RequestMethod.POST)
	public ModelAndView getIndex(HttpServletResponse respone, HttpServletRequest request, HttpSession session,@ModelAttribute("MenuForm")MenuForm menuForm ){
		 ModelAndView  mav= new ModelAndView();
		 PubUser userContext=UtilFactory.getSpringContext().getUserInfoContext().getUser();
		 String path = request.getContextPath();
		 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		 mav.setViewName("redirect:"+basePath.concat("/").concat(menuForm.getAction()));
		 session.setAttribute("MENUFORM", menuForm);
		return mav;
	}
	/**
	 * 获取菜单
	 * @param pubMenu
	 * @return
	 */
//	@RequestMapping("/COM_MENUITEM_SAVE")
//	@ResponseBody Map<String,Object> getMenus(){
//		try{
//			
//			ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
//			ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
//		}catch(Exception e){
//			ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
//			ht.put(BaseHint.MESSAGE, e.getMessage());
//			_log.error(e.getMessage());
//			e.printStackTrace();
//		}
//		return ht;
//	}
}
