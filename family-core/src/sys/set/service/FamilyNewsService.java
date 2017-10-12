package sys.set.service;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import sys.jpa.repository.BaseRepository;
import sys.model.FamilyNew;

public interface FamilyNewsService extends BaseRepository<FamilyNew, Integer> {
    public List<FamilyNew> findAll();

    public List<FamilyNew> findByFlag(int flag);

    @Query(value = "SELECT event_type from family_news where flag=?1 group by event_type", nativeQuery = true)
    public List<Object> findAllByFlagGroupByEventTypeColumn(int flag);

    // -----------------以下市前台查询-----------------------

    // 按类型,栏目排序查询
    public List<FamilyNew> findByFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(int flag);

    // 宗族族谱 标题搜索方法
    @Query(value = "SELECT * from family_news where title like ?1 and flag=?2 order by placed_top desc,placed_top_date desc,relese_date desc", nativeQuery = true)
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    public List<FamilyNew> findByTitleLikeAndFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(String title,
	    Integer flag);

    // 按类型分组
    @Query(value = "SELECT * from family_news where flag=?1 group by event_type order by placed_top desc", nativeQuery = true)
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    public List<FamilyNew> findAllByFlagGroupByEventType(int flag);

    // 查询最新4条数据
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    public List<FamilyNew> findTop4ByFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(int flag);

    // 查询最新8条数据
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
    public List<FamilyNew> findTop8ByFlagOrderByPlacedTopDescPlacedTopDateDescReleseDateDesc(int flag);

}
