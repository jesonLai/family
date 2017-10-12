package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.FamilyMessage;

public interface FamilyMessageService extends BaseRepository<FamilyMessage, Integer>{
	public List<FamilyMessage> findAll();
	//查询最新6条数据
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public List<FamilyMessage> findTop6ByOrderByMessageDateDesc();
}
