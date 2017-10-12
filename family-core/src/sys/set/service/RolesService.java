package sys.set.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sys.jpa.repository.BaseRepository;
import sys.model.PubRole;
@Repository
public interface RolesService extends BaseRepository<PubRole, Integer>,CrudRepository<PubRole, Integer>{
	public List<PubRole> findAll();
	public List<PubRole> findByRoleId(Integer roleId);
	
	public PubRole findOneByRoleSysName(String roleSysName);
	public PubRole findOneByRoleName(String roleName);
	
	public List<PubRole> findByRoleName(String roleName);
	public List<PubRole> findByRoleSysName(String roleSysName);
	
	public PubRole findByRoleParent(PubRole roleParent);
	
	
	
	public PubRole findOneByRoleNameAndRoleIdNot(String roleName,Integer roleId);
	public PubRole findOneByRoleSysNameAndRoleIdNot(String roleSysName,Integer roleId);
	
}
