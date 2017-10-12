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
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;

import sys.util.StringUtil;
import sys.util.func.UtilFactory;

public class SelectTable extends Directive{
	private static final VelocityEngine velocityEngine = new VelocityEngine(); 
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "selectColumn"; 
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		 return LINE;  
	}
/**
 * 获取对应表的列
 * select [key,value]
 * 参数四个个：表名 列=key 列=value 条件=where
 */
	@Override
	@SuppressWarnings({"unchecked","rawtypes"})
	public boolean render(InternalContextAdapter context, Writer writer,  
	          Node node)throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		 SimpleNode sn = (SimpleNode) node.jjtGetChild(0);     
	     String tableName = (String)sn.value(context);//表名[数据库名称]
	     
	 	 SimpleNode sn1 = (SimpleNode) node.jjtGetChild(1);
	     String key = (String)sn1.value(context);//key
	      
	 	 SimpleNode sn2 = (SimpleNode) node.jjtGetChild(2);
	     String value = (String)sn2.value(context);//value
	     
	     SimpleNode sn3 = (SimpleNode) node.jjtGetChild(3);
	     String where = (String)sn3.value(context);//where 
	      
	     StringBuffer sb=new StringBuffer("select ");
	     sb.append(key).append(" , ").append(value).append(" from ").append(tableName);
	     if(!StringUtil.isEmpty(where))
	     sb.append(" where "+where);
	     
	      EntityManager em= UtilFactory.getSpringContext().getEntityManagerFactory().createEntityManager();
//	      
	      Query query= em.createNativeQuery(sb.toString());
	      SQLQuery nativeQuery=query.unwrap(SQLQuery.class);
	      nativeQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
	      List<Map<String,String>> list=nativeQuery.list();
	      Map map = new HashMap();  
	      map.put("TABLELIST", list);  
	      String select="<option></option>#foreach($TABLE in $TABLELIST)"
	      		+ "<option value=$!{TABLE."+key+"}>$!{TABLE."+value+"}</option>#end";
	      writer.write(renderTemplate(map,select));  
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
