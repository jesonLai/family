package sys.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sys.SpringAnnotationDef;
import sys.model.InstationSearch;
import sys.set.service.InstationSearchService;

@Repository(SpringAnnotationDef.Web_SER_INSTATIONSEARCH)
@Transactional(readOnly = true)
public class InstationSearchServiceImpl {
	@Resource
	InstationSearchService instationSearchService;
	/**
	 * 添加站内搜索
	 * @param map
	 */
	@Transactional
	public void addNew(InstationSearch instationSearch){
	    instationSearchService.save(instationSearch);
	}
	/**
	 * 删除站内搜索
	 * @param map
	 */
	@Transactional
	public void delete(InstationSearch instationSearch){
	    instationSearchService.delete(instationSearch);
	}
	
	
}
