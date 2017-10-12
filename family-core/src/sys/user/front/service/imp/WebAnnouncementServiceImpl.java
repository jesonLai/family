package sys.user.front.service.imp;

import java.util.List;

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
import sys.model.Announcement;
import sys.set.service.AnnouncementService;

@Repository(SpringAnnotationDef.Web_SER_ANNOUNCEMENT)
@Transactional(readOnly = true)
public class WebAnnouncementServiceImpl {
	
	/***
	 * 
	 * 公告
	 */
	
	@Resource
	AnnouncementService announcementService;
	
	// 分页查找公告方法  
		public Page<Announcement> findAllFront(int pageNumber, int pageSize) {
			Sort sort = new Sort(Direction.DESC, "createDate"); //按发布时间查询新闻信息
			PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
			Page<Announcement> productPage = announcementService.findAll(new Specification<Announcement>() {
				@Override
				public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					List<Expression<Boolean>> expressions = predicate.getExpressions();
					
					//expressions.add(cb.equal(root.<Integer> get("flag"),typeId));
					
					return predicate;
				}
			}, pageRequest);
			return productPage;
		}
		
		//按id查询
		public Announcement findById(Integer id){
			return announcementService.findOne(id);
		}
		
		//查询最新6条数据
		public List<Announcement> findAnnouncementTop6(){
			return announcementService.findTop6ByOrderByPlacedTopDescPlacedTopDateDescCreateDateDesc();
		}
	

}
