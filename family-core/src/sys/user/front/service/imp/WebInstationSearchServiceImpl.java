package sys.user.front.service.imp;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import sys.SpringAnnotationDef;
import sys.set.service.InstationSearchService;
import sys.util.HtmlRegexpUtil;
import sys.util.func.UtilFactory;

/**
 * 站内搜索
 * 
 * @author lxr
 *
 */
@Repository(SpringAnnotationDef.Web_SER_FAMILYINSTATIONSEARCH)
@Transactional(readOnly = true)
public class WebInstationSearchServiceImpl {

    @Resource
    InstationSearchService instationSearchService;

    public Page<Object> instationSearch(String searchParams, int pageNumber, int pageSize) {

	Sort sort = new Sort(Direction.DESC, "createDate"); // 按id降序
	PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
	// Page<InstationSearch> InstationSearchPage =
	// instationSearchService.findAll(new Specification<InstationSearch>() {
	// @Override
	// public Predicate toPredicate(Root<InstationSearch> root,
	// CriteriaQuery<?> query, CriteriaBuilder cb) {
	// Predicate predicate = cb.conjunction();
	// List<Expression<Boolean>> expressions = predicate.getExpressions();
	// // 站内搜索
	// if (!StringUtil.isEmpty(searchParams)){
	// expressions.add(cb.or(cb.like(root.get("childId").as(String.class),
	// "" + searchParams + "")
	// ,cb.like(root.get("childTitle").as(String.class), "" + searchParams
	// + "")
	// ,cb.like(root.get("mainTitle").as(String.class), "" + searchParams +
	// "")
	// ,cb.like(root.get("content").as(String.class), "" + searchParams +
	// "")
	// ));
	//
	// }
	//
	// return predicate;
	// }
	// }, pageRequest);
	searchParams = "%" + searchParams + "%";
	EntityManagerFactory xEntityManagerFactory = UtilFactory.getSpringContext().getEntityManagerFactory();
	EntityManager xEntityManager = xEntityManagerFactory.createEntityManager();
	String countSql = "select count(1) from v_instation_search where "
		+ "child_id like :childId or child_title like :childTitle or main_title like :mainTitle "
		+ "or user_name like :userName or generation like :generation";
	String sql = "select * from v_instation_search where "
			+ "child_id like :childId or child_title like :childTitle or main_title like :mainTitle "
			+ "or user_name like :userName or generation like :generation";
	Query query = xEntityManager.createNativeQuery(countSql);
	query.setParameter("childId", searchParams);
	query.setParameter("childTitle", searchParams);
	query.setParameter("mainTitle", searchParams);
	query.setParameter("userName", searchParams);
	query.setParameter("generation", searchParams);
	BigInteger total = (BigInteger) query.getSingleResult();

	query = xEntityManager.createNativeQuery(sql);
	query.setParameter("childId", searchParams);
	query.setParameter("childTitle", searchParams);
	query.setParameter("mainTitle", searchParams);
	query.setParameter("userName", searchParams);
	query.setParameter("generation", searchParams);
	query.setFirstResult((pageNumber - 1) * pageSize);
	query.setMaxResults(pageSize);

	List<Object[]> resultList = query.getResultList();
	List<Object> resultLists = Lists.newArrayList();
	for (Object[] object : resultList) {
	    String content = new HtmlRegexpUtil().filterHtml(String.valueOf(object[7]));
	    object[7] = content != null && content.length() > 350 ? content.substring(0, 350) : content;

	    resultLists.add(object);
	}
	Page<Object> page = new PageImpl<Object>(resultLists, pageRequest, total.longValue());
	return page;
    }
}
