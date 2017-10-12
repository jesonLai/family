package sys.user.front.service.imp;

import java.util.ArrayList;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import sys.SpringAnnotationDef;
//import sys.model.Family;
import sys.model.UserInfo;
import sys.set.service.UserInfoService;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_RELATIONSHIP)
@Transactional(readOnly = true)
public class RelationshipServiceImpl {
	@Resource
	UserInfoService userInfoService;
//	{
//        "id": 7,
//        "name": "Michile",
//        "rank": "Vice President",
//        "department": "Research and Development",
//        "level": "ICT Service Desk Manager",
//        "parent": 6
//    }
	public List<Map<String,Object>> getRelationshipService(UserInfo userChild){
		List<Map<String,Object>> list=Lists.newArrayList();
		Map<String,Object> map=Maps.newHashMap();
		UserInfo userChildCurr=UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo();
		UserInfo userChildParameter=userChild;
		
		if(!StringUtil.isEmpty(userChild)&&!StringUtil.isEmpty(userChild.getUserInfoId())&&userChild.getUserInfoId()!=0){
			userChild=userInfoService.findOne(userChild.getUserInfoId());
		}else{
			userChild=userChildCurr;
		}
		
		if(StringUtil.isEmpty(userChild)||StringUtil.isEmpty(userChild.getUserInfoId())||StringUtil.isEmpty(userChild.getUserFlag()!=1)){
			return null;
		}
		UserInfo fatherUserInfo=userChild.getFatherUserInfo();
	if(!StringUtil.isEmpty(fatherUserInfo)&&fatherUserInfo.getUserInfoId()!=null&&fatherUserInfo.getUserInfoId()!=0){
		//父亲
		UserInfo userParent=fatherUserInfo;
		map.put("id",userParent.getUserInfoId());
		map.put("name",userParent.getUserName());
		map.put("level","父亲");
		map.put("cardId",userParent.getUserIdentityCard());
		map.put("remarks",StringUtil.isEmpty(userParent.getRemarks())?"":userParent.getRemarks());
		map.put("parent","");
		map.put("maritalStatus",userParent.getMaritalStatus());
		map.put("spouseName",StringUtil.isEmpty(userParent.getSpouseName())?"":userParent.getSpouseName());
		map.put("userDesc", StringUtil.isEmpty(userParent.getUserDesc())?"":userParent.getUserDesc());
			//爷爷
		UserInfo grandpaUserInfo=fatherUserInfo.getFatherUserInfo();
		if (!StringUtil.isEmpty(grandpaUserInfo)&&grandpaUserInfo.getUserInfoId()!=null&&grandpaUserInfo.getUserInfoId()!=0) {
			UserInfo  grandpa=grandpaUserInfo;
			Map<String,Object> mapgrandpa=Maps.newHashMap();
			mapgrandpa.put("id",grandpa.getUserInfoId());
			mapgrandpa.put("name",grandpa.getUserName());
			mapgrandpa.put("level","爷爷");
			map.put("cardId",grandpa.getUserIdentityCard());
			mapgrandpa.put("remarks",StringUtil.isEmpty(grandpa.getRemarks())?"":grandpa.getRemarks());
			mapgrandpa.put("parent","");
			map.put("parent",grandpa.getUserInfoId());
			mapgrandpa.put("maritalStatus",grandpa.getMaritalStatus());
			mapgrandpa.put("spouseName",StringUtil.isEmpty(grandpa.getSpouseName())?"":grandpa.getSpouseName());
			mapgrandpa.put("userDesc", StringUtil.isEmpty(grandpa.getUserDesc())?"":grandpa.getUserDesc());
			list.add(mapgrandpa);//爷爷
		}
		list.add(map);//父亲
		//儿女
		String level="";
		for (UserInfo childUserInfo : fatherUserInfo.getChildUserInfos()) {
			UserInfo son=childUserInfo;
			level=userChildParameter.getUserInfoId()==son.getUserInfoId()?"根节点":
				userChild.getUserInfoId()==son.getUserInfoId()?"根节点":"兄弟姐妹";
			map=Maps.newHashMap();
			map.put("id",son.getUserInfoId());
			map.put("name",son.getUserName());
			map.put("level",level);
			map.put("cardId",son.getUserIdentityCard());
			map.put("remarks",StringUtil.isEmpty(son.getRemarks())?"":son.getRemarks());
			map.put("parent",userParent.getUserInfoId());
			map.put("maritalStatus",son.getMaritalStatus());
			map.put("spouseName",StringUtil.isEmpty(son.getSpouseName())?"":son.getSpouseName());
			map.put("userDesc", StringUtil.isEmpty(son.getUserDesc())?"":son.getUserDesc());
			list.add(map);
			//孙子
			for (UserInfo sonUserInfo : son.getChildUserInfos()) {
				UserInfo grandson=sonUserInfo;
				Integer xSex=grandson.getUserSex();
				map=Maps.newHashMap();
				map.put("id",grandson.getUserInfoId());
				map.put("name",grandson.getUserName());
				map.put("level",xSex==1?"儿子":xSex==2?"女儿":"未知");
				map.put("cardId",grandson.getUserIdentityCard());
				map.put("remarks",StringUtil.isEmpty(grandson.getRemarks())?"":son.getRemarks());
				map.put("parent",son.getUserInfoId());
				map.put("maritalStatus",grandson.getMaritalStatus());
				map.put("spouseName",StringUtil.isEmpty(grandson.getSpouseName())?"":grandson.getSpouseName());
				map.put("userDesc", StringUtil.isEmpty(grandson.getUserDesc())?"":grandson.getUserDesc());
				list.add(map);
				for (UserInfo grandsonUserInfo : grandson.getChildUserInfos()) {
					UserInfo grandSonSon=grandsonUserInfo;
					map=Maps.newHashMap();
					map.put("id",grandSonSon.getUserInfoId());
					map.put("name",grandSonSon.getUserName());
					map.put("level","孙子");
					map.put("cardId",grandSonSon.getUserIdentityCard());
					map.put("remarks",StringUtil.isEmpty(grandSonSon.getRemarks())?"":grandSonSon.getRemarks());
					map.put("parent",grandson.getUserInfoId());
					map.put("maritalStatus",grandSonSon.getMaritalStatus());
					map.put("spouseName",StringUtil.isEmpty(grandSonSon.getSpouseName())?"":grandSonSon.getSpouseName());
					map.put("userDesc", StringUtil.isEmpty(grandSonSon.getUserDesc())?"":grandSonSon.getUserDesc());
					list.add(map);
				}
				
			}
		}
	}else{//自己
		map=Maps.newHashMap();
		map.put("id",userChild.getUserInfoId());
		map.put("name",userChild.getUserName());
		map.put("level","根节点");
		map.put("cardId",userChild.getUserIdentityCard());
		map.put("remarks",StringUtil.isEmpty(userChild.getRemarks())?"":userChild.getRemarks());
		map.put("parent","");
		map.put("maritalStatus",userChild.getMaritalStatus());
		map.put("spouseName",StringUtil.isEmpty(userChild.getSpouseName())?"":userChild.getSpouseName());
		map.put("userDesc", StringUtil.isEmpty(userChild.getUserDesc())?"":userChild.getUserDesc());
		list.add(map);
	}
		return list;
	}
	public List<Map<String, Object>> getInitUserName(final String searchParams, int pageLimit) {
		List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
		Page<UserInfo> pageUserInfo = userInfoService.findAll(new Specification<UserInfo>() {
			@Override
			public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> arg1, CriteriaBuilder cb) {
				return cb.like(root.get("userName").as(String.class), "%" + searchParams + "%");
			}
		}, new PageRequest(0, pageLimit, new Sort(Direction.DESC, "createDate")));
		for (UserInfo ui : pageUserInfo.getContent()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ui.getUserInfoId());
			map.put("text", ui.getUserName());
			map.put("userBornDate", ui.getUserBornDate());
			map.put("userAge", ui.getUserAge());
			map.put("spouseName", ui.getSpouseName());

			lm.add(map);
		}

		return lm;
	}
	/**
	 * 查询是否有关系
	 * @return
	 */
	public boolean isFamilyRelationship(UserInfo userChild){
		boolean bl=false;
		UserInfo userChildCurr=UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo();
		
		if(!StringUtil.isEmpty(userChild)&&!StringUtil.isEmpty(userChild.getUserInfoId())&&userChild.getUserInfoId()!=0){
			userChild=userInfoService.findOne(userChild.getUserInfoId());
		}else{
			userChild=userChildCurr;
		}
		
		if(StringUtil.isEmpty(userChild)||StringUtil.isEmpty(userChild.getUserInfoId())||StringUtil.isEmpty(userChild.getUserFlag()!=1)){
			return bl;
		}
		UserInfo fatherUserInfo=userChild.getFatherUserInfo();
		if(!StringUtil.isEmpty(fatherUserInfo)&&fatherUserInfo.getUserInfoId()!=null&&fatherUserInfo.getUserInfoId()!=0)bl=true;
		return bl;
	}
	
}
