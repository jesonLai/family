package sys.set.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import sys.jpa.repository.BaseRepository;
import sys.model.PubAuthority;
import sys.model.PubRole;
import sys.model.PubRolesAuthority;
/**
 * 权限角色
 * @author lxr
 *
 */
@Repository
public interface AuthorityRolesService extends BaseRepository<PubRolesAuthority, Integer>{
	public List<PubRolesAuthority> findByPubRole(PubRole pubRole);
	public PubRolesAuthority findByPubAuthorityAndPubRole(PubAuthority pubAuthority,PubRole pubRole);
}
