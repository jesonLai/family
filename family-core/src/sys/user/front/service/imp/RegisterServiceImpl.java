package sys.user.front.service.imp;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.PubUsersRole;
import sys.model.UserInfo;
import sys.model.form.UserInfoForm;
import sys.set.service.RolesService;
import sys.set.service.UserInfoService;
import sys.set.service.UsersRolesService;
import sys.set.service.UsersService;
import sys.util.StringUtil;

@Repository(SpringAnnotationDef.SER_REGISTER_FRONT)
@Transactional(readOnly = true)
public class RegisterServiceImpl {
	private static final String INSERT_SUCCESS = "提交成功！";
	private static final String INSERT_FAIL = "提交失败！";
	@Resource
	UserInfoService userInfoService;
	@Resource
	UsersService usersService;
	@Resource
	UsersRolesService usersRolesService;
	@Resource
	 RolesService rolesService;
	/**
	 * 新增注册
	 * uFlag:账号的状态（0=待审核 1=通过 2=没有通过）
	 * accountType:登录的类型（1=邮箱 2=手机 3=qq 4=微信 5=自定义）
	 * @param map
	 * @throws BusinessException
	 * 
	 */
	@Transactional
	public void addNew(Map<String, Object> map,PubUser pu) throws BusinessException {
		String userAccount=pu.getUserAccount();
		if(StringUtil.isEmpty(pu)||StringUtil.isAllEmpty(userAccount)){
			map.put(BaseHint.MESSAGE, "登录账号信息丢失！");
			return;
		}
		if(StringUtil.isEmpty(pu.getUserPassword())||pu.getUserPassword().length()<7){
			map.put(BaseHint.MESSAGE, "密码至少6位以上!");
			return;
		}
		if(!checkUserAccount(userAccount)){
			map.put(BaseHint.MESSAGE, "账号已存在!");
			return;
		}
		PubRole pubRole=rolesService.findOneByRoleSysName("ROLE_USER");
		if(StringUtil.isEmpty(pubRole)){
			map.put(BaseHint.MESSAGE, "角色不存在[ROLE_USER]");
			return;
		}
		pu.setUserAccount(userAccount.trim());
		pu.setUserPassword(StringUtil.bCryptPasswordEncoder(pu.getUserPassword()));
		pu.setAccountType(5);
		pu.setuFlag(1);
		pu=usersService.save(pu);//添加账号
		
		
		PubUsersRole pubUsersRole=new PubUsersRole();
		
			
		pubUsersRole.setPubRole(pubRole);
		pubUsersRole.setPubUser(pu);
		usersRolesService.save(pubUsersRole);
		
		if(pu.getUId()>0)
			map.put(BaseHint.MESSAGE, "提交成功");
		else
			map.put(BaseHint.MESSAGE, "提交失败");
	}
	/**
	 * 检查登陆名称是否被注册
	 * @param userAccount
	 * @return
	 */
	public boolean checkUserAccount(String userAccount){
		PubUser pu=usersService.findOneByUserAccount(userAccount);
		boolean bl=StringUtil.isEmpty(pu)||StringUtil.isEmpty(pu.getUId())?true:false;
		return bl;
	}
	
