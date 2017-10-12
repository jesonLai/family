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
import sys.model.AncestralTemple;
import sys.set.service.AncestralTempleService;

@Repository(SpringAnnotationDef.Web_SER_ANCESTRALTEMPLE)
@Transactional(readOnly = true)
public class WebAncestralTempleServiceImpl {
    /***
     * 祠堂方法
     */

    @Resource
    AncestralTempleService ancestralTempleService;

    // 分页查找祠堂方法
    public Page<AncestralTemple> findAllFront(int pageNumber, int pageSize) {
	Sort sort = new Sort(Direction.DESC, "createDate"); // 按发布时间查询新闻信息
	PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
	Page<AncestralTemple> productPage = ancestralTempleService.findAll(new Specification<AncestralTemple>() {
	    @Override
	    public Predicate toPredicate(Root<AncestralTemple> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = cb.conjunction();
		List<Expression<Boolean>> expressions = predicate.getExpressions();

		// expressions.add(cb.equal(root.<Integer> get("flag"),typeId));
		return predicate;
	    }
	}, pageRequest);
	return productPage;
    }

    // 按id查询
    public AncestralTemple findById(Integer id) {
	return ancestralTempleService.findOne(id);
    }

    // 查询最新5条数据
    public List<Object> findAncestralTempleTop5() {
	List<Object> objectList = Lists.newArrayList();
	List<AncestralTemple> ancestralTempleList = ancestralTempleService
		.findTop5ByOrderByPlacedTopDescPlacedTopDateDescCreateDateDesc();
	for (AncestralTemple ancestralTemple : ancestralTempleList) {
	    Map<String, Object> ancestralTempleMap = Maps.newHashMap();
	    ancestralTempleMap.put("ancestralTempleId", ancestralTemple.getAtId());
	    ancestralTempleMap.put("ancestralTempleImg",
		    MessageFormat.format(Sys_Const.FAMILY_ANCESTRALTEMPLE_FILE_FOLDER_PATH, "/") + "/"
			    + ancestralTemple.getAtImg());
	    ancestralTempleMap.put("ancestralTempleTitle", ancestralTemple.getAtTitle());
	    objectList.add(ancestralTempleMap);
	}
	return objectList;
    }

}
