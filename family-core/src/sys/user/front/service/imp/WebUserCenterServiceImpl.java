package sys.user.front.service.imp;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.PubUser;
import sys.model.UserInfo;
import sys.set.service.UserInfoService;
import sys.set.service.UsersService;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.Web_SER_USERS)
@Transactional(readOnly = true)
public class WebUserCenterServiceImpl {
	
	@Resource 
	private UsersService usersService;
	
	@Resource
	private UserInfoService userInfoService;
	
	//判断密码新密码与原密码是否相同
	public Boolean passWordSame(String oldPwd){
		
		//查下登陆账号
		PubUser pu=usersService.findOne(UtilFactory.getSpringContext().getUserInfoContext().getUser().getUId());
		
		String uPwd=pu.getUserPassword();
		return new BCryptPasswordEncoder().matches(oldPwd, uPwd);
	}
	
	
	//修改密码
	@Transactional(readOnly = false)
	public void updatePassWord(String newPass){
		//查下登陆账号
		PubUser pu=usersService.findOne(UtilFactory.getSpringContext().getUserInfoContext().getUser().getUId());
		pu.setUserPassword(new BCryptPasswordEncoder(11).encode(newPass));
	}
	
	//修改个人信息
	@Transactional
	public void updatePersonInfo(UserInfo userInfo) throws BusinessException, IllegalArgumentException, IllegalAccessException{
		if(StringUtil.isEmpty(userInfo)||StringUtil.isEmpty(userInfo.getUserInfoId()))throw new BusinessException("未知参数信息");
		UserInfo userInfoOld=userInfoService.findOne(userInfo.getUserInfoId());
		if(StringUtil.isEmpty(userInfoOld)||StringUtil.isEmpty(userInfoOld.getUserInfoId()))throw new BusinessException("查询不到信息");
		
//		Class<? extends UserInfo> userInfoOldCln=userInfoOld.getClass();
//		Field[] fieldUserInfoOlds=userInfoOldCln.getDeclaredFields();
//		Class<? extends UserInfo> userInfoCln=userInfo.getClass();
//		Field[] fieldUserInfos=userInfoCln.getDeclaredFields();
//		for (int i = 0; i < fieldUserInfoOlds.length; i++) {
//			Field fieldUserInfoOld=fieldUserInfoOlds[i];
//			fieldUserInfoOld.setAccessible(true);
//			Field fieldUserInfo=fieldUserInfos[i];
//			fieldUserInfo.setAccessible(true);
//			if(!StringUtil.isEmpty(fieldUserInfo.get(userInfo)))
//			fieldUserInfoOld.set(userInfoOld, fieldUserInfo.get(userInfo));
//		}
		int i=userInfoService.updateUserInfo(userInfo.getUserBornDate(), userInfo.getUserDesc(), 
				userInfo.getUserEmail(),userInfo.getUserName(), userInfo.getUserPhone(),
				userInfo.getUserQq(), userInfo.getUserSex(), userInfo.getUserAge(), 
				userInfo.getSpouseName(), userInfo.getMaritalStatus(), userInfo.getHomeAddress(),
				userInfo.getUserInfoId());
		UserInfo userInfonew =userInfoService.findOne(userInfo.getUserInfoId());
		if(!StringUtil.isEmpty(userInfonew)&&!StringUtil.isEmpty(userInfonew.getUserInfoId()))
		UtilFactory.getSpringContext().getUserInfoContext().getUser().setUserInfo(userInfonew);
		
	}

}
