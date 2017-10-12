package sys.admin.action;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import sys.set.service.impl.FamilyNewsServiceImpl;
import sys.util.StringUtil;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/familynews")
/**
 * 文本编辑
 * 
 * @author lxr
 *
 */
public class FamilynewsAction extends BaseAction {
    private static final Logger logger = Logger.getLogger(FamilynewsAction.class.getName());
    @Resource(name = SpringAnnotationDef.SER_FAMILYNEWS)
    private FamilyNewsServiceImpl familyNewsServiceImpl;

    /**
     * 获取新闻列表的界面
     */
    @RequestMapping("/familynewsAllPage")
    public ModelAndView familyNewsAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familynews/familynews.vm");
	return mav;
    }

    /**
     * 获取新闻列表及分页信息
     */
    @RequestMapping("/familynewsList")
    @ResponseBody
    TableRquestData familyNewsList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	return familyNewsServiceImpl.getFamilyNewPagination(tableRquestData, search);
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @RequestMapping("/familynewsAddPage")
    public ModelAndView familyNewsAddAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familynews/familynews-edit.vm");
	mav.addObject("EVENTYPENAME", new JSONArray().fromObject(familyNewsServiceImpl.familyNewsByEventTypeColumn()));
	return mav;
    }

    /**
     * 持久化新闻信息
     * 
     * @param request
     * @param familyNew
     * @param editorValue
     * @return
     */
    @RequestMapping("/family_news_save")
    @ResponseBody
    Map<String, Object> addNew(HttpServletRequest request, @ModelAttribute("FamilyNew") FamilyNew familyNew,
	    @RequestParam("editorValue") String editorValue) {
	try {

	    familyNewsServiceImpl.addNew(request, familyNew, editorValue);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, StringUtil.isEmpty(be.getMessage()) ? "服务器跑路了！" : be.getMessage());
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
	    logger.info(be.getMessage());
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, StringUtil.isEmpty(e.getMessage()) ? "服务器跑路了！" : e.getMessage());
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return ht;
    }

    @RequestMapping("/family_news_DEL")
    @ResponseBody
    Map<String, Object> delFamilyNew(HttpServletRequest request, @ModelAttribute("FamilyNew") FamilyNew familyNew) {
	try {

	    familyNewsServiceImpl.delFamilyNews(request, familyNew.getFamilyNewsId());
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    logger.info(be.getMessage());
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return ht;
    }

    @RequestMapping("/family_news_top")
    @ResponseBody
    Map<String, Object> topFamilyNew(@ModelAttribute("FamilyNew") FamilyNew familyNew) {
	try {

	    familyNewsServiceImpl.familyNewPlacedTop(familyNew);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    logger.info(be.getMessage());
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return ht;
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
	dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
	    public void setAsText(String value) {
		try {
		    if (StringUtil.isEmpty(value)) {
			setValue(null);
		    } else {
			setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
		    }

		} catch (ParseException e) {
		    setValue(null);
		}
	    }

	    public String getAsText() {
		return new SimpleDateFormat("yyyy-MM-dd").format((Date) getValue());
	    }

	});
    }
}
