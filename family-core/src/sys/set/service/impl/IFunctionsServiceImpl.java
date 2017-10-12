package sys.set.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.PubFunction;
import sys.set.service.AuthorityFunctionsService;
import sys.set.service.AuthorityService;
import sys.set.service.FunctionsService;
import sys.set.service.impl.IMenuServiceImpl.PushMenu;
import sys.util.JSONUtils;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Service(SpringAnnotationDef.SER_FUNCTION)
@Transactional(readOnly = true)
public class IFunctionsServiceImpl{

	protected final Log _Log = LogFactory.getLog(getClass());
	private static final String DELETESUCCESS = "删除成功!";
	private static final String DELETEDATALOSE = "删除数据丢失!";
	private static final String INSERTSUCCESS = "提交成功!";
	private static final String INSERTDATALOSE = "新增数据丢失!";
	private static final String OPERATIONDATALOSE = "操作数据丢失!";
	@Resource
	private FunctionsService functionsService;

	public List<PubFunction> findAll(){
		return functionsService.findAll();
	}

	/**
	 * 获取每一个功能
	 * 
	 * @param functionId
	 * @return
	 */
	public PubFunction getPubFunction(Integer functionId){
		return functionsService.findOne(functionId);
	}

	/**
	 * 新增
	 * 
	 * @param map
	 * @throws SQLException
	 */
	@Transactional
	public void addNew(PubFunction function) throws SQLException{
		if (StringUtil.isEmpty(function) || StringUtil.isEmpty(function.getFunctionName())){
			throw new BusinessException(INSERTDATALOSE);
		}
		function = functionsService.save(function);
	}

	/**
	 * 获取所有权限资源列表
	 * 
	 * @param tableRquestData
	 * @param search
	 * @return
	 */
	public Map<String, Object> getFunctionsPagination(){
		List<PubFunction> pubFunctionList = functionsService.findAll();
		List<Map<String, Object>> mapData = new ArrayList<Map<String, Object>>();
		Map<String, Object> maps = new HashMap<String, Object>();
		HashMap functionList = new HashMap();
		// 根节点  
		List<Map<String, Object>>functionRoot = new ArrayList<Map<String, Object>>();
		// 根据结果集构造节点列表（存入散列表）  
		for (Iterator it = pubFunctionList.iterator();it.hasNext();){
			PubFunction pFunction = (PubFunction) it.next();
			PushFunction pushFunction = new PushFunction();
			pushFunction.functionId = ObjectUtils.toString(StringUtil.isEmpty(pFunction.getFunctionId()) ? 0 : pFunction.getFunctionId());
			pushFunction.functionName = StringUtil.isEmpty(pFunction.getFunctionName()) ? "" : pFunction.getFunctionName();
			pushFunction.functionUrl = StringUtil.isEmpty(pFunction.getFunctionUrl()) ? "" : pFunction.getFunctionUrl();
			pushFunction.functionCreateDate = StringUtil.isEmpty(pFunction.getCreateDate()) ? "" : UtilFactory.getSysDate().DateToString(pFunction.getCreateDate());
			pushFunction.functionCreatePerson = StringUtil.isEmpty(pFunction.getCreatePubUser()) ? "" : pFunction.getCreatePubUser().getUserAccount();
			pushFunction.functionUpdateDate = StringUtil.isEmpty(pFunction.getUpdateDate()) ? "" : UtilFactory.getSysDate().DateToString(pFunction.getUpdateDate());
			pushFunction.functionUpdatePerson = StringUtil.isEmpty(pFunction.getUpdatePubUser()) ? "" : pFunction.getUpdatePubUser().getUserAccount();
			pushFunction.functionParentId = ObjectUtils.toString(StringUtil.isEmpty(pFunction.getFunctionParent()) ? "" : pFunction.getFunctionParent().getFunctionId());
			pushFunction.functionParentName = StringUtil.isEmpty(pFunction.getFunctionParent()) ? "" : StringUtil.isEmpty(pFunction.getFunctionParent().getFunctionName()) ? "" : pFunction
					.getFunctionParent().getFunctionName();
			functionList.put(pushFunction.functionId,pushFunction);
		}
		// 构造无序的多叉树  
		Set entrySet = functionList.entrySet();
		for (Iterator it = entrySet.iterator();it.hasNext();){
			PushFunction pushFunction = (PushFunction) ((Map.Entry) it.next()).getValue();
			if (pushFunction == null || StringUtil.isEmpty(pushFunction.functionParentId)){
				functionRoot.add(JSONUtils.toJSONObject(pushFunction.toString()));
			}else{
				((PushFunction) functionList.get(pushFunction.functionParentId)).addChild(pushFunction);
			}
		}
		maps.put("data",functionRoot);
		return maps;
	}

