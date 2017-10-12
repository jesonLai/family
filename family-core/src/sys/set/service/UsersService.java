package sys.set.service;
import java.util.List;

import sys.jpa.repository.BaseRepository;
import sys.model.PubUser;
import sys.model.UserInfo;
public interface UsersService extends BaseRepository<PubUser, Integer>{
	public List<PubUser> findAll();
//	public PubUser save(PubUser user);
//	public PubUser findByUId(String user_id);
	
	public PubUser findOneByUserAccount(String userAccount);
	List<PubUser> findByUserInfo(UserInfo userInfo);
	public PubUser findOneByUserAccountAndUIdNot(String userAccount,Integer uId);
	
	public PubUser findOneByUserInfoNotAndUserAccount(UserInfo userInfo,String userAccount);
	
	
}
