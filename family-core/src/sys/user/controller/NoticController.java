package sys.user.controller;

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
import sys.model.Announcement;
import sys.set.service.impl.AnnouncementServiceImpl;
import sys.user.front.service.imp.WebAnnouncementServiceImpl;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping(value = "/user/announcement")
public class NoticController extends BaseAction{
	
	@Resource(name=SpringAnnotationDef.Web_SER_ANNOUNCEMENT)
	private WebAnnouncementServiceImpl webAnnouncementServiceImpl;
	
	
	
	//查询全部新闻
	@RequestMapping(value = "announcementList")
	public String announcementList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,String type, HttpServletRequest request,Model model){
		Page<Announcement> resultList  = webAnnouncementServiceImpl.findAllFront(pageNumber, 20);
		model.addAttribute("resultList", resultList);
		return "/user/noticeList.jsp";
	}
		
		
	//通过新闻id查询新闻详情
	@RequestMapping(value = "announcementDetail")
	public String announcementDetail(Integer id,Model model){
		Announcement announcement = webAnnouncementServiceImpl.findById(id);
		model.addAttribute("announcement", announcement);
		return "/user/noticeDetail.jsp";
		
	}
	

}
