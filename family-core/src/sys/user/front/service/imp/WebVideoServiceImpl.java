package sys.user.front.service.imp;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.model.FamilyVideo;
import sys.set.service.FamilyVideoService;

@Repository(SpringAnnotationDef.Web_SER_FAMILYVIDEO)
@Transactional(readOnly = true)
public class WebVideoServiceImpl {

    /**
     * 视频
     */

    @Resource
    FamilyVideoService familyVideoService;

    // 分页查找视频方法
    public Page<FamilyVideo> findAllFront(final Map<String, Object> searchParams, final String type, int pageNumber,
	    int pageSize) {
	Sort sort = new Sort(Direction.DESC, "id"); // 按发布时间查询新闻信息
	PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
	Page<FamilyVideo> productPage = familyVideoService.findAll(new Specification<FamilyVideo>() {
	    @Override
	    public Predicate toPredicate(Root<FamilyVideo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = cb.conjunction();
		List<Expression<Boolean>> expressions = predicate.getExpressions();
		if (type != null)
		    expressions.add(cb.equal(root.<String> get("eventType"), type));

		// 视频标题
		if (searchParams != null) {
		    if (searchParams.get("GTE_title") != null && !searchParams.get("GTE_title").equals("")) {
			expressions.add(cb.like(root.<String> get("title"), "%" + searchParams.get("GTE_title") + "%"));
		    }
		}
		return predicate;
	    }
	}, pageRequest);
	return productPage;
    }

    // 通过id查询视频详细
    public FamilyVideo findByVideoId(Integer newsId) {
	return familyVideoService.findOne(newsId);
    }

    // 通过类型降序
    public List<FamilyVideo> findByOrderByEventType() {
	return familyVideoService.findAllOrderByLastModifiedDesc();
    }

    // 通过类型分组
    public List<FamilyVideo> findAllGroupByEventType() {
	return familyVideoService.findByGroupByEventType();
    }

    // 查询最新4条数据
    public List<Object> findFamilyImageTop4() {
	List<Object> objectList = Lists.newArrayList();
	List<FamilyVideo> familyVideoList = familyVideoService.findFamilyVideoTop4();
	for (FamilyVideo familyVideo : familyVideoList) {
	    Map<String, Object> familyVideoMap = Maps.newHashMap();
	    familyVideoMap.put("videoId", familyVideo.getId());
	    familyVideoMap.put("videoImg", MessageFormat.format(Sys_Const.FAMILY_VIDEO_FILE_FOLDER_PATH, "/") + "/"
		    + familyVideo.getThumbnail());
	    familyVideoMap.put("videoTitle", familyVideo.getTitle());
	    objectList.add(familyVideoMap);
	}
	return objectList;
    }

}
