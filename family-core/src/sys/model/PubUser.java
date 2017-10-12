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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the pub_users database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pub_user_cache")
@Entity
@Table(name="pub_users")
@NamedQuery(name="PubUser.findAll", query="SELECT p FROM PubUser p")
public class PubUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="u_id")
	private Integer uId;

	@Column(name="user_account")
	private String userAccount;

	@Column(name="user_password")
	private String userPassword;
	
	@Column(name="account_type")
	private Integer accountType;
	
	private String ip;
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Column(name="last_login_time")	
	private Date lastLoginTime;
	
	@Column(name="user_enabled")
	private Integer userEnabled;
	@Column(name="browser_name")
	private String browserName;
	@Column(name="os_name")
	private String osName;
	

	//bi-directional many-to-one association to FamilyNew
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubUser")
	private List<FamilyNew> familyNews;

	//bi-directional many-to-one association to Message
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubUser")
	private List<FamilyMessage> familyMessage;

	//bi-directional many-to-one association to PubMenu
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubUser")
	private List<PubMenu> pubMenus;
	
	//bi-directional many-to-one association to UserInfo
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserInfo userInfo;
	
	@Column(name="u_flag")
	private int uFlag;

	//bi-directional many-to-one association to PubUsersRole
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubUser")
	private List<PubUsersRole> pubUsersRoles;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubUser")
	private List<FamilyVideo> FamilyVideo;


	public PubUser() {
	}

	public Integer getUId() {
		return this.uId;
	}

	public void setUId(int uId) {
		this.uId = uId;
	}

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public List<FamilyNew> getFamilyNews() {
		return this.familyNews;
	}

	public void setFamilyNews(List<FamilyNew> familyNews) {
		this.familyNews = familyNews;
	}

	public FamilyNew addFamilyNew(FamilyNew familyNew) {
		getFamilyNews().add(familyNew);
		familyNew.setPubUser(this);

		return familyNew;
	}

	public FamilyNew removeFamilyNew(FamilyNew familyNew) {
		getFamilyNews().remove(familyNew);
		familyNew.setPubUser(null);

		return familyNew;
	}


	public List<FamilyMessage> getFamilyMessage() {
		return familyMessage;
	}

	public void setFamilyMessage(List<FamilyMessage> familyMessage) {
		this.familyMessage = familyMessage;
	}

	public FamilyMessage addFamilyMessage(FamilyMessage message) {
		getFamilyMessage().add(message);
		message.setPubUser(this);

		return message;
	}

	public FamilyMessage removeFamilyMessage(FamilyMessage message) {
		getFamilyMessage().remove(message);
		message.setPubUser(null);

		return message;
	}

	public List<PubMenu> getPubMenus() {
		return this.pubMenus;
	}

	public void setPubMenus(List<PubMenu> pubMenus) {
		this.pubMenus = pubMenus;
	}

	public PubMenu addPubMenus(PubMenu pubMenus) {
		getPubMenus().add(pubMenus);
		pubMenus.setPubUser(this);

		return pubMenus;
	}

	public PubMenu removePubMenus(PubMenu pubMenus) {
		getPubMenus().remove(pubMenus);
		pubMenus.setPubUser(null);

		return pubMenus;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<PubUsersRole> getPubUsersRoles() {
		return this.pubUsersRoles;
	}

	public void setPubUsersRoles(List<PubUsersRole> pubUsersRoles) {
		this.pubUsersRoles = pubUsersRoles;
	}

	public PubUsersRole addPubUsersRole(PubUsersRole pubUsersRole) {
		getPubUsersRoles().add(pubUsersRole);
		pubUsersRole.setPubUser(this);

		return pubUsersRole;
	}

	public PubUsersRole removePubUsersRole(PubUsersRole pubUsersRole) {
		getPubUsersRoles().remove(pubUsersRole);
		pubUsersRole.setPubUser(null);

		return pubUsersRole;
	}

	public List<FamilyVideo> getFamilyVideo() {
		return FamilyVideo;
	}

	public void setFamilyVideo(List<FamilyVideo> familyVideo) {
		FamilyVideo = familyVideo;
	}
	public FamilyVideo addPubFamilyVideo(FamilyVideo familyVideo) {
		getFamilyVideo().add(familyVideo);
		familyVideo.setPubUser(this);

		return familyVideo;
	}

	public FamilyVideo removeFamilyVideo(FamilyVideo familyVideo) {
		getFamilyVideo().remove(familyVideo);
		familyVideo.setPubUser(null);

		return familyVideo;
	}

	public int getuFlag() {
		return uFlag;
	}

	public void setuFlag(int uFlag) {
		this.uFlag = uFlag;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(Integer userEnabled) {
		this.userEnabled = userEnabled;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}


	
}