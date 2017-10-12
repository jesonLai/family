package sys.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import sys.exception.handing.BusinessException;
import sys.model.controller.SenderEmail;

public class EmailSendHint {
	private static SenderEmail senderEmail = new SenderEmail();
	private final String xmlName="email.xml";
	public EmailSendHint(HttpServletRequest request){
		
		ServletContext sc = request.getSession().getServletContext();
		senderEmail.setXmlName(xmlName);
		senderEmail.setXmlPath(sc.getRealPath("/WEB-INF/"));
		XMLOperation.readXML(senderEmail);
	}
	 /**
     * 获取Session
     * @return
	 * @throws BusinessException 
     */
    private static Session getSession() throws BusinessException {
    	if(StringUtil.isEmpty(senderEmail)||
    		StringUtil.isEmpty(senderEmail.getHost())||
    		StringUtil.isEmpty(senderEmail.getProtocol())||
    		StringUtil.isEmpty(senderEmail.getPort())){
    		throw new BusinessException("请在首页中设置发件人邮箱");
    	}
        Properties props = new Properties();
        props.put("mail.smtp.host", StringUtil.isEmpty(senderEmail.getHost())?"smtp.163.com":senderEmail.getHost());//设置服务器地址
        props.put("mail.store.protocol" , StringUtil.isEmpty(senderEmail.getProtocol())?"smtp":senderEmail.getProtocol());//设置协议
        props.put("mail.smtp.port", StringUtil.isEmpty(senderEmail.getPort())?"25":senderEmail.getPort());//设置端口
        props.put("mail.smtp.auth" , "true");

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail.getFrom(), senderEmail.getPwd());
            }

        };
        Session session = Session.getDefaultInstance(props , authenticator);

        return session;
    }

    public  void send(String title,String toEmail , String content) throws AddressException, MessagingException, BusinessException {
        Session session = getSession();
        // Instantiate a message
        Message msg = new MimeMessage(session);

        //Set message attributes
        msg.setFrom(new InternetAddress(senderEmail.getFrom()));
        InternetAddress[] address = {new InternetAddress(toEmail)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(title);
        msg.setSentDate(new Date());
        msg.setContent(content , "text/html;charset=utf-8");
        //Send the message
        Transport.send(msg);
    }
}
