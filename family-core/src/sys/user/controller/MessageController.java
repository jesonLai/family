package sys.user.controller;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.model.FamilyMessage;
import sys.model.PubUser;
import sys.user.front.service.imp.WebMessageServiceImpl;
import sys.util.func.UtilFactory;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping(value = "/user/message")
public class MessageController extends BaseAction{
	
	@Resource(name=SpringAnnotationDef.Web_SER_FAMILYMESSAGE)
	private WebMessageServiceImpl webMessageServiceImpl;
	
	@RequestMapping(value = "messageList")
	public String findVideoList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,HttpServletRequest request,Model model){
		//Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<FamilyMessage> resultList  = webMessageServiceImpl.findAllFront(null, pageNumber, 10);
		model.addAttribute("resultList", resultList);
		
		return "/user/familyMessage/familyMessage.jsp";
	}
	
	//添加留言
	@RequestMapping(value = "addMessage")
	public String addMessage(FamilyMessage message){
		PubUser user = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		if(user==null)
			return "/user/login.jsp";
		message.setPubUser(user);
		message.setMessageDate(new Timestamp(new Date().getTime()));
		webMessageServiceImpl.addMessage(message);
		return "redirect:messageList";
	}
		

}
