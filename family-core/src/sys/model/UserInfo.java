package sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the user_info database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="user_info_cache")
@Entity
@Table(name="user_info")
@NamedQuery(name="UserInfo.findAll", query="SELECT u FROM UserInfo u")
public class UserInfo implements Serializable {
//	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_info_id")
	private Integer userInfoId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date createDate;
	
	@Column(name="user_desc")
	
	private String userDesc;

	@Column(name="user_email")
	private String userEmail;

	@Column(name="user_enabled")
	private Integer userEnabled;

	@Column(name="user_identity_card")
	private String userIdentityCard;

	private Integer user_isSys;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_phone")
	private String userPhone;

	@Column(name="user_qq")
	private String userQq;

	@Column(name="user_sex")
	private Integer userSex;
	
	@Column(name="user_age")
	private Integer userAge;
	
	@Column(name="spouse_name")
	private String spouseName;
	
	@Column(name="tribal_region")
	private String tribalRegion;
	
	@Column(name="user_weixin")
	private String userWeixin;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="user_born_date")
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date userBornDate;
	
	@Column(name="marital_status")
	private Integer maritalStatus;
	
	@Column(name="home_address")
	private String homeAddress;
	
	@Column(name="head_image_folder")
	private String headImageFolder;
	
	@Column(name="head_image_name")
	private String headImageName;
	
	@Column(name="user_curr_flag")
	private int userCurrFlag;
	
	@Column(name="user_flag")
	private int userFlag;
	
	private String remarks;
	
	@Column(name="celebrity_flag")
	private int celebrityFlag;
	
	@Column(name="release_flag")
	private int releaseFlag;
	
	@OneToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="father")
	private UserInfo fatherUserInfo;

	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="fatherUserInfo")
	private List<UserInfo> childUserInfos;

	//bi-directional many-to-one association to PubUser
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="userInfo")
	private List<PubUser> pubUsers;

	public UserInfo() {
	}

	public Integer getUserInfoId() {
		return this.userInfoId;
	}

	public void setUserInfoId(Integer userInfoId) {
		this.userInfoId = userInfoId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserEnabled() {
		return this.userEnabled;
	}

	public void setUserEnabled(Integer userEnabled) {
		this.userEnabled = userEnabled;
	}

	public String getUserIdentityCard() {
		return this.userIdentityCard;
	}

	public void setUserIdentityCard(String userIdentityCard) {
		this.userIdentityCard = userIdentityCard;
	}

	public Integer getUser_isSys() {
		return this.user_isSys;
	}

	public void setUser_isSys(Integer user_isSys) {
		this.user_isSys = user_isSys;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserQq() {
		return this.userQq;
	}

	public String getTribalRegion() {
		return tribalRegion;
	}

	public void setTribalRegion(String tribalRegion) {
		this.tribalRegion = tribalRegion;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public Integer getUserSex() {
		return this.userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserWeixin() {
		return this.userWeixin;
	}

	public void setUserWeixin(String userWeixin) {
		this.userWeixin = userWeixin;
	}

	public Date getUserBornDate() {
		return userBornDate;
	}

	public void setUserBornDate(Date userBornDate) {
		this.userBornDate = userBornDate;
	}

	public String getHeadImageFolder() {
		return headImageFolder;
	}

	public void setHeadImageFolder(String headImageFolder) {
		this.headImageFolder = headImageFolder;
	}

	public String getHeadImageName() {
		return headImageName;
	}

	public void setHeadImageName(String headImageName) {
		this.headImageName = headImageName;
	}

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public List<PubUser> getPubUsers() {
		return this.pubUsers;
	}

	public void setPubUsers(List<PubUser> pubUsers) {
		this.pubUsers = pubUsers;
	}

	public PubUser addPubUser(PubUser pubUser) {
		getPubUsers().add(pubUser);
		pubUser.setUserInfo(this);

		return pubUser;
	}

	public PubUser removePubUser(PubUser pubUser) {
		getPubUsers().remove(pubUser);
		pubUser.setUserInfo(null);

		return pubUser;
	}

	public int getUserCurrFlag() {
		return userCurrFlag;
	}

	public void setUserCurrFlag(int userCurrFlag) {
		this.userCurrFlag = userCurrFlag;
	}

	public int getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(int userFlag) {
		this.userFlag = userFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getCelebrityFlag() {
		return celebrityFlag;
	}

	public void setCelebrityFlag(int celebrityFlag) {
		this.celebrityFlag = celebrityFlag;
	}

	public int getReleaseFlag() {
		return releaseFlag;
	}

	public void setReleaseFlag(int releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public UserInfo getFatherUserInfo() {
		return fatherUserInfo;
	}

	public void setFatherUserInfo(UserInfo fatherUserInfo) {
		this.fatherUserInfo = fatherUserInfo;
	}

	public List<UserInfo> getChildUserInfos() {
		return childUserInfos;
	}

	public void setChildUserInfos(List<UserInfo> childUserInfos) {
		this.childUserInfos = childUserInfos;
	}

	public UserInfo addUserInfo(UserInfo userInfo) {
		getChildUserInfos().add(userInfo);
		userInfo.setFatherUserInfo(this);

		return userInfo;
	}

	public UserInfo removeUserInfo(UserInfo userInfo) {
		getChildUserInfos().remove(userInfo);
		userInfo.setFatherUserInfo(null);

		return userInfo;
	}


}