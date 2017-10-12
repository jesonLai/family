package sys.model.controller;
/**
 * 发件人
 * @author lxr
 *
 */
public class SenderEmail {
	private String  host;
	private String protocol;
	private String port;
	private String from;
	private String pwd;
	private String xmlName;
	private String xmlPath;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getXmlPath() {
		return xmlPath;
	}
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public String getXmlName() {
		return xmlName;
	}
	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
	
}
