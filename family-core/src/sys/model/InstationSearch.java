package sys.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;


/**
 * The persistent class for the instation_search database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="instation_search_cache")
@Entity
@Table(name="instation_search")
@NamedQuery(name="InstationSearch.findAll", query="SELECT i FROM InstationSearch i")
public class InstationSearch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="child_id")
	private int childId;

	@Column(name="child_title")
	private String childTitle;

	@Column(name="child_url")
	private String childUrl;

	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="main_title")
	private String mainTitle;

	@Column(name="main_url")
	private String mainUrl;

	@Column(name="type_title")
	private String typeTitle;

	@Column(name="type_url")
	private String typeUrl;

	public InstationSearch() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChildId() {
		return this.childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	public String getChildTitle() {
		return this.childTitle;
	}

	public void setChildTitle(String childTitle) {
		this.childTitle = childTitle;
	}

	public String getChildUrl() {
		return this.childUrl;
	}

	public void setChildUrl(String childUrl) {
		this.childUrl = childUrl;
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

	public String getMainTitle() {
		return this.mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getMainUrl() {
		return this.mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getTypeTitle() {
		return this.typeTitle;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

	public String getTypeUrl() {
		return this.typeUrl;
	}

	public void setTypeUrl(String typeUrl) {
		this.typeUrl = typeUrl;
	}

}