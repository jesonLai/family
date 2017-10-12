package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.FamilyImage;

public interface FamilyImageService extends BaseRepository<FamilyImage, Integer>{
	public List<FamilyImage> findAll();
	
	@Query(value="SELECT event_type from family_image group by event_type",nativeQuery=true)
	public List<Object> findAllGroupByEventTypeColumn();
	
	//------------前台查询---------
	//按图片类型降序查询
	public List<FamilyImage> findByOrderByEventType();
	
	//按类型分组
	@Query(value="SELECT * from family_image group by event_type",nativeQuery=true)
	public List<FamilyImage> findAllGroupByEventType();
	
	//查询最新4条数据(方式一：暂不用此方法)
	@Query(value="SELECT * from family_image ORDER BY img_id DESC LIMIT 4",nativeQuery=true)
	public List<FamilyImage> findFamilyImageTop4();
	
	//查询最新4条数据(方式一：现用此方法，持久化)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public List<FamilyImage> findTop4ByOrderByLastModified();
	
	
}
