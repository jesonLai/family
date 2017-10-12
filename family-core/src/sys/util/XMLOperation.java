package sys.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import sys.exception.handing.BusinessException;
import sys.model.controller.SenderEmail;

public class XMLOperation {
	private static final Logger logger = Logger.getLogger(XMLOperation.class.getName());

	public static void printXml(SenderEmail senderEmail) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("EMAIL");
		root.addElement("HOST").setText(senderEmail.getHost());
		root.addElement("PROTOCOL").setText(senderEmail.getProtocol());
		root.addElement("PORT").setText(senderEmail.getPort());
		root.addElement("FROM").setText(senderEmail.getFrom());
		root.addElement("PWD").setText(senderEmail.getPwd());

		String filePath = senderEmail.getXmlPath();
		String fileName = senderEmail.getXmlName();

		String xmlStr = "";
		xmlStr = document.asXML();
		try {
			Document dcmt = DocumentHelper.parseText(xmlStr);
			saveDocumentToFile(dcmt, filePath, fileName, true, "UTF-8");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void readXML(SenderEmail senderEmail) {
		SAXReader sr = new SAXReader();// 获取读取xml的对象。
		try {
			String pathname=senderEmail.getXmlPath()+File.separator+senderEmail.getXmlName();
			File file=new File(pathname);
			if(!file.exists()) {logger.info("邮箱文件没有创建");return;}
			Document doc = (Document) sr.read(pathname);// 得到xml所在位置。然后开始读取。并将数据放入doc中
			Element el_root = doc.getRootElement();// 向外取数据，获取xml的根节点。
			Iterator it = el_root.elementIterator();// 从根节点下依次遍历，获取根节点下所有子节点
			while (it.hasNext()) {// 遍历子节点
				Object o = it.next();
				
				Element el_row = (Element) o;
				
				String text = el_row.getText();
				String name=el_row.getName();
				if("HOST".equals(name))senderEmail.setHost(text);
				if("PROTOCOL".equals(name))senderEmail.setProtocol(text);
				if("PORT".equals(name))senderEmail.setPort(text);
				if("FROM".equals(name))senderEmail.setFrom(text);
				if("PWD".equals(name))senderEmail.setPwd(text);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void saveDocumentToFile(Document document, String xmlFilePath, String xmlFileName, boolean isTrimText,
			String xmlEncoding) {
		if (document == null || xmlFilePath == null || "".equals(xmlFileName)) {
			return;
		}

		File file = new File(xmlFilePath);
		// 判断路径是否存在，不存在创建
		if (!file.exists()) {
			file.mkdirs();
		}
		// 保存文件
		OutputFormat format = null;

		if (isTrimText) {
			format = OutputFormat.createPrettyPrint();
		} else {
			format = DomXmlOutputFormat.createPrettyPrint();// 保留xml属性值中的回车换行
		}
		if (xmlEncoding != null) {
			format.setEncoding(xmlEncoding);// GBK
		} else {
			format.setEncoding("UTF-8");// UTF-8
		}

		try {
			org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(
					new FileOutputStream(xmlFilePath +File.separator.concat(xmlFileName)), format);// FileOutputStream可以使UTF-8生效
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println("保存XML异常：" + e.getMessage());
			System.out.println("正在保存的文件名是：" + xmlFileName);
		}
		
	}
public static void main(String[] args) {
	SenderEmail senderEmail=new SenderEmail();
//	senderEmail.setFrom("924102134@163.com");
//	senderEmail.setPwd("213845498");
//	senderEmail.setHost("smtp.163.com");
//	senderEmail.setPort("25");
//	senderEmail.setProtocol("smtp");
//	senderEmail.setXmlPath("D:");
//	senderEmail.setXmlName("email.xml");
//	XMLOperation.printXml(senderEmail);
	senderEmail.setXmlPath("D:\\"+"email.xml");
	XMLOperation.readXML(senderEmail);
	System.out.println(senderEmail.getHost());
}
}
