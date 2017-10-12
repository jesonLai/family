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

import sys.ProjectConst;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.PubAuhorityResource;
import sys.model.PubAuthority;
import sys.model.PubRole;
import sys.model.PubRolesAuthority;
import sys.model.controller.TableRquestData;
import sys.set.service.AuthorityRolesService;
import sys.set.service.AuthorityService;
import sys.set.service.RolesService;
import sys.util.StringUtil;

import com.google.common.collect.Lists;

/**
 * 角色管理
 * @author lxr
 *
 */
@Repository(SpringAnnotationDef.SER_ROLES)
@Transactional(readOnly = true)
public class RoleServiceImpl {
	private final static String DATA_MISSING="参数丟失";
	private final static String DATA_REPEAT="显示名称已存在";
	private final static String DATA_SYSNAME_REPEAT="操作名称已存在";
	@Resource
	RolesService roleService;
	@Resource
	AuthorityService authorityService;
	@Resource
	AuthorityRolesService authorityRolesService;
	
	/**
	 * 新增角色
	 * @param map
	 * @throws BusinessException 
	 */
	@Transactional
	public PubRole addNew(PubRole pubRole) throws BusinessException{
		if (StringUtil.isEmpty(pubRole)) throw new BusinessException(DATA_MISSING);
		if(!isRoleName(pubRole.getRoleName(),pubRole.getRoleId()))throw new BusinessException(DATA_REPEAT);
		if(!isRoleSysName(pubRole.getRoleSysName(),pubRole.getRoleId()))throw new BusinessException(DATA_SYSNAME_REPEAT);
		if(StringUtil.isEmpty(pubRole.getRoleId())){
			pubRole.setCreateDate(new Date());
			return roleService.save(pubRole);
		}else{
			PubRole pubRoleOld=roleService.findOne(pubRole.getRoleId());
			pubRoleOld.setRoleSysName(pubRole.getRoleSysName());
			pubRoleOld.setRoleName(pubRole.getRoleName());
			pubRoleOld.setRoleDesc(pubRole.getRoleDesc());
			return pubRoleOld;
		}
	}
	/**
	 * 获取所有角色列表
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
	public TableRquestData getRolesPagination(String authorityId,TableRquestData tableRquestData, final String search) {
		int pageNumber = tableRquestData.getStart();
		int pageSize = tableRquestData.getLength();
		if (pageSize < 0)
			pageSize = roleService.findAll().size();
		Page<PubRole> familyRolesPage = roleService.findAll(new Specification<PubRole>() {
			@Override
			public Predicate toPredicate(Root<PubRole> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//				if (StringUtil.isEmpty(search)) {
					return null;
//				} else {
//					
//					Predicate predicate1 = cb.like(root.get("eventType").as(String.class), "%" + search + "%");
//					Predicate predicate2 = cb.like(root.join("pubUser").get("userAccount").as(String.class), "%" + search + "%");
//					return cb.equal(predicate,cb.or(predicate1,predicate2));
//				}
				
			}

		}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "createDate")));
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		List<PubRole> prl = familyRolesPage.getContent();
		List<Integer> roleIds=Lists.newArrayList();
		PubAuthority pubAuthority=authorityService.findOne(authorityId);
		if(!StringUtil.isEmpty(pubAuthority)){
			List<PubRolesAuthority> pubRolesAuthorities=pubAuthority.getPubRolesAuthorities();
			for (PubRolesAuthority pubRolesAuthoritie : pubRolesAuthorities) {
				PubRole pubRole=pubRolesAuthoritie.getPubRole();
				if(!StringUtil.isEmpty(pubRole)){
					roleIds.add(pubRole.getRoleId());
				}
				
			}
		}
		for (PubRole pr : prl) {
			String roleCheckbox="";
			String checked="";
			Integer roleIdVal=StringUtil.isEmpty(pr.getRoleId()) ? 0 : pr.getRoleId();
			if(roleIdVal!=0&&roleIds.contains(roleIdVal)){
				checked="checked=checked";
			}
			roleCheckbox="<input type=\"checkbox\"  class=\"checkchild\"  value="+roleIdVal+" "+checked+"/>";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleCheckbox", roleCheckbox);
			map.put("rolesId", roleIdVal);
			map.put("rolesName", pr.getRoleName());
			map.put("rolesSystemName", pr.getRoleSysName());
			map.put("rolesStatus", pr.getRoleEnabled());
			map.put("rolesIsSys", pr.getRole_isSys());
			map.put("rolesDesc", pr.getRoleDesc());
			mapData.add(map);
		}
		tableRquestData.setRecordsFiltered(familyRolesPage.getTotalElements());
		tableRquestData.setRecordsTotal(familyRolesPage.getTotalElements());
		tableRquestData.setData(mapData);
		return tableRquestData;
	}
	public List<Map<String, Object>> getAuthorityCenter(PubRole role){
		List<Map<String, Object>> ztreeData=new ArrayList<Map<String, Object>>();
		List<PubAuthority> pubAuthorityList=authorityService.findAll();
		Map<String, Object> ztreeMap=new HashMap<String,Object>();
		ztreeMap.put("id","ARF");
		ztreeMap.put("pId","");
		ztreeMap.put("name","功能模块");
		ztreeData.add(ztreeMap);
		ztreeMap=new HashMap<String,Object>();
		ztreeMap.put("id","ARM");
		ztreeMap.put("pId","");
		ztreeMap.put("name","菜单");
		ztreeData.add(ztreeMap);
		for (PubAuthority pubAuthority:pubAuthorityList){
			ztreeMap=new HashMap<String,Object>();
			PubAuhorityResource pubAuhorityResource=pubAuthority.getPubAuhorityResource();
			String resourceType="";
			if(pubAuhorityResource!=null){
				resourceType=pubAuhorityResource.getAuthorityResourceType();
			}
			ztreeMap.put("id",pubAuthority.getAuthorityId());
			if(ProjectConst.META_RESOURCE_FUNCTION_TYPES.equals(resourceType)){
				ztreeMap.put("pId","ARF");
			}else if(ProjectConst.META_RESOURCE_MENU_TYPES.equals(resourceType)){
				ztreeMap.put("pId","ARM");
			}else{
				ztreeMap.put("pId","");
			}
			ztreeMap.put("name",pubAuthority.getAuthorityName());
			ztreeData.add(ztreeMap);
		}
		return ztreeData;
	}
	
	
	/**
	 * 检查显示名称的唯一
	 * 
	 * @param userWeixin
	 * @param userInfoId
	 * @return
	 */
	public boolean isRoleName(String roleName, Integer roleId) {
		if (StringUtil.isEmpty(roleId)||roleId==0) {
			return StringUtil.isEmpty(roleService.findOneByRoleName(roleName));
		} else {
			roleId = StringUtil.isEmpty(roleId) ? 0 : roleId;
			return StringUtil.isEmpty(roleService.findOneByRoleNameAndRoleIdNot(roleName, roleId));
		}

	}
	/**
	 * 检查微信号的唯一
	 * 
	 * @param userWeixin
	 * @param userInfoId
	 * @return
	 */
	public boolean isRoleSysName(String roleSysName, Integer roleId) {
		if (StringUtil.isEmpty(roleId)||roleId==0) {
			return StringUtil.isEmpty(roleService.findOneByRoleSysName(roleSysName));
		} else {
			roleId = StringUtil.isEmpty(roleId) ? 0 : roleId;
			return StringUtil.isEmpty(roleService.findOneByRoleSysNameAndRoleIdNot(roleSysName, roleId));
		}

	}
}
