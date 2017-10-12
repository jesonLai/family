package sys.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pubAuthorityGroup_cache")
@Entity
@Table(name="pub_authority_group")
@NamedQuery(name="PubAuthorityGroup.findAll", query="SELECT p FROM PubAuthorityGroup p")
public class PubAuthorityGroup{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="authority_group_id")
	private Integer authorityGroupId;
	
	@Column(name="authority_group_name")
	private String authorityGroupName;
	
	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="authority_group_parent",referencedColumnName="authority_group_id")
	private PubAuthorityGroup authorityGroupParent;
	
	@Column(name="authority_group_desc")
	private String authorityGroupDesc;
	
	@Column(name="create_date")
	private Date createDate;
	
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
	
	public Integer getAuthorityGroupId(){
		return authorityGroupId;
	}
	
	public void setAuthorityGroupId(Integer authorityGroupId){
		this.authorityGroupId = authorityGroupId;
	}
	
	public String getAuthorityGroupName(){
		return authorityGroupName;
	}
	
	public void setAuthorityGroupName(String authorityGroupName){
		this.authorityGroupName = authorityGroupName;
	}
	
	public PubAuthorityGroup getAuthorityGroupParent(){
		return authorityGroupParent;
	}
	
	public void setAuthorityGroupParent(PubAuthorityGroup authorityGroupParent){
		this.authorityGroupParent = authorityGroupParent;
	}
	
	public String getAuthorityGroupDesc(){
		return authorityGroupDesc;
	}
	
	/**
	 * @param authorityGroupDesc the authorityGroupDesc to set
	 */
	public void setAuthorityGroupDesc(String authorityGroupDesc){
		this.authorityGroupDesc = authorityGroupDesc;
	}

	
	/**
	 * @return the createDate
	 */
	public Date getCreateDate(){
		return createDate;
	}

	
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	
	/**
	 * @return the createPerson
	 */
	public PubUser getCreatePerson(){
		return createPerson;
	}

	
	/**
	 * @param createPerson the createPerson to set
	 */
	public void setCreatePerson(PubUser createPerson){
		this.createPerson = createPerson;
	}

	
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate(){
		return updateDate;
	}

	
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	
	/**
	 * @return the updatePerson
	 */
	public PubUser getUpdatePerson(){
		return updatePerson;
	}

	
	/**
	 * @param updatePerson the updatePerson to set
	 */
	public void setUpdatePerson(PubUser updatePerson){
		this.updatePerson = updatePerson;
	}
	

	
}
