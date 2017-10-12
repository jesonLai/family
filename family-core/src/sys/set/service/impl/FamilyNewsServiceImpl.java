package sys.set.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.model.FamilyNew;
import sys.model.controller.FileEntity;
import sys.model.controller.TableRquestData;
import sys.set.service.FamilyNewsService;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_FAMILYNEWS)
@Transactional(readOnly = true)
/**
 * 
 * @author lxr 宗族新聞
 */
public class FamilyNewsServiceImpl {
    private static final Logger _Log = LoggerFactory.getLogger(FamilyNewsServiceImpl.class);
    private static final String FAMILYNEW = "FAMILYNEW";
    private static final String DATA_MISSING = "参数丢失！";
    private static final String INSERT_SUCCESS = "提交成功！";
    private static final String INSERT_FAIL = "提交失败！";
    private static final String EDITOR_CONTENT_NULL = "编辑内容不能为空！";
    private static final String FAMILY_NEWS_NOT_IN = "未查找到该条宗族新闻！";
    private static final String FAMILY_NAME = "宗族新闻";

    @Resource
    FamilyNewsService familyNewsService;

    /**
     * 新增宗族新闻
     * 
     * @param map
     * @throws BusinessException
     * @throws IOException
     * @throws IllegalStateException
     */
    @Transactional
    public void addNew(HttpServletRequest request, FamilyNew familyNew, String editorValue)
	    throws BusinessException, IllegalStateException, IOException {
	_Log.debug("**********新增宗族新闻**********");
	if (StringUtil.isEmpty(familyNew)) {
	    _Log.debug("The familyNew[" + familyNew + "]");
	    throw new BusinessException(DATA_MISSING);
	}
	if (StringUtil.isEmpty(editorValue)) {
	    _Log.debug("The familyNew HtmlContent[" + EDITOR_CONTENT_NULL + "]");
	    throw new BusinessException(EDITOR_CONTENT_NULL);
	}
	if (StringUtil.isEmpty(familyNew.getEventType())) {
	    _Log.debug("The familyNew eventType defalut[" + FAMILY_NAME + "]");
	    familyNew.setEventType(FAMILY_NAME);
	}

	List<FileEntity> fileEntities = FileUpload.writeFile(request, Sys_Const.FAMILY_NEWS_FILE_FOLDER_PATH,
		familyNew.getFamilyNewsImgName());
	if (!StringUtil.isEmpty(fileEntities) && fileEntities.size() != 0 && !StringUtil.isEmpty(fileEntities.get(0))
		&& !StringUtil.isEmpty(fileEntities.get(0).getName())) {
	    familyNew.setFamilyNewsImgName(fileEntities.get(0).getName());
	}

	Integer familyNewId = familyNew.getFamilyNewsId() == null ? 0 : familyNew.getFamilyNewsId();
	_Log.debug("The familyNew familyNewId[" + familyNewId + "]");
	FamilyNew fmailyNewOld = familyNewsService.findOne(familyNewId);
	_Log.debug("The fmailyNewOld[" + fmailyNewOld + "]");
	if (StringUtil.isEmpty(fmailyNewOld)) {
	    _Log.debug("The fmailyNewOld Is Null");
	    familyNew.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
	    familyNew.setReleseDate(new Date());
	    familyNew.setFlag(0);
	    familyNew = familyNewsService.save(familyNew);
	} else {
	    _Log.debug("The fmailyNewOld Is Not Null");
	    fmailyNewOld.setEventType(familyNew.getEventType());
	    fmailyNewOld.setTitle(familyNew.getTitle());
	    fmailyNewOld.setHtmlContent(familyNew.getHtmlContent());
	    fmailyNewOld.setFamilyNewsImgFolder(familyNew.getFamilyNewsImgFolder());
	    fmailyNewOld.setFamilyNewsImgName(familyNew.getFamilyNewsImgName());
	}

    }

