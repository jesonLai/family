package sys.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sys.SpringAnnotationDef;
import sys.admin.action.BaseAction;
import sys.user.front.service.imp.WebInstationSearchServiceImpl;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping(value = "/user/instation")
/***
 * 站内搜索
 */
public class InstationSearchController extends BaseAction {
    @Resource(name = SpringAnnotationDef.Web_SER_FAMILYINSTATIONSEARCH)
    WebInstationSearchServiceImpl webInstationSearchService;

    @RequestMapping(value = "/search/{searchParams}")
    public String instationSearch(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
	    HttpServletRequest request, Model model, @PathVariable("searchParams") String searchParams)
	    throws UnsupportedEncodingException {
//	searchParams = new String(searchParams.getBytes("ISO-8859-1"), "UTF-8");
	Page<Object> instationSearchPage = webInstationSearchService.instationSearch(searchParams, pageNumber, 10);
	model.addAttribute("familyData", instationSearchPage);
	model.addAttribute("searchParams", new HashMap<>());
	model.addAttribute("searchParamVal", searchParams);
	return "/user/familyInstationSearch.jsp";
    }

}
