package sys.set.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.model.UserInfo;
import sys.model.controller.FileEntity;
import sys.model.controller.TableRquestData;
import sys.set.service.FamilyNewsService;
import sys.set.service.UserInfoService;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Repository(SpringAnnotationDef.SER_FAMILYCELEBRITY)
@Transactional(readOnly = true)
public class FamilyCelebrityServiceImpl {
    private static final String FAMILYNEW = "FAMILYNEW";
    private static final String DATA_MISSING = "参数丢失！";
    private static final String INSERT_SUCCESS = "提交成功！";
    private static final String INSERT_FAIL = "提交失败！";
    private static final String USERINFONAME_NULL = "名人名称不能为空！";
    private static final String EDITOR_CONTENT_NULL = "编辑内容不能为空！";
    private static final String FAMILY_NCELEBRITY_NOT_IN = "未查找到名人信息！";

    @Resource
    FamilyNewsService familyNewsService;

    @Resource
    UserInfoService userInfoService;

    /**
     * 添加家族名人
     * 
     * @param map
     * @throws IOException
     * @throws IllegalStateException
     */
    @Transactional
    public void addNew(HttpServletRequest request, UserInfo userInfo, String editorValue)
	    throws IllegalStateException, IOException {
	if (StringUtil.isEmpty(userInfo)) {
	    throw new BusinessException(DATA_MISSING);
	}
	if (StringUtil.isEmpty(userInfo.getUserName())) {
	    throw new BusinessException(USERINFONAME_NULL);
	}
	if (StringUtil.isEmpty(editorValue)) {
	    throw new BusinessException(EDITOR_CONTENT_NULL);
	}
	userInfo.setUserDesc(editorValue);
	List<FileEntity> fileEntities = FileUpload.writeFile(request, Sys_Const.FAMILY_CELEBRITY_FILE_FOLDER_PATH,
		userInfo.getHeadImageName());
	if (!StringUtil.isEmpty(fileEntities) && fileEntities.size() != 0 && !StringUtil.isEmpty(fileEntities.get(0))
		&& !StringUtil.isEmpty(fileEntities.get(0).getName())) {
	    userInfo.setHeadImageName(fileEntities.get(0).getName());
	}
	userInfo.setCreateDate(new Date());
	userInfo.setCelebrityFlag(1);
	userInfo = userInfoService.save(userInfo);
	if (userInfo.getUserInfoId() < 0) {
	    throw new BusinessException(INSERT_FAIL);
	}

    }

    /**
     * 获取所有家族名人列表
     * 
     * @param tableRquestData
     * @param search
     * @return
     */
    public TableRquestData getFamilyCelebrityPagination(TableRquestData tableRquestData, final String search) {
	int pageNumber = tableRquestData.getStart();
	int pageSize = tableRquestData.getLength();
	if (pageSize < 0)
	    pageSize = userInfoService.findByCelebrityFlag(1).size();
	Page<UserInfo> userInfoPage = userInfoService.findAll(new Specification<UserInfo>() {
	    @Override
	    public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		Predicate predicate = cb.equal(root.get("celebrityFlag").as(int.class), 1);
		if (StringUtil.isEmpty(search)) {
		    return predicate;
		}
		return null;
		// else {
		//
		// Predicate predicate1 =
		// cb.like(root.get("eventType").as(String.class), "%" + search
		// + "%");
		// Predicate predicate2 =
		// cb.like(root.join("pubUser").get("userAccount").as(String.class),
		// "%" + search + "%");
		// return cb.equal(predicate,cb.or(predicate1,predicate2));
		// }

	    }

	}, new PageRequest((pageNumber / pageSize), pageSize, new Sort(Direction.DESC, "createDate")));
	List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
	List<UserInfo> uil = userInfoPage.getContent();
	for (UserInfo ui : uil) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    String imgSrc = StringUtil.isEmpty(ui.getHeadImageFolder()) || StringUtil.isEmpty(ui.getHeadImageName())
		    ? "" : MessageFormat.format(ui.getHeadImageFolder(), "/") + "/" + ui.getHeadImageName();
	    map.put("userInfoId", StringUtil.isEmpty(ui.getUserInfoId()) ? "" : ui.getUserInfoId());
	    map.put("userInfoName", StringUtil.isEmpty(ui.getUserName()) ? "" : ui.getUserName());
	    map.put("userInfoHeadImage", MessageFormat.format(Sys_Const.FAMILY_CELEBRITY_FILE_FOLDER_PATH, "/") + "/"
		    + ui.getHeadImageName());
	    map.put("userInfoDesc", StringUtil.isEmpty(ui.getUserDesc()) ? "" : ui.getUserDesc());
	    map.put("userInfoCreateDate", StringUtil.isEmpty(ui.getCreateDate()) ? ""
		    : UtilFactory.getSysDate().DateToString(ui.getCreateDate()));
	    map.put("userInfoReleaseFlag", StringUtil.isEmpty(ui.getReleaseFlag()) ? "" : ui.getReleaseFlag());
	    mapData.add(map);
	}
	tableRquestData.setRecordsFiltered(userInfoPage.getTotalElements());
	tableRquestData.setRecordsTotal(userInfoPage.getTotalElements());
	tableRquestData.setData(mapData);
	return tableRquestData;
    }

    /**
     * 删除名人
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    @Transactional
    public void delFamilyCelebrity(HttpServletRequest request, UserInfo userInfo) throws BusinessException {
	if (StringUtil.isEmpty(userInfo) || StringUtil.isEmpty(userInfo.getUserInfoId()))
	    throw new BusinessException(DATA_MISSING);
	userInfo = userInfoService.findOne(userInfo.getUserInfoId());
	if (StringUtil.isEmpty(userInfo) || StringUtil.isEmpty(userInfo.getUserInfoId()))
	    throw new BusinessException(FAMILY_NCELEBRITY_NOT_IN);
	userInfoService.delete(userInfo);
	FileUpload.deleteFile(request, Sys_Const.FAMILY_CELEBRITY_FILE_FOLDER_PATH, userInfo.getHeadImageName());
    }

    /**
     * 查询用户信息
     * 
     * @param userInfo
     * @return
     */
    public UserInfo getUserInfo(UserInfo userInfo) {
	if (!StringUtil.isEmpty(userInfo) && !StringUtil.isEmpty(userInfo.getUserInfoId()))
	    userInfo = userInfoService.findOne(userInfo.getUserInfoId());
	return userInfo;
    }

    @Transactional
    public void releaseFamilyCelebrity(UserInfo userInfo) throws BusinessException {
	if (StringUtil.isEmpty(userInfo) || StringUtil.isEmpty(userInfo.getUserInfoId()))
	    throw new BusinessException(DATA_MISSING);
	userInfo = userInfoService.findOne(userInfo.getUserInfoId());
	if (StringUtil.isEmpty(userInfo) || StringUtil.isEmpty(userInfo.getUserInfoId()))
	    throw new BusinessException(FAMILY_NCELEBRITY_NOT_IN);

	userInfo.setReleaseFlag(userInfo.getReleaseFlag() != 1 ? 1 : 0);
    }

}
