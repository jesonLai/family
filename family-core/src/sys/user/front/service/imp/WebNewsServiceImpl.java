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
import sys.model.FamilyNew;
import sys.set.service.FamilyNewsService;

@Repository(SpringAnnotationDef.Web_SER_FAMILYNEWS)
@Transactional(readOnly = true)
public class WebNewsServiceImpl {

    /***
     * 新闻
     */

    @Resource
    FamilyNewsService familyNewsService;

    // 分页查找新闻、文化、名人、功德、家谱方法
    public Page<FamilyNew> findAllFront(final Map<String, Object> searchParams, int pageNumber, int pageSize,
	    final Integer typeId, final String eventType) {
	Sort sort = new Sort(Direction.DESC, "releseDate"); // 按发布时间查询新闻信息
	PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
	Page<FamilyNew> productPage = familyNewsService.findAll(new Specification<FamilyNew>() {
	    @Override
	    public Predicate toPredicate(Root<FamilyNew> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = cb.conjunction();
		List<Expression<Boolean>> expressions = predicate.getExpressions();

		// 0=家族新闻,1=家族文化,2=家族名人,3=捐赠功德榜 4= 家谱
		expressions.add(cb.equal(root.<Integer> get("flag"), typeId));

		// 栏目
		if (eventType != null)
		    expressions.add(cb.equal(root.<String> get("eventType"), eventType));
		// 新闻标题
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

    // 通过id查询新闻、文化、名人、功德、家谱详细
    public FamilyNew findByNewsId(Integer newsId) {
	return familyNewsService.findOne(newsId);
    }

    // 按类型,栏目排序查询
    public List<FamilyNew> findByFlagOrderByEventType(Integer flag) {
	return familyNewsService.findByFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(flag);
    }

    // 宗族族谱 标题搜索方法
    public List<FamilyNew> findByTitleLikeAndFlagOrderByEventType(String title, Integer flag) {
	return familyNewsService
		.findByTitleLikeAndFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc("%" + title + "%", flag);
    }

    // 按类型,栏目分组
    public List<FamilyNew> findAllByFlagGroupByEventType(Integer flag) {
	return familyNewsService.findAllByFlagGroupByEventType(flag);
    }

    // 查询最新4条数据
    public List<FamilyNew> findFamilyNewTop4(int flag) {
	return familyNewsService.findTop4ByFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(flag);
    }

    // 查询最新8条数据
    public List<FamilyNew> findFamilyNewTop8(int flag) {
	return familyNewsService.findTop8ByFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(flag);
    }

    // 查询最新6条新闻图片数据
    public List<Map<String, Object>> findFamilyNewpImgTop8(int flag) {
	List<Map<String, Object>> lists = Lists.newArrayList();
	List<FamilyNew> list = familyNewsService
		.findTop8ByFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(flag);
	for (FamilyNew entity : list) {
	    Map<String, Object> map = Maps.newHashMap();
	    map.put("thumbnailUrl", MessageFormat.format(Sys_Const.FAMILY_NEWS_FILE_FOLDER_PATH, "/") + "/"
		    + entity.getFamilyNewsImgName());
	    map.put("familyNewsId", entity.getFamilyNewsId());
	    map.put("title", entity.getTitle());
	    lists.add(map);
	}
	return lists;
    }

}
