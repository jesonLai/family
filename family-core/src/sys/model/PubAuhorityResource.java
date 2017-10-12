package sys.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="pub_auhority_resource_cache")
@Entity
@Table(name="pub_auhority_resource")
@NamedQuery(name="PubAuhorityResource.findAll", query="SELECT p FROM PubAuhorityResource p")
public class PubAuhorityResource{
	@Id
	@Column(name="authority_id")
	private String authorityId;
	
	@Column(name="authority_resource_type")
	private String authorityResourceType;
	
	@Column(name="resource_id")
	private Integer resourceId;
	
	/**
	 * @return the authorityId
	 */
	public String getAuthorityId(){
		return authorityId;
	}
	
	/**
	 * @param authorityId the authorityId to set
	 */
	public void setAuthorityId(String authorityId){
		this.authorityId = authorityId;
	}
	
	/**
	 * @return the authorityResourceType
	 */
	public String getAuthorityResourceType(){
		return authorityResourceType;
	}
	
	/**
	 * @param authorityResourceType the authorityResourceType to set
	 */
	public void setAuthorityResourceType(String authorityResourceType){
		this.authorityResourceType = authorityResourceType;
	}

	
	/**
	 * @return the resourceId
	 */
	public int getResourceId(){
		return resourceId;
	}

	
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(int resourceId){
		this.resourceId = resourceId;
	}

}
