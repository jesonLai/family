package sys.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
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


public class SendEmail {
	public static final String HOST = "smtp.hi720.com";
    public static final String PROTOCOL = "smtp";   
    public static final int PORT = 25;
    public static final String FROM = "720server@hi720.com";//发件人的email
    public static final String PWD = "Cadelang1818918";//发件人密码

    /**
     * 获取Session
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.store.protocol" , PROTOCOL);//设置协议
        props.put("mail.smtp.port", PORT);//设置端口
        props.put("mail.smtp.auth" , "true");

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }

        };
        Session session = Session.getDefaultInstance(props , authenticator);

        return session;
    }

    public static void send(String title,String toEmail , String content) throws AddressException, MessagingException {
        Session session = getSession();
        // Instantiate a message
        Message msg = new MimeMessage(session);

        //Set message attributes
        msg.setFrom(new InternetAddress(FROM));
        InternetAddress[] address = {new InternetAddress(toEmail)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(title);
        msg.setSentDate(new Date());
        msg.setContent(content , "text/html;charset=utf-8");
        //Send the message
        Transport.send(msg);
    }
    
    public static void main(String[] args) throws IOException {
    	 Socket socket = new Socket();
    	try{
    	
    	 socket.connect(new InetSocketAddress("smtp.hi720.com", 25));
    	 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
    	 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    	  // 超时时间(毫秒)
    	  long timeout = 6000;
    	 // 睡眠时间片段(50毫秒)
    	 int sleepSect = 50;
    	  // 连接(服务器是否就绪)
    	 System.out.println(getResponseCode(timeout, sleepSect, bufferedReader));
    	 if(getResponseCode(timeout, sleepSect, bufferedReader) != 220) {
    	 }
//    	 // 握手
//         bufferedWriter.write("HELO " + "www.baidu.com" + "\r\n");
//         bufferedWriter.flush();
//         if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
//             System.out.println("失败");
//         }
//         // 身份
//         bufferedWriter.write("MAIL FROM: <check@" + "www.baidu.com" + ">\r\n");
//         bufferedWriter.flush();
//         if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
//        	 System.out.println("失败");
//         }
         // 验证
         bufferedWriter.write("RCPT TO: <" + "720server@hi720.com" + ">\r\n");
         bufferedWriter.flush();
         System.out.println(getResponseCode(timeout, sleepSect, bufferedReader));
         if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
        	 System.out.println("失败");
         }
         // 断开
         bufferedWriter.write("QUIT\r\n");
         bufferedWriter.flush();
     } catch (NumberFormatException e) {
     }catch (IOException e) {
     } catch (InterruptedException e) {
     } finally {
         try {
             socket.close();
         } catch (IOException e) {
         }
     }
	}
    private static int getResponseCode(long timeout, int sleepSect, BufferedReader bufferedReader) throws InterruptedException, NumberFormatException, IOException {
        int code = 0;
        for(long i = sleepSect; i < timeout; i += sleepSect) {
            Thread.sleep(sleepSect);
            if(bufferedReader.ready()) {
                String outline = bufferedReader.readLine();
                // FIXME 读完……
                while(bufferedReader.ready())
                    /*System.out.println(*/bufferedReader.readLine()/*)*/;
                /*System.out.println(outline);*/
                code = Integer.parseInt(outline.substring(0, 3));
                break;
            }
        }
        return code;
    }
}
