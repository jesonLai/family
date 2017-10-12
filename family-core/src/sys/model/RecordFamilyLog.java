package sys.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * The persistent class for the record_family_log database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="record_family_log_cache")
@Entity
@Table(name="record_family_log")
@NamedQuery(name="RecordFamilyLog.findAll", query="SELECT r FROM RecordFamilyLog r")
public class RecordFamilyLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="create_man", referencedColumnName="u_id")
	private PubUser createMan;

	private String description;

	@Column(name="exception_code")
	private String exceptionCode;

	@Column(name="exception_detail")
	private String exceptionDetail;

	@Column(name="request_ip")
	private String requestIp;

	@Column(name="request_method")
	private String requestMethod;

	@Column(name="rquest_params")
	private String rquestParams;
	
	private int statistical;
	
	@Column(name="update_date")
	private Date updateDate;

	public RecordFamilyLog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public PubUser getCreateMan() {
		return createMan;
	}

	public void setCreateMan(PubUser createMan) {
		this.createMan = createMan;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExceptionCode() {
		return this.exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionDetail() {
		return this.exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

	public String getRequestIp() {
		return this.requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getRequestMethod() {
		return this.requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRquestParams() {
		return this.rquestParams;
	}

	public void setRquestParams(String rquestParams) {
		this.rquestParams = rquestParams;
	}

	public int getStatistical() {
		return statistical;
	}

	public void setStatistical(int statistical) {
		this.statistical = statistical;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



}