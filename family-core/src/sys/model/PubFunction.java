package sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the pub_function database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pubfunction_cache")
@Entity
@Table(name="pub_function")
@NamedQuery(name="PubFunction.findAll", query="SELECT p FROM PubFunction p")
public class PubFunction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="function_id")
	private int functionId;

	@Column(name="function_desc")
	private String functionDesc;

	@Column(name="function_enabled")
	private int functionEnabled;

	@Column(name="function_html")
	private String functionHtml;

	private int function_isSys;

	@Column(name="function_menu")
	private int functionMenu;

	@Column(name="function_name")
	private String functionName;

	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="function_parent",referencedColumnName="function_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private PubFunction functionParent;

	@Column(name="function_priority")
	private int functionPriority;

	@Column(name="function_type")
	private int functionType;

	@Column(name="function_url")
	private String functionUrl;
	
	@Column(name="create_date")
	private Date createDate;
	
	@Column(name="update_date")
	private Date updateDate;

	//bi-directional many-to-one association to PubAuthoritiesFunction
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubFunction")
	private List<PubAuthoritiesFunction> pubAuthoritiesFunctions;
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="update_person", referencedColumnName="u_id")
	private PubUser updatePubUser;


	//bi-directional many-to-one association to PubUser
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="create_person", referencedColumnName="u_id")
	private PubUser createPubUser;

	public PubFunction() {
	}

	public int getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getFunctionDesc() {
		return this.functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public int getFunctionEnabled() {
		return this.functionEnabled;
	}

	public void setFunctionEnabled(int functionEnabled) {
		this.functionEnabled = functionEnabled;
	}

	public String getFunctionHtml() {
		return this.functionHtml;
	}

	public void setFunctionHtml(String functionHtml) {
		this.functionHtml = functionHtml;
	}

	public int getFunction_isSys() {
		return this.function_isSys;
	}

	public void setFunction_isSys(int function_isSys) {
		this.function_isSys = function_isSys;
	}

	public int getFunctionMenu() {
		return this.functionMenu;
	}

	public void setFunctionMenu(int functionMenu) {
		this.functionMenu = functionMenu;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public PubFunction getFunctionParent() {
		return this.functionParent;
	}

	public void setFunctionParent(PubFunction functionParent) {
		this.functionParent = functionParent;
	}

	public int getFunctionPriority() {
		return this.functionPriority;
	}

	public void setFunctionPriority(int functionPriority) {
		this.functionPriority = functionPriority;
	}

	public int getFunctionType() {
		return this.functionType;
	}

	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}

	public String getFunctionUrl() {
		return this.functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public List<PubAuthoritiesFunction> getPubAuthoritiesFunctions() {
		return this.pubAuthoritiesFunctions;
	}

	public void setPubAuthoritiesFunctions(List<PubAuthoritiesFunction> pubAuthoritiesFunctions) {
		this.pubAuthoritiesFunctions = pubAuthoritiesFunctions;
	}

	public PubAuthoritiesFunction addPubAuthoritiesFunction(PubAuthoritiesFunction pubAuthoritiesFunction) {
		getPubAuthoritiesFunctions().add(pubAuthoritiesFunction);
		pubAuthoritiesFunction.setPubFunction(this);

		return pubAuthoritiesFunction;
	}

	public PubAuthoritiesFunction removePubAuthoritiesFunction(PubAuthoritiesFunction pubAuthoritiesFunction) {
		getPubAuthoritiesFunctions().remove(pubAuthoritiesFunction);
		pubAuthoritiesFunction.setPubFunction(null);

		return pubAuthoritiesFunction;
	}

	public Date getCreateDate(){
		return createDate;
	}

	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getUpdateDate(){
		return updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	
	public PubUser getUpdatePubUser(){
		return updatePubUser;
	}

	
	public void setUpdatePubUser(PubUser updatePubUser){
		this.updatePubUser = updatePubUser;
	}

	
	public PubUser getCreatePubUser(){
		return createPubUser;
	}

	public void setCreatePubUser(PubUser createPubUser){
		this.createPubUser = createPubUser;
	}

}