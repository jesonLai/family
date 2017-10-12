package sys.set.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import sys.jpa.repository.BaseRepository;
import sys.model.PubFunction;
@Repository
public interface FunctionsService extends BaseRepository<PubFunction, Integer>{
	public List<PubFunction> findAll();
	public PubFunction save(PubFunction pubFunction);
	public PubFunction findOneByFunctionName(String functionName);
}
