package sys.model;

import java.io.Serializable;

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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the pub_roles_authorities database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pub_roles_authority_cache")
@Entity
@Table(name="pub_roles_authorities")
@NamedQuery(name="PubRolesAuthority.findAll", query="SELECT p FROM PubRolesAuthority p")
public class PubRolesAuthority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ra_id")
	private int raId;

	//bi-directional many-to-one association to PubAuthority
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="authority_id")
	private PubAuthority pubAuthority;

	//bi-directional many-to-one association to PubRole
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	private PubRole pubRole;

	public PubRolesAuthority() {
	}

	public int getRaId() {
		return this.raId;
	}

	public void setRaId(int raId) {
		this.raId = raId;
	}

	public PubAuthority getPubAuthority() {
		return this.pubAuthority;
	}

	public void setPubAuthority(PubAuthority pubAuthority) {
		this.pubAuthority = pubAuthority;
	}

	public PubRole getPubRole() {
		return this.pubRole;
	}

	public void setPubRole(PubRole pubRole) {
		this.pubRole = pubRole;
	}

}