package sys.set.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import sys.SpringAnnotationDef;
import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.PubUsersRole;
import sys.model.RecordFamilyLog;
import sys.model.controller.TableRquestData;
import sys.set.service.RecordFamilyLogService;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;
/**
 *监测实现
 *
 */
@Service(SpringAnnotationDef.SER_RECORDFAMILYLOG)
@Transactional(readOnly = true)
public class RecordFamilyLogServiceImpl {
	private static final Logger  _log= Logger.getLogger(RecordFamilyLogServiceImpl.class.getName());
    private static final String DATA_NOT_NULL="参数错误";
    private static final String MENU_NAME_IN="菜单名称已存在";
    private static final String[][] MENU_HINT={{"菜单名称不能为空"}};
	private static final String DATA_MISSING = "参数丢失！";
    @Resource()
    private RecordFamilyLogService recordFamilyLogService;
    
    /**
	 *  获取监测信息
	 */
	public TableRquestData getRecordFamilyLogPagination(TableRquestData tableRquestData,final String search){
		int pageNumber=tableRquestData.getStart();
		int pageSize=tableRquestData.getLength();
		if(pageSize<0)pageSize=recordFamilyLogService.findAll().size();
		Page<RecordFamilyLog> userPage=recordFamilyLogService.findAll(new Specification<RecordFamilyLog>() {
			@Override
			public Predicate toPredicate(Root<RecordFamilyLog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//				if (StringUtil.isEmpty(search)) {
//					return null;
//				} else {
//					Predicate predicate1 = cb.like(root.get("userAccount").as(String.class), "%" + search + "%");
//					Predicate predicate3 =cb.like(root.join("userInfo").get("userName").as(String.class), "%" + search + "%");
//					if (search.equals("男") || search.equals("女") || search.equals("未知")) {
//						Predicate predicate2 = cb.equal(root.join("userInfo").get("userSex").as(int.class),
//								search.equals("男") ? "1" : search.equals("女") ? "2" : "3");
//						return cb.or(predicate3,cb.or(predicate1, predicate2));
//					} else {
//						return cb.or(predicate1,predicate3);
//					}
//				}
				return null;
			}

		},new PageRequest((pageNumber/pageSize),pageSize,new Sort(Direction.DESC,"createDate")));
		
		List<Map<String,Object>> mapData=new ArrayList<Map<String,Object>>();
		List<RecordFamilyLog> rfl=userPage.getContent();
			for (RecordFamilyLog rf : rfl) {
				List<String> roleNames=Lists.newArrayList();
				PubUser pu=rf.getCreateMan();
				if(!StringUtil.isEmpty(pu)){
				List<PubUsersRole>pubUsersRoles=pu.getPubUsersRoles();
					for (PubUsersRole pubUsersRole : pubUsersRoles) {
						PubRole pubRole=pubUsersRole.getPubRole();
						if(!StringUtil.isEmpty(pubRole)&&!StringUtil.isEmpty(pubRole.getRoleName()))
							roleNames.add(pubRole.getRoleName());
					}
				}
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("requestIp", StringUtil.isEmpty(rf.getRequestIp())?"":rf.getRequestIp());
				map.put("createMan", StringUtil.isEmpty(pu)||StringUtil.isEmpty(pu.getUserAccount())?"":pu.getUserAccount());
				map.put("createDate",StringUtil.isEmpty(rf.getCreateDate())?"":UtilFactory.getSysDate().DateToString(rf.getCreateDate()));
//				map.put("requestMethod", StringUtil.isEmpty(rf.getRequestMethod())?"0":rf.getRequestMethod());
				map.put("description", StringUtil.isEmpty(rf.getDescription())?"0":rf.getDescription());
				map.put("roleName", roleNames==null?"":roleNames.toString());
				mapData.add(map);
			}
		tableRquestData.setRecordsFiltered(userPage.getTotalElements());
		tableRquestData.setRecordsTotal(userPage.getTotalElements());
		tableRquestData.setData(mapData);
		return tableRquestData;
	}
}
