package sys.set.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.PubUsersRole;
import sys.model.UserInfo;
import sys.model.controller.FileEntity;
import sys.model.controller.TableRquestData;
import sys.set.service.RolesService;
import sys.set.service.UserInfoService;
import sys.set.service.UsersRolesService;
import sys.set.service.UsersService;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_USERS)
@Transactional(readOnly = true)
public class IUsersServiceImpl {
    protected final Log _Log = LogFactory.getLog(getClass());
    private static final String DATA_MISSING = "用户参数丢失！";
    private static final String USER_NOT_IN = "用户不存在！";
    private static final String USER_IN = "用户已存在！";
    private static final String EMAIL_IN = "邮箱地址已存在！";
    private static final String WEIXIN_IN = "微信账号已存在！";
    private static final String QQ_IN = "QQ账号已存在！";
    private static final String PHONE_IN = "手机号码已存在！";
    private static final String FATHER_INFO_NOT_IN = "系统查询不到父亲信息";
    private static final String LOGIN_ROLE_NOT_KNOW = "未知的的登录角色！";
    private static final String USER_DETAIL_INFO_NOT_IN = "用户详细信息不存在!";
    private static final String USER_DETAIL_ILLEGAL = "当前用户信息异常!无法修改头像！";
    private static final String USER_CHILD_FATHER_EQUALS = "当前用户的父亲不能为自己！";
    private static final String USERLOGIN_ACCOUNT_DEL = "没有权限删除！";
    Object[][] eUserinfo = { { "userName", "姓名", "user_name" }, { "fatherName", "父亲名称", "user_info_id" },
	    { "userAge", "年龄", "user_age" }, { "userIdentityCard", "身份证号", "user_identityCard" },
	    { "userSex", "性别", "user_sex" }, { "userQq", "QQ号码", "user_qq" }, { "userWeixin", "微信号码", "user_weixin" },
	    { "userEmail", "邮箱地址", "user_email" }, { "userPhone", "手机号码", "user_phone" },
	    { "userBornDate", "出生日期", "user_born_date" }, { "maritalStatus", "婚姻状况", "marital_status" },
	    { "spouseName", "配偶名称", "spouse_name" }, { "homeAddress", "家庭地址", "home_address" },
	    { "tribalRegion", "宗族地区", "tribal_region" }, { "celebrityFlag", "是否名人", "celebrity_flag" },
	    { "userEnabled", "是否禁用", "user_enable" },
	    // {"headImageFolder","头像","head_image_folder"},
	    // {"userFlag","状态","user_flag"},
	    { "userDesc", "个人简介", "user_desc" }, { "remarks", "备注", "remarks" },
	    { "userAccount", "登录账号", "user_account" }, { "userPassword", "登录账号", "user_password" },
	    { "userRole", "角色", "user_role" } };

    @Resource
    UserInfoService userInfoService;

    @Resource
    UsersService usersService;

    @Resource
    UsersRolesService usersRolesService;

    @Resource
    RolesService rolesService;

    /**
     * 新增
     * 
     * @param map
     * @throws SQLException
     * 
     */
    @Transactional
    public void addNew(UserInfo userInfo, String fatherId) throws BusinessException {
	boolean bl = checkAddAll(userInfo);
	userInfo.setCreateDate(new Date());
	userInfo.setUserFlag(1);
	userInfo.setUserCurrFlag(2);// 区分前后台添加
	userInfo.setUserName(userInfo.getUserName().trim());
	userInfo = userInfoService.save(userInfo);
	if (!StringUtil.isEmpty(fatherId) && fatherId.matches("[0-9]+")) {
	    UserInfo fatherUserInfo = userInfoService.findOne(Integer.parseInt(fatherId));
	    if (StringUtil.isEmpty(fatherUserInfo)) {
		throw new BusinessException(FATHER_INFO_NOT_IN);
	    }
	    if (userInfo.getUserName().equals(fatherUserInfo.getUserName()))
		throw new BusinessException(USER_CHILD_FATHER_EQUALS);
	    userInfo.setFatherUserInfo(fatherUserInfo);
	}
    }

