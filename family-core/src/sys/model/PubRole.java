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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the pub_roles database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pubrole_cache")
@Entity
@Table(name="pub_roles")
@NamedQuery(name="PubRole.findAll", query="SELECT p FROM PubRole p")
public class PubRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id")
	private int roleId;

	@Column(name="role_desc")
	private String roleDesc;

	@Column(name="role_enabled")
	private int roleEnabled;

	private int role_isSys;

	@Column(name="role_name")
	private String roleName;

	@Column(name="role_sys_name")
	private String roleSysName;
	
	@Column(name="create_date")
	private Date createDate;

	@OneToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="role_parent")
	private PubRole roleParent;
	
	//bi-directional many-to-one association to PubRolesAuthority
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubRole")
	private List<PubRolesAuthority> pubRolesAuthorities;

	//bi-directional many-to-one association to PubUsersRole
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubRole")
	private List<PubUsersRole> pubUsersRoles;

	public PubRole() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public int getRoleEnabled() {
		return this.roleEnabled;
	}

	public void setRoleEnabled(int roleEnabled) {
		this.roleEnabled = roleEnabled;
	}

	public int getRole_isSys() {
		return this.role_isSys;
	}

	public void setRole_isSys(int role_isSys) {
		this.role_isSys = role_isSys;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSysName() {
		return this.roleSysName;
	}

	public void setRoleSysName(String roleSysName) {
		this.roleSysName = roleSysName;
	}

	public List<PubRolesAuthority> getPubRolesAuthorities() {
		return this.pubRolesAuthorities;
	}

	public void setPubRolesAuthorities(List<PubRolesAuthority> pubRolesAuthorities) {
		this.pubRolesAuthorities = pubRolesAuthorities;
	}

	public PubRolesAuthority addPubRolesAuthority(PubRolesAuthority pubRolesAuthority) {
		getPubRolesAuthorities().add(pubRolesAuthority);
		pubRolesAuthority.setPubRole(this);

		return pubRolesAuthority;
	}

	public PubRolesAuthority removePubRolesAuthority(PubRolesAuthority pubRolesAuthority) {
		getPubRolesAuthorities().remove(pubRolesAuthority);
		pubRolesAuthority.setPubRole(null);

		return pubRolesAuthority;
	}

	public List<PubUsersRole> getPubUsersRoles() {
		return this.pubUsersRoles;
	}

	public void setPubUsersRoles(List<PubUsersRole> pubUsersRoles) {
		this.pubUsersRoles = pubUsersRoles;
	}

	public PubUsersRole addPubUsersRole(PubUsersRole pubUsersRole) {
		getPubUsersRoles().add(pubUsersRole);
		pubUsersRole.setPubRole(this);

		return pubUsersRole;
	}

	public PubUsersRole removePubUsersRole(PubUsersRole pubUsersRole) {
		getPubUsersRoles().remove(pubUsersRole);
		pubUsersRole.setPubRole(null);

		return pubUsersRole;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public PubRole getRoleParent() {
		return roleParent;
	}

	public void setRoleParent(PubRole roleParent) {
		this.roleParent = roleParent;
	}


}