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
import sys.set.service.impl.FamilyCultureServiceImpl;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/familyCulture")
/**
 * 家族文化
 * 
 * @author lxr
 *
 */
public class FamilyCultureAction extends BaseAction {
    protected final Log _Log = LogFactory.getLog(getClass());
    @Resource(name = SpringAnnotationDef.SER_FAMILYCULTURE)
    private FamilyCultureServiceImpl familyCultureService;

    /**
     * 获取家族文化列表的界面
     */
    @RequestMapping("/familyCultureAllPage")
    public ModelAndView familyCultureAllPage() {
	_Log.debug(">>>>>>>>>>>>>>>>>familyCultureAllPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("GET familyCulture.vm");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyCulture/familyCulture.vm");
	return mav;
    }

    /**
     * 获取家族文化列表及分页信息
     */
    @RequestMapping("/familyCultureList")
    @ResponseBody
    TableRquestData familyCultureList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	_Log.debug(">>>>>>>>>>>>>>>>>familyCultureList()<<<<<<<<<<<<<<<<<");
	_Log.debug("parameter1[tableRquestData]:" + tableRquestData);
	_Log.debug("parameter2[search]:" + search);
	return familyCultureService.getFamilyCulturePagination(tableRquestData, search);
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @RequestMapping("/familyCultureAddPage")
    public ModelAndView familyCultureAddPage() {
	_Log.debug(">>>>>>>>>>>>>>>>>familyCultureAddPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("GET familyCulture-add.vm");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyCulture/familyCulture-add.vm");
	List<Object> evenTypeNames = familyCultureService.familyCultureByEventTypeColumn();
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
     * 添加家族文化
     * 
     * @param familyNew
     * @param editorValue
     * @return
     */
    @RequestMapping("/COM_FAMILYCULTURE_SAVE")
    @ResponseBody
    Map<String, Object> addNew(@ModelAttribute("FamilyNew") FamilyNew familyNew,
	    @RequestParam("editorValue") String editorValue) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>addNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[familyNew]:" + familyNew);
	    familyNew.setHtmlContent(editorValue);
	    ht.put("FAMILYNEW", familyNew);
	    familyCultureService.addNew(ht);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while addNew()", e);
	}
	ht.remove("FAMILYNEW");
	return ht;
    }

    /**
     * 删除文化
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping("/FAMILY_CULTURE_DEL")
    @ResponseBody
    Map<String, Object> delFamilyNew(@ModelAttribute("FamilyNew") FamilyNew familyNew) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>delFamilyNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[familyNew]:" + familyNew);
	    familyCultureService.delFamilyCulture(familyNew.getFamilyNewsId());
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while delFamilyNew()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while delFamilyNew()", e);
	    e.printStackTrace();
	}
	return ht;
    }

    @RequestMapping("/FAMILY_CULTURE_TOP")
    @ResponseBody
    Map<String, Object> topFamilyNew(@ModelAttribute("FamilyNew") FamilyNew familyNew) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>topFamilyNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[familyNew]:" + familyNew);
	    familyCultureService.familyCulturePlacedTop(familyNew);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while topFamilyNew()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while topFamilyNew()", e);
	}
	return ht;
    }
}