    /**
     * 
     * @param filterStatement
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws BusinessException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional
    public void addNewBatch() throws IOException, IllegalArgumentException, IllegalAccessException, BusinessException {
	List<Map<String, Object>> list = UtilFactory.getSpringContext().getUserInfoContext().getList();

	int flag = 0;
	for (Map<String, Object> map : list) {
	    flag = flag + 1;
	    UserInfo userInfo = new UserInfo();
	    UserInfo fatherUserInfo = new UserInfo();
	    PubUser pubUser = new PubUser();
	    PubRole pubRole = new PubRole();
	    Class clnUserInfo = userInfo.getClass();
	    Field[] fields = clnUserInfo.getDeclaredFields();
	    for (int i = 0; i < eUserinfo.length; i++) {
		String fieldVal = String.valueOf(map.get(eUserinfo[i][0]));
		for (int j = 0; j < fields.length; j++) {
		    fields[j].setAccessible(true);
		    String type = String.valueOf(fields[j].getType());
		    String field = fields[j].getName();
		    // 族员信息
		    if (eUserinfo[i][0].equals(field)) {
			if (type.endsWith("Integer") || type.endsWith("int")) {
			    if ("celebrityFlag".equals(field) || "userEnabled".equals(field)) {
				fieldVal = "是".equals(fieldVal) || "1".equals(fieldVal) ? "1" : "0";
			    } else if ("maritalStatus".equals(field)) {
				fieldVal = "已婚".equals(fieldVal) || "2".equals(fieldVal) ? "2" : "1";
			    } else if ("userSex".equals(field)) {
				fieldVal = "男".equals(fieldVal) || "1".equals(fieldVal) ? "1"
					: "女".equals(fieldVal) || "2".equals(fieldVal) ? "2" : "3";
			    } else
				fields[j].set(userInfo, Integer.parseInt(
					StringUtil.isEmpty(fieldVal) || fieldVal.matches("[0-9]+") ? "0" : fieldVal));
			} else if (type.endsWith("Date")) {
			    fields[j].set(userInfo, StringUtil.isEmpty(fieldVal) ? null : String.valueOf(fieldVal));
			} else {
			    if ("userName".equals(field) && StringUtil.isEmpty(fieldVal)) {
				throw new BusinessException("第[" + flag + "]行" + USER_NOT_IN);
			    }
			    fields[j].set(userInfo, fieldVal);
			}
		    }
		    // 族员父亲
		    if ("fatherName".equals(eUserinfo[i][0])) {
			fatherUserInfo.setUserName(fieldVal);
		    }
		    // 账号
		    if ("userAccount".equals(eUserinfo[i][0])) {
			pubUser.setUserAccount(
				StringUtil.isEmpty(String.valueOf(fieldVal)) ? null : String.valueOf(fieldVal).trim());
		    }
		    if ("userPassword".equals(eUserinfo[i][0])) {
			pubUser.setUserPassword(fieldVal);
		    }
		    if ("userRole".equals(eUserinfo[i][0])) {
			fieldVal = "普通".equals(fieldVal) || "ROLE_USER".equals(fieldVal) || "普通用户".equals(fieldVal)
				? "普通用户"
				: "会员".equals(fieldVal) || "ROLE_VIP".equals(fieldVal) ? "会员"
					: "管理员".equals(fieldVal) || "ROLE_ADMIN".equals(fieldVal) ? "管理员" : "普通用户";
			pubRole.setRoleName(fieldVal);
		    }
		}
	    }
	    if (isUserName(userInfo.getUserName(), userInfo.getUserInfoId())) {
		if (!StringUtil.isEmpty(fatherUserInfo) && !StringUtil.isEmpty(fatherUserInfo.getUserName())) {
		    fatherUserInfo = userInfoService.findOneByUserName(fatherUserInfo.getUserName());
		    if (!StringUtil.isEmpty(fatherUserInfo) && !StringUtil.isEmpty(fatherUserInfo.getUserInfoId())) {
			UserInfo fFatherUserInfo = fatherUserInfo.getFatherUserInfo();
			// 限制父亲的父亲不是父亲的儿子
			if (StringUtil.isEmpty(fFatherUserInfo) || !StringUtil.isEmpty(fFatherUserInfo)
				&& !userInfo.getUserName().equals(fFatherUserInfo.getUserName())) {
			    userInfo.setFatherUserInfo(fatherUserInfo);
			}
		    }
		}

		if (!StringUtil.isEmpty(userInfo.getUserQq())
			&& !isUserQq(userInfo.getUserQq(), userInfo.getUserInfoId())) {
		    throw new BusinessException("第[" + flag + "]行" + QQ_IN);
		}
		if (!StringUtil.isEmpty(userInfo.getUserWeixin())
			&& !isUserWeixin(userInfo.getUserWeixin(), userInfo.getUserInfoId())) {
		    throw new BusinessException("第[" + flag + "]行" + WEIXIN_IN);
		}
		if (!StringUtil.isEmpty(userInfo.getUserPhone())
			&& !isUserPhone(userInfo.getUserPhone(), userInfo.getUserInfoId())) {
		    throw new BusinessException("第[" + flag + "]行" + PHONE_IN);
		}
		userInfoService.save(userInfo);
		pubUser.setUserInfo(userInfo);
	    } else {
		throw new BusinessException("第[" + flag + "]行" + USER_IN);
	    }
	    if (!StringUtil.isEmpty(pubUser.getUserAccount())
		    && StringUtil.isEmpty(usersService.findOneByUserAccount(pubUser.getUserAccount()))) {
		pubUser.setUserPassword(StringUtil.bCryptPasswordEncoder(pubUser.getUserPassword()));
		pubUser.setuFlag(userInfo.getUserFlag());
		usersService.save(pubUser);
		EntityManager em = UtilFactory.getSpringContext().getEntityManagerFactory().createEntityManager();
		if (list.size() % 30 == 0) {
		    em.flush();
		    em.clear();
		}
		if (!StringUtil.isEmpty(pubRole.getRoleName())) {
		    pubRole = rolesService.findOneByRoleName(pubRole.getRoleName().trim());
		    if (!StringUtil.isEmpty(pubRole) && pubRole.getRoleId() != 0) {
			PubUsersRole pubUsersRole = new PubUsersRole();
			pubUsersRole.setPubRole(pubRole);
			pubUsersRole.setPubUser(pubUser);
			usersRolesService.save(pubUsersRole);
		    } else {
			throw new BusinessException("第[" + flag + "]行" + LOGIN_ROLE_NOT_KNOW);
		    }
		}
	    }
	}
	UtilFactory.getSpringContext().getUserInfoContext().setList(null);
    }

    public List<Map<String, Object>> getExcelData(InputStream fileInputStream)
	    throws NumberFormatException, IllegalArgumentException, IllegalAccessException {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	try {
	    List<Map<String, Object>> lists = UtilFactory.getSysExcel().readExcelData(fileInputStream, 0, 0,
		    eUserinfo.length, ",", eUserinfo.length, eUserinfo);
	    int flag = 0;
	    // StringBuilder sb=new StringBuilder();
	    // StringBuilder warning=new StringBuilder();
	    for (Map<String, Object> map : lists) {
		flag = flag + 1;
		Map<String, Object> rspMap = Maps.newHashMap();
		Vector v = new Vector();
		v.addAll(map.values());
		rspMap.put("excelData", v);
		// for (int i = 0; i < eUserinfo.length; i++) {
		// String field=String.valueOf(eUserinfo[i][0]);
		// String fieldVal=String.valueOf(map.get(eUserinfo[i][0]));
		// if("celebrityFlag".equals(field)&&!"是".equals(fieldVal)&&!"否".equals(fieldVal))
		// warning.append("第["+flag+"]行"+"是否名人强制转化为否");
		// if("userEnabled".equals(field)&&!"是".equals(fieldVal)&&!"否".equals(fieldVal))
		// warning.append("第["+flag+"]行"+"是否禁用强制转化为否");
		// if("userName".equals(field)){
		// sb.append("第["+flag+"]行"+USER_NOT_IN);
		// }
		// if(!"userName".equals(field)&&!isUserName(fieldVal, 0)){
		// sb.append("第["+flag+"]行"+USER_IN);
		// }
		//
		// }
		// rspMap.put("errorFlag",sb);
		// rspMap.put("warningFlag",warning);
		list.add(rspMap);
	    }
	    lists.remove(0);
	    UtilFactory.getSpringContext().getUserInfoContext().setList(lists);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    _Log.error("getExcelData()", e);
	}
	return list;
    }

    /**
     * 查询所有家族成员信息
     * 
     * @param tableRquestData
     * @param search
     * @return
     */
    public TableRquestData getUserPagination(TableRquestData tableRquestData, final String search) {
	int pageNumber = tableRquestData.getStart();
	int pageSize = tableRquestData.getLength();
	if (pageSize < 0)
	    pageSize = userInfoService.findAll().size();
	Page<UserInfo> userPage = userInfoService.findAll(new Specification<UserInfo>() {
	    @Override
	    public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		if (StringUtil.isEmpty(search)) {
		    return null;
		} else {
		    Predicate predicate1 = cb.like(root.get("userName").as(String.class), "%" + search + "%");
		    if (search.equals("男") || search.equals("女") || search.equals("未知")) {
			Predicate predicate2 = cb.equal(root.get("userSex").as(int.class),
				search.equals("男") ? "1" : search.equals("女") ? "2" : "3");
			return cb.or(predicate1, predicate2);
		    } else {
			return cb.like(root.get("userName").as(String.class), "%" + search + "%");
		    }
		}
	    }

	}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "createDate")));
	List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
	List<UserInfo> puL = userPage.getContent();
	for (UserInfo ui : puL) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("userInfoId", StringUtil.isEmpty(ui.getUserInfoId()) ? "" : ui.getUserInfoId());
	    map.put("userName", StringUtil.isEmpty(ui.getUserName()) ? "" : ui.getUserName());
	    map.put("userFaterName",
		    StringUtil.isEmpty(ui.getFatherUserInfo()) ? ""
			    : StringUtil.isEmpty(ui.getFatherUserInfo().getUserName()) ? ""
				    : ui.getFatherUserInfo().getUserName());
	    map.put("userPhone", StringUtil.isEmpty(ui.getUserPhone()) ? "" : ui.getUserPhone());
	    map.put("createDate", StringUtil.isEmpty(ui.getCreateDate()) ? ""
		    : UtilFactory.getSysDate().DateToString(ui.getCreateDate()));
	    map.put("userStatus", ui.getUserFlag() == 2 || ui.getUserFlag() == 3 ? "审核" : "正常");

	    map.put("userSex", StringUtil.isEmpty(ui.getUserSex()) ? ""
		    : ui.getUserSex() == 1 ? "男" : ui.getUserSex() == 2 ? "女" : "未知");
	    mapData.add(map);
	}
	tableRquestData.setRecordsFiltered(userPage.getTotalElements());
	tableRquestData.setRecordsTotal(userPage.getTotalElements());
	tableRquestData.setData(mapData);
	return tableRquestData;
    }

    /**
     * 查看根据主键
     * 
     * @param userInfoId
     * @return
     */
    public UserInfo getUserInfo(int userInfoId) {

	return userInfoService.findOne(userInfoId);
    }

    /**
     * 修改当前用户的头像
     * 
     * @throws IOException
     * @throws IllegalStateException
     */
    @Transactional
    public void updateCurrHeadImg(HttpServletRequest request, Map<String, Object> map, String headImageName)
	    throws BusinessException, IllegalStateException, IOException {
	List<FileEntity> fileEntities = FileUpload.writeFile(request, Sys_Const.FAMILY_HEAD_IMAGE_FILE_FOLDER_PATH,
		headImageName);
	PubUser user = UtilFactory.getSpringContext().getUserInfoContext().getUser();
	UserInfo ui = user.getUserInfo();
	if (!StringUtil.isEmpty(ui)) {
	    if (!StringUtil.isEmpty(fileEntities) && fileEntities.size() != 0
		    && !StringUtil.isEmpty(fileEntities.get(0)) && !StringUtil.isEmpty(fileEntities.get(0).getName())) {
		ui = userInfoService.findOne(ui.getUserInfoId());
		if (StringUtil.isEmpty(ui) || StringUtil.isEmpty(ui.getUserInfoId()))
		    throw new BusinessException(USER_DETAIL_ILLEGAL);
		ui.setHeadImageName(fileEntities.get(0).getName());
		String imgSrc = MessageFormat.format(Sys_Const.FAMILY_HEAD_IMAGE_FILE_FOLDER_PATH, "/") + "/"
			+ ui.getHeadImageName();
		map.put("result", BaseHint.RESULT_TRUE);
		map.put("imgPath", imgSrc);
		map.put("headImageName", fileEntities.get(0).getName());
		map.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
		user.setUserInfo(ui);
	    }
	}
    }

    /**
     * 获取父亲信息
     * 
     * @param userInfoId
     * @return
     */
    public Map<String, Object> getUserInfoParent(int userInfoId) {
	Map<String, Object> map = Maps.newHashMap();
	UserInfo ui = getUserInfo(userInfoId);
	if (!StringUtil.isEmpty(ui) && !StringUtil.isEmpty(ui.getFatherUserInfo())) {
	    ui = ui.getFatherUserInfo();
	    map.put("id", ui.getUserInfoId());
	    map.put("text", ui.getUserName());
	    map.put("userBornDate", ui.getUserBornDate());
	    map.put("userAge", ui.getUserAge());
	    map.put("spouseName", ui.getSpouseName());
	}
	return map;
    }

    /**
     * 检验提交的成员名称的唯一性
     */
    public boolean isUserName(String userName, Integer uId) {
	try {
	    if (uId == null || uId == 0) {
		return StringUtil.isEmpty(userInfoService.findOneByUserName(userName));
	    } else {
		uId = uId == null ? 0 : uId;
		return StringUtil.isEmpty(userInfoService.findOneByUserNameAndUserInfoIdNot(userName, uId));
	    }
	} catch (NonUniqueResultException e) {
	    return false;
	} catch (Exception e) {
	    return false;
	}

    }

    /**
     * 检查用户信息邮箱的唯一
     * 
     * @param userEmail
     * @param userInfoId
     * @return
     */
    public boolean isUserEmail(String userEmail, Integer userInfoId) {
	try {
	    if (StringUtil.isEmpty(userInfoId) || userInfoId == 0) {
		return StringUtil.isEmpty(userInfoService.findOneByUserEmail(userEmail));
	    } else {
		userInfoId = StringUtil.isEmpty(userInfoId) ? 0 : userInfoId;
		return StringUtil.isEmpty(userInfoService.findOneByUserEmailAndUserInfoIdNot(userEmail, userInfoId));
	    }
	} catch (NonUniqueResultException e) {
	    return false;
	} catch (Exception e) {
	    return false;
	}

    }

    /**
     * 检查qq的唯一
     * 
     * @param userQq
     * @param userInfoId
     * @return
     */
    public boolean isUserQq(String userQq, Integer userInfoId) {
	try {
	    if (StringUtil.isEmpty(userInfoId) || userInfoId == 0) {
		return StringUtil.isEmpty(userInfoService.findOneByUserQq(userQq));
	    } else {
		userInfoId = StringUtil.isEmpty(userInfoId) ? 0 : userInfoId;
		return StringUtil.isEmpty(userInfoService.findOneByUserQqAndUserInfoIdNot(userQq, userInfoId));
	    }
	} catch (NonUniqueResultException e) {
	    return false;
	} catch (Exception e) {
	    return false;
	}

    }

    /**
     * 检查手机号的唯一
     * 
     * @param userPhone
     * @param userInfoId
     * @return
     */
    public boolean isUserPhone(String userPhone, Integer userInfoId) {
	try {
	    if (StringUtil.isEmpty(userInfoId) || userInfoId == 0) {
		return StringUtil.isEmpty(userInfoService.findOneByUserPhone(userPhone));
	    } else {
		userInfoId = StringUtil.isEmpty(userInfoId) ? 0 : userInfoId;
		return StringUtil.isEmpty(userInfoService.findOneByUserPhoneAndUserInfoIdNot(userPhone, userInfoId));
	    }
	} catch (NonUniqueResultException e) {
	    return false;
	} catch (Exception e) {
	    return false;
	}

    }

    public List<Map<String, Object>> getInitFather(final String searchParams, int pageLimit) {
	List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
	Page<UserInfo> pageUserInfo = userInfoService.findAll(new Specification<UserInfo>() {
	    @Override
	    public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> arg1, CriteriaBuilder cb) {
		Predicate userFlag = cb.notEqual(root.get("userFlag").as(int.class), 3);
		return cb.and(userFlag, cb.like(root.get("userName").as(String.class), "%" + searchParams + "%"));
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
     * 检查微信号的唯一
     * 
     * @param userWeixin
     * @param userInfoId
     * @return
     */
    public boolean isUserWeixin(String userWeixin, Integer userInfoId) {
	if (StringUtil.isEmpty(userInfoId) || userInfoId == 0) {
	    return StringUtil.isEmpty(userInfoService.findOneByUserWeixin(userWeixin));
	} else {
	    userInfoId = StringUtil.isEmpty(userInfoId) ? 0 : userInfoId;
	    return StringUtil.isEmpty(userInfoService.findOneByUserWeixinAndUserInfoIdNot(userWeixin, userInfoId));
	}

    }

    /**
     * 检验新增用户信息时的参数
     * 
     * @param map
     * @param user
     * @return
     */
    public boolean checkAddAll(UserInfo ui) {
	if (!StringUtil.isEmpty(ui.getUserEmail())) {
	    if (!isUserEmail(ui.getUserEmail(), ui.getUserInfoId())) {
		throw new BusinessException(EMAIL_IN);
	    }
	} else {
	    ui.setUserEmail(null);
	}
	if (!StringUtil.isEmpty(ui.getUserWeixin())) {
	    if (!isUserWeixin(ui.getUserWeixin(), ui.getUserInfoId())) {
		throw new BusinessException(WEIXIN_IN);
	    }
	} else {
	    ui.setUserWeixin(null);
	}
	if (!StringUtil.isEmpty(ui.getUserPhone())) {
	    if (!isUserPhone(ui.getUserPhone(), ui.getUserInfoId())) {
		throw new BusinessException(PHONE_IN);
	    }
	} else {
	    ui.setUserPhone(null);
	}
	if (!StringUtil.isEmpty(ui.getUserQq())) {
	    if (!isUserQq(ui.getUserQq(), ui.getUserInfoId())) {
		throw new BusinessException(QQ_IN);
	    }
	} else {
	    ui.setUserQq(null);
	}
	if (!StringUtil.isEmpty(ui.getUserName())) {
	    if (!isUserName(ui.getUserName(), ui.getUserInfoId())) {
		throw new BusinessException(USER_IN);
	    }
	} else {
	    throw new BusinessException(DATA_MISSING);
	}
	return true;
    }

    /**
     * 删除用户信息
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    @Transactional
    public void delUserInfo(UserInfo userInfo) throws BusinessException {
	if (isAuthority())
	    throw new BusinessException(USERLOGIN_ACCOUNT_DEL);
	if (StringUtil.isEmpty(userInfo) || StringUtil.isEmpty(userInfo.getUserInfoId()))
	    throw new BusinessException(DATA_MISSING);
	userInfo = userInfoService.findOne(userInfo.getUserInfoId());
	if (StringUtil.isEmpty(userInfo) || StringUtil.isEmpty(userInfo.getUserInfoId()))
	    throw new BusinessException(USER_DETAIL_INFO_NOT_IN);
	userInfoService.delete(userInfo);
    }

    /**
     * 权限判断
     * 
     * @return
     */
    private boolean isAuthority() {
	Collection roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	boolean bl = true;
	for (Object object : roles) {
	    if ("ROLE_SYSTEM".equals(object.toString())) {
		bl = false;
		break;
	    }
	}
	return bl;
    }
}
