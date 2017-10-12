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

@Repository(SpringAnnotationDef.SER_FAMILYCULTURE)
@Transactional(readOnly = true)
public class FamilyCultureServiceImpl {
	private static final String FAMILYNEW = "FAMILYNEW";
	private static final String DATA_MISSING = "参数丢失！";
	private static final String INSERT_SUCCESS = "提交成功！";
	private static final String INSERT_FAIL = "提交失败！";
	private static final String EDITOR_CONTENT_NULL = "编辑内容不能为空！";
	private static final String FAMILY_CULTURE_NOT_IN = "未查找到文化！";
	@Resource
	FamilyNewsService familyNewsService;
	/**
	 * 添加家族文化
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
		 if(StringUtil.isEmpty(familyNew.getEventType()))familyNew.setEventType("家族文化");
		 familyNew.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
		 familyNew.setReleseDate(new Date());
		 familyNew.setFlag(1);
		 
		 familyNew=familyNewsService.save(familyNew);
		 if (familyNew.getFamilyNewsId() > 0) {
				map.put(BaseHint.MESSAGE, INSERT_SUCCESS);
				map.put("FAMILYNEWID", familyNew.getFamilyNewsId());
				map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			} else {
				map.put(BaseHint.MESSAGE, INSERT_FAIL);
			}
		 
	}
	/**
	 * 获取所有家族文化列表
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
	public TableRquestData getFamilyCulturePagination(TableRquestData tableRquestData, final String search) {
		int pageNumber = tableRquestData.getStart();
		int pageSize = tableRquestData.getLength();
		if (pageSize < 0)
			pageSize = familyNewsService.findByFlag(1).size();
		Page<FamilyNew> familyNewPage = familyNewsService.findAll(new Specification<FamilyNew>() {
			@Override
			public Predicate toPredicate(Root<FamilyNew> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("flag").as(int.class), 1);
				if (StringUtil.isEmpty(search)) {
					return predicate;
				} else {
					Predicate predicate1 = cb.like(root.get("eventType").as(String.class), "%" + search + "%");
					Predicate predicate2 = cb.like(root.join("pubUser").get("userAccount").as(String.class), "%" + search + "%");
					Predicate predicate3 = cb.like(root.get("title").as(String.class), "%" + search + "%");
					return cb.and(predicate,cb.or(predicate1,predicate2,predicate3));
				}
			}

		}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC,"placedTop","placedTopDate", "releseDate")));
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		List<FamilyNew> fnl = familyNewPage.getContent();
		for (FamilyNew fn : fnl) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyCultureId", StringUtil.isEmpty(fn.getFamilyNewsId()) ? "" : fn.getFamilyNewsId());
			map.put("familyEventType", StringUtil.isEmpty(fn.getEventType()) ? "" : fn.getEventType());
			map.put("familyCultureContent", StringUtil.isEmpty(fn.getContent()) ? "" : fn.getContent());
			map.put("familyCultureTitle", StringUtil.isEmpty(fn.getTitle()) ? "" : fn.getTitle());
			map.put("familyCultureHtmlContent", StringUtil.isEmpty(fn.getHtmlContent()) ? "" : fn.getHtmlContent());
			map.put("familyCulturePushMan", StringUtil.isEmpty(fn.getPubUser()) 
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
	 * 删除文化
	 * @param familyNewsId
	 * @throws BusinessException
	 */
	@Transactional
	public void delFamilyCulture(Integer familyNewsId) throws BusinessException{
		FamilyNew familyNews=familyNewsService.findOne(familyNewsId);
		if(familyNewsId==null||StringUtil.isEmpty(familyNews))
			throw new BusinessException(FAMILY_CULTURE_NOT_IN);
		familyNewsService.delete(familyNews);
	}
	/**
	 * 已有文化类型选择
	 * @param familyNewsId
	 * @throws BusinessException
	 */
	public List<Object> familyCultureByEventTypeColumn(){
		return familyNewsService.findAllByFlagGroupByEventTypeColumn(1);
	}
	/**
	 * 置顶
	 * @throws BusinessException 
	 */
	@Transactional
	public void familyCulturePlacedTop(FamilyNew familyNew) throws BusinessException{
		if(StringUtil.isEmpty(familyNew)||StringUtil.isEmpty(familyNew.getFamilyNewsId()))throw new BusinessException(DATA_MISSING);
		Integer familyNewsId=familyNew.getFamilyNewsId();
		familyNew=familyNewsService.findOne(familyNewsId);
		if(StringUtil.isEmpty(familyNew))throw new BusinessException(FAMILY_CULTURE_NOT_IN);
		familyNew.setPlacedTop(1);
		familyNew.setPlacedTopDate(new Date());
	}
}
