package sys.user.controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.admin.action.BaseAction;
import sys.model.FamilyImage;
import sys.user.front.service.imp.WebImageMessageServiceImpl;
import sys.util.StringUtil;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping(value = "/user/image")
public class ImageController extends BaseAction {

    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYIMAGE)
    WebImageMessageServiceImpl webImageMessageServiceImpl;

    @RequestMapping(value = "imgMain")
    public String imgMain(HttpServletRequest request, Model model) {

	Map<String, Object> map = Maps.newHashMap();

	// 按类型分组
	List<FamilyImage> groupList = webImageMessageServiceImpl.findAllGroupByEventType();

	// 按类型排序
	List<FamilyImage> imgList = webImageMessageServiceImpl.findByOrderByEventType();

	for (FamilyImage image : groupList) {
	    List<Object> objectList = Lists.newArrayList();
	    objectList.clear();// 清空list
	    for (FamilyImage familyImage : imgList) {
		if (image.getEventType().equals(familyImage.getEventType())) {
		    Map<String, Object> familyImageMap = Maps.newHashMap();
		    familyImageMap.put("familyImgId", familyImage.getImgId());
		    familyImageMap.put("familyImgTitle", familyImage.getTitle());
		    familyImageMap.put("familyImgUrl",
			    MessageFormat.format(Sys_Const.FAMILY_IMAGES_FILE_FOLDER_PATH, "/") + "/"
				    + familyImage.getName());
		    objectList.add(familyImageMap);
		}
	    }
	    map.put(image.getEventType(), objectList);
	    imgList.removeAll(objectList);
	}

	model.addAttribute("map", map);

	return "/user/familyImages/picMain.jsp";
    }

    // 分页查询图片
    @RequestMapping(value = "imgList")
    public String findImgList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, String type,
	    HttpServletRequest request, Model model) {
	// Map<String, Object> searchParams =
	// Servlets.getParametersStartingWith(request, "search_");
	type = StringUtil.getEncoding(type);
	Page<FamilyImage> resultList = webImageMessageServiceImpl.findAllFront(null, type, pageNumber, 20);

	model.addAttribute("resultList", resultList);

	return "/user/familyImages/picList.jsp";
    }

    // 通过id查询图片详情
    @RequestMapping(value = "imgInfo")
    public String findByImgId(Integer imgId, Model model) {
	FamilyImage img = webImageMessageServiceImpl.findByImaId(imgId);
	model.addAttribute("img", img);
	return "/user/familyImages/picInfo.jsp";

    }

}
