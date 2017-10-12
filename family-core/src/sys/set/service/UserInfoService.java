package sys.set.service;

import java.util.Date;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import sys.jpa.repository.BaseRepository;
import sys.model.UserInfo;

public interface UserInfoService extends BaseRepository<UserInfo, Integer> {
    public List<UserInfo> findAll();

    // 模糊获取已有姓名
    public List<UserInfo> findByUserNameContaining(String userName);

    // 查询名称是否存在
    public UserInfo findOneByUserName(String userName);

    public UserInfo findOneByUserNameAndUserInfoIdNot(String userName, int userInfoId);

    // 获取字段名称
    public UserInfo findOneByUserEmail(String userEmail);

    public UserInfo findOneByUserQq(String userQq);

    public UserInfo findOneByUserPhone(String userPhone);

    public UserInfo findOneByUserWeixin(String userWeixin);

    // 检验form表单值
    public UserInfo findOneByUserEmailAndUserInfoIdNot(String userEmail, int userInfoId);

    public UserInfo findOneByUserQqAndUserInfoIdNot(String userQq, int userInfoId);

    public UserInfo findOneByUserPhoneAndUserInfoIdNot(String userPhone, int userInfoId);

    public UserInfo findOneByUserWeixinAndUserInfoIdNot(String userWeixin, int userInfoId);

    public UserInfo findOneByUserNameAndUserIdentityCard(String userName, String userIdentityCard);

    public List<UserInfo> findByCelebrityFlag(int celebrityFlag);

    // 查询最新5条数据

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    public List<UserInfo> findByCelebrityFlagAndReleaseFlagOrderByUserInfoIdDesc(int celebrityFlag, int releaseFlag);

    // 个人信息更新
    @Modifying(clearAutomatically = true)
    @Query(value = "update UserInfo u set u.userBornDate=:userBornDate,u.userDesc=:userDesc,"
	    + "u.userEmail=:userEmail,u.userName=:userName,u.userPhone=:userPhone,"
	    + "u.userQq=:userQq,u.userSex=:userSex,u.userAge=:userAge,u.spouseName=:spouseName,"
	    + "u.maritalStatus=:maritalStatus,u.homeAddress=:homeAddress where u.userInfoId=:userInfoId")
    public int updateUserInfo(@Param("userBornDate") Date userBornDate, @Param("userDesc") String userDesc,
	    @Param("userEmail") String userEmail, @Param("userName") String userName,
	    @Param("userPhone") String userPhone, @Param("userQq") String userQq, @Param("userSex") Integer userSex,
	    @Param("userAge") Integer userAge, @Param("spouseName") String spouseName,
	    @Param("maritalStatus") Integer maritalStatus, @Param("homeAddress") String homeAddress,
	    @Param("userInfoId") Integer userInfoId);

}
