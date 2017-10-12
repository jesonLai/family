package sys.set.service;


import java.util.List;

import org.springframework.stereotype.Repository;

import sys.jpa.repository.BaseRepository;
import sys.model.PubAuthoritiesFunction;
import sys.model.PubAuthority;
import sys.model.PubFunction;
/**
 * 权限功能资源
 * @author lxr
 * @param <T>
 *
 */
@Repository
public interface AuthorityFunctionsService extends BaseRepository<PubAuthoritiesFunction, Integer>{
	PubAuthoritiesFunction findOneByPubAuthorityAndPubFunction(PubAuthority pubAuthority , PubFunction pubFunction);
	PubAuthoritiesFunction findOneByPubFunction(PubFunction pubFunction);
	List<PubAuthoritiesFunction> findByPubFunction(PubFunction pubFunction);
	
}
