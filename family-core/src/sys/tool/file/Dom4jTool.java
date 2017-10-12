package sys.tool.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import sys.tool.string.StringUtils;

public class Dom4jTool{

	public static Element getRootElement(Document doc){
		if (StringUtils.isNull(doc)){
			return null;
		}
		return doc.getRootElement();
	}

	public static String getElementValue(Element eleName, String defaultValue){
		if (StringUtils.isNull(eleName)){
			return defaultValue == null ? "" : defaultValue;
		}
		return eleName.getTextTrim();
	}

	public static String getElementValue(String eleName, Element parentElement){
		if (StringUtils.isNull(parentElement)){
			return null;
		}
		Element element = parentElement.element(eleName);
		if (StringUtils.isNotNull(element))
			return element.getTextTrim();
		try{
			throw new Exception("找不到节点" + eleName);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static String getElementValue(Element eleName){
		return getElementValue(eleName,null);
	}

	public static Document read(File file){
		return read(file,null);
	}

	public static Document findCDATA(Document body, String path){
		return stringToXml(getElementValue(path,body.getRootElement()));
	}

	public static Document read(File file, String charset){
		if (StringUtils.isNull(file)){
			return null;
		}
		SAXReader reader = new SAXReader();
		if (StringUtils.isNotNull(charset)){
			reader.setEncoding(charset);
		}
		Document document = null;
		try{
			document = reader.read(file);
		}catch (DocumentException e){
			e.printStackTrace();
		}
		return document;
	}

	public static Document read(URL url){
		return read(url,null);
	}

	public static Document read(URL url, String charset){
		if (StringUtils.isNull(url)){
			return null;
		}
		SAXReader reader = new SAXReader();
		if (StringUtils.isNotNull(charset)){
			reader.setEncoding(charset);
		}
		Document document = null;
		try{
			document = reader.read(url);
		}catch (DocumentException e){
			e.printStackTrace();
		}
		return document;
	}

	public static String xmltoString(Document doc){
		return xmltoString(doc,null);
	}

	public static String xmltoString(Document doc, String charset){
		if (StringUtils.isNull(doc)){
			return "";
		}
		if (StringUtils.isNull(charset)){
			return doc.asXML();
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		StringWriter strWriter = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(strWriter, format);
		try{
			xmlWriter.write(doc);
			xmlWriter.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		return strWriter.toString();
	}

	public static void xmltoFile(Document doc, File file, String charset) throws Exception{
		if (StringUtils.isNull(doc)){
			throw new NullPointerException("doc cant not null");
		}
		if (StringUtils.isNull(charset)){
			throw new NullPointerException("charset cant not null");
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		FileOutputStream os = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(os, charset);
		XMLWriter xmlWriter = new XMLWriter(osw, format);
		try{
			xmlWriter.write(doc);
			xmlWriter.close();
			if (osw != null)
				osw.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void xmltoFile(Document doc, String filePath, String charset) throws Exception{
		xmltoFile(doc,new File(filePath),charset);
	}

	public static void writDocumentToFile(Document doc, String filePath, String charset) throws Exception{
		xmltoFile(doc,new File(filePath),charset);
	}

	public static Document stringToXml(String text){
		try{
			return DocumentHelper.parseText(text);
		}catch (DocumentException e){
			e.printStackTrace();
		}
		return null;
	}

	public static Document createDocument(){
		return DocumentHelper.createDocument();
	}
}