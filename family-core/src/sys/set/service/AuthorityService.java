package sys.set.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import sys.jpa.repository.BaseRepository;
import sys.model.PubAuthority;
/**
 * 权限中心
 * @author lxr
 *
 */
@Repository
public interface AuthorityService extends BaseRepository<PubAuthority, String>{
	public List<PubAuthority> findAll();
	public List<PubAuthority> findByAuthorityId(Integer authorityId);
	public PubAuthority findOneByAuthorityName(String authorityName);
	public PubAuthority findOneByAuthorityNameAndAuthorityIdNot(String authorityName,Integer authorityId);
	
}
