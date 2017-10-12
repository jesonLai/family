package sys.admin.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.FamilyVideo;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.FamilyVideoServiceImpl;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/familyVideo")
/**
 * 视频
 * 
 * @author lxr
 *
 */
public class FamilyVideoAction extends BaseAction {
    private static final Logger logger = Logger.getLogger(ImageAction.class.getName());
    @Resource(name = SpringAnnotationDef.SER_FAMILYVIDEO)
    FamilyVideoServiceImpl familyVideoService;

    /**
     * 获取视频上传界面
     */
    @RequestMapping("/familyVideoAllPage")
    public ModelAndView usersAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyVideo/familyVideo.vm");
	// mav.addObject("EVENTYPENAME",new
	// JSONArray().fromObject(familyVideoService.familyNewsByEventTypeColumn()));
	return mav;
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @RequestMapping("/familyVideoAddPage")
    public ModelAndView familyNewsAddAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyVideo/familyVideo-add.vm");
	mav.addObject("EVENTYPENAME", new JSONArray().fromObject(familyVideoService.familyNewsByEventTypeColumn()));
	return mav;
    }

    /**
     * 获取视频信息及分页信息
     */
    @RequestMapping("/familyVideoList")
    @ResponseBody
    TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	return familyVideoService.getFamilyVideoPagination(tableRquestData, search);
    }

    /**
     * 添加在线视频
     * 
     * @param familyVideo
     * @return
     */
    @RequestMapping(value = "/COM_FAMILYVIDEO_SAVE", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> addNew(HttpServletRequest request, @ModelAttribute("FamilyVideo") FamilyVideo familyVideo) {
	try {
	    familyVideoService.addNew(request, familyVideo);
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

    /**
     * 
     * @param familyVideo
     * @return
     */
    @RequestMapping(value = "/COM_FAMILYVIDEO_REMOVE", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> delFamilyvideo(HttpServletRequest request, @ModelAttribute("familyVideoId") int familyVideoId) {
	try {
	    familyVideoService.delFamilyVideo(request, familyVideoId);
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
}
