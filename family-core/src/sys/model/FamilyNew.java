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
 * The persistent class for the family_news database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="family_new_cache")
@Entity
@Table(name="family_news")
@NamedQuery(name="FamilyNew.findAll", query="SELECT f FROM FamilyNew f")
public class FamilyNew implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="family_news_id")
	private Integer familyNewsId;

	@Lob
	private String content;
	
	@Lob
	@Column(name="html_content")
	private String htmlContent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="relese_date")
	private Date releseDate;

	private String title;
	
	@Column(name="flag")
	private Integer flag;
	
	@Column(name="event_type")
	private String eventType;
	
	@Column(name="placed_top")
	private Integer placedTop;
	
	@Column(name="placed_top_date")
	private Date placedTopDate;
	
	@Column(name="family_news_img_folder")
	private String familyNewsImgFolder;
	
	@Column(name="family_news_img_name")
	private String familyNewsImgName;

	//bi-directional many-to-one association to PubUser
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="u_id")
	private PubUser pubUser;

	public FamilyNew() {
	}

	public Integer getFamilyNewsId() {
		return familyNewsId;
	}


	public void setFamilyNewsId(Integer familyNewsId) {
		this.familyNewsId = familyNewsId;
	}


	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public Date getReleseDate() {
		return this.releseDate;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setReleseDate(Date releseDate) {
		this.releseDate = releseDate;
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

	public String getFamilyNewsImgFolder() {
		return familyNewsImgFolder;
	}

	public void setFamilyNewsImgFolder(String familyNewsImgFolder) {
		this.familyNewsImgFolder = familyNewsImgFolder;
	}

	public String getFamilyNewsImgName() {
		return familyNewsImgName;
	}

	public void setFamilyNewsImgName(String familyNewsImgName) {
		this.familyNewsImgName = familyNewsImgName;
	}


}