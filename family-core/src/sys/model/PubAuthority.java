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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the pub_authorities database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pubAuthority_cache")
@Entity
@Table(name="pub_authorities")
@NamedQuery(name="PubAuthority.findAll", query="SELECT p FROM PubAuthority p")
public class PubAuthority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="authority_id")
	private String authorityId;

	@Column(name="authority_desc")
	private String authorityDesc;

	@Column(name="authority_enabled")
	private int authorityEnabled;

	private int authority_isSys;

	@Column(name="authority_name")
	private String authorityName;

	@Column(name="authority_type")
	@Deprecated
	private int authorityType;
	
	@Column(name="create_date")
	private Date createDate;
	
	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="authority_group_id",referencedColumnName="authority_group_id")
	private PubAuthorityGroup authorityGroup;
	
	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="create_person",referencedColumnName="u_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private PubUser createPerson;
	
	@Column(name="update_date")
	private Date updateDate;
	
	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="update_person",referencedColumnName="u_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private PubUser updatePerson;

	//bi-directional many-to-one association to PubAuthoritiesFunction
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubAuthority")
	private List<PubAuthoritiesFunction> pubAuthoritiesFunctions;

	//bi-directional many-to-one association to PubRolesAuthority
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubAuthority")
	private List<PubRolesAuthority> pubRolesAuthorities;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubAuthority")
	private List<PubAuthoritiesMenu> pubAuthoritiesMenu;

	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="authority_id",referencedColumnName="authority_id")
	private PubAuhorityResource pubAuhorityResource;
	
	
	public PubAuthority() {
	}

	public String getAuthorityId() {
		return this.authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityDesc() {
		return this.authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	public int getAuthorityEnabled() {
		return this.authorityEnabled;
	}

	public void setAuthorityEnabled(int authorityEnabled) {
		this.authorityEnabled = authorityEnabled;
	}

	public int getAuthority_isSys() {
		return this.authority_isSys;
	}

	public void setAuthority_isSys(int authority_isSys) {
		this.authority_isSys = authority_isSys;
	}

	public String getAuthorityName() {
		return this.authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public int getAuthorityType() {
		return this.authorityType;
	}

	public void setAuthorityType(int authorityType) {
		this.authorityType = authorityType;
	}

	public List<PubAuthoritiesFunction> getPubAuthoritiesFunctions() {
		return this.pubAuthoritiesFunctions;
	}

	public Date getCreateDate(){
		return createDate;
	}

	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	
//	public PubAuthorityGroup getAuthorityGroup(){
//		return authorityGroup;
//	}
//
//	
//	public void setAuthorityGroup(PubAuthorityGroup authorityGroup){
//		this.authorityGroup = authorityGroup;
//	}

	
	public PubUser getCreatePerson(){
		return createPerson;
	}

	
	public void setCreatePerson(PubUser createPerson){
		this.createPerson = createPerson;
	}

	
	public Date getUpdateDate(){
		return updateDate;
	}

	
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public PubUser getUpdatePerson(){
		return updatePerson;
	}

	public void setUpdatePerson(PubUser updatePerson){
		this.updatePerson = updatePerson;
	}

	public void setPubAuthoritiesFunctions(List<PubAuthoritiesFunction> pubAuthoritiesFunctions) {
		this.pubAuthoritiesFunctions = pubAuthoritiesFunctions;
	}

	public PubAuthoritiesFunction addPubAuthoritiesFunction(PubAuthoritiesFunction pubAuthoritiesFunction) {
		getPubAuthoritiesFunctions().add(pubAuthoritiesFunction);
		pubAuthoritiesFunction.setPubAuthority(this);

		return pubAuthoritiesFunction;
	}

	public PubAuthoritiesFunction removePubAuthoritiesFunction(PubAuthoritiesFunction pubAuthoritiesFunction) {
		getPubAuthoritiesFunctions().remove(pubAuthoritiesFunction);
		pubAuthoritiesFunction.setPubAuthority(null);

		return pubAuthoritiesFunction;
	}

	public List<PubRolesAuthority> getPubRolesAuthorities() {
		return this.pubRolesAuthorities;
	}

	public void setPubRolesAuthorities(List<PubRolesAuthority> pubRolesAuthorities) {
		this.pubRolesAuthorities = pubRolesAuthorities;
	}

	public PubRolesAuthority addPubRolesAuthority(PubRolesAuthority pubRolesAuthority) {
		getPubRolesAuthorities().add(pubRolesAuthority);
		pubRolesAuthority.setPubAuthority(this);

		return pubRolesAuthority;
	}

	public PubRolesAuthority removePubRolesAuthority(PubRolesAuthority pubRolesAuthority) {
		getPubRolesAuthorities().remove(pubRolesAuthority);
		pubRolesAuthority.setPubAuthority(null);

		return pubRolesAuthority;
	}

	public List<PubAuthoritiesMenu> getPubAuthoritiesMenu() {
		return pubAuthoritiesMenu;
	}

	public void setPubAuthoritiesMenu(List<PubAuthoritiesMenu> pubAuthoritiesMenu) {
		this.pubAuthoritiesMenu = pubAuthoritiesMenu;
	}
	public PubAuthoritiesMenu addPubRolesMenu(PubAuthoritiesMenu pubAuthoritiesMenu) {
		getPubAuthoritiesMenu().add(pubAuthoritiesMenu);
		pubAuthoritiesMenu.setPubAuthority(this);
		return pubAuthoritiesMenu;
	}

	public PubAuthoritiesMenu removePubRolesMenu(PubAuthoritiesMenu pubAuthoritiesMenu) {
		getPubAuthoritiesMenu().remove(pubAuthoritiesMenu);
		pubAuthoritiesMenu.setPubAuthority(null);
		return pubAuthoritiesMenu;
	}

	
	/**
	 * @return the authorityGroup
	 */
	public PubAuthorityGroup getAuthorityGroup(){
		return authorityGroup;
	}

	
	/**
	 * @param authorityGroup the authorityGroup to set
	 */
	public void setAuthorityGroup(PubAuthorityGroup authorityGroup){
		this.authorityGroup = authorityGroup;
	}

	
	/**
	 * @return the pubAuhorityResource
	 */
	public PubAuhorityResource getPubAuhorityResource(){
		return pubAuhorityResource;
	}

	
	/**
	 * @param pubAuhorityResource the pubAuhorityResource to set
	 */
	public void setPubAuhorityResource(PubAuhorityResource pubAuhorityResource){
		this.pubAuhorityResource = pubAuhorityResource;
	}
	
}