package sys.set.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

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
import sys.model.UserInfo;
import sys.model.controller.TableRquestData;
import sys.model.form.UserInfoForm;
import sys.set.service.UserInfoService;
import sys.set.service.UsersService;
import sys.util.EmailSendHint;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_REGISTER)
@Transactional(readOnly = true)
public class RegisterServiceImpl {
	private static final String REGISTER_NULL = "注册信息不为空,请填完带星号项！";
	private static final String INSERT_SUCCESS = "提交成功！";
	private static final String INSERT_FAIL = "提交失败！";
	@Resource
	UserInfoService userInfoService;
	@Resource
	UsersService usersService;
	/**
	 * 查询所有认证的信息
	 * userCurrFlag=1,第一级为当前提交者 再查提交者的父亲与爷爷
	 * userFlag:用户的状态（0=待审核，1=通过能正常使用 2=没有通过反馈中）
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
		public TableRquestData getUserInfoCheckPagination(TableRquestData tableRquestData, final String search) {
			int pageNumber = tableRquestData.getStart();
			int pageSize = tableRquestData.getLength();
			if (pageSize < 0)
				pageSize = userInfoService.findAll().size();
			Page<UserInfo> userPage = userInfoService.findAll(new Specification<UserInfo>() {
				@Override
				public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
					Predicate userCurrFlag = cb.equal(root.get("userCurrFlag").as(int.class), 1);
					Predicate userFlag = cb.equal(root.get("userFlag").as(int.class), 3);
					Predicate predicate = cb.and(userCurrFlag,userFlag);
					if (StringUtil.isEmpty(search)) {
						return predicate;
					} else {
						Predicate predicate1 = cb.like(root.get("userName").as(String.class), "%" + search + "%");
						if (search.equals("男") || search.equals("女") || search.equals("未知")) {
							Predicate predicate2 = cb.equal(root.get("userSex").as(int.class),
									search.equals("男") ? "1" : search.equals("女") ? "2" : "3");
							return cb.and(predicate, cb.or(predicate1, predicate2));
						} else {
							return cb.and(predicate,cb.like(root.get("userName").as(String.class), "%" + search + "%"));
						}
					}
				}

			}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "createDate")));
			List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
			List<UserInfo> puL = userPage.getContent();
			for (UserInfo ui : puL) {
				Map<String, Object> map = new HashMap<String, Object>();
//				Family family=familyService.findOneByUserChild(ui);
				map.put("userInfoId", StringUtil.isEmpty(ui.getUserInfoId()) ? "" : ui.getUserInfoId());
				map.put("userName", StringUtil.isEmpty(ui.getUserName()) ? "" : ui.getUserName());
				map.put("userPhone", StringUtil.isEmpty(ui.getUserPhone()) ? "" : ui.getUserPhone());
				map.put("createDate", StringUtil.isEmpty(ui.getCreateDate()) ? ""
						: UtilFactory.getSysDate().DateToString(ui.getCreateDate()));
				map.put("userSex", StringUtil.isEmpty(ui.getUserSex()) ? ""
						: ui.getUserSex() == 1 ? "男" : ui.getUserSex() == 2 ? "女" : "未知");
				map.put("userEmail", StringUtil.isEmpty(ui.getUserPhone()) ? "" : ui.getUserPhone());
				map.put("userFather", StringUtil.isEmpty(ui.getFatherUserInfo())?"":
						StringUtil.isEmpty(ui.getFatherUserInfo().getUserName())?"":
							ui.getFatherUserInfo().getUserName());
				map.put("userBornDate", StringUtil.isEmpty(ui.getUserBornDate()) ? "" : UtilFactory.getSysDate().DateToString(ui.getUserBornDate()));
				map.put("userAge", StringUtil.isEmpty(ui.getUserAge()) ? "" : ui.getUserAge());
				
				mapData.add(map);
			}
			tableRquestData.setRecordsFiltered(userPage.getTotalElements());
			tableRquestData.setRecordsTotal(userPage.getTotalElements());
			tableRquestData.setData(mapData);
			return tableRquestData;
		}
		/**
		 * 审核
		 * userFlag:用户的状态（0=待审核，1=通过能正常使用 2=没有通过反馈中）
		 * userCurrFlag:当前操作者（注册时指定是谁提交的 0=关联添加 1=这个人  2=不是注册的 是系统后台提交的）
		 * u_flag:账号的状态（0=待审核 1=通过 2=没有通过）
		 * @param map
		 * @throws SQLException
		 * 
		 */
//		@Transactional
//		public void addNew(Map<String, Object> map,UserInfoForm userInfoForm,PubUser pu) throws BusinessException {
//			if(StringUtil.isEmpty(userInfoForm)||userInfoForm.getUserInfoList().size()==0){
//				map.put(BaseHint.MESSAGE, REGISTER_NULL);
//				return;
//			}
//			if(StringUtil.isEmpty(pu)||StringUtil.isAllEmpty(pu.getUserAccount())){
//				map.put(BaseHint.MESSAGE, "登录账号信息丢失！");
//				return;
//			}
//			if(StringUtil.isEmpty(pu.getUserPassword())||pu.getUserPassword().length()<7){
//				map.put(BaseHint.MESSAGE, "密码至少6位以上!");
//				return;
//			}
//			if(!StringUtil.isEmpty(usersService.findOneByUserAccount(pu.getUserAccount()))){
//				map.put(BaseHint.MESSAGE, "账号已存在!");
//				return;
//			}
//			UserInfo userInfoChild=userInfoForm.getUserInfoList().get(0);
//			UserInfo userInfoFather=userInfoForm.getUserInfoList().get(1);
//			
//			
//			if(StringUtil.isEmpty(userInfoChild.getUserName())){
//				map.put(BaseHint.MESSAGE, "真实名称不允许为空!");
//				return;
//			}
//			
//			//如与数据库匹配就通过审核(自动审核：名称、身份证号)
//			//父亲信息会有冗余
//			//重复提交信息
//			String userIdentityCard=userInfoFather.getUserIdentityCard();
//			String userName=userInfoFather.getUserName();
//			pu.setUserPassword(StringUtil.bCryptPasswordEncoder(pu.getUserPassword()));
//			
//			UserInfo userInfoOldFather=userInfoService.findOneByUserNameAndUserIdentityCard
//					(StringUtil.isEmpty(userName)?"":userName,
//					 StringUtil.isEmpty(userIdentityCard)?"":userIdentityCard);
//			if(!StringUtil.isEmpty(userIdentityCard)&&!StringUtil.isEmpty(userName)&&
//					!StringUtil.isEmpty(userInfoOldFather)){
//				userInfoChild.setUserCurrFlag(1);
//				userInfoChild.setUserFlag(1);
//				
//				userInfoFather.setUserCurrFlag(1);
//				userInfoFather.setUserFlag(1);
//				
//				pu.setuFlag(1);
//				
//				userInfoChild=userInfoService.save(userInfoChild);
//				userInfoFather=userInfoService.save(userInfoFather);
//				pu.setUserInfo(userInfoChild);
//				usersService.save(pu);
//			}else{
//				pu.setUserPassword(StringUtil.bCryptPasswordEncoder(pu.getUserPassword()));
//				userInfoChild=userInfoService.save(userInfoChild);
//				userInfoFather=userInfoService.save(userInfoFather);
//				pu.setUserInfo(userInfoChild);
//				pu=usersService.save(pu);
//				userInfoChild.setFatherUserInfo(userInfoFather);
//			}
//			
//			if (userInfoChild.getUserInfoId() > 0) {
//				map.put(BaseHint.MESSAGE, INSERT_SUCCESS);
//				map.put("USERINFOID", userInfoChild.getUserInfoId());
//			} else {
//				map.put(BaseHint.MESSAGE, INSERT_FAIL);
//			}
//		}
		public UserInfo findOneByUserInfoId(UserInfo userInfo){
			return userInfoService.findOne(userInfo.getUserInfoId());
		}
		/**
		 * 审核通过
		 * *userFlag:用户的状态（0=待审核，1=通过能正常使用 2=没有通过反馈中）
		 * @throws MessagingException 
		 * @throws AddressException 
		 * @throws BusinessException 
		 */
		@Transactional
		public void checkUserInfo(Map<String,Object> map,HttpServletRequest request,UserInfoForm userInfoForm) throws AddressException, MessagingException, BusinessException{
			if(StringUtil.isEmpty(userInfoForm)||userInfoForm.getUserInfoList().size()==0)throw new BusinessException("被审核的对象信息丢失");
			UserInfo userInfoChild=userInfoForm.getUserInfoList().get(0);
			UserInfo userInfoParent=userInfoForm.getUserInfoList().get(1);
			if(StringUtil.isEmpty(userInfoChild)||StringUtil.isEmpty(userInfoChild.getUserInfoId()))throw new BusinessException("个人信息参数信息丢失");
			UserInfo userInfoOld=userInfoService.findOne(userInfoChild.getUserInfoId());
			if(StringUtil.isEmpty(userInfoOld)||StringUtil.isEmpty(userInfoOld.getUserInfoId()))throw new BusinessException("被审核的对象查询不到");
			
			if(!StringUtil.isEmpty(userInfoParent)||!StringUtil.isEmpty(userInfoParent.getUserInfoId())){
				UserInfo userInfoParentOld=userInfoService.findOne(userInfoParent.getUserInfoId());
				if(StringUtil.isEmpty(userInfoParentOld)||StringUtil.isEmpty(userInfoParentOld.getUserInfoId()))throw new BusinessException("被审核的对象查询不到");
				userInfoService.save(userInfoParent);
			}
			userInfoChild.setUserFlag(1);
			userInfoChild=userInfoService.save(userInfoChild);
//			Family family=familyService.findOneByUserChild(userInfoChild);
//			if(StringUtil.isEmpty(family)&&!StringUtil.isEmpty(userInfoParent)){
//				family=new Family();
//				family.setPubUser(UtilFactory.getSpringContext().getUserInfoContext().getUser());
//				family.setUserChild(userInfoChild);
//				family.setUserParent(userInfoParent);
//				familyService.save(family);
//			}
			 String msg="成功审核，已成功发送";
			if(StringUtil.isEmpty(userInfoChild.getUserEmail())){
				map.put(BaseHint.MESSAGE, "被审核的对象没有邮箱无法发送提示");
				msg+=",但无法发函通知到。[未知的邮箱地址]";
			}else{
				try{
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
				new EmailSendHint(request).send("信息认证审核结果--"+userInfoChild.getUserName(), userInfoChild.getUserEmail(), 
						"恭喜【"+userInfoChild.getUserName()+"】先生/女士,您成功通过了信息认证,请到<a href='"+basePath+"'>宗族网站</a>查看详情！");
				}catch(BusinessException e){
					msg+=",但无法发函通知到。["+e.getMessage()+"]";
				}
			}
			map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			map.put(BaseHint.MESSAGE, msg);
		}
		/**
		 * 审核不通过
		 * *userFlag:用户的状态（0=待审核，1=通过能正常使用 2=没有通过反馈中）
		 * @throws MessagingException 
		 * @throws AddressException 
		 * @throws BusinessException 
		 */
		@Transactional
		public void checkNotThroughUserInfo(Map<String,Object> map,HttpServletRequest request,UserInfo userInfo) throws AddressException, MessagingException, BusinessException{
			if(StringUtil.isEmpty(userInfo)||StringUtil.isEmpty(userInfo.getUserInfoId())){
				map.put(BaseHint.MESSAGE, "被审核的对象信息丢失");
				return;
			}
			String remarks=userInfo.getRemarks();//管理员审核不通过的原因
			userInfo=userInfoService.findOne(userInfo.getUserInfoId());
			if(StringUtil.isEmpty(userInfo)||StringUtil.isEmpty(userInfo.getUserInfoId())){
				map.put(BaseHint.MESSAGE, "被审核的对象查询不到");
				return;
			}
			if(StringUtil.isEmpty(userInfo.getUserEmail())){
				map.put(BaseHint.MESSAGE, "被审核的对象没有邮箱无法发送提示");
//				return;
			}
			userInfo.setUserFlag(2);
			userInfo.setRemarks(remarks);
			String path = request.getContextPath();
			 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
			 remarks=StringUtil.isEmpty(remarks)?"":remarks;
			 String msg="成功审核，已成功发送";
			 try {
				 new EmailSendHint(request).send("实名认证审核结果--"+userInfo.getUserName(), userInfo.getUserEmail(), 
							"不好意思【"+userInfo.getUserName()+"】先生/女士,您实名认证的信息无法通过,管理员留言信息【"+remarks+"】,请到<a href='"+basePath+"'>宗族网站</a>查看详情！");
			} catch (BusinessException e) {
				msg+=",但无法发函通知到。["+e.getMessage()+"]";
			}
			
			map.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
			map.put(BaseHint.MESSAGE, msg);
		}
}
