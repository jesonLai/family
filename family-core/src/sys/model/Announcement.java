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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;




/**
 * The persistent class for the announcement database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="announcement_Cache")
@Entity()
@Table(name="announcement")
@NamedQuery(name="Announcement.findAll", query="SELECT a FROM Announcement a")
public class Announcement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	private String title;
	
	@Column(name="placed_top")
	private Integer placedTop;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="placed_top_date")
	private Date placedTopDate;
	

	//bi-directional many-to-one association to PubUser
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="u_id")
	private PubUser pubUser;

	public Announcement() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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