    /**
     * 获取所有新闻列表
     * 
     * @param tableRquestData
     * @param search
     * @return
     */
    public TableRquestData getFamilyNewPagination(TableRquestData tableRquestData, final String search) {
	_Log.debug("**********获取所有新闻列表**********");
	int pageNumber = tableRquestData.getStart();
	int pageSize = tableRquestData.getLength();
	if (pageSize < 0)
	    pageSize = familyNewsService.findByFlag(0).size();
	Page<FamilyNew> familyNewPage = familyNewsService.findAll(new Specification<FamilyNew>() {
	    @Override
	    public Predicate toPredicate(Root<FamilyNew> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		Predicate predicate = cb.equal(root.get("flag").as(int.class), 0);
		if (StringUtil.isEmpty(search)) {
		    return predicate;
		} else {
		    Predicate predicate1 = cb.like(root.get("eventType").as(String.class), "%" + search + "%");
		    Predicate predicate2 = cb.like(root.join("pubUser").get("userAccount").as(String.class),
			    "%" + search + "%");
		    Predicate predicate3 = cb.like(root.get("title").as(String.class), "%" + search + "%");
		    return cb.and(predicate, cb.or(predicate1, predicate2, predicate3));
		}
	    }

	}, new PageRequest((pageNumber / pageSize), pageSize,
		new Sort(Direction.DESC, "placedTop", "placedTopDate", "releseDate")));
	List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
	List<FamilyNew> fnl = familyNewPage.getContent();

	for (FamilyNew fn : fnl) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("familyNewId", StringUtil.isEmpty(fn.getFamilyNewsId()) ? "" : fn.getFamilyNewsId());
	    map.put("familyEventType", StringUtil.isEmpty(fn.getEventType()) ? "" : fn.getEventType());
	    map.put("familyNewContent", StringUtil.isEmpty(fn.getContent()) ? "" : fn.getContent());
	    map.put("familyNewTitle", StringUtil.isEmpty(fn.getTitle()) ? "" : fn.getTitle());
	    map.put("familyNewHtmlContent", StringUtil.isEmpty(fn.getHtmlContent()) ? "" : fn.getHtmlContent());
	    map.put("familyNewImage", MessageFormat.format(Sys_Const.FAMILY_NEWS_FILE_FOLDER_PATH, "/") + "/"
		    + fn.getFamilyNewsImgName());
	    map.put("familyNewsImgName", fn.getFamilyNewsImgName());
	    map.put("familyNewPushMan", StringUtil.isEmpty(fn.getPubUser()) ? ""
		    : StringUtil.isEmpty(fn.getPubUser().getUserInfo()) ? fn.getPubUser().getUserAccount().concat("/")
			    : fn.getPubUser().getUserAccount().concat("/")
				    .concat(fn.getPubUser().getUserInfo().getUserName()));
	    map.put("releseDate", StringUtil.isEmpty(fn.getReleseDate()) ? ""
		    : UtilFactory.getSysDate().DateToString(fn.getReleseDate()));
	    mapData.add(map);
	}
	tableRquestData.setRecordsFiltered(familyNewPage.getTotalElements());
	tableRquestData.setRecordsTotal(familyNewPage.getTotalElements());
	tableRquestData.setData(mapData);
	return tableRquestData;
    }

    /**
     * 删除
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    @Transactional
    public void delFamilyNews(HttpServletRequest request, Integer familyNewsId) throws BusinessException {
	_Log.debug("**********删除宗族新闻**********");
	FamilyNew familyNews = familyNewsService.findOne(familyNewsId);
	if (familyNewsId == null || StringUtil.isEmpty(familyNews))
	    throw new BusinessException(FAMILY_NEWS_NOT_IN);
	familyNewsService.delete(familyNews);
	FileUpload.deleteFile(request, Sys_Const.FAMILY_NEWS_FILE_FOLDER_PATH, familyNews.getFamilyNewsImgName());
    }

    /**
     * 已有新闻类型选择
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    public List<Object> familyNewsByEventTypeColumn() {
	_Log.debug("**********已有新闻类型选择**********");
	return familyNewsService.findAllByFlagGroupByEventTypeColumn(0);
    }

    /**
     * 置顶
     * 
     * @throws BusinessException
     */
    @Transactional
    public void familyNewPlacedTop(FamilyNew familyNew) throws BusinessException {
	_Log.debug("**********置顶宗族新闻**********");
	if (StringUtil.isEmpty(familyNew) || StringUtil.isEmpty(familyNew.getFamilyNewsId()))
	    throw new BusinessException(DATA_MISSING);
	Integer familyNewsId = familyNew.getFamilyNewsId();
	familyNew = familyNewsService.findOne(familyNewsId);
	if (StringUtil.isEmpty(familyNew))
	    throw new BusinessException(FAMILY_NEWS_NOT_IN);
	familyNew.setPlacedTop(1);
	familyNew.setPlacedTopDate(new Date());
    }

}
