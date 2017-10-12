package sys.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import com.google.common.collect.Lists;

import sys.model.PubMenu;
import sys.model.controller.UserInfoContext;
import sys.util.MyComparator;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

public class GetFamilyMenu extends Directive{
	private static final VelocityEngine velocityEngine = new VelocityEngine(); 
	private List<PubMenu> pubMenued=Lists.newArrayList();
	private static String basePath;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "getFamilyMenus"; 
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		 return LINE;  
	}
	@Override
	public boolean render(InternalContextAdapter context, Writer writer,  
	          Node node)throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		UserInfoContext ufc= UtilFactory.getSpringContext().getUserInfoContext();
		basePath=ufc.getBasePath();
	    String menuTree=StringUtil.isEmpty(ufc.getFormattMenu())?firtLi(ufc):ufc.getFormattMenu(); 
	    ufc.setFormattMenu(menuTree);
		writer.write(menuTree);  
	      return true; 
	}
	private String firtLi(UserInfoContext ufc){
		List<PubMenu> pubMenus=ufc.getPubMenus();
		Collections.sort(pubMenus,new MyComparator());
		StringBuilder sTreeMenu=new StringBuilder();
		if(pubMenus!=null&&pubMenus.size()>0){
			for (PubMenu pubMenu : pubMenus) {
				PubMenu pubMenuParent=pubMenu.getPubMenuParent();ufc.getBasePath();
				if(StringUtil.isEmpty(pubMenuParent)||StringUtil.isEmpty(pubMenuParent.getMenuId())||pubMenuParent.getMenuId()==0||pubMenuParent.getMenuId()==null){
					String menuUrl=pubMenu.getMenuUrl();
					String isSend=StringUtil.isEmpty(menuUrl)?"send=\"no\"":"send=\"yes\"";
					StringBuffer sb=new StringBuffer("<li>");
					sb.append("<a href=\"javascript:void(0)\" page=\""+basePath+"/admin/menus/sysMenu?action=");
					sb.append(menuUrl).append("\""+isSend+"><i class=\"")
					.append(pubMenu.getItemIcon()).append("\"></i>").append("<span class=\"title\">").append(pubMenu.getMenuName())
					.append("</span>");
					pubMenued.add(pubMenu);
					
					childrenLi(pubMenus,pubMenu.getMenuId(),sb);
					sb.append("</li>");
					sTreeMenu.append(sb);
				}
			}
		}
		return sTreeMenu.toString();
	}
	private String childrenLi(List<PubMenu> pubMenus,Integer menuId,StringBuffer sb){
		List<PubMenu> childrenList=getChildren(pubMenus, menuId);
		if(childrenList.size()>0){
			sb.append("<span class=\"selected\"></span>");
			sb.append("<span class=\"arrow\"></span>");
			sb.append("</a>");
			sb.append("<ul class=\"sub-menu\">");
			for (PubMenu pubMenu : childrenList) {
				String menuUrl=pubMenu.getMenuUrl();
				String isSend=StringUtil.isEmpty(menuUrl)?"send=\"no\"":"send=\"yes\"";
				sb.append("<li class=\"active\"><a href=\"javascript:void(0)\" page=\""+basePath+"/admin/menus/sysMenu?action=");
				sb.append(menuUrl).append("\""+isSend+"><i class=\"")
				.append(pubMenu.getItemIcon()).append("\"></i>").append(pubMenu.getMenuName()).append("</a>");
				childrenLi(pubMenus,pubMenu.getMenuId(),sb);
				sb.append("</li>");
			}
			sb.append("</ul>");
		}else{
			sb.append("</a>");
		}
		return sb.toString();
		
	}
	private List<PubMenu> getChildren(List<PubMenu> pubMenus,Integer menuId){
		List<PubMenu> childrenList=Lists.newArrayList();
		if(pubMenus!=null&&pubMenus.size()>0){
			for (PubMenu pubMenu : pubMenus) {
				if(!pubMenued.contains(pubMenu)){
					PubMenu pubMenuParent=pubMenu.getPubMenuParent();
					if(!StringUtil.isEmpty(pubMenuParent)&&!StringUtil.isEmpty(pubMenuParent.getMenuId())&&pubMenuParent.getMenuId()==menuId&&menuId!=null&&menuId!=0){
						pubMenued.add(pubMenu);
						childrenList.add(pubMenu);
	//					childrenList.add(sb);
	////					childrenList(pubMenus,pubMenuParent.getMenuId(),sbAll);
	//					sb.append("<li><a href=\"javascript:void(0)\" page=\""+basePath+"/admin/menus/sysMenu?action=");
	//					sb.append(pubMenu.getMenuUrl()).append("\"><i class=\"")
	//					.append(pubMenu.getItemIcon()).append("\"></i>").append(pubMenu.getMenuName()).append("</a>");
						
						
					}
				}
			}
		}
		return childrenList;
	}
	
}
