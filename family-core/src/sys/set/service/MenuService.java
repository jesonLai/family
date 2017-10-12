package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.PubMenu;

public interface MenuService extends BaseRepository<PubMenu, Integer>{
	public PubMenu save(PubMenu pubMenu);
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public List<PubMenu> findAll();
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public PubMenu findOneByMenuName(String menuName);
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public PubMenu findOneByMenuNameAndMenuIdNot(String menuName,Integer menuId);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query(value="select * from pub_menu where  menu_order=(select min(menu_order) from pub_menu where menu_order>?1)",nativeQuery=true)
	public PubMenu findOneByPrev(Integer menuOrder);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query(value="select * from pub_menu where  menu_order=(select max(menu_order) from pub_menu where menu_order<?1)",nativeQuery=true)
	public PubMenu findOneByPrevNext(Integer menuOrder);
	
	@Query(value=" select count(vfm.authority_id) AS authority_id from v_family_menus_in vfm where vfm.menu_id=?1 AND vfm.role_id=?2",nativeQuery=true)
	public long findByIsRelationship( Integer menuId , Integer roleId);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public List<PubMenu> findByMenuIdNot(Integer menuId);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public List<PubMenu> findByPubMenuParent(Integer menuId);
	
	
	
	
	
	
	
	
}