	/**
	 * 更新
	 * 
	 * @param map
	 * @throws SQLException
	 */
	public void update(Map<String, Object> map) throws SQLException{
		if (StringUtil.isEmpty(map.get("function"))){
			map.put(BaseHint.MESSAGE,"需要修改的数据丢失!");
			return;
		}
		PubFunction function = (PubFunction) map.get("function");
		System.out.println(functionsService.findOne(function.getFunctionId()));
		if (StringUtil.isEmpty(functionsService.findOne(function.getFunctionId()))){
			map.put(BaseHint.MESSAGE,"需要修改的数据对象不存在!");
			return;
		}
		function = functionsService.save(function);
		map.put(BaseHint.MESSAGE,"提交成功!");
	}

	/**
	 * 删除
	 * 
	 * @param map
	 */
	public void delete(Map<String, Object> map){
		if (StringUtil.isEmpty(map.get("function"))){
			map.put(BaseHint.MESSAGE,DELETEDATALOSE);
			return;
		}
		PubFunction function = (PubFunction) map.get("function");
		if (StringUtil.isEmpty(function) || StringUtil.isEmpty(function.getFunctionId())){
			map.put(BaseHint.MESSAGE,OPERATIONDATALOSE);
			return;
		}
		functionsService.delete(function.getFunctionId());
		map.put(BaseHint.MESSAGE,DELETESUCCESS);
		map.remove("function");
		map.put("check",true);
	}

	class PushFunction{

		public String functionId;
		public String functionName;
		public String functionUrl;
		public String functionCreateDate;
		public String functionCreatePerson;
		public String functionUpdateDate;
		public String functionUpdatePerson;
		public String functionParentId;
		public String functionParentName;
		public int functionOrder;
		private Children children = new Children();

		public void addChild(PushFunction pushFunction){
			this.children.addChild(pushFunction);
		}

		public String toString(){
			String result = "{functionId:'" + functionId + "', functionName:'" + functionName + "', functionUrl:'" + functionUrl + "', functionCreateDate:'" + functionCreateDate
							+ "', functionCreatePerson:'" + functionCreatePerson + "', functionUpdateDate:'" + functionUpdateDate + "', functionUpdatePerson:'" + functionUpdatePerson
							+ "', functionParentId:'" + functionParentId + "', functionParentName:'" + functionParentName + "'";
			if (children != null && children.getSize() != 0){
				result += ", children : " + children.toString();
			}else{
				result += ", leaf : true";
			}
			return result + "}";
		}
	}

	class Children{

		private List<PushFunction> list = new ArrayList<PushFunction>();

		public int getSize(){
			return list.size();
		}

		public void addChild(PushFunction pushFunction){
			list.add(pushFunction);
		}

		// 拼接孩子节点的JSON字符串  
		public String toString(){
			String result = "[";
			for (Iterator it = list.iterator();it.hasNext();){
				result += ((PushFunction) it.next()).toString();
				result += ",";
			}
			result = result.substring(0,result.length() - 1);
			result += "]";
			return result;
		}
	}

	class NodeIDComparator implements Comparator{

		// 按照节点编号比较  
		public int compare(Object o1, Object o2){
			int j1 = ((PushFunction) o1).functionOrder;
			int j2 = ((PushFunction) o2).functionOrder;
			return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
		}
	}
}
