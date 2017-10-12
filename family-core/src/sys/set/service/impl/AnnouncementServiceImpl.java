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
import sys.model.Announcement;
import sys.model.controller.TableRquestData;
import sys.set.service.AnnouncementService;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_ANNOUNCEMENT)
@Transactional(readOnly = true)
public class AnnouncementServiceImpl {
	private static final String DATA_MISSING = "参数丢失！";
	private static final String INSERT_SUCCESS = "提交成功！";
	private static final String INSERT_FAIL = "提交失败！";
	private static final String EDITOR_CONTENT_NULL = "编辑内容不能为空！";
	private static final String EDITOR_TITLE_NULL = "标题不能为空！";
	private static final String FAMILY_ANCESTRALTEMPL_NOT_IN = "未查找到公告信息！";
	@Resource
	AnnouncementService announcementService;
	/**
	 * 发布公告
	 * @param map
	 */
	@Transactional
	public void addNew(Map<String, Object> map,Announcement announcement,String editorValue){
		if(StringUtil.isEmpty(announcement)){
			map.put(BaseHint.MESSAGE, DATA_MISSING);
			return;
		}
		if(StringUtil.isEmpty(announcement.getTitle())){
			map.put(BaseHint.MESSAGE, EDITOR_TITLE_NULL);
			return;
		}
		 if(StringUtil.isEmpty(editorValue)){
			 map.put(BaseHint.MESSAGE, EDITOR_CONTENT_NULL);
			return;
		}
		 announcement.setContent(editorValue);
		 announcement.setCreateDate(new Date());
		 announcement.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
		 announcement= announcementService.save(announcement);
		 if (announcement.getId()!=null&&announcement.getId() > 0) {
				map.put(BaseHint.MESSAGE, INSERT_SUCCESS);
				map.put("ANNOUNCEMENTID", announcement.getId() );
				map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			} else {
				map.put(BaseHint.MESSAGE, INSERT_FAIL);
			}
		 
	}
	/**
	 * 获取所有公告列表
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
	public TableRquestData getAnnouncementPagination(TableRquestData tableRquestData, final String search) {
		int pageNumber = tableRquestData.getStart();
		int pageSize = tableRquestData.getLength();
		if (pageSize < 0)
			pageSize = announcementService.findAll().size();
		Page<Announcement> familyAnnouncementPage = announcementService.findAll(new Specification<Announcement>() {
			@Override
			public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//				if (StringUtil.isEmpty(search)) {
					return null;
//				} else {
//					
//					Predicate predicate1 = cb.like(root.get("eventType").as(String.class), "%" + search + "%");
//					Predicate predicate2 = cb.like(root.join("pubUser").get("userAccount").as(String.class), "%" + search + "%");
//					return cb.equal(predicate,cb.or(predicate1,predicate2));
//				}
				
			}

		}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "placedTop","placedTopDate","createDate")));
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		List<Announcement> acl = familyAnnouncementPage.getContent();
		for (Announcement ac : acl) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("announcementId", StringUtil.isEmpty(ac.getId()) ? "" : ac.getId());
			map.put("announcementTitle", StringUtil.isEmpty(ac.getTitle()) ? "" : ac.getTitle());
			map.put("announcementContent", StringUtil.isEmpty(ac.getContent()) ? "" : ac.getContent());
			map.put("announcementPushMan",
											StringUtil.isEmpty(ac.getPubUser()) 
											? "" :StringUtil.isEmpty(ac.getPubUser().getUserInfo())
											? ac.getPubUser().getUserAccount().concat("/")
											:ac.getPubUser().getUserAccount().concat("/").concat(ac.getPubUser().getUserInfo().getUserName()));
			map.put("announcementCreateDate", StringUtil.isEmpty(ac.getCreateDate()) ? ""
					: UtilFactory.getSysDate().DateToString(ac.getCreateDate()));
			mapData.add(map);
		}
		tableRquestData.setRecordsFiltered(familyAnnouncementPage.getTotalElements());
		tableRquestData.setRecordsTotal(familyAnnouncementPage.getTotalElements());
		tableRquestData.setData(mapData);
		return tableRquestData;
	}
	
	/**
	 * 删除公告
	 * @param familyNewsId
	 * @throws BusinessException
	 */
	@Transactional
	public void delAnnouncement(Announcement announcement) throws BusinessException{
		if(StringUtil.isEmpty(announcement))throw new BusinessException(DATA_MISSING);
		announcement=announcementService.findOne(announcement.getId());
		if(StringUtil.isEmpty(announcement))
			throw new BusinessException(FAMILY_ANCESTRALTEMPL_NOT_IN);
		announcementService.delete(announcement);
	}
	/**
	 * 置顶
	 * @throws BusinessException 
	 */
	@Transactional
	public void familyAnnouncementPlacedTop(Announcement announcement) throws BusinessException{
		if(StringUtil.isEmpty(announcement)||StringUtil.isEmpty(announcement.getId()))throw new BusinessException(DATA_MISSING);
		Integer id=announcement.getId();
		announcement=announcementService.findOne(id);
		if(StringUtil.isEmpty(announcement))throw new BusinessException(FAMILY_ANCESTRALTEMPL_NOT_IN);
		announcement.setPlacedTop(1);
		announcement.setPlacedTopDate(new Date());
	}
}
