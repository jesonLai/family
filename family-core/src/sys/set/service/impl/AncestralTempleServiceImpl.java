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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.model.AncestralTemple;
import sys.model.controller.FileEntity;
import sys.model.controller.TableRquestData;
import sys.set.service.AncestralTempleService;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_ANCESTRALTEMPLE)
@Transactional(readOnly = true)
public class AncestralTempleServiceImpl {
    private static final String DATA_MISSING = "参数丢失！";
    private static final String INSERT_SUCCESS = "提交成功！";
    private static final String INSERT_FAIL = "提交失败！";
    private static final String EDITOR_CONTENT_NULL = "编辑内容不能为空！";
    private static final String EDITOR_IMG_NULL = "请上传封面图片！";
    private static final String EDITOR_TITLE_NULL = "标题不能为空！";
    private static final String FAMILY_ANCESTRALTEMPL_NOT_IN = "未查找到祠堂信息！";

    @Resource
    AncestralTempleService ancestralTempleService;

    /**
     * 添加祠堂
     * 
     * @param map
     * @throws IOException
     * @throws IllegalStateException
     */
    @Transactional
    public void addNew(Map<String, Object> map, AncestralTemple ancestralTemple, HttpServletRequest request)
	    throws IllegalStateException, IOException {
	List<FileEntity> fileEntities = FileUpload.writeFile(request, Sys_Const.FAMILY_ANCESTRALTEMPLE_FILE_FOLDER_PATH,
		ancestralTemple.getAtImg());
	if (!StringUtil.isEmpty(fileEntities) && fileEntities.size() != 0 && !StringUtil.isEmpty(fileEntities.get(0))
		&& !StringUtil.isEmpty(fileEntities.get(0).getName()))
	    ancestralTemple.setAtImg(fileEntities.get(0).getName());
	if (StringUtil.isEmpty(ancestralTemple)) {
	    map.put(BaseHint.MESSAGE, DATA_MISSING);
	    return;
	}
	if (StringUtil.isEmpty(ancestralTemple.getAtImg())) {
	    map.put(BaseHint.MESSAGE, EDITOR_IMG_NULL);
	    return;
	}
	if (StringUtil.isEmpty(ancestralTemple.getAtTitle())) {
	    map.put(BaseHint.MESSAGE, EDITOR_TITLE_NULL);
	    return;
	}
	if (StringUtil.isEmpty(ancestralTemple.getAtContent())) {
	    map.put(BaseHint.MESSAGE, EDITOR_CONTENT_NULL);
	    return;
	}
	ancestralTemple.setCreateDate(new Date());
	ancestralTemple.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
	ancestralTemple = ancestralTempleService.save(ancestralTemple);
	if (ancestralTemple.getAtId() != null && ancestralTemple.getAtId() > 0) {
	    map.put(BaseHint.MESSAGE, INSERT_SUCCESS);
	    map.put("ANCESTRALTEMPLEID", ancestralTemple.getAtId());
	    map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	} else {
	    map.put(BaseHint.MESSAGE, INSERT_FAIL);
	}

    }

    /**
     * 获取所有祠堂列表
     * 
     * @param tableRquestData
     * @param search
     * @return
     */
    public TableRquestData getAncestralTemplePagination(TableRquestData tableRquestData, final String search) {
	int pageNumber = tableRquestData.getStart();
	int pageSize = tableRquestData.getLength();
	if (pageSize < 0)
	    pageSize = ancestralTempleService.findAll().size();
	Page<AncestralTemple> familyAncestralTemplePage = ancestralTempleService
		.findAll(new Specification<AncestralTemple>() {
		    @Override
		    public Predicate toPredicate(Root<AncestralTemple> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
			// if (StringUtil.isEmpty(search)) {
			return null;
			// } else {
			//
			// Predicate predicate1 =
			// cb.like(root.get("eventType").as(String.class), "%" +
			// search + "%");
			// Predicate predicate2 =
			// cb.like(root.join("pubUser").get("userAccount").as(String.class),
			// "%" + search + "%");
			// return
			// cb.equal(predicate,cb.or(predicate1,predicate2));
			// }

		    }

		}, new PageRequest((pageNumber / pageSize), pageSize,
			new Sort(Direction.DESC, "placedTop", "placedTopDate", "createDate")));
	List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
	List<AncestralTemple> atl = familyAncestralTemplePage.getContent();
	for (AncestralTemple at : atl) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    String ImgSrc = StringUtil.isEmpty(at.getAtFolder()) || StringUtil.isEmpty(at.getAtImg()) ? ""
		    : MessageFormat.format(at.getAtFolder(), "/") + "/" + at.getAtImg();
	    map.put("ancestralTempleId", StringUtil.isEmpty(at.getAtId()) ? "" : at.getAtId());
	    map.put("ancestralTempleTitle", StringUtil.isEmpty(at.getAtTitle()) ? "" : at.getAtTitle());
	    map.put("ancestralTempleContent", StringUtil.isEmpty(at.getAtContent()) ? "" : at.getAtContent());
	    map.put("ancestralTempleImg", StringUtil.isEmpty(at.getAtImg()) ? "" : at.getAtImg());
	    map.put("ancestralTempleSrc",
		    MessageFormat.format(Sys_Const.FAMILY_ANCESTRALTEMPLE_FILE_FOLDER_PATH, "/") + "/" + at.getAtImg());
	    map.put("ancestralTemplePushMan", StringUtil.isEmpty(at.getPubUser()) ? ""
		    : StringUtil.isEmpty(at.getPubUser().getUserInfo()) ? at.getPubUser().getUserAccount().concat("/")
			    : at.getPubUser().getUserAccount().concat("/")
				    .concat(at.getPubUser().getUserInfo().getUserName()));
	    map.put("ancestralTempleCreateDate", StringUtil.isEmpty(at.getCreateDate()) ? ""
		    : UtilFactory.getSysDate().DateToString(at.getCreateDate()));
	    mapData.add(map);
	}
	tableRquestData.setRecordsFiltered(familyAncestralTemplePage.getTotalElements());
	tableRquestData.setRecordsTotal(familyAncestralTemplePage.getTotalElements());
	tableRquestData.setData(mapData);
	return tableRquestData;
    }

    /**
     * 删除祠堂
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    @Transactional
    public void delAncestralTemple(Integer atId, HttpServletRequest request) throws BusinessException {
	AncestralTemple ancestralTemple = ancestralTempleService.findOne(atId);
	if (atId == null || StringUtil.isEmpty(atId))
	    throw new BusinessException(FAMILY_ANCESTRALTEMPL_NOT_IN);
	ancestralTempleService.delete(ancestralTemple);
	FileUpload.deleteFile(request, Sys_Const.FAMILY_ANCESTRALTEMPLE_FILE_FOLDER_PATH, ancestralTemple.getAtImg());
    }

    /**
     * 置顶
     * 
     * @throws BusinessException
     */
    @Transactional
    public void familyAncestralTemplePlacedTop(AncestralTemple ancestralTemple) throws BusinessException {
	if (StringUtil.isEmpty(ancestralTemple) || StringUtil.isEmpty(ancestralTemple.getAtId()))
	    throw new BusinessException(DATA_MISSING);
	Integer atId = ancestralTemple.getAtId();
	ancestralTemple = ancestralTempleService.findOne(atId);
	if (StringUtil.isEmpty(ancestralTemple))
	    throw new BusinessException(FAMILY_ANCESTRALTEMPL_NOT_IN);
	ancestralTemple.setPlacedTop(1);
	ancestralTemple.setPlacedTopDate(new Date());
    }
}
