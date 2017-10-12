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


/**
 * The persistent class for the family_video database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="family_video_cache")
@Entity
@Table(name="family_video")
@NamedQuery(name="FamilyVideo.findAll", query="SELECT f FROM FamilyVideo f")
public class FamilyVideo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String flashUrl;

	@Column(name="html_code")
	private String htmlCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	@Column(name="page_url")
	private String pageUrl;

	private String source;

	private String summary;

	private String thumbnail;

	private String title;
	
	@Column(name="event_type")
	private String eventType;

	//bi-directional many-to-one association to PubUser
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="u_id")
	private PubUser pubUser;

	public FamilyVideo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlashUrl() {
		return this.flashUrl;
	}

	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}

	public String getHtmlCode() {
		return this.htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

}