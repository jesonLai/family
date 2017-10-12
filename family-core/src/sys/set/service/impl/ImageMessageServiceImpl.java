package sys.set.service.impl;

import java.io.File;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.model.FamilyImage;
import sys.model.controller.FileEntity;
import sys.model.controller.TableRquestData;
/**
 * 图片管理
 * @author lxr
 *
 */
import sys.set.service.FamilyImageService;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Service(SpringAnnotationDef.SER_FAMILYIMAGE)
@Transactional(readOnly = true)
public class ImageMessageServiceImpl {
    private static final String FAMILYIMAGE = "FamilyImage";
    private static final String DATA_MISSING = "参数丢失!";
    private static final String DATA_ILLEGAL = "非法参数!";
    private static final String EDITOR_CONTENT_NULL = "编辑内容不能为空！";
    private static final String EDITOR_IMG_NULL = "请上传封面图片！";
    private static final String EDITOR_TITLE_NULL = "标题不能为空！";
    private static final String INSERT_SUCCESS = "提交成功!";
    private static final String INSERT_FAIL = "提交失败!";
    @Resource
    FamilyImageService familyImageService;

    /**
     * 保存上传图片的信息
     * 
     * @throws IOException
     * @throws IllegalStateException
     */

    @Transactional
    public void addImage(Map<String, Object> map, FamilyImage familyImage, HttpServletRequest request)
	    throws IllegalStateException, IOException {
	List<FileEntity> fileEntities = FileUpload.writeFile(request, Sys_Const.FAMILY_IMAGES_FILE_FOLDER_PATH,
		familyImage.getName());
	if (!StringUtil.isEmpty(fileEntities) && fileEntities.size() != 0 && !StringUtil.isEmpty(fileEntities.get(0))
		&& !StringUtil.isEmpty(fileEntities.get(0).getName()))
	    familyImage.setName(fileEntities.get(0).getName());
	if (StringUtil.isEmpty(familyImage)) {
	    map.put(BaseHint.MESSAGE, DATA_MISSING);
	    return;
	}
	if (StringUtil.isEmpty(familyImage.getName())) {
	    map.put(BaseHint.MESSAGE, EDITOR_IMG_NULL);
	    return;
	}
	if (StringUtil.isEmpty(familyImage.getTitle())) {
	    map.put(BaseHint.MESSAGE, EDITOR_TITLE_NULL);
	    return;
	}
	if (StringUtil.isEmpty(familyImage.getHtmlContent())) {
	    map.put(BaseHint.MESSAGE, EDITOR_CONTENT_NULL);
	    return;
	}
	;
	familyImage.setLastModified(new Date());
	familyImage.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
	if (StringUtil.isEmpty(familyImage.getEventType())) {
	    familyImage.setEventType("家族图片");
	}
	familyImage = familyImageService.save(familyImage);
	Integer id = familyImage.getImgId();
	if (id != null && familyImage.getImgId() > 0) {
	    map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    map.put(BaseHint.MESSAGE, INSERT_SUCCESS);
	    map.put("FAMILYIMAGEID", id);
	} else {
	    map.put(BaseHint.MESSAGE, INSERT_FAIL);
	}
	map.remove(FAMILYIMAGE);
	familyImage = new FamilyImage();
    }

    /**
     * 获取所有图片列表
     * 
     * @param tableRquestData
     * @param search
     * @return
     */
    public TableRquestData getFamilyImagePagination(TableRquestData tableRquestData, final String search) {
	int pageNumber = tableRquestData.getStart();
	int pageSize = tableRquestData.getLength();
	if (pageSize < 0)
	    pageSize = familyImageService.findAll().size();
	Page<FamilyImage> familyNewPage = familyImageService.findAll(new Specification<FamilyImage>() {
	    @Override
	    public Predicate toPredicate(Root<FamilyImage> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		if (StringUtil.isEmpty(search)) {
		    return null;
		} else {
		    Predicate predicate1 = cb.equal(root.get("title").as(String.class), "%" + search + "%");
		    Predicate predicate2 = cb.equal(root.get("eventType").as(String.class), "%" + search + "%");
		    return cb.or(predicate1, predicate2);
		}
	    }

	}, new PageRequest((pageNumber / pageSize), pageSize,
		new Sort(Direction.DESC, new String[] { "eventType", "lastModified" })));
	List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
	List<FamilyImage> fnl = familyNewPage.getContent();
	for (FamilyImage fi : fnl) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("imgId", StringUtil.isEmpty(fi.getImgId()) ? "" : fi.getImgId());
	    map.put("thumbnailUrl",
		    MessageFormat.format(Sys_Const.FAMILY_IMAGES_FILE_FOLDER_PATH, "/") + "/" + fi.getName());
	    map.put("name", StringUtil.isEmpty(fi.getName()) ? "" : fi.getName());
	    map.put("title", StringUtil.isEmpty(fi.getTitle()) ? "" : fi.getTitle());
	    map.put("htmlContent", StringUtil.isEmpty(fi.getHtmlContent()) ? "" : fi.getHtmlContent());
	    map.put("eventType", StringUtil.isEmpty(fi.getEventType()) ? "" : fi.getEventType());
	    map.put("lastModified", StringUtil.isEmpty(fi.getLastModified()) ? ""
		    : UtilFactory.getSysDate().DateToString(fi.getLastModified()));
	    map.put("uploadMan",
		    StringUtil.isEmpty(fi.getPubUser()) || StringUtil.isEmpty(fi.getPubUser().getUserAccount()) ? ""
			    : fi.getPubUser().getUserAccount());

	    mapData.add(map);
	}
	tableRquestData.setRecordsFiltered(familyNewPage.getTotalElements());
	tableRquestData.setRecordsTotal(familyNewPage.getTotalElements());
	tableRquestData.setData(mapData);
	return tableRquestData;
    }

    /**
     * 删除上传图片
     */
    @Transactional
    public void delImgUpload(HttpServletRequest request, FamilyImage familyImage) throws BusinessException {
	Integer imgId = familyImage.getImgId();
	if (imgId == null)
	    throw new BusinessException(DATA_MISSING);
	familyImage = familyImageService.findOne(imgId);
	if (StringUtil.isEmpty(familyImage) || StringUtil.isEmpty(familyImage.getImgId()))
	    throw new BusinessException(DATA_ILLEGAL);
	StringBuffer sb = new StringBuffer("admin");
	String localFile = sb.append(File.separator).append("fileupload").append(File.separator)
		.append(familyImage.getName()).toString();
	File file = new File(localFile);
	if (file.exists())
	    file.delete();
	familyImageService.delete(familyImage);
	FileUpload.deleteFile(request, Sys_Const.FAMILY_IMAGES_FILE_FOLDER_PATH, familyImage.getName());

    }

    /**
     * 获取所有图片类型
     * 
     * @return
     */
    public List<Object> familyNewsByEventTypeColumn() {
	return familyImageService.findAllGroupByEventTypeColumn();
    }

}
