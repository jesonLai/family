package sys.set.service;

import java.util.List;

import sys.jpa.repository.BaseRepository;
import sys.model.PubUser;
import sys.model.RecordFamilyLog;

public interface RecordFamilyLogService extends BaseRepository<RecordFamilyLog,Integer>{
	List<RecordFamilyLog> findAll();
	public RecordFamilyLog findOneByRequestIpAndRequestMethodAndDescriptionAndCreateMan
		(String requestIp,String requestMethod,String description,PubUser createMan);
}
