package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.AncestralTemple;

public interface AncestralTempleService extends BaseRepository<AncestralTemple, Integer> {
    public List<AncestralTemple> findAll();

    // 查询最新5条数据
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    public List<AncestralTemple> findTop5ByOrderByPlacedTopDescPlacedTopDateDescCreateDateDesc();
}
