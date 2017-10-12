package sys.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import sys.model.PubRole;
import sys.util.func.UtilFactory;
/**
 * 获取权限
 * @author lxr
 *
 */
public class GetFamilyRoleLi extends Directive{
	private static final VelocityEngine velocityEngine = new VelocityEngine(); 
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "getFamilyRoleLi"; 
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		 return LINE;  
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean render(InternalContextAdapter context, Writer writer,  
	          Node node)throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		 EntityManager em= UtilFactory.getSpringContext().getEntityManagerFactory().createEntityManager();
	      Query query= em.createNamedQuery("PubRole.findAll");
	      List<PubRole> list=query.getResultList();
	      Map map = new HashMap();  
	      map.put("ROLESLIST", list);  
	      String authority="#foreach($ROLE in $ROLESLIST)"
	      		+ "#if(\"$!{ROLE.roleName}\"!=\"\")<li>"
	      		+ "<a href=\"javascript:void(0);\" class=\"btn\" ><input type=\"radio\" value=\"$!{ROLE.roleId}\" name=\"roleName\"/>$!{ROLE.roleName}</a><b></b></li>#end#end";
		writer.write(renderTemplate(map,authority));  
	    return true; 
	}
	public static String renderTemplate( Map map,String vimStr){  
	      VelocityContext context = new VelocityContext(map);  
	      StringWriter writer = new StringWriter();  
	      try {  
	          velocityEngine.evaluate(context, writer, "", vimStr);  
	      } catch (ParseErrorException e) {  
	          // TODO Auto-generated catch block   
	          e.printStackTrace();  
	      } catch (MethodInvocationException e) {  
	          // TODO Auto-generated catch block   
	          e.printStackTrace();  
	      } catch (ResourceNotFoundException e) {  
	          // TODO Auto-generated catch block   
	          e.printStackTrace();  
	      }
	      return writer.toString();  
	  }  
	
}
