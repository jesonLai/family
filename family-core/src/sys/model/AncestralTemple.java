package sys.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the ancestral_temple database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="ancestral_temple_cache")
@Entity
@Table(name="ancestral_temple")
@NamedQuery(name="AncestralTemple.findAll", query="SELECT a FROM AncestralTemple a")
public class AncestralTemple implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="at_id")
	private Integer atId;

	@Column(name="at_img")
	private String atImg;
	
	@Column(name="at_folder")
	private String atFolder;

	@Column(name="at_content")
	private String atContent;

	@Column(name="at_title")
	private String atTitle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;
	
	@Column(name="placed_top")
	private Integer placedTop;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="placed_top_date")
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date placedTopDate;
	


	//bi-directional many-to-one association to PubUser
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="u_id")
	private PubUser pubUser;

	public AncestralTemple() {
	}

	public Integer getAtId() {
		return this.atId;
	}

	public void setAtId(Integer atId) {
		this.atId = atId;
	}

	public String getAtImg() {
		return this.atImg;
	}

	public void setAtImg(String atImg) {
		this.atImg = atImg;
	}

	public String getAtFolder() {
		return atFolder;
	}

	public void setAtFolder(String atFolder) {
		this.atFolder = atFolder;
	}

	public String getAtContent() {
		return atContent;
	}

	public void setAtContent(String atContent) {
		this.atContent = atContent;
	}

	public String getAtTitle() {
		return this.atTitle;
	}

	public void setAtTitle(String atTitle) {
		this.atTitle = atTitle;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public PubUser getPubUser() {
		return this.pubUser;
	}

	public void setPubUser(PubUser pubUser) {
		this.pubUser = pubUser;
	}

	public Integer getPlacedTop() {
		return placedTop;
	}

	public void setPlacedTop(Integer placedTop) {
		this.placedTop = placedTop;
	}

	public Date getPlacedTopDate() {
		return placedTopDate;
	}

	public void setPlacedTopDate(Date placedTopDate) {
		this.placedTopDate = placedTopDate;
	}

}