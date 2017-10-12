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
 * The persistent class for the pub_authorities_menu database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="pubAuthorities_menu_cache")
@Entity
@Table(name="pub_authorities_menu")
@NamedQuery(name="PubAuthoritiesMenu.findAll", query="SELECT p FROM PubAuthoritiesMenu p")
public class PubAuthoritiesMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="am_id")
	private Integer amId;

	//bi-directional many-to-one association to PubAuthority
	@ManyToOne(cascade=CascadeType.REFRESH , fetch=FetchType.LAZY)
	@JoinColumn(name="authority_id")
	private PubAuthority pubAuthority;

	//bi-directional many-to-one association to PubFunction
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="menu_id")
	private PubMenu pubMenu;

	public PubAuthoritiesMenu() {
	}


	public Integer getAmId() {
		return amId;
	}


	public void setAmId(Integer amId) {
		this.amId = amId;
	}


	public PubAuthority getPubAuthority() {
		return pubAuthority;
	}

	public void setPubAuthority(PubAuthority pubAuthority) {
		this.pubAuthority = pubAuthority;
	}

	public PubMenu getPubMenu() {
		return pubMenu;
	}

	public void setPubMenu(PubMenu pubMenu) {
		this.pubMenu = pubMenu;
	}




}