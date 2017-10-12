package sys.user.front.service.imp;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.model.UserInfo;
import sys.set.service.UserInfoService;

@Repository(SpringAnnotationDef.Web_SER_USERSINFO)
@Transactional(readOnly = true)
public class WebUserInfoServiceImpl {

    @Resource
    UserInfoService userInfoService;

    // 分页查找家族名人方法
    public Page<UserInfo> findAllFront(int pageNumber, int pageSize, final int indexShow) {
	Sort sort = new Sort(Direction.DESC, "createDate"); // 按发布时间查询新闻信息
	PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
	Page<UserInfo> productPage = userInfoService.findAll(new Specification<UserInfo>() {
	    @Override
	    public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = cb.conjunction();
		List<Expression<Boolean>> expressions = predicate.getExpressions();

		// 家族名人
		expressions.add(cb.equal(root.<Integer> get("celebrityFlag"), 1));

		// 是否首页展示1:是，0:否
		if (indexShow == 1)
		    expressions.add(cb.equal(root.<Integer> get("releaseFlag"), indexShow));

		return predicate;
	    }
	}, pageRequest);
	return productPage;
    }

    // 按id查询数据
    public UserInfo findById(Integer id) {
	return userInfoService.findOne(id);
    }

    // 查询最新5条数据
    public List<Object> findUserMingren(int celebrityFlag, int releaseFlag) {
	List<Object> objectList = Lists.newArrayList();
	List<UserInfo> userInfoList = userInfoService
		.findByCelebrityFlagAndReleaseFlagOrderByUserInfoIdDesc(celebrityFlag, releaseFlag);
	for (UserInfo userInfo : userInfoList) {
	    Map<String, Object> userInfoMap = Maps.newHashMap();
	    userInfoMap.put("userInfoId", userInfo.getUserInfoId());
	    userInfoMap.put("userInfoImg", MessageFormat.format(Sys_Const.FAMILY_CELEBRITY_FILE_FOLDER_PATH, "/") + "/"
		    + userInfo.getHeadImageName());
	    userInfoMap.put("userInfoName", userInfo.getUserName());
	    objectList.add(userInfoMap);
	}
	return objectList;
    }

}
