package sys.admin.action;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
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
import sys.model.controller.FileEntity;
import sys.set.service.impl.AdvertisementServiceImpl;

/**
 * 广告管理
 * 
 * @author lxr
 *
 */
@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/advertisement")
public class AdvertisementAction extends BaseAction {
    protected final Log _Log = LogFactory.getLog(getClass());
    @Resource(name = SpringAnnotationDef.SER_ADVERTISEMENT)
    AdvertisementServiceImpl advertisementService;

    /**
     * 获取广告列表的界面
     */
    @RequestMapping("/advertisementAllPage")
    public ModelAndView usersAllPage(HttpServletRequest request) {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/advertisement/advertisement.vm");
	StringBuilder builder = new StringBuilder(File.separator);
	builder.append("WEB-INF").append(File.separator).append("advertisement.xml");
	try {
	    mav.addObject("NAVIGATION", advertisementService
		    .writeXml(request.getSession().getServletContext().getRealPath(builder.toString()), request));
	} catch (BusinessException e) {
	    _Log.error("Error occur while usersAllPage()", e);
	} catch (IOException e) {
	    _Log.error("Error occur while usersAllPage()", e);
	} catch (DocumentException e) {
	    _Log.error("Error occur while usersAllPage()", e);
	}
	return mav;
    }

    /**
     * 新增广告
     */
    @RequestMapping("/COM_ADVERTISEMENT_ADD")
    @ResponseBody
    Map<String, Object> advertisementNew(HttpServletRequest request, @ModelAttribute("FileEntity") FileEntity file) {
	try {
	    StringBuilder builder = new StringBuilder(File.separator);
	    builder.append("WEB-INF").append(File.separator).append("advertisement.xml");
	    advertisementService.addNewXml(request.getSession().getServletContext().getRealPath(builder.toString()),
		    request, file);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while advertisementNew()", be);
	} catch (IOException e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while advertisementNew()", e);
	} catch (DocumentException e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while advertisementNew()", e);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while advertisementNew()", e);
	}
	return ht;
    }

    /**
     * 移除广告
     */
    @RequestMapping("/COM_ADVERTISEMENT_REMOVE")
    @ResponseBody
    Map<String, Object> advertisementRemove(HttpServletRequest request, @RequestParam("id") String id) {
	try {
	    StringBuilder builder = new StringBuilder(File.separator);
	    builder.append("WEB-INF").append(File.separator).append("advertisement.xml");
	    advertisementService.deleteXmlNode(request.getSession().getServletContext().getRealPath(builder.toString()),
		    id);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while advertisementRemove()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while advertisementRemove()", e);
	}
	return ht;
    }

    // /**
    // * 获取广告列表及分页信息
    // */
    // @RequestMapping("/ancestralTempleList")
    // @ResponseBody TableRquestData
    // usersList(@ModelAttribute("TableRquestData") TableRquestData
    // tableRquestData,@RequestParam(value="search[value]",defaultValue="")String
    // search){
    // return
    // ancestralTempleService.getAncestralTemplePagination(tableRquestData,search);
    // }
}
