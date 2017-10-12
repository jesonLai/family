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

@Service(SpringAnnotationDef.SER_AUTHORITY)
@Transactional(readOnly = true)
public class AuthorityServiceImpl{
    protected final Log _Log = LogFactory.getLog(getClass());
	private static final String AUTHORITY_NAME_IN="资源名称已存在";
	private static final String AUTHORITY_NAME_IS_NULL="权限名称不能为空";
	private static final String AUTHORITY_GROUP_NAME_IS_NULL="权限组名称不能为空";

	@Resource
	AuthorityService authorityService;
	@Resource
	AuthorityGroupService authorityGroupService;
	@Resource
	AuhorityResourceService auhorityResourceService;
	@Resource
	FunctionsService functionsService;
	@Resource
	MenuService menuService;
	@Resource
	AuthorityRolesService authorityRolesService;
	
	/**
	 * 添加权限
	 * @param map
	 */
	@Transactional
	public void addNew(PubAuthority pubAuthority){
		
		
		if(StringUtil.isEmpty(pubAuthority)||StringUtil.isEmpty(pubAuthority.getAuthorityName()))
			throw new BusinessException(AUTHORITY_NAME_IS_NULL);
		if(!isAuthorityName(pubAuthority.getAuthorityId()))
			throw new BusinessException(AUTHORITY_NAME_IN);
		pubAuthority.setCreateDate(new Date());
		pubAuthority.setCreatePerson(UtilFactory.getSpringContext().getUserInfoContext().getUser());
		authorityService.save(pubAuthority);
		
	}
	/**
	 * 添加权限组
	 * @param map
	 */
	@Transactional
	public void addAuthorityGroup(PubAuthorityGroup pubAuthorityGroup){
		if(StringUtil.isEmpty(pubAuthorityGroup)||StringUtil.isEmpty(pubAuthorityGroup.getAuthorityGroupName()))
			throw new BusinessException(AUTHORITY_GROUP_NAME_IS_NULL);
		pubAuthorityGroup.setCreateDate(new Date());
		pubAuthorityGroup.setCreatePerson(UtilFactory.getSpringContext().getUserInfoContext().getUser());
		authorityGroupService.save(pubAuthorityGroup);
		
	}
	
	
	/**
	 * 权限中心数据
	 * 
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAuthorityCenter(){
//		List<PubAuthorityg> pubAuthorityList=authorityGroupService.findAll();
		List<PubAuthority> pubAuthorityList = authorityService.findAll();
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		Map<String, Object> maps = new HashMap<String, Object>();
		HashMap<String,PushAuthority> authorityList = new HashMap<String,PushAuthority>();
		// 根节点  
		List<Map<String, Object>> authorityRoot = new ArrayList<Map<String, Object>>();
		List<PushAuthority> authorityRoots = new ArrayList<PushAuthority>();
		// 根据结果集构造节点列表（存入散列表）  
		for (Iterator it = pubAuthorityList.iterator();it.hasNext();){
			PubAuthority pa = (PubAuthority) it.next();
			PushAuthority pushAuthority = new PushAuthority();
			pushAuthority.authorityId=pa.getAuthorityId();
			pushAuthority.authorityName=StringUtil.isEmpty(pa.getAuthorityName()) ? "" : pa.getAuthorityName();
			pushAuthority.createName=StringUtil.isEmpty(pa.getCreatePerson()) ? "" : pa.getCreatePerson().getUserAccount();
			pushAuthority.createDate= StringUtil.isEmpty(pa.getCreateDate()) ? "" : UtilFactory.getSysDate().DateToString(pa.getCreateDate());
			pushAuthority.updateName=StringUtil.isEmpty(pa.getUpdatePerson()) ? "" : pa.getUpdatePerson().getUserAccount();
			pushAuthority.updateDate= StringUtil.isEmpty(pa.getUpdateDate()) ? "" : UtilFactory.getSysDate().DateToString(pa.getUpdateDate());
			pushAuthority.authorityDesc= StringUtil.isEmpty(pa.getAuthorityDesc()) ? "" : pa.getAuthorityDesc();
			pushAuthority.resourceType=StringUtil.isEmpty(pa.getPubAuhorityResource()) ? "" : pa.getPubAuhorityResource().getAuthorityResourceType();
			pushAuthority.resourceId=StringUtil.isEmpty(pa.getPubAuhorityResource()) ? 0 : pa.getPubAuhorityResource().getResourceId();
			
			if(ProjectConst.META_RESOURCE_FUNCTION_TYPES.equals(pushAuthority.resourceType)){
				PubFunction pubFunction=functionsService.findOne(pushAuthority.resourceId);
				if(!StringUtil.isEmpty(pubFunction)){
					pushAuthority.resourceName=StringUtil.nullIF(pubFunction.getFunctionName(),"");
				}
			}else if(ProjectConst.META_RESOURCE_MENU_TYPES.equals(pushAuthority.resourceType)){
				PubMenu pubMenu=menuService.findOne(pushAuthority.resourceId);
				if(!StringUtil.isEmpty(menuService.findOne(pushAuthority.resourceId))){
					pushAuthority.resourceName=StringUtil.nullIF(pubMenu.getMenuName(),"");
				}
			}else{
				pushAuthority.resourceName="";
			}
			PubAuthorityGroup  pubAuthorityGroup=pa.getAuthorityGroup();
			pushAuthority.auhtorityGroupId=StringUtil.isEmpty(pubAuthorityGroup) ? "0" : ""+pubAuthorityGroup.getAuthorityGroupId();
			pushAuthority.auhtorityGroupParnetId=StringUtil.isEmpty(pubAuthorityGroup) ?""+ 0 :StringUtil.isEmpty(pubAuthorityGroup.getAuthorityGroupParent())?""+0:""+pubAuthorityGroup.getAuthorityGroupParent().getAuthorityGroupId();
			authorityList.put(StringUtil.isEmpty(pushAuthority.auhtorityGroupId)||"0".equals(pushAuthority.auhtorityGroupId)?pushAuthority.authorityId:""+pushAuthority.auhtorityGroupId,pushAuthority);
			
			
			
//			authorityRoots.add(pushAuthority);
		}
		
		// 构造无序的多叉树  
		Set entrySet = authorityList.entrySet();
		for (Iterator it = entrySet.iterator();it.hasNext();){
			PushAuthority pushAuthority = (PushAuthority) ((Map.Entry) it.next()).getValue();
			_Log.debug("auhtorityGroupParnetId:"+pushAuthority.auhtorityGroupParnetId);
			if (StringUtil.isEmpty(pushAuthority.auhtorityGroupParnetId)||"0".equals(pushAuthority.auhtorityGroupParnetId)){
				authorityRoot.add(JSONUtils.toJSONObject(pushAuthority.toString()));
			}else{
				
				((PushAuthority) authorityList.get(pushAuthority.auhtorityGroupParnetId)).addChild(pushAuthority);
			}
		}
		
		maps.put("data",authorityRoot);
//		maps.put("data",authorityRoots);
		return maps;
	}
	
	
	@Transactional
	public void allocation(String authorityId,String type,Integer resourceId){
		if(ProjectConst.META_RESOURCE_FUNCTION_TYPES.equals(type)){
			if(StringUtil.isEmpty(functionsService.findOne(resourceId))){
				throw new BusinessException("未寻找到编号["+resourceId+"]的功能!");	
			}
		}else if(ProjectConst.META_RESOURCE_MENU_TYPES.equals(type)){
			if(StringUtil.isEmpty(menuService.findOne(resourceId))){
				throw new BusinessException("未寻找到编号["+resourceId+"]菜单!");	
			}
		}else{
			throw new BusinessException("未知类型!");	
		}
		
		if(StringUtil.isEmpty(authorityService.findOne(authorityId))){
			throw new BusinessException("未寻找到编号["+authorityId+"]权限!");	
		}
		PubAuhorityResource pubAuhorityResource=new PubAuhorityResource();
		pubAuhorityResource.setAuthorityId(authorityId);
		pubAuhorityResource.setAuthorityResourceType(type);
		pubAuhorityResource.setResourceId(resourceId);
		auhorityResourceService.save(pubAuhorityResource);
	}
	 /**
     * 删除
     * 
     * @param familyNewsId
     * @throws BusinessException
     */
    @Transactional
    public void delAuthority(PubAuthority pubAuthority ) throws BusinessException {
    	if(pubAuthority==null){
    		throw new BusinessException("删除失败");
    	}
    	PubAuhorityResource pubAuhorityResource=pubAuthority.getPubAuhorityResource();
    	List<PubRolesAuthority> pubRolesAuthorityList= pubAuthority.getPubRolesAuthorities();
    	if(pubRolesAuthorityList!=null){
	    	for (PubRolesAuthority pubRolesAuthority:pubRolesAuthorityList){
	    		authorityRolesService.delete(pubRolesAuthority);
			}
    	}
    	if(pubAuhorityResource!=null){
    		auhorityResourceService.delete(pubAuhorityResource);
    	}
    	if(pubAuthority!=null){
    		authorityService.delete(pubAuthority);
    	}
    }
	/**
	 * 检查权限编号的唯一
	 * 
	 * @param authorityId 权限编号
	 * @return
	 */
	public boolean isAuthorityName(String authorityId) {
			return StringUtil.isEmpty(authorityService.findOne(authorityId));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	class PushAuthority{
		public String auhtorityGroupId; 
		public String auhtorityGroupParnetId;
		public String authorityId;
		public String authorityName;
		public String createName;
		public String createDate;
		public String updateName;
		public String updateDate;
		public String authorityDesc;
		public Integer resourceId;
		public String resourceType;
		public String resourceName="";
		private Children children = new Children();

		public String toString(){
			String result = "{auhtorityGroupId:'"+auhtorityGroupId+"',authorityId:'" + authorityId + "', authorityName:'" + authorityName + "', createName:'" + createName + "', createDate:'" + createDate + "', updateName:'" + updateName
							+ "', updateDate:'" + updateDate + "', authorityDesc:'" + authorityDesc + "',resourceType:'"+resourceType+"',resourceName:'"+resourceName+"'";
			if (children != null && children.getSize() != 0){
				result += ", children : " + children.toString();
			}else{
				result += ", leaf : true";
			}
			return result + "}";
		}

		/**
		 * 
		 * @param pushMenu void
		 */
		public void addChild(PushAuthority pushAuthority){
			this.children.addChild(pushAuthority);
		}
	}

	class Children{

		private List list = new ArrayList();

		public int getSize(){
			return list.size();
		}

		public void addChild(PushAuthority pushAuthority){
			list.add(pushAuthority);
		}

		// 拼接孩子节点的JSON字符串  
		public String toString(){
			String result = "[";
			for (Iterator it = list.iterator();it.hasNext();){
				result += ((PushAuthority) it.next()).toString();
				result += ",";
			}
			result = result.substring(0,result.length() - 1);
			result += "]";
			return result;
		}
	}
}
