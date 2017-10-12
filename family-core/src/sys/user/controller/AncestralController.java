package sys.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.model.AncestralTemple;
import sys.set.service.impl.AncestralTempleServiceImpl;
import sys.user.front.service.imp.WebAncestralTempleServiceImpl;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping(value = "/user/ancestral")
public class AncestralController extends BaseAction{
	
	@Resource(name=SpringAnnotationDef.Web_SER_ANCESTRALTEMPLE)
	private WebAncestralTempleServiceImpl webAncestralTempleServiceImpl;
	
	
	
	//查询祠堂分页
	@RequestMapping(value = "ancestralList")
	public String ancestralList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,String type, HttpServletRequest request,Model model){
		Page<AncestralTemple> resultList  = webAncestralTempleServiceImpl.findAllFront(pageNumber, 20);
		model.addAttribute("resultList", resultList);
		return "/user/ancestralList.jsp";
	}
		
		
	//查询祠堂简介
	@RequestMapping(value = "ancestralDetail")
	public String ancestralDetail(Integer id,Model model){
		AncestralTemple ancestralTemple = webAncestralTempleServiceImpl.findById(id);
		model.addAttribute("ancestral", ancestralTemple);
		return "/user/ancestralDetail.jsp";
		
	}
	

}
