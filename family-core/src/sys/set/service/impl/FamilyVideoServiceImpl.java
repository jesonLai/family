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

import org.apache.log4j.Logger;
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
import sys.model.FamilyVideo;
import sys.model.controller.FileEntity;
import sys.model.controller.TableRquestData;
import sys.set.service.FamilyVideoService;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_FAMILYVIDEO)
@Transactional(readOnly = true)
public class FamilyVideoServiceImpl {
    private static final Logger _log = Logger.getLogger(FamilyVideoServiceImpl.class.getName());
    private static final String FAMILYVIDEO = "FAMILYVIDEO";
    private static final String DATA_MISSING = "参数丢失！";
    private static final String INSERT_SUCCESS = "提交成功！";
    private static final String INSERT_FAIL = "提交失败！";
    private static final String VIDEO_URL_NULL = "视频网址不能为空！";
    private static final String VIDEO_URL_INFO_NULL = "获取不到视频信息,请检查视频网址！";
    private static final String VIDEO_URL_INFO_ERROR = "检索视频网址出错！";
    private static final String VIDEO_NOT_IN = "视频信息已不存在！";
    @Resource
    FamilyVideoService familyVideoService;

    /**
     * 添加家族名人
     * 
     * @param map
     * @throws BusinessException
     * @throws IOException
     * @throws IllegalStateException
     */
    @Transactional
    public void addNew(HttpServletRequest request, FamilyVideo familyVideo)
	    throws BusinessException, IllegalStateException, IOException {

	List<FileEntity> fileEntities = FileUpload.writeFile(request, Sys_Const.FAMILY_VIDEO_FILE_FOLDER_PATH,
		familyVideo.getThumbnail());
	if (!StringUtil.isEmpty(fileEntities) && fileEntities.size() != 0 && !StringUtil.isEmpty(fileEntities.get(0))
		&& !StringUtil.isEmpty(fileEntities.get(0).getName())) {
	    familyVideo.setThumbnail(fileEntities.get(0).getName());
	}
	if (StringUtil.isEmpty(familyVideo)) {
	    throw new BusinessException(DATA_MISSING);
	}
	String flashUrl = familyVideo.getFlashUrl();
	if (StringUtil.isEmpty(flashUrl)) {
	    throw new BusinessException(VIDEO_URL_NULL);
	}
	familyVideo.setLastModified(new Date());
	familyVideo.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
	familyVideo = familyVideoService.save(familyVideo);
    }

    /**
     * 获取所有视频列表
     * 
     * @param tableRquestData
     * @param search
     * @return
     */
    public TableRquestData getFamilyVideoPagination(TableRquestData tableRquestData, final String search) {
	int pageNumber = tableRquestData.getStart();
	int pageSize = tableRquestData.getLength();
	if (pageSize < 0)
	    pageSize = familyVideoService.findAll().size();
	Page<FamilyVideo> familyVideoPage = familyVideoService.findAll(new Specification<FamilyVideo>() {
	    @Override
	    public Predicate toPredicate(Root<FamilyVideo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		if (StringUtil.isEmpty(search)) {
		    return null;
		} else {
		    Predicate predicate1 = cb.equal(root.get("title").as(String.class), "%" + search + "%");
		    Predicate predicate2 = cb.equal(root.get("eventType").as(String.class), "%" + search + "%");
		    return cb.or(predicate1, predicate2);
		}
	    }

	}, new PageRequest((pageNumber / pageSize), pageSize,
		new Sort(Direction.DESC, new String[] { "lastModified" })));
	List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
	List<FamilyVideo> fvl = familyVideoPage.getContent();
	for (FamilyVideo fv : fvl) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("familyVideoId", StringUtil.isEmpty(fv.getId()) ? "" : fv.getId());
	    // map.put("familyVideoSource", StringUtil.isEmpty(fv.getSource()) ?
	    // "" : fv.getSource());
	    map.put("familyVideoFlashUrl", StringUtil.isEmpty(fv.getFlashUrl()) ? "" : fv.getFlashUrl());
	    map.put("familyVideoTitle", StringUtil.isEmpty(fv.getTitle()) ? "" : fv.getTitle());
	    map.put("familyVideoThumbnail",
		    MessageFormat.format(Sys_Const.FAMILY_VIDEO_FILE_FOLDER_PATH, "/") + "/" + fv.getThumbnail());
	    map.put("familyVideoFlashUrl", StringUtil.isEmpty(fv.getFlashUrl()) ? "" : fv.getFlashUrl());
	    map.put("familyVideoLastModified", StringUtil.isEmpty(fv.getLastModified()) ? ""
		    : UtilFactory.getSysDate().DateToString(fv.getLastModified()));
	    map.put("familyVideoUploadMan",
		    StringUtil.isEmpty(fv.getPubUser()) || StringUtil.isEmpty(fv.getPubUser().getUserAccount()) ? ""
			    : fv.getPubUser().getUserAccount());
	    map.put("familyVideoEventType", StringUtil.isEmpty(fv.getEventType()) ? "" : fv.getEventType());

	    mapData.add(map);
	}
	tableRquestData.setRecordsFiltered(familyVideoPage.getTotalElements());
	tableRquestData.setRecordsTotal(familyVideoPage.getTotalElements());
	tableRquestData.setData(mapData);
	return tableRquestData;
    }

    /**
     * 获取所有图片类型
     * 
     * @return
     */
    public List<Object> familyNewsByEventTypeColumn() {
	return familyVideoService.findByGroupByEventTypeColumn();
    }

    /**
     * 删除
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    @Transactional
    public void delFamilyVideo(HttpServletRequest request, Integer familyVideoId) throws BusinessException {
	FamilyVideo familyVideo = familyVideoService.findOne(familyVideoId);
	if (StringUtil.isEmpty(familyVideo) || familyVideo.getId() == 0)
	    throw new BusinessException(VIDEO_NOT_IN);
	familyVideoService.delete(familyVideo);
	FileUpload.deleteFile(request, Sys_Const.FAMILY_VIDEO_FILE_FOLDER_PATH, familyVideo.getThumbnail());
    }
}
