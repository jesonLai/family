package sys.set.service.impl;

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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.FamilyNew;
import sys.model.controller.TableRquestData;
import sys.set.service.FamilyNewsService;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_CONTRIBUTION)
@Transactional(readOnly = true)
public class FamilyContributionServiceImpl {
	private static final String FAMILYNEW = "FAMILYNEW";
	private static final String DATA_MISSING = "参数丢失！";
	private static final String INSERT_SUCCESS = "提交成功！";
	private static final String INSERT_FAIL = "提交失败！";
	private static final String EDITOR_CONTENT_NULL = "编辑内容不能为空！";
	private static final String FAMILY_FINANCIAL_NOT_IN = "未查找到功德榜信息！";
	@Resource
	FamilyNewsService familyContributionService;
	/**
	 * 添加功德榜
	 * @param map
	 */
	@Transactional
	public void addNew(Map<String, Object> map){
		if (StringUtil.isEmpty(map.get(FAMILYNEW))) {
			map.put(BaseHint.MESSAGE, DATA_MISSING);
			return;
		}
		 FamilyNew familyNew= (FamilyNew) map.get(FAMILYNEW);
		 if(StringUtil.isEmpty(familyNew.getHtmlContent())){
			 map.put(BaseHint.MESSAGE, EDITOR_CONTENT_NULL);
			return;
		}
		 if(StringUtil.isEmpty(familyNew.getEventType()))familyNew.setEventType("家族财务");
		 familyNew.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
		 familyNew.setReleseDate(new Date());
		 familyNew.setFlag(5);
		 familyNew=familyContributionService.save(familyNew);
		 if (familyNew.getFamilyNewsId() > 0) {
				map.put(BaseHint.MESSAGE, INSERT_SUCCESS);
				map.put("FAMILYNEWID", familyNew.getFamilyNewsId());
				map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			} else {
				map.put(BaseHint.MESSAGE, INSERT_FAIL);
			}
		 
	}
	/**
	 * 获取所有功德榜列表
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
	public TableRquestData getContributionPagination(TableRquestData tableRquestData, final String search) {
		int pageNumber = tableRquestData.getStart();
		int pageSize = tableRquestData.getLength();
		if (pageSize < 0)
			pageSize = familyContributionService.findByFlag(5).size();
		Page<FamilyNew> familyNewPage = familyContributionService.findAll(new Specification<FamilyNew>() {
			@Override
			public Predicate toPredicate(Root<FamilyNew> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("flag").as(int.class), 5);
				if (StringUtil.isEmpty(search)) {
					return predicate;
				} else {
					Predicate predicate1 = cb.like(root.get("eventType").as(String.class), "%" + search + "%");
					Predicate predicate2 = cb.like(root.join("pubUser").get("userAccount").as(String.class), "%" + search + "%");
					Predicate predicate3 = cb.like(root.get("title").as(String.class), "%" + search + "%");
					return cb.and(predicate,cb.or(predicate1,predicate2,predicate3));
				}
			}

		}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "releseDate")));
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		List<FamilyNew> fnl = familyNewPage.getContent();
		for (FamilyNew fn : fnl) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyContributionId", StringUtil.isEmpty(fn.getFamilyNewsId()) ? "" : fn.getFamilyNewsId());
			map.put("familyEventType", StringUtil.isEmpty(fn.getEventType()) ? "" : fn.getEventType());
			map.put("familyContributionContent", StringUtil.isEmpty(fn.getContent()) ? "" : fn.getContent());
			map.put("familyContributionTitle", StringUtil.isEmpty(fn.getTitle()) ? "" : fn.getTitle());
			map.put("familyContributionHtmlContent", StringUtil.isEmpty(fn.getHtmlContent()) ? "" : fn.getHtmlContent());
			map.put("familyContributionPushMan",  StringUtil.isEmpty(fn.getPubUser()) 
											? "" :StringUtil.isEmpty(fn.getPubUser().getUserInfo())
											? fn.getPubUser().getUserAccount().concat("/")
											:fn.getPubUser().getUserAccount().concat("/").concat(fn.getPubUser().getUserInfo().getUserName()));
			map.put("releseDate", StringUtil.isEmpty(fn.getReleseDate()) ? ""
					: UtilFactory.getSysDate().DateToString(fn.getReleseDate()));
			mapData.add(map);
		}
		tableRquestData.setRecordsFiltered(familyNewPage.getTotalElements());
		tableRquestData.setRecordsTotal(familyNewPage.getTotalElements());
		tableRquestData.setData(mapData);
		return tableRquestData;
	}
	/**
	 * 删除功德榜
	 * @param familyNewsId
	 * @throws BusinessException
	 */
	@Transactional
	public void delFamilyContribution(Integer familyNewsId) throws BusinessException{
		FamilyNew familyNews=familyContributionService.findOne(familyNewsId);
		if(familyNewsId==null||StringUtil.isEmpty(familyNews))
			throw new BusinessException(FAMILY_FINANCIAL_NOT_IN);
		familyContributionService.delete(familyNews);
	}
	/**
	 * 已有功德榜类型选择
	 * @param familyNewsId
	 * @throws BusinessException
	 */
	public List<Object> familyContributionByEventTypeColumn(){
		return familyContributionService.findAllByFlagGroupByEventTypeColumn(5);
	}
}
