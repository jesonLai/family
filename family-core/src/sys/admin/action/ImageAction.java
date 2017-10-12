
package sys.admin.action;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.baidu.ueditor.ActionEnter;

import net.sf.json.JSONArray;
import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.FamilyImage;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.ImageMessageServiceImpl;
import sys.util.StringUtil;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/image")
/**
 * 图片
 * 
 * @author lxr
 *
 */
public class ImageAction extends BaseAction {
    private static final Logger logger = Logger.getLogger(TestImageAction.class.getName());
    @Resource(name = SpringAnnotationDef.SER_FAMILYIMAGE)
    ImageMessageServiceImpl imageMessageServiceImpl;

    /**
     * 获取图片上传界面
     */
    @RequestMapping("/imageAllPage")
    public ModelAndView usersAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/image/image.vm");

	return mav;
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @RequestMapping("/imageAddPage")
    public ModelAndView familyNewsAddAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/image/image-add.vm");
	mav.addObject("EVENTYPENAME",
		new JSONArray().fromObject(imageMessageServiceImpl.familyNewsByEventTypeColumn()));
	return mav;
    }

    /**
     * 获取图片信息及分页信息
     */
    @RequestMapping("/familyImageList")
    @ResponseBody
    TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	return imageMessageServiceImpl.getFamilyImagePagination(tableRquestData, search);
    }

    /**
     * 图片上传并保存进数据库
     * 
     * @param request
     * @param response
     * @param familyImage
     * @return
     * @throws IllegalStateException
     * @throws IOException
     *             synchronized
     */
    @RequestMapping("/action/upload")
    @ResponseBody
    Map<String, Object> uploadImage(HttpServletRequest request, HttpServletResponse response,
	    @ModelAttribute("FamilyImage") FamilyImage familyImage, @RequestParam("editorValue") String editorValue)
	    throws IllegalStateException, IOException {
	try {
	    familyImage.setHtmlContent(editorValue);
	    imageMessageServiceImpl.addImage(ht, familyImage, request);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return ht;
    }

    @RequestMapping("/removeImageUpload")
    @ResponseBody
    Map<String, Object> removeImageUpload(HttpServletRequest request, HttpServletResponse response,
	    @ModelAttribute("FamilyImage") FamilyImage familyImage) {
	try {
	    imageMessageServiceImpl.delImgUpload(request, familyImage);
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

    // ueditor图片上传
    @RequestMapping("/imageUpload")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
	request.setCharacterEncoding("utf-8");
	response.setHeader("Content-Type", "text/html");
	String rootPath = request.getSession().getServletContext().getRealPath("/");
	response.getWriter().write(new ActionEnter(request, rootPath).exec());
	response.getWriter().flush();
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