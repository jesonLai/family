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
 * The persistent class for the pub_users_roles database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pub_users_role_cache")
@Entity
@Table(name="pub_users_roles")
@NamedQuery(name="PubUsersRole.findAll", query="SELECT p FROM PubUsersRole p")
public class PubUsersRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ur_id")
	private int urId;

	//bi-directional many-to-one association to PubRole
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	private PubRole pubRole;

	//bi-directional many-to-one association to PubUser
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="u_id")
	private PubUser pubUser;

	public PubUsersRole() {
	}

	public int getUrId() {
		return this.urId;
	}

	public void setUrId(int urId) {
		this.urId = urId;
	}

	public PubRole getPubRole() {
		return this.pubRole;
	}

	public void setPubRole(PubRole pubRole) {
		this.pubRole = pubRole;
	}

	public PubUser getPubUser() {
		return this.pubUser;
	}

	public void setPubUser(PubUser pubUser) {
		this.pubUser = pubUser;
	}

}