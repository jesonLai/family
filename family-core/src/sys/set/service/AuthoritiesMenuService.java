package sys.set.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import sys.jpa.repository.BaseRepository;
import sys.model.PubAuthoritiesMenu;
import sys.model.PubAuthority;
import sys.model.PubMenu;

/**
 * 权限功能资源
 * 
 * @author lxr
 * @param <T>
 *
 */
@Repository
public interface AuthoritiesMenuService extends BaseRepository<PubAuthoritiesMenu, Integer> {
    PubAuthoritiesMenu findOneByPubAuthorityAndPubMenu(PubAuthority pubAuthority, PubMenu pubMenu);

    // PubAuthoritiesMenu findOneByPubMenu(PubMenu pubMenu);
    List<PubAuthoritiesMenu> findByPubMenu(PubMenu pubMenu);
}
