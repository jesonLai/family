package sys.admin.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.SpringAnnotationDef;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.RecordFamilyLogServiceImpl;


@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping("/admin/menus/recordFamilyLog")
/**
 * 监测用户
 * @author lxr
 *
 */
public class RecordFamilyLogAction extends BaseAction {
	private Logger _log=Logger.getLogger(RecordFamilyLogAction.class);
	@Resource(name=SpringAnnotationDef.SER_RECORDFAMILYLOG)
	private RecordFamilyLogServiceImpl recordFamilyLogService;
	
	/**
	 * 获取菜单界面
	 */
	@RequestMapping("/recordFamilyLogAllPage")
	public ModelAndView usersAllPage(ModelAndView modelAndView){
		modelAndView.setViewName("admin/menus/set/recordFamilyLog/recordFamilyLog.vm");  
		return modelAndView;
	}
	/**
	 * 获取菜单信息及分页信息
	 */
	@RequestMapping("/recordFamilyLogList")
	@ResponseBody TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,@RequestParam(value="search[value]",defaultValue="")String search){
		return recordFamilyLogService.getRecordFamilyLogPagination(tableRquestData, search);
	}
}
