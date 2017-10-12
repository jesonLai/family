package sys.admin.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import sys.set.service.impl.FamilyContributionServiceImpl;

/**
 * 功德榜
 * 
 * @author lxr
 *
 */
@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/familyContribution")
public class ContributionAction extends BaseAction {
    protected final Log _Log = LogFactory.getLog(getClass());
    @Resource(name = SpringAnnotationDef.SER_CONTRIBUTION)
    FamilyContributionServiceImpl familyContributionService;

    /**
     * 获取功德榜列表的界面
     */
    @RequestMapping("/familyContributionAllPage")
    public ModelAndView familyContributionAllPage() {
	_Log.debug(">>>>>>>>>>>>>>>>>familyContributionAllPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("GET familyContribution.vm");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyContribution/familyContribution.vm");
	return mav;
    }

    /**
     * 获取功德榜列表及分页信息
     */
    @RequestMapping("/familyContributionList")
    @ResponseBody
    TableRquestData familyContributionList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	_Log.debug(">>>>>>>>>>>>>>>>>familyContributionList()<<<<<<<<<<<<<<<<<");
	_Log.debug("parameter1[tableRquestData]:" + tableRquestData);
	return familyContributionService.getContributionPagination(tableRquestData, search);
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @SuppressWarnings("static-access")
    @RequestMapping("/familyContributionAddPage")
    public ModelAndView familyContributionAddPage() {
	_Log.debug(">>>>>>>>>>>>>>>>>familyContributionAddPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("GET familyContribution-add.vm");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyContribution/familyContribution-add.vm");
	List<Object> evenTypeNames = familyContributionService.familyContributionByEventTypeColumn();
	if (_Log.isDebugEnabled()) {
	    _Log.debug("evenTypeNames size:" + evenTypeNames != null ? evenTypeNames.size() : 0);
	    _Log.debug("evenTypeNames conetent:" + evenTypeNames);
	}
	JSONArray evenTypeNamesJsonArray = new JSONArray().fromObject(evenTypeNames);
	if (_Log.isDebugEnabled()) {
	    _Log.debug("evenTypeNames To JSONArray conetent:" + evenTypeNamesJsonArray);
	}
	mav.addObject("EVENTYPENAME", evenTypeNamesJsonArray);
	return mav;
    }

    /**
     * 功德榜添加
     * 
     * @param familyNew
     * @param editorValue
     * @return
     */
    @RequestMapping("/COM_familyContribution_SAVE")
    @ResponseBody
    Map<String, Object> addNew(@ModelAttribute("FamilyNew") FamilyNew familyNew,
	    @RequestParam("editorValue") String editorValue) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>addNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[familyNew]:" + familyNew);
	    _Log.debug("parameter2[editorValue]:" + editorValue);
	    familyNew.setHtmlContent(editorValue);
	    ht.put("FAMILYNEW", familyNew);
	    familyContributionService.addNew(ht);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while addNew()", e);
	}
	ht.remove("FAMILYNEW");
	return ht;
    }

    /**
     * 删除功德榜
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping("/FAMILY_FINANCIAL_DEL")
    @ResponseBody
    Map<String, Object> delFamilyNew(@ModelAttribute("FamilyNew") FamilyNew familyNew) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>delFamilyNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[familyNew]:" + familyNew);
	    familyContributionService.delFamilyContribution(familyNew.getFamilyNewsId());
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while delFamilyNew()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while addNew()", e);
	}
	return ht;
    }
}
