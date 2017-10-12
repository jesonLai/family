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

import net.sf.json.JSONArray;
import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.FamilyNew;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.FamilyFinancialServiceImpl;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus/familyFinancial")
/**
 * 家族文化
 * @author lxr
 *
 */
public class FamilyFinancialAction extends BaseAction{
	private static final Logger logger=Logger.getLogger(FamilyFinancialAction.class.getName());
	@Resource(name=SpringAnnotationDef.SER_FAMILYFINANCIAL)
	private FamilyFinancialServiceImpl familyFinancialService;
	/**
	 * 获取家族名人列表的界面
	 */
	@RequestMapping("/familyFinancialAllPage")
	public ModelAndView usersAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/familyFinancial/familyFinancial.vm");  
		return mav;
	}
	/**
	 * 获取家族名人列表及分页信息
	 */
	@RequestMapping("/familyFinancialList")
	@ResponseBody TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
		return familyFinancialService.getFamilyFinancialPagination(tableRquestData,search);
	}
	/**
	 * 获取编辑页
	 * @return
	 */
	@RequestMapping("/familyFinancialAddPage")
	public ModelAndView familyNewsAddAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/familyFinancial/familyFinancial-add.vm");  
         mav.addObject("EVENTYPENAME",new JSONArray().fromObject(familyFinancialService.familyFinancialByEventTypeColumn()));
		return mav;
	}
	/**
	 * 财务添加
	 * @param familyNew
	 * @param editorValue
	 * @return
	 */
	@RequestMapping("/COM_FAMILYFINANCIAL_SAVE")
	@ResponseBody Map<String,Object> addNew(@ModelAttribute("FamilyNew")FamilyNew familyNew,@RequestParam("editorValue")String editorValue){
		try{
			
			familyNew.setHtmlContent(editorValue);
			ht.put("FAMILYNEW", familyNew);
			familyFinancialService.addNew(ht);
		}catch(Exception e){
			ht.put(BaseHint.MESSAGE, e.getMessage());
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		ht.remove("FAMILYNEW");
		return ht;
	}
	/**
	 * 删除名人
	 * @param familyNew
	 * @return
	 */
	@RequestMapping("/FAMILY_FINANCIAL_DEL")
	@ResponseBody Map<String,Object> delFamilyNew(@ModelAttribute("FamilyNew")FamilyNew familyNew){
		try{
			
			familyFinancialService.delFamilyFinancial(familyNew.getFamilyNewsId());
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
	@RequestMapping("/FAMILY_FINANCIA_TOP")
	@ResponseBody Map<String,Object> topFamilyNew(@ModelAttribute("FamilyNew")FamilyNew familyNew){
		try{
			familyFinancialService.familyFinancialPlacedTop(familyNew);
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
