package sys.set.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.PubUsersRole;
import sys.model.UserInfo;
import sys.model.controller.TableRquestData;
import sys.set.service.RolesService;
import sys.set.service.UsersRolesService;
import sys.set.service.UsersService;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_USERSLOGIN)
@Transactional(readOnly = true)
public class UserLoginServiceImpl extends IUsersServiceImpl{

	protected final Log _Log = LogFactory.getLog(getClass());
	private static final String CURR_USER_NOT_IN = "当前用户未找到!";
	private static final String NEW_ACCOUNT_NULL = "账号不能为空!";
	private static final String OLD_PASSWORD_NOT_EQUALS = "旧密码输入错误!";
	private static final String NEW_PASSWORD_NOT_EQUALS = "新密码不一致!";
	private static final String NEW_PASSWORD_NULL = "新密码不能为空!";
	private static final String USERLOGIN_ACCOUNT_ID_NOT_IN = "登录编号参数丢失!";
	private static final String USERLOGIN_ACCOUNT_REMOVE = "没有权限删除账号！";
	private static final String USERLOGIN_ACCOUNT_UPDATE = "没有权限新增或修改账号！";
	private static final String USERLOGIN_ACCOUNT_REGISTER_ON = "账号已注册！";
	@Resource
	private UsersService usersService;
	@Resource
	private RolesService rolesService;
	@Resource
	private UsersRolesService usersRolesService;

	/**
	 * 新增--账号 角色
	 * 
	 * @param pu
	 * @param rolesId
	 * @throws BusinessException
	 */
	@Transactional
	public void addNew(PubUser pu, Integer[] delRoleIds, Integer[] insRoleIds) throws BusinessException{
		boolean isExist = true;
		if (StringUtil.isEmpty(pu))
			throw new BusinessException(CURR_USER_NOT_IN);
		if (StringUtil.isEmpty(pu.getUserAccount()))
			throw new BusinessException(NEW_ACCOUNT_NULL);
		if (!isUserAccount(pu.getUserAccount(),pu.getUId()))
			throw new BusinessException(USERLOGIN_ACCOUNT_REGISTER_ON);
		if (isAuthority(pu.getUId()))
			throw new BusinessException(USERLOGIN_ACCOUNT_UPDATE);
		Integer uId = pu.getUId() == null ? 0 : pu.getUId();
		PubUser puOld = usersService.findOne(uId);// 是否已存在
		_Log.debug(puOld);
		_Log.debug(StringUtil.isEmpty(puOld));
		isExist = StringUtil.isEmpty(puOld) || StringUtil.isEmpty(puOld.getUId()) || puOld.getUId() == 0;
		_Log.debug("isExist is a " + isExist);
		if (isExist){// 不存在
			if (StringUtil.isEmpty(pu.getUserPassword())){
				pu.setUserPassword(StringUtil.bCryptPasswordEncoder("12345678"));
			}else{
				pu.setUserPassword(StringUtil.bCryptPasswordEncoder(pu.getUserPassword()));
			}
			pu.setUserAccount(pu.getUserAccount().trim());
			pu = usersService.save(pu);
		}else{
			puOld.setuFlag(1);
			if (StringUtil.isEmpty(pu.getUserPassword()))
				puOld.setUserPassword(StringUtil.bCryptPasswordEncoder("12345678"));
			else puOld.setUserPassword(StringUtil.bCryptPasswordEncoder(pu.getUserPassword()));
			pu = puOld;
		}
		if (delRoleIds != null && delRoleIds.length != 0){
			for (Integer delV:delRoleIds){
				PubRole pubRole = rolesService.findOne(delV);
				if (!StringUtil.isEmpty(pubRole)){
					PubUsersRole pubUsersRole = usersRolesService.findOneByPubRoleAndPubUser(pubRole,pu);
					if (!StringUtil.isEmpty(pubUsersRole)){
						usersRolesService.delete(pubUsersRole);
					}
				}
			}
		}
		if (insRoleIds != null && insRoleIds.length != 0){
			for (Integer insV:insRoleIds){
				PubRole pubRole = rolesService.findOne(insV);
				if (!StringUtil.isEmpty(pubRole)){
					PubUsersRole pubUsersRole = usersRolesService.findOneByPubRoleAndPubUser(pubRole,pu);
					if (StringUtil.isEmpty(pubUsersRole)){
						pubUsersRole = new PubUsersRole();
						pubUsersRole.setPubUser(pu);
						pubUsersRole.setPubRole(pubRole);
						usersRolesService.save(pubUsersRole);
					}
				}
			}
		}
	}

