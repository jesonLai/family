package sys.admin.action;

import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.model.PubUser;
import sys.model.UserInfo;
import sys.model.controller.SenderEmail;
import sys.set.service.UsersService;
import sys.set.service.impl.IMenuServiceImpl;
import sys.util.StringUtil;
import sys.util.XMLOperation;
import sys.util.func.UtilFactory;

@Controller
@RequestMapping("/")
public class HomeAction extends BaseAction {
    protected final Log _Log = LogFactory.getLog(getClass());
    private final String xmlName = "email.xml";
    @Resource(name = SpringAnnotationDef.SER_MENU)
    private IMenuServiceImpl menuService;
    @Resource
    private UsersService usersService;

    /**
     * 后台首页
     * 
     * @param respone
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/index.html")
    public ModelAndView getIndex(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
	_Log.debug(">>>>>>>>>>>>>>>>>>>getIndex()<<<<<<<<<<<<<<<<");
	ModelAndView mav = new ModelAndView();
	response.setHeader("x-Frame-Options", "SAMEORIGIN");
	PubUser userContext = UtilFactory.getSpringContext().getUserInfoContext().getUser();
	String production = request.getServletContext().getInitParameter("spring.profiles.default");
	_Log.debug(">>>>>>>>>>>>>>>>>>>production[" + production + "]<<<<<<<<<<<<<<<<");
	_Log.debug(">>>>>>>>>>>>>>>>>>>userContext[" + userContext + "]<<<<<<<<<<<<<<<<");
	if (StringUtil.isEmpty(userContext) || StringUtil.isEmpty(String.valueOf(userContext.getUId()))) {
	    mav.setViewName("redirect:" + request.getServletContext().getInitParameter("back_Login_Page"));

	} else {
	    _Log.debug(">>>>>>>>>>>>>>>>>>>/admin/index.vm<<<<<<<<<<<<<<<<");
	    mav.setViewName("/admin/index.vm");
	    UserInfo ui = userContext.getUserInfo();
	    Object headImageNameObject=ui==null?"":ui.getHeadImageName();
	    String imgSrc = MessageFormat.format(Sys_Const.FAMILY_HEAD_IMAGE_FILE_FOLDER_PATH, "/") + "/"
		    + headImageNameObject;
	    session.setAttribute("HEANDIMAGESRC", imgSrc);
	    session.setAttribute("USERINFOPERSON", userContext.getUserInfo());

	    SenderEmail senderEmail = new SenderEmail();
	    try {
		ServletContext sc = request.getSession().getServletContext();
		senderEmail.setXmlName(xmlName);
		senderEmail.setXmlPath(sc.getRealPath("/WEB-INF/"));
		XMLOperation.readXML(senderEmail);

	    } catch (Exception e) {
		_Log.info(e.getMessage());
	    }
	    mav.addObject("SENDEREMAIL", senderEmail);
	}

	return mav;
    }

    /**
     * 前台登录
     * 
     * @param respone
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.html")
    public ModelAndView userLoginPage(ModelAndView modelAndView, HttpServletResponse respone, HttpSession session,
	    HttpServletRequest request) {
	modelAndView.setViewName(request.getServletContext().getInitParameter("show_front_Login_Page"));
	UtilFactory.getSpringContext().getUserInfoContext().setFrontOrBack("front");
	return modelAndView;
    }

    /**
     * 后台登录
     * 
     * @param respone
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/login.html")
    public ModelAndView adminLoginPage(ModelAndView modelAndView, HttpServletResponse respone, HttpSession session,
	    HttpServletRequest request) {
	modelAndView.setViewName(request.getServletContext().getInitParameter("show_back_Login_Page"));
	UtilFactory.getSpringContext().getUserInfoContext().setFrontOrBack("back");
	return modelAndView;
    }

}
