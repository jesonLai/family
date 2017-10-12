package sys.user.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.model.UserInfo;
import sys.user.front.service.imp.RelationshipServiceImpl;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Scope(value=SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller  
@RequestMapping(value = "/")
public class RelationshipController extends BaseAction{
	private static final Logger logger = Logger.getLogger(RelationshipController.class.getName());
	@Resource(name=SpringAnnotationDef.SER_RELATIONSHIP)
	RelationshipServiceImpl relationshipService;
	
	/**
	 * 宗族关系
	 */
	@RequestMapping("user/relationship.html")
	public ModelAndView registerPage(ModelAndView mdv,@ModelAttribute("UserInfo")UserInfo userChild){
		List<Map<String,Object>> relationship=relationshipService.getRelationshipService(userChild);
		JSONArray jSONArray=new JSONArray();
		mdv.addObject("RELATIONSHIP",StringUtil.isEmpty(relationship)?jSONArray:jSONArray.fromObject(relationship));
		mdv.addObject("RSUSERINFO",UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo());
		mdv.setViewName("user/personal/relationship/relationship.jsp");
		return mdv;
	}
	/**
	 * 宗族关系数据
	 */
	@RequestMapping("user/relationshipData")
	@ResponseBody Map<String,Object> relationshipData(@ModelAttribute("UserInfo")UserInfo userChild){
		List<Map<String,Object>> relationship=relationshipService.getRelationshipService(userChild);
		JSONArray jSONArray=new JSONArray();
		ht.put("RELATIONSHIP",StringUtil.isEmpty(relationship)?jSONArray:jSONArray.fromObject(relationship));
		return ht;
	}
	
	/**
	 * 判断成员是否有关系
	 */
	@RequestMapping(value="user/isReletionship",method=RequestMethod.POST)
	@ResponseBody boolean isReletionship(@ModelAttribute("UserInfo")UserInfo userChild){
		return relationshipService.isFamilyRelationship(userChild);
	}
	
	/**
	 * 查询宗族成员信息
	 * @param q
	 * @param pageLimit
	 * @return
	 */
	@RequestMapping(value="user/userName",method=RequestMethod.POST)
	@ResponseBody List<Map<String,Object>> getFatherNameInfo(@RequestParam(value="q",defaultValue="")String q,@RequestParam(value="page_limit",defaultValue="")int pageLimit){
		
		return relationshipService.getInitUserName(q,pageLimit);
	}
	
	
	
	@RequestMapping("user/cus-relationship.html")
	public ModelAndView registerPage2(ModelAndView mdv,@ModelAttribute("UserInfo")UserInfo userChild){
		List<Map<String,Object>> relationship=relationshipService.getRelationshipService(userChild);
		JSONArray jSONArray=new JSONArray();
		mdv.addObject("RELATIONSHIP",StringUtil.isEmpty(relationship)?jSONArray:jSONArray.fromObject(relationship));
		mdv.setViewName("user/cus-relationship.jsp");
		return mdv;
	}
	
	
}
