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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sys.SpringAnnotationDef;
import sys.model.FamilyMessage;
import sys.set.service.FamilyMessageService;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.Web_SER_FAMILYMESSAGE)
@Transactional(readOnly = true)
public class WebMessageServiceImpl {
    /**
     * 留言
     */
    @Resource
    FamilyMessageService familyMessageService;

    // 分页查找留言方法
    public Page<FamilyMessage> findAllFront(final Map<String, Object> searchParams, int pageNumber, int pageSize) {
	Sort sort = new Sort(Direction.DESC, "id"); // 按id降序
	PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
	Page<FamilyMessage> productPage = familyMessageService.findAll(new Specification<FamilyMessage>() {
	    @Override
	    public Predicate toPredicate(Root<FamilyMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = cb.conjunction();
		List<Expression<Boolean>> expressions = predicate.getExpressions();

		// expressions.add(cb.equal(root.<Integer> get("flag"),typeId));

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

    // 添加留言
    @Transactional(readOnly = false)
    public void addMessage(FamilyMessage message) {
	message.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
	familyMessageService.save(message);
    }

    // 查询最新6条数据
    public List<FamilyMessage> findFamilyMessageTop6() {
	return familyMessageService.findTop6ByOrderByMessageDateDesc();
    }
}
