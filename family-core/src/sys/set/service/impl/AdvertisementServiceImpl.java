package sys.set.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import sys.SpringAnnotationDef;
import sys.exception.handing.BusinessException;
import sys.model.controller.FileEntity;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;
/**
 * 前台滚动
 * 生成XML文件--=读取XML文件
 * 文件路径：/WEB-INF/advertisement.xml
 * 图片路径：admin/advertisement/名称
 * @author Administrator
 *
 */
@Repository(SpringAnnotationDef.SER_ADVERTISEMENT)
public class AdvertisementServiceImpl {
	private static final Logger logger = Logger.getLogger(AdvertisementServiceImpl.class.getName());
	
	private Document document;
	public void addNewXml(final String systemUrl,HttpServletRequest request,FileEntity fe) throws BusinessException, IOException, DocumentException{
		List<FileEntity> fel=new FileUpload().writeFile(request, null, null);
		if(StringUtil.isEmpty(fel)||fel.size()==0)throw new BusinessException("未知文件");
		OutputFormat format = OutputFormat.createPrettyPrint();//指定XML的输出样式
		format.setEncoding("UTF-8");
		File file = new File(systemUrl);
		Element root;
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			document=DocumentHelper.createDocument();
			root=document.addElement("NAVIGATION");
		}else{
			SAXReader sr=new SAXReader();
			document = (Document)sr.read(file);
			root=document.getRootElement();
		}
	
		
		
		for (FileEntity fileEntity : fel) {
			String src=fileEntity.getHttpUrl()+"/"+fileEntity.getName();
			String name=fileEntity.getName();
			String initName=fileEntity.getInitName();
			String href=fe.getHref();
			if(StringUtil.isEmpty(src))throw new BusinessException("未知图片路径");
			if(StringUtil.isEmpty(name))throw new BusinessException("未知图片名称");
			
			Element image=root.addElement("IMAGE");
			image.addAttribute("id",UUID.randomUUID().toString());
			image.addAttribute("src", src);
			image.addAttribute("name", name);
			image.addAttribute("initName", initName);
			image.addAttribute("href", href);
			image.addAttribute("localFile", fileEntity.getSystemUrl());
			image.addAttribute("CreateDataTime", UtilFactory.getSysDate().DateToString(fileEntity.getDateTime()));
		}
		
		XMLWriter xmlWriter = new XMLWriter(
				new FileOutputStream(systemUrl), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
	public List<FileEntity> writeXml(final String systemUrl,HttpServletRequest request) throws BusinessException, IOException, DocumentException{
		List<FileEntity> fel=Lists.newArrayList();
		File file = new File(systemUrl);
		if (file.exists()) {
			SAXReader sr=new SAXReader();
			Document document = (Document)sr.read(file);
			Element el_root = document.getRootElement();// 向外取数据，获取xml的根节点。
			Iterator it = el_root.elementIterator();// 从根节点下依次遍历，获取根节点下所有子节点
			while (it.hasNext()) {// 遍历子节点
				FileEntity fe=new FileEntity();
				Element el_row = (Element) it.next();
				fe.setId(el_row.attributeValue("id"));
				fe.setHttpUrl(el_row.attributeValue("src"));
				fe.setName(el_row.attributeValue("name"));
				fe.setHref(el_row.attributeValue("href"));
				fe.setSystemUrl(el_row.attributeValue("localFile"));
				fe.setDateTime(UtilFactory.getSysDate().strToDate(el_row.attributeValue("CreateDataTime")));
				fel.add(fe);
			}
		}
		return fel;
	}
	public void deleteXmlNode(final String systemUrl,String id) throws BusinessException{
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();//指定XML的输出样式
			format.setEncoding("UTF-8");
			SAXReader sr=new SAXReader();
			File file=new File(systemUrl);
			if(!file.exists()) {logger.info("初始化advertisement.xml");return;}
			document=sr.read(file);
			if(StringUtil.isEmpty(id))
				throw new BusinessException("无效参数");
			String nodeElement="//IMAGE[@id='"+id+"']";
			Node node=document.selectSingleNode(nodeElement);
			if(!node.getParent().remove(node))
			throw new BusinessException("移除失败");
//			List list=document.selectNodes("/NAVIGATION");
//			Iterator it=list.iterator();
//			while(it.hasNext()){
//				Element element=(Element)it.next();
//				Iterator iterator=element.elementIterator("IMAGE");
//				while (iterator.hasNext()) {
//					Element elementC=(Element)iterator.next();
//					Attribute oldId=elementC.attribute("id");
//					if(!StringUtil.isEmpty(oldId)&&oldId.equals(id)){
//						document.remove(elementC);
//					}
//					
//				}
//			}
			XMLWriter xmlWriter = new XMLWriter(
						new FileOutputStream(systemUrl), format);
				xmlWriter.write(document);
				xmlWriter.close();
		} catch (DocumentException e) {
			throw new BusinessException("读取或创建XML失败");
		} catch (IOException e) {
			throw new BusinessException("保存XML失败");
		}finally{
			
		}
		
	}

}
