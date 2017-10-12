package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.Announcement;

public interface AnnouncementService  extends BaseRepository<Announcement, Integer>{
	public List<Announcement> findAll();
	
	//查询最新4条数据
	@Query(value="SELECT * from Announcement ORDER BY id DESC LIMIT 5",nativeQuery=true)
	public List<Announcement> findTop5OrderByIdDesc();
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public List<Announcement> findTop6ByOrderByPlacedTopDescPlacedTopDateDescCreateDateDesc();
}
