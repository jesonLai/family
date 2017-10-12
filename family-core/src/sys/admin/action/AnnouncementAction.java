package sys.admin.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.Announcement;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.AnnouncementServiceImpl;

/**
 * 公告
 * @author lxr
 *
 */
@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus/announcement")
public class AnnouncementAction extends BaseAction{
	private static final Logger logger=Logger.getLogger(AnnouncementAction.class.getName());
	@Resource(name=SpringAnnotationDef.SER_ANNOUNCEMENT)
	AnnouncementServiceImpl announcementService;
	/**
	 * 获取公告列表的界面
	 */
	@RequestMapping("/announcementAllPage")
	public ModelAndView usersAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/announcement/announcement.vm");  
		return mav;
	}
	/**
	 * 获取公告列表及分页信息
	 */
	@RequestMapping("/announcementList")
	@ResponseBody TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
		return announcementService.getAnnouncementPagination(tableRquestData,search);
	}
	/**
	 * 获取编辑页
	 * @return
	 */
	@RequestMapping("/announcementAddPage")
	public ModelAndView familyNewsAddAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/announcement/announcement-add.vm");  
		return mav;
	}
	/**
	 * 添加公告
	 * @param editorValue
	 * @return
	 */
	@RequestMapping(value="/COM_ANNOUNCEMENT_SAVE",method=RequestMethod.POST)
	@ResponseBody Map<String,Object> addNew(HttpServletRequest request,@ModelAttribute("Announcement")Announcement announcement,@RequestParam("editorValue")String editorValue){
		try{
			announcementService.addNew(ht, announcement, editorValue);
		}catch(Exception e){
			ht.put(BaseHint.MESSAGE, e.getMessage());
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return ht;
	}
	/**
	 * 删除祠堂
	 * @param familyNew
	 * @return
	 */
	@RequestMapping("/FAMILY_ANNOUNCEMENT_DEL")
	@ResponseBody Map<String,Object> delFamilyNew(@ModelAttribute("Announcement")Announcement announcement){
		try{
			
			announcementService.delAnnouncement(announcement);
			 ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
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
	@RequestMapping("/FAMILY_ANNOUNCEMENT_TOP")
	@ResponseBody Map<String,Object> topFamilyNew(@ModelAttribute("Announcement")Announcement announcement){
		try{
			
			announcementService.familyAnnouncementPlacedTop(announcement);
			 ht.put(BaseHint.RESULT,BaseHint.RESULT_TRUE);
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
}
