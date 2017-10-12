package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.PubUsersRole;

public interface UsersRolesService extends BaseRepository<PubUsersRole, Integer> {
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	PubUsersRole findOneByPubRoleAndPubUser(PubRole pubRole,PubUser pubUser );
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	List<PubUsersRole> findByPubUser(PubUser pubUser );
}
