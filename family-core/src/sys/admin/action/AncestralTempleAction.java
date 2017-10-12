package sys.admin.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import sys.model.AncestralTemple;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.AncestralTempleServiceImpl;

/**
 * 祠堂
 * 
 * @author lxr
 *
 */
@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/ancestralTemple")
public class AncestralTempleAction extends BaseAction {
    protected final Log _Log = LogFactory.getLog(getClass());
    @Resource(name = SpringAnnotationDef.SER_ANCESTRALTEMPLE)
    AncestralTempleServiceImpl ancestralTempleService;

    /**
     * 获取祠堂列表的界面
     */
    @RequestMapping("/ancestralTempleAllPage")
    public ModelAndView ancestralTempleAllPage() {
	_Log.debug(">>>>>>>>>>>>>>>>>ancestralTempleAllPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("get ancestralTemple.vm");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/ancestralTemple/ancestralTemple.vm");
	return mav;
    }

    /**
     * 获取祠堂列表及分页信息
     */
    @RequestMapping("/ancestralTempleList")
    @ResponseBody
    TableRquestData ancestralTempleList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	_Log.debug(">>>>>>>>>>>>>>>>>ancestralTempleList()<<<<<<<<<<<<<<<<<");
	_Log.debug("parameter1[tableRquestData]:" + tableRquestData);
	_Log.debug("parameter2[tableRquestData]:" + search);
	return ancestralTempleService.getAncestralTemplePagination(tableRquestData, search);
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @RequestMapping("/ancestralTempleAddPage")
    public ModelAndView ancestralTempleAddPage() {
	_Log.debug(">>>>>>>>>>>>>>>>>ancestralTempleAddPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("get ancestralTemple-add.vm");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/ancestralTemple/ancestralTemple-add.vm");
	return mav;
    }

    /**
     * 添加祠堂
     * 
     * @param editorValue
     * @return
     */
    @RequestMapping(value = "/COM_ANCESRALTEMPLE_SAVE", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> addNew(HttpServletRequest request,
	    @ModelAttribute("AncestralTemple") AncestralTemple ancestralTemple,
	    @RequestParam("editorValue") String editorValue) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>addNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[ancestralTemple]:" + ancestralTemple);
	    _Log.debug("parameter2[editorValue]:" + editorValue);
	    ancestralTemple.setAtContent(editorValue);
	    ancestralTempleService.addNew(ht, ancestralTemple, request);
	    _Log.debug("result[ht]:" + ht);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while addNew()", e);
	}
	return ht;
    }

    /**
     * 删除祠堂
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping("/FAMILY_ANCESTRALTEMPLE_DEL")
    @ResponseBody
    Map<String, Object> delAncestralTemple(HttpServletRequest request,
	    @ModelAttribute("AncestralTemple") AncestralTemple ancestralTemple) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>delAncestralTemple()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[ancestralTemple]:" + ancestralTemple);
	    ancestralTempleService.delAncestralTemple(ancestralTemple.getAtId(), request);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while delAncestralTemple()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while delAncestralTemple()", e);
	}
	return ht;
    }

    /**
     * 置顶祠堂
     * 
     * @param ancestralTemple
     * @return
     */
    @RequestMapping("/FAMILY_ANCESTRALTEMPLE_TOP")
    @ResponseBody
    Map<String, Object> topAncestralTemple(@ModelAttribute("AncestralTemple") AncestralTemple ancestralTemple) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>delAncestralTemple()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[ancestralTemple]:" + ancestralTemple);
	    ancestralTempleService.familyAncestralTemplePlacedTop(ancestralTemple);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while topAncestralTemple()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while topAncestralTemple()", e);
	}
	return ht;
    }
}
