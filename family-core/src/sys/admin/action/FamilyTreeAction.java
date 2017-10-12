package sys.admin.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.FamilyNew;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.FamilyTreeServiceImpl;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/familyTree")
/**
 * 宗族族谱
 * 
 * @author lxr
 *
 */
public class FamilyTreeAction extends BaseAction {
    private static final Logger logger = Logger.getLogger(FamilyTreeAction.class.getName());
    @Resource(name = SpringAnnotationDef.SER_FAMILYTREE)
    private FamilyTreeServiceImpl familyTreeService;

    /**
     * 获取宗族族谱列表的界面
     */
    @RequestMapping("/familyTreeAllPage")
    public ModelAndView usersAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyTree/familyTree.vm");
	return mav;
    }

    /**
     * 获取宗族族谱列表及分页信息
     */
    @RequestMapping("/familyTreeList")
    @ResponseBody
    TableRquestData usersList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search) {
	return familyTreeService.getFamilyTreePagination(tableRquestData, search);
    }

    /**
     * 获取编辑页
     * 
     * @return
     */
    @RequestMapping("/familyTreeAddPage")
    public ModelAndView familyNewsAddAllPage() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("admin/menus/set/familyTree/familyTree-add.vm");
	mav.addObject("EVENTYPENAME", new JSONArray().fromObject(familyTreeService.familyTreeByEventTypeColumn()));
	return mav;
    }

    /**
     * 添加宗族族谱
     * 
     * @param familyNew
     * @param editorValue
     * @return
     */
    @RequestMapping("/COM_FAMILYTREE_SAVE")
    @ResponseBody
    Map<String, Object> addNew(@ModelAttribute("FamilyNew") FamilyNew familyNew,
	    @RequestParam("editorValue") String editorValue) {
	try {

	    familyNew.setHtmlContent(editorValue);
	    ht.put("FAMILYNEW", familyNew);
	    familyTreeService.addNew(ht);
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	ht.remove("FAMILYNEW");
	return ht;
    }

    /**
     * 删除宗族族谱
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping("/FAMILY_TREE_DEL")
    @ResponseBody
    Map<String, Object> delFamilyNew(@ModelAttribute("FamilyNew") FamilyNew familyNew) {
	try {

	    familyTreeService.delFamilyTree(familyNew.getFamilyNewsId());
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

    /***
     * 置頂宗族族谱
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping("/FAMILY_TREE_TOP")
    @ResponseBody
    Map<String, Object> topFamilyNew(@ModelAttribute("FamilyNew") FamilyNew familyNew) {
	try {

	    familyTreeService.familyTreePlacedTop(familyNew);
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
