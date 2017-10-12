package sys.admin.action;

import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.model.UserInfo;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.FamilyCelebrityServiceImpl;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/familyCelebrity")
/**
 * 家族名人
 * 
 * @author lxr
 *
 */
public class FamilyCelebrityAction extends BaseAction {
    protected final Log _Log = LogFactory.getLog(getClass());
    @Resource(name = SpringAnnotationDef.SER_FAMILYCELEBRITY)
    private FamilyCelebrityServiceImpl familyCelebrityService;

    /**
     * 获取家族名人列表的界面
     */
    @RequestMapping("/familyCelebrityAllPage")
    public ModelAndView familyCelebrityAllPage() {
	_Log.debug(">>>>>>>>>>>>>>>>>familyCelebrityAllPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("GET familyCelebrity.vm");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyCelebrity/familyCelebrity.vm");
	return mav;
    }

    /**
     * 获取家族名人列表及分页信息
     */
    @RequestMapping("/familyCelebrityList")
    @ResponseBody
    TableRquestData familyCelebrityList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	_Log.debug(">>>>>>>>>>>>>>>>>familyCelebrityList()<<<<<<<<<<<<<<<<<");
	_Log.debug("parameter1[tableRquestData]:" + tableRquestData);
	_Log.debug("parameter2[search]:" + search);
	return familyCelebrityService.getFamilyCelebrityPagination(tableRquestData, search);
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @RequestMapping("/familyCelebrityAddPage")
    public ModelAndView familyCelebrityAddPage(HttpServletRequest request,
	    @ModelAttribute("UserInfo") UserInfo userInfo) {
	_Log.debug(">>>>>>>>>>>>>>>>>familyCelebrityAddPage()<<<<<<<<<<<<<<<<<");
	_Log.debug("parameter1[userInfo]:" + userInfo);
	ModelAndView mav = new ModelAndView();
	UserInfo ui = familyCelebrityService.getUserInfo(userInfo);
	_Log.debug("Find UserInfo Content:" + ui);
	String folder = MessageFormat.format(Sys_Const.FAMILY_HEAD_IMAGE_FILE_FOLDER_PATH, "/") + "/";
	_Log.debug("Folder Content:" + folder);
	String imgSrc = folder + ui.getHeadImageName();
	_Log.debug("Image Src Content:" + folder);
	mav.addObject("USERINFO", ui);
	mav.addObject("HEANDIMAGESRCONE", getBasePath(request, imgSrc));
	_Log.debug("GET familyCelebrity-add.vm");
	mav.setViewName("admin/menus/set/familyCelebrity/familyCelebrity-add.vm");
	return mav;
    }

    /**
     * 添加家族名人
     * 
     * @param familyNew
     * @param editorValue
     * @return
     */
    @RequestMapping("/COM_FAMILYCELEBRITY_SAVE")
    @ResponseBody
    Map<String, Object> addNew(HttpServletRequest request, @ModelAttribute("UserInfo") UserInfo userInfo,
	    @RequestParam("editorValue") String editorValue) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>addNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[userInfo]:" + userInfo);
	    _Log.debug("parameter2[editorValue]:" + editorValue);
	    familyCelebrityService.addNew(request, userInfo, editorValue);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	    ht.put("FAMILYNEWID", userInfo.getUserInfoId());
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while addNew()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while addNew()", e);
	}
	ht.remove("FAMILYNEW");
	return ht;
    }

    /**
     * 删除名人
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping("/FAMILY_CELEBRITY_DEL")
    @ResponseBody
    Map<String, Object> delFamilyNew(HttpServletRequest request, @ModelAttribute("UserInfo") UserInfo userInfo) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>delFamilyNew()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[userInfo]:" + userInfo);
	    familyCelebrityService.delFamilyCelebrity(request, userInfo);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while delFamilyNew()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while delFamilyNew()", e);
	}
	return ht;
    }

    /**
     * 首页名人展示
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping("/FAMILY_CELEBRITY_RELEASE")
    @ResponseBody
    Map<String, Object> familyCelebrateRelease(@ModelAttribute("UserInfo") UserInfo userInfo) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>familyCelebrateRelease()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[userInfo]:" + userInfo);
	    familyCelebrityService.releaseFamilyCelebrity(userInfo);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    _Log.error("Error occur while familyCelebrateRelease()", be);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while familyCelebrateRelease()", e);
	}
	return ht;
    }

}
