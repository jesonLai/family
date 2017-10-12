package sys.set.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import sys.jpa.repository.BaseRepository;
import sys.model.InstationSearch;

/**
 * 站内搜索
 * 
 * @author lxr
 *
 */
@Repository
public interface InstationSearchService extends BaseRepository<InstationSearch, Integer> {
    public List<InstationSearch> findAll();

    public InstationSearch save(InstationSearch instationSearch);
}
