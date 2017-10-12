package sys.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.model.FamilyNew;
import sys.model.UserInfo;
import sys.user.front.service.imp.WebNewsServiceImpl;
import sys.user.front.service.imp.WebUserInfoServiceImpl;
import sys.util.StringUtil;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping(value = "/user")
/***
 * 家族新闻
 */
public class NewsController extends BaseAction {
    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYNEWS)
    private WebNewsServiceImpl webNewsServiceImpl;
    @Resource(name = SpringAnnotationDef.Web_SER_USERSINFO)
    private WebUserInfoServiceImpl webUserInfoServiceImpl;

    @RequestMapping(value = "/news/newsMain/{typeId}.html")
    public String videoMain(@PathVariable(value = "typeId") int typeId, HttpServletRequest request, Model model) {
	Map<String, List<FamilyNew>> map = new HashMap<>();
	// 按类型,栏目分组
	List<FamilyNew> groupList = webNewsServiceImpl.findAllByFlagGroupByEventType(typeId);
	// 按类型,栏目排序查询
	List<FamilyNew> imgList = webNewsServiceImpl.findByFlagOrderByEventType(typeId);

	for (FamilyNew image : groupList) {
	    List<FamilyNew> list = new ArrayList<FamilyNew>();
	    list.clear();// 清空list

	    for (FamilyNew familyImage : imgList) {
		if (image.getEventType().equals(familyImage.getEventType()))
		    list.add(familyImage);
	    }

	    map.put(image.getEventType(), list);
	    imgList.removeAll(list);
	}

	model.addAttribute("map", map);
	String view = "/user/familyTree/jiapuMain.jsp";
	if (typeId == 0)
	    view = "/user/familyNews/newsMain.jsp";
	if (typeId == 1)
	    view = "/user/familyCulture/wenhuaMain.jsp";
	if (typeId == 2)
	    view = "/user/familyCelebrity/mingrenMain.jsp";
	if (typeId == 3)
	    view = "/user/familyFinancial/familymoneyMain.jsp";
	return view;
    }

    // 宗族族谱 标题搜索方法
    @RequestMapping(value = "/news/newsMain/title", method = RequestMethod.POST)
    public String videoMainTitle(int ht_typeId, String ht_title, HttpServletRequest request, Model model) {

	Map<String, List<FamilyNew>> map = new HashMap<>();

	// 按类型,栏目分组
	List<FamilyNew> groupList = webNewsServiceImpl.findByTitleLikeAndFlagOrderByEventType(ht_title, ht_typeId);
	for (FamilyNew image : groupList) {

	    if (!map.isEmpty()) {
		if (map.containsKey(image.getEventType())) {
		    map.get(image.getEventType()).add(image);
		} else {
		    List<FamilyNew> list = new ArrayList<FamilyNew>();
		    list.clear();// 清空list
		    list.add(image);
		    map.put(image.getEventType(), list);
		}
	    } else {
		List<FamilyNew> list = new ArrayList<FamilyNew>();
		list.clear();// 清空list
		list.add(image);
		map.put(image.getEventType(), list);
	    }
	}

	model.addAttribute("map", map);
	return "/user/familyTree/jiapuMain.jsp";
    }

    // 查询全部新闻
    @RequestMapping(value = "/familyNews/newsList/{eventType}/{typeId}")
    public String findNewsList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
	    @PathVariable("eventType") String eventType, @PathVariable("typeId") Integer typeId,
	    HttpServletRequest request, Model model) {
	Page<FamilyNew> resultList = webNewsServiceImpl.findAllFront(null, pageNumber, 20, typeId,
		StringUtil.getEncoding(eventType));
	model.addAttribute("resultList", resultList);
	model.addAttribute("eventType", StringUtil.getEncoding(eventType));

	String view = "/user/familyTree/jiapuList.jsp";
	if (typeId == 0) {
	    view = "/user/familyNews/newsList.jsp";
	    model.addAttribute("mainType", "宗族新闻");
	}
	if (typeId == 1) {
	    view = "/user/familyCulture/wenhuaList.jsp";
	    model.addAttribute("mainType", "宗族文化");
	}
	if (typeId == 2) {
	    view = "/user/familyCelebrity/jiazumingren.jsp";
	    model.addAttribute("mainType", "能人贤士");
	}
	if (typeId == 3) {
	    view = "/user/familyFinancial/familymoney.jsp";
	    model.addAttribute("mainType", "宗族财务");
	}
	if (typeId == 5) {
	    view = "/user/familyContribution/gongdebang.jsp";
	    model.addAttribute("mainType", "功德榜");
	}
	return view;
    }

    // 通过新闻id查询新闻详情
    @RequestMapping(value = "/familyNews/newsInfo/{newsId}")
    public String findByNewsId(@PathVariable("newsId") Integer newsId, Model model) {
	FamilyNew news = webNewsServiceImpl.findByNewsId(newsId);
	model.addAttribute("news", news);
	return "/user/familyNews/newsInfo.jsp";

    }

    // 查询全部新闻
    @RequestMapping(value = "/news/jiapuList")
    public String jipuList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, String eventType,
	    Integer typeId, HttpServletRequest request, Model model) {
	// Page<UserInfo> resultList =
	// iUsersServiceImpl.findAllFront(pageNumber, 20, 0);
	// model.addAttribute("resultList", resultList);
	return "/user/familyTree/jiapuList.jsp";

    }

    // 家族名人
    @RequestMapping(value = "/news/mingrenList")
    public String mingrenList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, String eventType,
	    Integer typeId, HttpServletRequest request, Model model) {
	Page<UserInfo> resultList = webUserInfoServiceImpl.findAllFront(pageNumber, 20, 0);
	model.addAttribute("resultList", resultList);
	return "/user/familyCelebrity/jiazumingren.jsp";

    }

    @RequestMapping(value = "/news/mingrenInfo")
    public String mingrenInfo(Integer id, HttpServletRequest request, Model model) {
	UserInfo userInfo = webUserInfoServiceImpl.findById(id);
	model.addAttribute("userInfo", userInfo);
	return "/user/familyCelebrity/mingrenInfo.jsp";

    }

}
