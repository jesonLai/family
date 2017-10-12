package sys.user.front.service.imp;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.SpringAnnotationDef;
import sys.model.FamilyImage;
import sys.set.service.FamilyImageService;

@Service(SpringAnnotationDef.Web_SER_FAMILYIMAGE)
@Transactional(readOnly = true)
public class WebImageMessageServiceImpl {
    /**
     * 图片
     */

    @Resource
    FamilyImageService familyImageService;

    // 分页查找图片方法
    public Page<FamilyImage> findAllFront(final Map<String, Object> searchParams, final String type, int pageNumber,
	    int pageSize) {
	Sort sort = new Sort(Direction.DESC, "imgId"); // 按id降序
	PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
	Page<FamilyImage> productPage = familyImageService.findAll(new Specification<FamilyImage>() {
	    @Override
	    public Predicate toPredicate(Root<FamilyImage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = cb.conjunction();
		List<Expression<Boolean>> expressions = predicate.getExpressions();
		if (type != null) {
		    expressions.add(cb.equal(root.<String> get("eventType"), type));
		}
		// 标题查询
		/*
		 * if (searchParams != null) { if (searchParams.get("GTE_title")
		 * != null && !searchParams.get("GTE_title").equals("")) {
		 * expressions.add(cb.like(root.<String>
		 * get("title"),"%"+searchParams.get("GTE_title")+"%")); } }
		 */
		return predicate;
	    }
	}, pageRequest);
	return productPage;
    }

    // 通过id查询图片详细
    public FamilyImage findByImaId(Integer imgId) {
	return familyImageService.findOne(imgId);
    }

    // 通过类型降序
    public List<FamilyImage> findByOrderByEventType() {
	return familyImageService.findByOrderByEventType();
    }

    // 通过类型分组
    public List<FamilyImage> findAllGroupByEventType() {
	return familyImageService.findAllGroupByEventType();
    }

    // 查询最新4条数据
    public List<FamilyImage> findFamilyImageTop4() {
	return familyImageService.findTop4ByOrderByLastModified();
    }

}
