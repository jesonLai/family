package sys.set.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.ProjectConst;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.PubAuhorityResource;
import sys.model.PubAuthority;
import sys.model.PubAuthorityGroup;
import sys.model.PubFunction;
import sys.model.PubMenu;
import sys.model.PubRolesAuthority;
import sys.set.service.AuhorityResourceService;
import sys.set.service.AuthorityGroupService;
import sys.set.service.AuthorityRolesService;
import sys.set.service.AuthorityService;
import sys.set.service.FunctionsService;
import sys.set.service.MenuService;
import sys.util.JSONUtils;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Service(SpringAnnotationDef.SER_AUTHORITY_GROUP)
@Transactional(readOnly = true)
public class AuthorityServiceGroupImpl{
    protected final Log _Log = LogFactory.getLog(getClass());
    @Resource
    AuthorityGroupService authorityGroupService;
    
    /**
     * 已有权限组
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    public List<Map<String, Object>> finAllAuthorityGroup() {
		_Log.debug("**********已有权限组选择**********");
		List<Map<String,Object>> listMaps=new ArrayList<Map<String,Object>>();
				
		List<PubAuthorityGroup> pubAuthorityGroupList=authorityGroupService.findAll();
		for (PubAuthorityGroup pubAuthorityGroup:pubAuthorityGroupList){
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("value",pubAuthorityGroup.getAuthorityGroupId());
			map.put("label",pubAuthorityGroup.getAuthorityGroupName());
			listMaps.add(map);
		}
		return listMaps;
    }
}
