package sys.set.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import sys.jpa.repository.BaseRepository;
import sys.model.PubAuthority;
import sys.model.PubAuthorityGroup;


public interface AuthorityGroupService  extends BaseRepository<PubAuthorityGroup,Integer>{
	public List<PubAuthorityGroup> findAll();
	 @Query(value = "SELECT authority_group_id,authority_group_name from pub_authority_group ", nativeQuery = true)
	 public List<Object> findAllByFlagGroupByAuthorityGroupName();
}
