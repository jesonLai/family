package sys.admin.action;

import java.util.Map;

import javax.annotation.Resource;

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
import sys.model.FamilyMessage;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.FamilyMessageServiceImpl;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus/familyMessage")
/**
 * 宗亲留言
 * @author lxr
 *
 */
public class FamilyMessageAction extends BaseAction{
	private static final Logger logger=Logger.getLogger(FamilyMessageAction.class.getName());
	@Resource(name=SpringAnnotationDef.SER_FAMILYMESSAGE)
	private FamilyMessageServiceImpl familyMessageService;
	/**
	 * 获取留言列表的界面
	 */
	@RequestMapping("/familyMessageAllPage")
	public ModelAndView usersAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/familyMessage/familyMessage.vm");  
		return mav;
	}
	/**
	 * 获取留言列表及分页信息
	 */
	@RequestMapping("/familyMessageList")
	@ResponseBody TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
		return familyMessageService.getFamilyMessagePagination(tableRquestData,search);
	}
	/**
	 * 删除留言
	 * @param familyNew
	 * @return
	 */
	@RequestMapping("/FAMILY_MESSAGE_DEL")
	@ResponseBody Map<String,Object> delFamilyMessage(@ModelAttribute("FamilyMessage")FamilyMessage familyMessage){
		try{
			
			familyMessageService.delFamilyMessage(familyMessage);
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
