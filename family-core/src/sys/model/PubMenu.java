package sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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


/**
 * The persistent class for the pub_menu database table.
 * 
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region="pubmenu_cache")
@Entity
@Table(name="pub_menu")
@NamedQuery(name="PubMenu.findAll", query="SELECT p FROM PubMenu p")
public class PubMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="menu_id")
	private Integer menuId;

	@Column(name="menu_name")
	private String menuName;

	@OneToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="menu_parents",referencedColumnName="menu_id")
	private PubMenu pubMenuParent;

	@Column(name="menu_url")
	private String menuUrl;
	
	@Column(name="create_date")
	private Date createDate;
	
	@Column(name="update_date")
	private Date updateDate;
	
	@Column(name="item_icon")
	private String itemIcon;
	
	@Column(name="menu_order")
	private int menuOrder;
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="update_person", referencedColumnName="u_id")
	private PubUser updatePubUser;

	//bi-directional many-to-one association to PubUser
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="u_id", referencedColumnName="u_id")
	private PubUser pubUser;
	
	//bi-directional many-to-one association to PubAuthoritiesFunction
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY,mappedBy="pubMenu")
	private List<PubAuthoritiesMenu> pubAuthoritiesMenu;
	
	  // 子节点  
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pubMenuParent", fetch = FetchType.EAGER)
    private Set<PubMenu> children = new LinkedHashSet<PubMenu>();
	public PubMenu() {
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public PubMenu getPubMenuParent() {
		return pubMenuParent;
	}

	public void setPubMenuParent(PubMenu pubMenuParent) {
		this.pubMenuParent = pubMenuParent;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public PubUser getPubUser() {
		return this.pubUser;
	}

	public void setPubUser(PubUser pubUser) {
		this.pubUser = pubUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public PubUser getUpdatePubUser() {
		return updatePubUser;
	}

	public void setUpdatePubUser(PubUser updatePubUser) {
		this.updatePubUser = updatePubUser;
	}

	public String getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(String itemIcon) {
		this.itemIcon = itemIcon;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public List<PubAuthoritiesMenu> getPubAuthoritiesMenu() {
		return pubAuthoritiesMenu;
	}

	public void setPubAuthoritiesMenu(List<PubAuthoritiesMenu> pubAuthoritiesMenu) {
		this.pubAuthoritiesMenu = pubAuthoritiesMenu;
	}
	public PubAuthoritiesMenu addPubAuthoritiesMenu(PubAuthoritiesMenu pubAuthoritiesMenu) {
		getPubAuthoritiesMenu().add(pubAuthoritiesMenu);
		pubAuthoritiesMenu.setPubMenu(this);

		return pubAuthoritiesMenu;
	}

	public PubAuthoritiesMenu removePubAuthoritiesFunction(PubAuthoritiesMenu pubAuthoritiesMenu) {
		getPubAuthoritiesMenu().remove(pubAuthoritiesMenu);
		pubAuthoritiesMenu.setPubMenu(null);

		return pubAuthoritiesMenu;
	}

	public Set<PubMenu> getChildren(){
		
		return children;
	}

	public void setChildren(Set<PubMenu> children){
		
		this.children = children;
	}

	public String toChildrenStr(){
		return "PubMenu [menuId=" + menuId + ", menuName=" + menuName + ", pubMenuParent=" + pubMenuParent + ", menuUrl=" + menuUrl + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", itemIcon=" + itemIcon + "]";
	}
	public String toTreeStr(){
		return "PubMenu [menuId=" + menuId + ", menuName=" + menuName + ", pubMenuParent=" + pubMenuParent + ", menuUrl=" + menuUrl + ", createDate=" + createDate + ", updateDate=" + updateDate
		+ ", itemIcon=" + itemIcon + ", children=" + children+ "]";
	}

	
}