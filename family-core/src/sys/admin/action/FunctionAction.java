package sys.admin.action;

import java.util.Map;

import javax.annotation.Resource;

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
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.family.annotation.SystemControllerLog;
import sys.model.PubFunction;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.AuthorityServiceImpl;
import sys.set.service.impl.IFunctionsServiceImpl;

/**
 * 功能
 * @author lxr
 *
 */
@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus/function")
public class FunctionAction extends BaseAction{
	private static final Logger logger=Logger.getLogger(FunctionAction.class.getName());
	@Resource(name=SpringAnnotationDef.SER_AUTHORITY)
	AuthorityServiceImpl authorityServiceImpl;
	@Resource(name=SpringAnnotationDef.SER_FUNCTION)
	IFunctionsServiceImpl iFunctionsService;
	/**
	 * 获取功能列表的界面
	 */
	@RequestMapping("/functionMainPage")
	public ModelAndView functionAllPage(){
		 ModelAndView  mav= new ModelAndView();  
         mav.setViewName("admin/menus/set/functions/functions.vm");  
		return mav;
	}
	/**
	 * 获取功能资源列表及分页信息
	 */
	@RequestMapping("/functionList")
	@ResponseBody Map<String,Object> functionList(){
		return iFunctionsService.getFunctionsPagination();
	}
	@RequestMapping("/functionAddPage")
	public  ModelAndView authorityAllPage(@RequestParam(value="functionId",defaultValue="0")Integer functionId,ModelAndView modelAndView){
		modelAndView.addObject("FUNCTIONINFO",iFunctionsService.getPubFunction(functionId));
		modelAndView.setViewName("admin/menus/set/functions/functions-add.vm");  
		return modelAndView;
	}
	/**
	 * 添加功能
	 * @param editorValue
	 * @return
	 */
	@RequestMapping(value="/COM_FUNCTIONS_SAVE",method=RequestMethod.POST)
	@ResponseBody Map<String,Object> addNew(@ModelAttribute("PubFunction")PubFunction pubFunction){
		try {
			iFunctionsService.addNew(pubFunction);
			ht.put("FUNCTIONID", pubFunction.getFunctionId());
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
	/**
	 * 移除
	 * @param pubFunction
	 * @return
	 */
	@RequestMapping(value="/COM_FUNCTION_REMOVE",method=RequestMethod.POST)
	@ResponseBody Map<String,Object> remove(@ModelAttribute("PubFunction")PubFunction pubFunction){
		try {
			 ht.put(BaseHint.RESULT,BaseHint.RESULT_FALSE);
			 ht.put("function", pubFunction);
			 iFunctionsService.delete(ht);
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
