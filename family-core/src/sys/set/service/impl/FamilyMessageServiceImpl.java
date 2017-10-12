package sys.set.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import sys.exception.handing.BusinessException;
import sys.model.FamilyMessage;
import sys.model.FamilyNew;
import sys.model.controller.TableRquestData;
import sys.set.service.FamilyMessageService;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_FAMILYMESSAGE)
@Transactional(readOnly = true)
public class FamilyMessageServiceImpl {
	private static final String DATA_MISSING = "参数丢失！";
	@Resource
	FamilyMessageService familyMessageService;
	/**
	 * 获取所有家族文化列表
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
	public TableRquestData getFamilyMessagePagination(TableRquestData tableRquestData, final String search) {
		int pageNumber = tableRquestData.getStart();
		int pageSize = tableRquestData.getLength();
		if (pageSize < 0)
			pageSize = familyMessageService.findAll().size();
		Page<FamilyMessage> familyMessagePage = familyMessageService.findAll(new Specification<FamilyMessage>() {
			@Override
			public Predicate toPredicate(Root<FamilyMessage> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				return null;
			}

		}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "messageDate")));
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		List<FamilyMessage> fnl = familyMessagePage.getContent();
		for (FamilyMessage fm : fnl) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyMessageId", StringUtil.isEmpty(fm.getId()) ? "" : fm.getId());
			map.put("familyMessageTitle", StringUtil.isEmpty(fm.getTitle()) ? "" : fm.getTitle());
			map.put("familyMessageContent", StringUtil.isEmpty(fm.getContent()) ? "" : fm.getContent());
			map.put("familyMessageDate", StringUtil.isEmpty(fm.getMessageDate()) ? "" : UtilFactory.getSysDate().DateToString(fm.getMessageDate()));
			map.put("familyMessageMan",  StringUtil.isEmpty(fm.getPubUser()) 
					? "" :StringUtil.isEmpty(fm.getPubUser().getUserInfo())
					? fm.getPubUser().getUserAccount().concat("/")
					:fm.getPubUser().getUserAccount().concat("/").concat(fm.getPubUser().getUserInfo().getUserName()));
			mapData.add(map);
		}
		tableRquestData.setRecordsFiltered(familyMessagePage.getTotalElements());
		tableRquestData.setRecordsTotal(familyMessagePage.getTotalElements());
		tableRquestData.setData(mapData);
		return tableRquestData;
	}
	/**
	 * 删除留言内容
	 * @param familyNewsId
	 * @throws BusinessException
	 */
	@Transactional
	public void delFamilyMessage(FamilyMessage familyMessage) throws BusinessException{
		if(StringUtil.isEmpty(familyMessage)||StringUtil.isEmpty(familyMessage.getId())){
			throw new BusinessException(DATA_MISSING);
		}
		familyMessage=familyMessageService.findOne(familyMessage.getId());
		if(StringUtil.isEmpty(familyMessage)||StringUtil.isEmpty(familyMessage.getId())){
			throw new BusinessException(DATA_MISSING);
		}
		familyMessageService.delete(familyMessage);
	}
	
}
