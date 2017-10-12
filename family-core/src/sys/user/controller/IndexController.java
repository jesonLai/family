package sys.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.set.service.impl.AdvertisementServiceImpl;
import sys.user.front.service.imp.WebAncestralTempleServiceImpl;
import sys.user.front.service.imp.WebAnnouncementServiceImpl;
import sys.user.front.service.imp.WebImageMessageServiceImpl;
import sys.user.front.service.imp.WebMessageServiceImpl;
import sys.user.front.service.imp.WebNewsServiceImpl;
import sys.user.front.service.imp.WebUserInfoServiceImpl;
import sys.user.front.service.imp.WebVideoServiceImpl;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping(value = "/user")
public class IndexController extends BaseAction {

    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYIMAGE)
    WebImageMessageServiceImpl webImageMessageServiceImpl;
    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYVIDEO)
    WebVideoServiceImpl webVideoServiceImpl;
    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYNEWS)
    private WebNewsServiceImpl webNewsServiceImpl;
    @Resource(name = SpringAnnotationDef.Web_SER_USERSINFO)
    private WebUserInfoServiceImpl webUserInfoServiceImpl;
    @Resource(name = SpringAnnotationDef.Web_SER_ANCESTRALTEMPLE)
    private WebAncestralTempleServiceImpl webAncestralTempleServiceImpl;
    @Resource(name = SpringAnnotationDef.Web_SER_ANNOUNCEMENT)
    private WebAnnouncementServiceImpl webAnnouncementServiceImpl;
    @Resource(name = SpringAnnotationDef.SER_ADVERTISEMENT)
    AdvertisementServiceImpl advertisementService;

    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYMESSAGE)
    WebMessageServiceImpl webMessageServiceImpl;

    @RequestMapping(value = "index.html")
    public String indexFront(HttpServletRequest request, Model model) {
	// 首页图片查询最新四条记录
	model.addAttribute("imgList", webImageMessageServiceImpl.findFamilyImageTop4());
	// 首页视频最新四条
	model.addAttribute("videoList", webVideoServiceImpl.findFamilyImageTop4());

	// 资讯类 按类型id=0 宗族新闻
	model.addAttribute("newsList", webNewsServiceImpl.findFamilyNewTop8(0));
	model.addAttribute("newsListImg", webNewsServiceImpl.findFamilyNewpImgTop8(0)); // 新闻图片

	// 资讯类 按类型id=1 宗族文化
	model.addAttribute("wenhuList", webNewsServiceImpl.findFamilyNewTop8(1));
	// 留言板
	model.addAttribute("messgaeList", webMessageServiceImpl.findFamilyMessageTop6());

	// 家族名人参数：页数，条数，是否首页显示(1:显示，0：不显示)
	model.addAttribute("mingrenList", webUserInfoServiceImpl.findUserMingren(1, 1));

	// 祠堂方法
	model.addAttribute("ancestralTempleList", webAncestralTempleServiceImpl.findAncestralTempleTop5());

	// 公告方法
	model.addAttribute("announcementList", webAnnouncementServiceImpl.findAnnouncementTop6());

	// 资讯类 按类型id=4 宗族家谱
	model.addAttribute("jiapuList", webNewsServiceImpl.findFamilyNewTop4(3));
	return "user/index.jsp";
    }

}
