package sys.user.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.admin.action.BaseAction;
import sys.model.FamilyVideo;
import sys.user.front.service.imp.WebVideoServiceImpl;
import sys.util.StringUtil;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping(value = "/user/familyVideo")
public class VideoController extends BaseAction {

    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYVIDEO)
    WebVideoServiceImpl webVideoServiceImpl;

    @RequestMapping(value = "videoMain.html")
    public String videoMain(HttpServletRequest request, Model model) {
	Map<String, List<Object>> map = Maps.newHashMap();
	// 按类型分组
	List<FamilyVideo> groupList = webVideoServiceImpl.findAllGroupByEventType();
	// 按类型排序
	List<FamilyVideo> imgList = webVideoServiceImpl.findByOrderByEventType();

	for (FamilyVideo image : groupList) {
	    List<Object> objectList = new ArrayList<Object>();
	    objectList.clear();// 清空list
	    for (FamilyVideo familyVideo : imgList) {
		if (image.getEventType().equals(familyVideo.getEventType())) {
		    Map<String, Object> familyVideoMap = Maps.newHashMap();
		    familyVideoMap.put("familyVideoId", familyVideo.getId());
		    familyVideoMap.put("familyVideoTitle", familyVideo.getTitle());
		    familyVideoMap.put("familyVideoUrl",
			    MessageFormat.format(Sys_Const.FAMILY_VIDEO_FILE_FOLDER_PATH, "/") + "/"
				    + familyVideo.getThumbnail());
		    objectList.add(familyVideoMap);
		}
	    }

	    map.put(image.getEventType(), objectList);
	    imgList.removeAll(objectList);
	}

	model.addAttribute("map", map);
	return "/user/familyVideo/videoMain.jsp";
    }

    // 查询全部视频
    @RequestMapping(value = "videoList.html")
    public String findVideoList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, String type,
	    HttpServletRequest request, Model model) {
	Page<FamilyVideo> resultList = webVideoServiceImpl.findAllFront(null, StringUtil.getEncoding(type), pageNumber,
		20);
	model.addAttribute("resultList", resultList);
	model.addAttribute("type", StringUtil.getEncoding(type));

	return "/user/familyVideo/viewList.jsp";
    }

    // 通过id查询视频详情
    @RequestMapping(value = "videoDetail/{videoId}")
    public String findByVideoId(@PathVariable("videoId") Integer videoId, Model model) {
	FamilyVideo video = webVideoServiceImpl.findByVideoId(videoId);
	model.addAttribute("video", video);
	return "/user/familyVideo/videoDetal.jsp";

    }

}
