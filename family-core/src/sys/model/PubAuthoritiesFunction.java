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
 * The persistent class for the pub_authorities_function database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pubAuthorities_function_cache")
@Entity
@Table(name="pub_authorities_function")
@NamedQuery(name="PubAuthoritiesFunction.findAll", query="SELECT p FROM PubAuthoritiesFunction p")
public class PubAuthoritiesFunction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ar_id")
	private String arId;

	//bi-directional many-to-one association to PubAuthority
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="authority_id")
	private PubAuthority pubAuthority;

	//bi-directional many-to-one association to PubFunction
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="function_id")
	private PubFunction pubFunction;

	public PubAuthoritiesFunction() {
	}

	public String getArId() {
		return this.arId;
	}

	public void setArId(String arId) {
		this.arId = arId;
	}

	public PubAuthority getPubAuthority() {
		return this.pubAuthority;
	}

	public void setPubAuthority(PubAuthority pubAuthority) {
		this.pubAuthority = pubAuthority;
	}

	public PubFunction getPubFunction() {
		return this.pubFunction;
	}

	public void setPubFunction(PubFunction pubFunction) {
		this.pubFunction = pubFunction;
	}

}