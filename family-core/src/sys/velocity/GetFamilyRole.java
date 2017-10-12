package sys.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.google.common.collect.Lists;

import sys.model.PubRole;
import sys.model.PubUser;
import sys.model.PubUsersRole;
import sys.model.controller.UserInfoContext;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

public class GetFamilyRole extends Directive{
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "getFamilyRole"; 
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		 return LINE;  
	}
	@Override
	public boolean render(InternalContextAdapter context, Writer writer,  
	          Node node)throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		 SimpleNode sn = (SimpleNode) node.jjtGetChild(0);     
		 Integer loginId= (Integer)sn.value(context);//登录编号
		 UserInfoContext userInfoContext= UtilFactory.getSpringContext().getUserInfoContext();
		EntityManager em= UtilFactory.getSpringContext().getEntityManagerFactory().createEntityManager();
	    Query query= em.createQuery("SELECT p FROM PubUser p where p.uId=:uId");
	    Query roleQuery= em.createQuery("SELECT p FROM PubRole p");
	    query.setParameter("uId", StringUtil.isEmpty(loginId)?0:loginId);
	    List<PubUser> pubUsers=query.getResultList();
	    List<PubRole> pubRoles=roleQuery.getResultList();
	    String checkeedRole=formatPubUsers(pubUsers,pubRoles,userInfoContext);
		writer.write(checkeedRole);  
	      return true; 
	}
	//当前用户是开发者才可以拥有全部角色
	private String formatPubUsers(List<PubUser> pubUsers,List<PubRole> pubRoles,UserInfoContext userInfoContext){
		StringBuilder optionRole=new StringBuilder();
		//当前操作者的角色
		List<Integer> contextSysName=Lists.newArrayList();
		List<PubRole> contextUsersRoles=userInfoContext.getPubRoles(); 
		for (PubRole pubRole : contextUsersRoles) {
			contextSysName.add(pubRole.getRoleId());
		}
		
		
		//获取已有的角色
		List<String> sysName=Lists.newArrayList();
		if(pubUsers!=null&&pubUsers.size()>0){
			for (PubUser pubUser : pubUsers) {
				List<PubUsersRole> pubUsersRoles=pubUser.getPubUsersRoles();
				for (PubUsersRole pubUsersRole : pubUsersRoles) {
					PubRole checkedRole= pubUsersRole.getPubRole();
					sysName.add(checkedRole.getRoleSysName());
				}
			}
		}
		for (PubRole pubRole : pubRoles) {
			if(!contextSysName.contains(pubRole.getRoleId())){//过滤等级高的
				
			}else if(sysName!=null&&sysName.size()>0&&sysName.contains(pubRole.getRoleSysName())){
				optionRole.append("<option value="+pubRole.getRoleId()+" selected=selected>"+pubRole.getRoleName()+"</option>");
			}else{
				optionRole.append("<option value="+pubRole.getRoleId()+">"+pubRole.getRoleName()+"</option>");
			}
		}
		return optionRole.toString();
	}
	
}