	/**
	 * 新增实名认证
	 * userFlag:用户的状态（0=提交成功，1=正常使用 2=没有通过反馈中 3=待审核 ）
	 * userCurrFlag:当前操作者（注册时指定是谁提交的 0=关联添加 1=这个人  2=不是注册的 是系统后台提交的）
	 * uFlag:账号的状态（0=待审核 1=通过 2=没有通过）
	 * 提交审核完成后不能修改
	 * 未审核之前可以进行修改
	 * 只可以提交一次
	 * @param map
	 * @throws SQLException
	 * 
	 */
	@Transactional
	public void addRealName(Map<String, Object> map,UserInfoForm userInfoForm) throws BusinessException {
		if(StringUtil.isEmpty(userInfoForm)||userInfoForm.getUserInfoList().size()==0){
			map.put(BaseHint.MESSAGE, "认证信息丢失");
			return;
		}
		//提交者
		UserInfo userInfoChild=userInfoForm.getUserInfoList().get(0);
		//提交者父亲
		UserInfo userInfoFather=userInfoForm.getUserInfoList().get(1);
		if(StringUtil.isEmpty(userInfoChild.getUserName())){
			map.put(BaseHint.MESSAGE, "真实名称不允许为空");
			return;
		}
		if(!StringUtil.isEmpty(userInfoService.findOneByUserNameAndUserInfoIdNot(userInfoChild.getUserName(),userInfoChild.getUserInfoId()))){
			map.put(BaseHint.MESSAGE, "个人信息-->真实姓名已存在");
			return;
		}
		
		if(!StringUtil.isEmpty(userInfoFather.getUserName())&&!StringUtil.isEmpty(userInfoService.findOneByUserNameAndUserInfoIdNot(userInfoFather.getUserName(),userInfoFather.getUserInfoId()))){
			map.put(BaseHint.MESSAGE, "父亲信息-->真实姓名已存在");
			return;
		}
		
		
			
		//审核完不能重复审核
		if(!StringUtil.isEmpty(userInfoChild.getUserInfoId())){
			UserInfo f=userInfoService.findOne(userInfoChild.getUserInfoId());
			if(f.getUserFlag()==1){
				map.put(BaseHint.MESSAGE, "已审核");
				return;
			}
		}
	
		userInfoChild.setCreateDate(new Date());
		userInfoChild.setUserFlag(3);
		userInfoChild.setUserCurrFlag(1);//提交者标识
		userInfoChild=userInfoService.save(userInfoChild);
		//父亲信息是新增的才需要保存进数据库
		if(StringUtil.isEmpty(userInfoFather.getUserInfoId())){
			userInfoFather.setCreateDate(new Date());
			userInfoFather.setUserFlag(0);
			userInfoFather=userInfoService.save(userInfoFather);
		}
		
		if (userInfoChild.getUserInfoId() > 0) {
			map.put(BaseHint.MESSAGE, INSERT_SUCCESS);
			map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			map.put("USERINFOID", userInfoChild.getUserInfoId());
		} else {
			map.put(BaseHint.MESSAGE, INSERT_FAIL);
		}
	}
//	public Family findFamily(){
//		return familyService.findOneByUserChild(UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo());
//	}
	/**
	 * 检验提交的成员名称的唯一性
	 */
	public boolean isUserName(String userName,Integer uId) {
		try {
			if (uId==null||uId==0) {
				return StringUtil.isEmpty(userInfoService.findOneByUserName(userName));
			} else {
				uId=uId==null? 0 : uId;
				return StringUtil.isEmpty(userInfoService.findOneByUserNameAndUserInfoIdNot(userName, uId));
			}
		} catch (NonUniqueResultException e) {
			return false;
		} catch (Exception e) {
			return false;
		}

	}
	/**
	 * 已有成员选择
	 * @param familyNewsId
	 * @throws BusinessException
	 */
	public Map<String,Object> familyByUserNameColumn(String userName){
		userName=StringUtil.getEncoding(userName);
//		List<Object> list=Lists.newArrayList();
		Map<String,Object> map=Maps.newHashMap();
		map.put("query",userName);
		List<Map<String,Object>> dataList=Lists.newArrayList();
		for (UserInfo ui : userInfoService.findByUserNameContaining(userName)) {
			Map<String,Object> userInfoList=Maps.newHashMap();
			userInfoList.put("value", ui.getUserName());
			userInfoList.put("data", ui.getUserInfoId());
			dataList.add(userInfoList);
		}
		map.put("suggestions",dataList);
//		list.add(map);
		
		return map;
	}
	/**
	 *h获取
	 * @param id
	 * @return
	 */
	public UserInfo getUserInfo(Integer id){
		
		return userInfoService.findOne(id);
	}
}
