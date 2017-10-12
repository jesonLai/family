package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.FamilyVideo;

public interface FamilyVideoService extends BaseRepository<FamilyVideo, Integer>{
	public List<FamilyVideo> findAll();
	public FamilyVideo findOneByPageUrl(String pageUrl);

	//按类型分组
	@Query(value="SELECT event_type from family_video group by event_type",nativeQuery=true)
	public List<Object> findByGroupByEventTypeColumn();
	
	
	//------------前台查询---------
	//按图片类型降序查询
	@Query(value="SELECT * from family_video order by lastModified DESC",nativeQuery=true)
	public List<FamilyVideo> findAllOrderByLastModifiedDesc();
	
	//按类型分组
	@Query(value="SELECT * from family_video group by event_type",nativeQuery=true)
	public List<FamilyVideo> findByGroupByEventType();
	
	//查询最新4条数据
	@Query(value="SELECT * from family_video ORDER BY id DESC LIMIT 4",nativeQuery=true)
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public List<FamilyVideo> findFamilyVideoTop4();
	
}