	/**
	 * 获取全部登录人的账号信息
	 */
	public TableRquestData getUserPagination(TableRquestData tableRquestData, final String search, final List<Integer> roleIds){
		int pageNumber = tableRquestData.getStart();
		int pageSize = tableRquestData.getLength();
		if (pageSize < 0)
			pageSize = usersService.findAll().size();
		Page<PubUser> userPage = usersService.findAll(new Specification<PubUser>(){

			@Override
			public Predicate toPredicate(Root<PubUser> root, CriteriaQuery<?> query, CriteriaBuilder cb){
				List<Predicate> list = Lists.newArrayList();
				if (!StringUtil.isEmpty(search)){
					Predicate predicate1 = cb.like(root.get("userAccount").as(String.class),"%" + search + "%");
					Predicate predicate3 = cb.like(root.join("userInfo").get("userName").as(String.class),"%" + search + "%");
					if (search.equals("男") || search.equals("女") || search.equals("未知")){
						Predicate predicate2 = cb.equal(root.join("userInfo").get("userSex").as(int.class),search.equals("男") ? "1" : search.equals("女") ? "2" : "3");
						list.add(cb.or(predicate3,cb.or(predicate1,predicate2)));
					}else{
						list.add(cb.or(predicate1,predicate3));
					}
				}
				ListJoin<PubUser, PubUsersRole> usersRoleJoin = root.join(root.getModel().getList("pubUsersRoles",PubUsersRole.class),JoinType.INNER);
				Join pubroleJoin = usersRoleJoin.join("pubRole",JoinType.INNER);
				List<Predicate> listRole = Lists.newArrayList();
				for (Integer roleId:roleIds){
					listRole.add(cb.equal(pubroleJoin.get("roleId"),roleId));
				}
				listRole.add(cb.isNull(pubroleJoin.get("roleId")));
				list.add(cb.or(listRole.toArray(new Predicate[listRole.size()])));
				Predicate[] p = new Predicate[list.size()];
				query.where(cb.and(list.toArray(p)));
				query.distinct(true);
				return query.getGroupRestriction();
			}
		},new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "uId")));
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		List<PubUser> puL = userPage.getContent();
		for (PubUser pu:puL){
			List<String> roles = Lists.newArrayList();
			List<PubUsersRole> purl = pu.getPubUsersRoles();
			for (PubUsersRole pur:purl){
				PubRole pr = pur.getPubRole();
				if (!StringUtil.isEmpty(pr) && !StringUtil.isEmpty(pr.getRoleName())){
					roles.add(pr.getRoleName());
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginId",StringUtil.isEmpty(pu.getUId()) ? "" : pu.getUId());
			map.put("loginAccount",StringUtil.isEmpty(pu.getUserAccount()) ? "" : pu.getUserAccount());
			map.put("loginName",StringUtil.isEmpty(pu.getUserInfo()) ? "" : StringUtil.isEmpty(pu.getUserInfo().getUserName()) ? "" : pu.getUserInfo().getUserName());
			map.put("loginSex",StringUtil.isEmpty(pu.getUserInfo()) ? "未知" : StringUtil.isEmpty(pu.getUserInfo().getUserSex()) ? "未知" : pu.getUserInfo().getUserSex() == 1 ? "男" : pu.getUserInfo()
					.getUserSex() == 2 ? "女" : "未知");
			map.put("loginPhone",StringUtil.isEmpty(pu.getUserInfo()) ? "" : StringUtil.isEmpty(pu.getUserInfo().getUserPhone()) ? "" : pu.getUserInfo().getUserPhone());
			map.put("loginIp",StringUtil.isEmpty(pu.getIp()) ? "" : pu.getIp());
			map.put("loginLastTime",StringUtil.isEmpty(pu.getLastLoginTime()) ? "" : UtilFactory.getSysDate().DateToString(pu.getLastLoginTime()));
			map.put("loginRoles",roles.size() == 0 ? "" : roles.toString());
			map.put("loginBrowserName",StringUtil.isEmpty(pu.getBrowserName()) ? "" : pu.getBrowserName());
			map.put("loginOsName",StringUtil.isEmpty(pu.getOsName()) ? "" : pu.getOsName());
			map.put("loginStatus",StringUtil.isEmpty(pu.getuFlag()) ? "0" : pu.getuFlag());
			mapData.add(map);
		}
		tableRquestData.setRecordsFiltered(userPage.getTotalElements());
		tableRquestData.setRecordsTotal(userPage.getTotalElements());
		tableRquestData.setData(mapData);
		return tableRquestData;
	}

	@Transactional
	public void updateUserPassWord(String oldPwd, String newOnePwd, String newTowPwd) throws BusinessException{
		PubUser pu = usersService.findOne(UtilFactory.getSpringContext().getUserInfoContext().getUser().getUId());
		if (StringUtil.isEmpty(pu) || StringUtil.isEmpty(pu.getUserPassword()))
			throw new BusinessException(CURR_USER_NOT_IN);
		String uPwd = pu.getUserPassword();
		if (!new BCryptPasswordEncoder().matches(oldPwd,uPwd))
			throw new BusinessException(OLD_PASSWORD_NOT_EQUALS);
		if (StringUtil.isEmpty(newOnePwd) || StringUtil.isEmpty(newTowPwd))
			throw new BusinessException(NEW_PASSWORD_NULL);
		if (!newOnePwd.equals(newTowPwd))
			throw new BusinessException(NEW_PASSWORD_NOT_EQUALS);
		pu.setUserPassword(new BCryptPasswordEncoder(11).encode(newOnePwd));
	}

	@Transactional
	public void resetPwd(Integer uId) throws BusinessException{
		if (uId == null)
			throw new BusinessException(USERLOGIN_ACCOUNT_ID_NOT_IN);
		PubUser pu = usersService.findOne(uId);
		if (StringUtil.isEmpty(pu) || StringUtil.isEmpty(pu.getUserPassword()))
			throw new BusinessException(CURR_USER_NOT_IN);
		pu.setUserPassword(StringUtil.bCryptPasswordEncoder("12345678"));
	}

	@Transactional
	public void updateUser_UserInfo(Integer uId, Integer uiId) throws BusinessException{
		PubUser pu = usersService.findOne(uId);
		if (StringUtil.isEmpty(pu) || pu.getUId() == null)
			throw new BusinessException(CURR_USER_NOT_IN);
		UserInfo ui = userInfoService.findOne(uiId);
		if (StringUtil.isEmpty(ui) || ui.getUserInfoId() == null)
			throw new BusinessException(CURR_USER_NOT_IN);
		pu.setUserInfo(ui);
	}

	/**
	 * 查询所有家族成员信息
	 * 
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
	public TableRquestData getUsersPagination(TableRquestData tableRquestData, final String search){
		int pageNumber = tableRquestData.getStart();
		int pageSize = tableRquestData.getLength();
		if (pageSize < 0)
			pageSize = userInfoService.findAll().size();
		Page<UserInfo> userPage = userInfoService.findAll(new Specification<UserInfo>(){

			@Override
			public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> cq, CriteriaBuilder cb){
				if (StringUtil.isEmpty(search)){
					return null;
				}else{
					Predicate predicate1 = cb.like(root.get("userName").as(String.class),"%" + search + "%");
					if (search.equals("男") || search.equals("女") || search.equals("未知")){
						Predicate predicate2 = cb.equal(root.get("userSex").as(int.class),search.equals("男") ? "1" : search.equals("女") ? "2" : "3");
						return cb.or(predicate1,predicate2);
					}else{
						return cb.like(root.get("userName").as(String.class),"%" + search + "%");
					}
				}
			}
		},new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "createDate")));
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		List<UserInfo> puL = userPage.getContent();
		for (UserInfo ui:puL){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userInfoId",StringUtil.isEmpty(ui.getUserInfoId()) ? "" : ui.getUserInfoId());
			map.put("userName",StringUtil.isEmpty(ui.getUserName()) ? "" : ui.getUserName());
			map.put("userPhone",StringUtil.isEmpty(ui.getUserPhone()) ? "" : ui.getUserPhone());
			map.put("createDate",StringUtil.isEmpty(ui.getCreateDate()) ? "" : UtilFactory.getSysDate().DateToString(ui.getCreateDate()));
			map.put("userSex",StringUtil.isEmpty(ui.getUserSex()) ? "" : ui.getUserSex() == 1 ? "男" : ui.getUserSex() == 2 ? "女" : "未知");
			mapData.add(map);
		}
		tableRquestData.setRecordsFiltered(userPage.getTotalElements());
		tableRquestData.setRecordsTotal(userPage.getTotalElements());
		tableRquestData.setData(mapData);
		return tableRquestData;
	}

	/**
	 * 账号删除 超级管理员权限才可以 如果有发布前台内容 则不能删除--（新闻、家族关系、家族图片、财务、视频、留言、祠堂、文化）
	 * 
	 * @param uId
	 * @throws BusinessException
	 */
	@Transactional
	public void removeAccount(Integer uId) throws BusinessException{
		if (isAuthority(uId))
			throw new BusinessException(USERLOGIN_ACCOUNT_REMOVE);
		PubUser pu = usersService.findOne(uId);
		if (StringUtil.isEmpty(pu) || pu.getUId() == null)
			throw new BusinessException(CURR_USER_NOT_IN);
		List<PubUsersRole> pur = usersRolesService.findByPubUser(pu);
		for (PubUsersRole pubUsersRole:pur){
			usersRolesService.delete(pubUsersRole);
		}
		usersService.delete(uId);
	}

	/**
	 * 权限判断
	 * 
	 * @return
	 */
	private boolean isAuthority(Integer uId){
		Collection roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		PubUser pubUser = UtilFactory.getSpringContext().getUserInfoContext().getUser();
		boolean bl = true;
		for (Object object:roles){
			if (uId == pubUser.getUId() || "ROLE_SYSTEM".equals(object.toString()) || "ROLE_DEVELOPMENT".equals(String.valueOf(object))){
				bl = false;
				break;
			}
		}
		return bl;
	}

	@Transactional
	public void changeUserFlag(PubUser pubUser){
		pubUser = usersService.findOne(pubUser.getUId());
		if (!StringUtil.isEmpty(pubUser) && !StringUtil.isEmpty(pubUser.getUId())){
			Integer uFlag = pubUser.getuFlag();
			uFlag = uFlag == null || uFlag == 0 ? 1 : 0;
			pubUser.setuFlag(uFlag);
		}
	}

	/**
	 * 检验账号的唯一性
	 */
	public boolean isUserAccount(String userAccount, Integer uId){
		try{
			if (uId == null || uId == 0){
				return StringUtil.isEmpty(usersService.findOneByUserAccount(userAccount));
			}else{
				uId = uId == null ? 0 : uId;
				return StringUtil.isEmpty(usersService.findOneByUserAccountAndUIdNot(userAccount,uId));
			}
		}catch (NonUniqueResultException e){
			return false;
		}catch (Exception e){
			return false;
		}
	}

	public PubUser getOnePubUser(Integer uId){
		return usersService.findOne(uId);
	}
}