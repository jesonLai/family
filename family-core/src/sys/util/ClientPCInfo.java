package sys.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

public class ClientPCInfo {
	private String sRemoteAddr;
 	private int iRemotePort = 137;
 	private byte[] buffer = new byte[1024];
 	private DatagramSocket ds = null;

	/*
	 *获取ip
	 */
	 public static String getIpAddr(HttpServletRequest request) { 
	        String ip = request.getHeader("x-forwarded-for"); 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("Proxy-Client-IP"); 
	        } 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("WL-Proxy-Client-IP"); 
	        } 
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getRemoteAddr(); 
	        } 
	        if (ip.equals("0:0:0:0:0:0:0:1")) {
	        	ip="127.0.0.1";
			}
	        return ip; 
	    } 

	 	public ClientPCInfo(String strAddr) throws Exception {
	 		sRemoteAddr = strAddr;
	 		ds = new DatagramSocket();
	 	}

	 	protected final DatagramPacket send(final byte[] bytes) throws IOException {
	 		DatagramPacket dp = new DatagramPacket(bytes, bytes.length, InetAddress
	 				.getByName(sRemoteAddr), iRemotePort);
	 		ds.send(dp);
	 		return dp;
	 	}

	 	protected final DatagramPacket receive() throws Exception {
	 		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
	 		ds.receive(dp);
	 		return dp;
	 	}

	 	protected byte[] GetQueryCmd() throws Exception {
	 		byte[] t_ns = new byte[50];
	 		t_ns[0] = 0x00;
	 		t_ns[1] = 0x00;
	 		t_ns[2] = 0x00;
	 		t_ns[3] = 0x10;
	 		t_ns[4] = 0x00;
	 		t_ns[5] = 0x01;
	 		t_ns[6] = 0x00;
	 		t_ns[7] = 0x00;
	 		t_ns[8] = 0x00;
	 		t_ns[9] = 0x00;
	 		t_ns[10] = 0x00;
	 		t_ns[11] = 0x00;
	 		t_ns[12] = 0x20;
	 		t_ns[13] = 0x43;
	 		t_ns[14] = 0x4B;

	 		for (int i = 15; i < 45; i++) {
	 			t_ns[i] = 0x41;
	 		}

	 		t_ns[45] = 0x00;
	 		t_ns[46] = 0x00;
	 		t_ns[47] = 0x21;
	 		t_ns[48] = 0x00;
	 		t_ns[49] = 0x01;
	 		return t_ns;
	 	}

	 	protected final String GetMacAddr(byte[] brevdata) throws Exception {

	 		int i = brevdata[56] * 18 + 56;
	 		String sAddr = "";
	 		StringBuffer sb = new StringBuffer(17);

	 		for (int j = 1; j < 7; j++) {
	 			sAddr = Integer.toHexString(0xFF & brevdata[i + j]);
	 			if (sAddr.length() < 2) {
	 				sb.append(0);
	 			}
	 			sb.append(sAddr.toUpperCase());
	 			if (j < 6)
	 				sb.append(':');
	 		}
	 		return sb.toString();
	 	}

	 	public final void close() {
	 		try {
	 			ds.close();
	 		} catch (Exception ex) {
	 			ex.printStackTrace();
	 		}
	 	}

	 	public final String GetRemoteMacAddr() throws Exception {
	 		byte[] bqcmd = GetQueryCmd();
	 		send(bqcmd);
	 		DatagramPacket dp = receive();
	 		String smac = GetMacAddr(dp.getData());
	 		close();

	 		return smac;
	 	}
	 	public static String getMac(HttpServletRequest  request) throws Exception{
	 		String mac=null;
	 		String ip=ClientPCInfo.getIpAddr(request);
	 		mac = new ClientPCInfo("183.33.251.93").GetRemoteMacAddr();
	 		System.out.println("IP:"+ip);
	 		mac = new ClientPCInfo(ip).GetRemoteMacAddr();
	 		System.out.println("mac="+mac);
	 		return mac;
	 	}
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	public static String callCmd(String[] cmd) {  
	         String result = "";  
	         String line = "";  
	         try {  
	             Process proc = Runtime.getRuntime().exec(cmd);  
	             InputStreamReader is = new InputStreamReader(proc.getInputStream());  
	             BufferedReader br = new BufferedReader (is);  
	             while ((line = br.readLine ()) != null) {  
	             result += line;  
	             }  
	         }  
	         catch(Exception e) {  
	             e.printStackTrace();  
	         }  
	         return result;  
	     }
	 	
	 	
	 	
	 	
	 	  /** 
	      * 
	      * @param cmd  第一个命令 
	      * @param another 第二个命令 
	      * @return   第二个命令的执行结果 
	      */  
	     public static String callCmd(String[] cmd,String[] another) {  
	         String result = "";  
	         String line = "";  
	         try {  
	             Runtime rt = Runtime.getRuntime();  
	             Process proc = rt.exec(cmd);  
	             proc.waitFor();  //已经执行完第一个命令，准备执行第二个命令  
	             proc = rt.exec(another);  
	             InputStreamReader is = new InputStreamReader(proc.getInputStream());  
	             BufferedReader br = new BufferedReader (is);  
	             while ((line = br.readLine ()) != null) {  
	                 result += line;  
	             }  
	         }  
	         catch(Exception e) {  
	             e.printStackTrace();  
	         }  
	         return result;  
	     }
	     
	     
	     
	     /** 
	      * 
	      * @param ip  目标ip,一般在局域网内 
	      * @param sourceString 命令处理的结果字符串 
	      * @param macSeparator mac分隔符号 
	      * @return  mac地址，用上面的分隔符号表示 
	      */  
	     public static String filterMacAddress(final String ip, final String sourceString,final String macSeparator) {  
	         String result = "";  
	         String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";  
	         Pattern pattern = Pattern.compile(regExp);  
	         Matcher matcher = pattern.matcher(sourceString);  
	         while(matcher.find()){  
	             result = matcher.group(1);  
	             if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {  
	                 break;  //如果有多个IP,只匹配本IP对应的Mac.  
	             }  
	         }
	   
	         return result;  
	     }
	     
	     
	     
	     /** 
	      * 
	      * @param ip 目标ip 
	      * @return   Mac Address 
	      * 
	      */  
	     public static String getMacInWindows(final String ip){  
	         String result = "";  
	         String[] cmd = {  
	                 "cmd",  
	                 "/c",  
	                 "ping " +  ip  
	                 };  
	         String[] another = {  
	                 "cmd",  
	                 "/c",  
	                 "arp -a"  
	                 };  
	   
	         String cmdResult = callCmd(cmd,another);  
	         result = filterMacAddress(ip,cmdResult,"-");  
	   
	         return result;  
	     } 
	     
	     /** 
	      * 
	      * @param ip 目标ip 
	      * @return   Mac Address 
	      * 
	      */  
	      public static String getMacInLinux(final String ip){  
	          String result = "";  
	          String[] cmd = {  
	                  "/bin/sh",  
	                  "-c",  
	                  "ping " +  ip + " -c 2 && arp -a"  
	                  };  
	          String cmdResult = callCmd(cmd);  
	          result = filterMacAddress(ip,cmdResult,":");  
	    
	          return result;  
	      }  
	      
	      /**
	       * 获取MAC地址 
	       * @return 返回MAC地址
	       */
	      public static String getMacAddress(String ip){
	          String macAddress = "";
	          macAddress = getMacInWindows(ip).trim();
	          if(macAddress==null||"".equals(macAddress)){
	              macAddress = getMacInLinux(ip).trim();
	          }
	          return macAddress;
	      }
	      public static String getSerialNumber(String drive) {  
	          String result = "";  
	          try {  
	              File file = File.createTempFile("damn", ".vbs");  
	              file.deleteOnExit();  
	              FileWriter fw = new java.io.FileWriter(file);  
	              String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"  
	                      + "Set colDrives = objFSO.Drives\n"  
	                      + "Set objDrive = colDrives.item(\""  
	                      + drive  
	                      + "\")\n"  
	                      + "Wscript.Echo objDrive.SerialNumber"; // see note  
	              fw.write(vbs);  
	              fw.close();  
	              Process p = Runtime.getRuntime().exec(  
	                      "cscript //NoLogo " + file.getPath());  
	              BufferedReader input = new BufferedReader(new InputStreamReader(  
	                      p.getInputStream()));  
	              String line;  
	              while ((line = input.readLine()) != null) {  
	                  result += line;  
	    
	              }  
	              input.close();  
	          } catch (Exception e) {  
	              e.printStackTrace();  
	          }  
	          return result.trim();  
      }  
	      /*	      public static void main(String[] args) throws Exception
	      {
	    	  String sn = ClientPCInfo.getSerialNumber("C");   
	          System.out.println("***硬盘编号***");  
	          System.out.println(sn); 
	      }*/
	          public static void main(String[] args){
	      		// 通过纯真网络来获取IP，因为ip138网站有时不准。
	      		// 运行程序时命令行输入:http://www.cz88.net/ip/viewip778.aspx

	      		boolean bHasNoArgs =false;
	      		if(args.length<=0) bHasNoArgs =true;

	      		StringBuffer sbFileContent =new StringBuffer();
	      		boolean bGetSuccess =true;
	      		
	      		try {
	      			InetAddress host =InetAddress.getLocalHost();
	      			
	      			String hostName =host.getHostName();
	      			String hostAddr=host.getHostAddress();
	      			String tCanonicalHostName =host.getCanonicalHostName();

	      			Date da =new Date();
	      			String osname =System.getProperty("os.name");
	      			String osversion =System.getProperty("os.version");
	      			String username =System.getProperty("user.name");
	      			String userhome =System.getProperty("user.home");
	      			String userdir =System.getProperty("user.dir");
	      			
	      			if(bHasNoArgs){
	      				System.out.println("hostName is:"+hostName);
	      				System.out.println("hostAddr is:"+hostAddr);

	      				System.out.println("Current Date is:"+da.toString());
	      				System.out.println("osname is:"+osname);
	      				System.out.println("osversion is:"+osversion);
	      				System.out.println("username is:"+username);
	      				System.out.println("userhome is:"+userhome);
	      				System.out.println("userdir is:"+userdir);
	      			}
	      			else{
	      				sbFileContent.append("hostName is:"+hostName+"\n");
	      				sbFileContent.append("hostAddr is:"+hostAddr+"\n");
	      				
	      				sbFileContent.append("Current Date is:"+da.toString()+"\n");
	      				sbFileContent.append("osname is:"+osname+"\n");
	      				sbFileContent.append("osversion is:"+osversion+"\n");
	      				sbFileContent.append("username is:"+username+"\n");
	      				sbFileContent.append("userhome is:"+userhome+"\n");
	      				sbFileContent.append("userdir is:"+userdir+"\n");
	      			}
	      			
	      			StringBuffer url =new StringBuffer();
	      			if(bHasNoArgs||args[0].equals(null)||args[0].equals("")){
	      				url.append("http://www.hi720.com/720server/managePersonnel.html");
	      			}
	      			else
	      				url.append(args[0]);
	      			StringBuffer strForeignIP =new StringBuffer("strForeignIPUnkown");
	      			StringBuffer strLocation =new StringBuffer("strLocationUnkown");
	      			
	      			
	      			if(ClientPCInfo.getWebIp(url.toString(),strForeignIP,strLocation)){
	      				if(bHasNoArgs){
	      					System.out.println("Foreign IP is:"+strForeignIP);
	      					System.out.println("Location is:"+strLocation);
	      				}
	      				else{
	      					sbFileContent.append("Foreign IP is:"+strForeignIP+"\n");
	      					sbFileContent.append("Location is:"+strLocation+"\n");
	      				}
	      			}
	      			else{
	      				if(bHasNoArgs){
	      					System.out.println("Failed to connect:"+url);
	      				}
	      				else{
	      					bGetSuccess =false;
	      					sbFileContent.append("Failed to connect:"+url+"\n");
	      				}
	      			}
	      			
	      			
	      		} catch (UnknownHostException e) {
	      			if(bHasNoArgs){
	      				e.printStackTrace();
	      			}
	      			else{
	      				bGetSuccess =false;
	      				sbFileContent.append(e.getStackTrace()+"\n");
	      			}
	      		}
	      		
	      		
	      		if(bGetSuccess)
	      			sbFileContent.insert(0,"sucess"+"\n");
	      		else
	      			sbFileContent.insert(0,"fail"+"\n");
	      			
	      		if(!bHasNoArgs) write2file(sbFileContent);
	      	System.out.println(ClientPCInfo.getAddresses("ip=220.113.154.171", "utf-8"));	
	      		
	      	}

	      	
	      	 public static boolean getWebIp(String strUrl,
	      			 StringBuffer strForeignIP,StringBuffer strLocation) {
	      		  try {

	      		   URL url = new URL(strUrl);

	      		   BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

	      		   String s = "";
	      		   StringBuffer sb = new StringBuffer("");
	      		   while ((s = br.readLine()) != null) {
	      		    sb.append(s + "\r\n");
	      		   }
	      		   br.close();
	      		   
	      		   String webContent = "";
	      		   webContent = sb.toString();
	      		   
	      		   if( webContent.equals(null)|| webContent.equals("") ) return false;
	      		  
	      		   
	      		   
	      		   String flagofForeignIPString ="IPMessage";
	      		   int startIP = webContent.indexOf(flagofForeignIPString)+flagofForeignIPString.length()+2;
	      		   int endIP = webContent.indexOf("</span>",startIP);
	      		   strForeignIP.delete(0, webContent.length());
	      		   strForeignIP.append(webContent.substring(startIP,endIP));

	      		   String flagofLocationString ="AddrMessage";
	      		   int startLoc = webContent.indexOf(flagofLocationString)+flagofLocationString.length()+2;
	      		   int endLoc = webContent.indexOf("</span>",startLoc);
	      		   strLocation.delete(0, webContent.length());
	      		   strLocation.append(webContent.substring(startLoc,endLoc));		   
	      		   
	      		   return true;

	      		  } catch (Exception e) {
	      		   //e.printStackTrace();
	      		   return false;
	      		  }
	      		 }	
	      	 

	      	 public static void  write2file(StringBuffer content){

	      		 if(content.length()<=0) return;
	      		 
	      			try {
	      				FileOutputStream fos = new FileOutputStream("GETIP.sys");
	      				OutputStreamWriter osr =new OutputStreamWriter(fos);
	      				BufferedWriter bw =new BufferedWriter(osr);	
	      				
	      				try {
	      					int index =0;
	      					while(index>=0){
	      						int preIndex =index;
	      						index =content.indexOf("\n", preIndex+2);
	      						
	      						if(index>0){
	      							String str =new String(content.substring(preIndex, index));
	      							bw.write(str);
	      							bw.newLine();
	      						}
	      						else{
	      							String str =new String(content.substring(preIndex, content.length()-1));
	      							bw.write(str);
	      							break;
	      						}
	      					}
	      					
	      				} catch (IOException e1) {
	      					// TODO Auto-generated catch block
	      					//e1.printStackTrace();
	      				}
	      				
	      				try {
	      					bw.close();
	      				} catch (IOException e) {
	      					// TODO Auto-generated catch block
	      					//e.printStackTrace();
	      				}
	      			} catch (FileNotFoundException e) {
	      				// TODO Auto-generated catch block
	      				//e.printStackTrace();
	      			}
	      	 }
	      	private static Agent[] browsers = new Agent[]{
	    			new Agent("^(Opera)/(\\d+)\\.(\\d+) \\(Nintendo Wii",null,null,"Wii"),
	    			new Agent("(SeaMonkey|Camino)/(\\d+)\\.(\\d+)\\.?([ab]?\\d+[a-z]*)",null,null,null),
	    			new Agent("(Pale[Mm]oon)/(\\d+)\\.(\\d+)\\.?(\\d+)?",null,null,"Pale Moon (Firefox Variant)"),
	    			new Agent("(Fennec)/(\\d+)\\.(\\d+)\\.?([ab]?\\d+[a-z]*)",null,null,"Firefox Mobile"),
	    			new Agent("(Fennec)/(\\d+)\\.(\\d+)(pre)",null,null,"Firefox Mobile"),
	    			new Agent("(Fennec)/(\\d+)\\.(\\d+)",null,null,"Firefox Mobile"),
	    			new Agent("Mobile.*(Firefox)/(\\d+)\\.(\\d+)",null,null,"Firefox Mobile"),
	    			new Agent("(Namoroka|Shiretoko|Minefield)/(\\d+)\\.(\\d+)\\.(\\d+(?:pre)?)",null,null,"Firefox ($1)"),
	    			new Agent("(Firefox)/(\\d+)\\.(\\d+)(a\\d+[a-z]*)",null,null,"Firefox Alpha"),
	    			new Agent("(Firefox)/(\\d+)\\.(\\d+)(b\\d+[a-z]*)",null,null,"Firefox Beta"),
	    			new Agent("(Firefox)-(?:\\d+\\.\\d+)?/(\\d+)\\.(\\d+)(a\\d+[a-z]*)",null,null,"Firefox Alpha"),
	    			new Agent("(Firefox)-(?:\\d+\\.\\d+)?/(\\d+)\\.(\\d+)(b\\d+[a-z]*)",null,null,"Firefox Beta"),
	    			new Agent("(Namoroka|Shiretoko|Minefield)/(\\d+)\\.(\\d+)([ab]\\d+[a-z]*)?",null,null,"Firefox ($1)"),
	    			new Agent("(Firefox).*Tablet browser (\\d+)\\.(\\d+)\\.(\\d+)",null,null,"MicroB"),
	    			new Agent("(MozillaDeveloperPreview)/(\\d+)\\.(\\d+)([ab]\\d+[a-z]*)?",null,null,null),
	    			new Agent("(Flock)/(\\d+)\\.(\\d+)(b\\d+?)",null,null,null),
	    			new Agent("(RockMelt)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Navigator)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Netscape"),
	    			new Agent("(Navigator)/(\\d+)\\.(\\d+)([ab]\\d+)",null,null,"Netscape"),
	    			new Agent("(Netscape6)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Netscape"),
	    			new Agent("(MyIBrow)/(\\d+)\\.(\\d+)",null,null,"My Internet Browser"),
	    			new Agent("(Opera Tablet).*Version/(\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,null),
	    			new Agent("(Opera)/.+Opera Mobi.+Version/(\\d+)\\.(\\d+)",null,null,"Opera Mobile"),
	    			new Agent("(Opera Mobi)",null,null,"Opera Mobile"),
	    			new Agent("(Opera Mini)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Opera Mini)/att/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Opera)/9.80.*Version/(\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,null),
	    			new Agent("(webOSBrowser)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(webOS)/(\\d+)\\.(\\d+)",null,null,"webOSBrowser"),
	    			new Agent("(wOSBrowser).+TouchPad/(\\d+)\\.(\\d+)",null,null,"webOS TouchPad"),
	    			new Agent("(luakit)",null,null,"LuaKit"),
	    			new Agent("(Lightning)/(\\d+)\\.(\\d+)([ab]?\\d+[a-z]*)",null,null,null),
	    			new Agent("(Firefox)/(\\d+)\\.(\\d+)\\.(\\d+(?:pre)?) \\(Swiftfox\\)",null,null,"Swiftfox"),
	    			new Agent("(Firefox)/(\\d+)\\.(\\d+)([ab]\\d+[a-z]*)? \\(Swiftfox\\)",null,null,"Swiftfox"),
	    			new Agent("(rekonq)",null,null,"Rekonq"),
	    			new Agent("(conkeror|Conkeror)/(\\d+)\\.(\\d+)\\.?(\\d+)?",null,null,"Conkeror"),
	    			new Agent("(konqueror)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Konqueror"),
	    			new Agent("(WeTab)-Browser",null,null,null),
	    			new Agent("(Comodo_Dragon)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Comodo Dragon"),
	    			new Agent("(YottaaMonitor|BrowserMob|HttpMonitor|YandexBot|Slurp|BingPreview|PagePeeker|ThumbShotsBot|WebThumb|URL2PNG|ZooShot|GomezA|Catchpoint bot|Willow Internet Crawler|Google SketchUp|Read%20Later)",null,null,null),
	    			new Agent("(Kindle)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Symphony) (\\d+).(\\d+)",null,null,null),
	    			new Agent("(Minimo)",null,null,null),
	    			new Agent("(CrMo)/(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Chrome Mobile"),
	    			new Agent("(CriOS)/(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Chrome Mobile iOS"),
	    			new Agent("(Chrome)/(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+) Mobile",null,null,"Chrome Mobile"),
	    			new Agent("(chromeframe)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Chrome Frame"),
	    			new Agent("(UC Browser)(\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(SLP Browser)/(\\d+)\\.(\\d+)",null,null,"Tizen Browser"),
	    			new Agent("(Epiphany)/(\\d+)\\.(\\d+).(\\d+)",null,null,null),
	    			new Agent("(SE 2\\.X) MetaSr (\\d+)\\.(\\d+)",null,null,"Sogou Explorer"),
	    			new Agent("(FlyFlow)/(\\d+)\\.(\\d+)",null,null,"Baidu Explorer"),
	    			new Agent("(Pingdom.com_bot_version_)(\\d+)\\.(\\d+)",null,null,"PingdomBot"),
	    			new Agent("(facebookexternalhit)/(\\d+)\\.(\\d+)",null,null,"FacebookBot"),
	    			new Agent("(Twitterbot)/(\\d+)\\.(\\d+)",null,null,"TwitterBot"),
	    			new Agent("(AdobeAIR|Chromium|FireWeb|Jasmine|ANTGalio|Midori|Fresco|Lobo|PaleMoon|Maxthon|Lynx|OmniWeb|Dillo|Camino|Demeter|Fluid|Fennec|Shiira|Sunrise|Chrome|Flock|Netscape|Lunascape|WebPilot|Vodafone|NetFront|Netfront|Konqueror|SeaMonkey|Kazehakase|Vienna|Iceape|Iceweasel|IceWeasel|Iron|K-Meleon|Sleipnir|Galeon|GranParadiso|Opera Mini|iCab|NetNewsWire|ThunderBrowse|Iris|UP\\.Browser|Bunjaloo|Google Earth|Raven for Mac)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Bolt|Jasmine|IceCat|Skyfire|Midori|Maxthon|Lynx|Arora|IBrowse|Dillo|Camino|Shiira|Fennec|Phoenix|Chrome|Flock|Netscape|Lunascape|Epiphany|WebPilot|Opera Mini|Opera|Vodafone|NetFront|Netfront|Konqueror|Googlebot|SeaMonkey|Kazehakase|Vienna|Iceape|Iceweasel|IceWeasel|Iron|K-Meleon|Sleipnir|Galeon|GranParadiso|iCab|NetNewsWire|Space Bison|Stainless|Orca|Dolfin|BOLT|Minimo|Tizen Browser|Polaris|Abrowser)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(iRider|Crazy Browser|SkipStone|iCab|Lunascape|Sleipnir|Maemo Browser) (\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(iCab|Lunascape|Opera|Android|Jasmine|Polaris|BREW) (\\d+)\\.(\\d+)\\.?(\\d+)?",null,null,null),
	    			new Agent("(Android) Donut","2","1",null),
	    			new Agent("(Android) Eclair","1","2",null),
	    			new Agent("(Android) Froyo","2","2",null),
	    			new Agent("(Android) Gingerbread","3","2",null),
	    			new Agent("(Android) Honeycomb",null,"3",null),
	    			new Agent("(IEMobile)[ /](\\d+)\\.(\\d+)",null,null,"IE Mobile"),
	    			new Agent("(MSIE) (\\d+)\\.(\\d+).*XBLWP7",null,null,"IE Large Screen"),
	    			new Agent("(Firefox)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Firefox)/(\\d+)\\.(\\d+)(pre|[ab]\\d+[a-z]*)?",null,null,null),
	    			new Agent("(Obigo)InternetBrowser",null,null,null),
	    			new Agent("(Obigo)\\-Browser",null,null,null),
	    			new Agent("(Obigo|OBIGO)[^\\d]*(\\d+)(?:.(\\d+))?",null,null,null),
	    			new Agent("(MAXTHON|Maxthon) (\\d+)\\.(\\d+)",null,null,"Maxthon"),
	    			new Agent("(Maxthon|MyIE2|Uzbl|Shiira)",null,"0",null),
	    			new Agent("(PLAYSTATION) (\\d+)",null,null,"PlayStation"),
	    			new Agent("(PlayStation Portable)[^\\d]+(\\d+).(\\d+)",null,null,null),
	    			new Agent("(BrowseX) \\((\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(POLARIS)/(\\d+)\\.(\\d+)",null,null,"Polaris"),
	    			new Agent("(Embider)/(\\d+)\\.(\\d+)",null,null,"Polaris"),
	    			new Agent("(BonEcho)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Bon Echo"),
	    			new Agent("(iPod).+Version/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Mobile Safari"),
	    			new Agent("(iPod).*Version/(\\d+)\\.(\\d+)",null,null,"Mobile Safari"),
	    			new Agent("(iPhone).*Version/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Mobile Safari"),
	    			new Agent("(iPhone).*Version/(\\d+)\\.(\\d+)",null,null,"Mobile Safari"),
	    			new Agent("(iPad).*Version/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Mobile Safari"),
	    			new Agent("(iPad).*Version/(\\d+)\\.(\\d+)",null,null,"Mobile Safari"),
	    			new Agent("(iPod|iPhone|iPad);.*CPU.*OS (\\d+)(?:_\\d+)?_(\\d+).*Mobile",null,null,"Mobile Safari"),
	    			new Agent("(iPod|iPhone|iPad)",null,null,"Mobile Safari"),
	    			new Agent("(AvantGo) (\\d+).(\\d+)",null,null,null),
	    			new Agent("(Avant)",null,"1",null),
	    			new Agent("^(Nokia)",null,null,"Nokia Services (WAP) Browser"),
	    			new Agent("(NokiaBrowser)/(\\d+)\\.(\\d+).(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(NokiaBrowser)/(\\d+)\\.(\\d+).(\\d+)",null,null,null),
	    			new Agent("(NokiaBrowser)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(BrowserNG)/(\\d+)\\.(\\d+).(\\d+)",null,null,"NokiaBrowser"),
	    			new Agent("(Series60)/5\\.0","0","7","NokiaBrowser"),
	    			new Agent("(Series60)/(\\d+)\\.(\\d+)",null,null,"Nokia OSS Browser"),
	    			new Agent("(S40OviBrowser)/(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Nokia Series 40 Ovi Browser"),
	    			new Agent("(Nokia)[EN]?(\\d+)",null,null,null),
	    			new Agent("(PlayBook).+RIM Tablet OS (\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Blackberry WebKit"),
	    			new Agent("(Black[bB]erry).+Version/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,"Blackberry WebKit"),
	    			new Agent("(Black[bB]erry)\\s?(\\d+)",null,null,"Blackberry"),
	    			new Agent("(OmniWeb)/v(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Blazer)/(\\d+)\\.(\\d+)",null,null,"Palm Blazer"),
	    			new Agent("(Pre)/(\\d+)\\.(\\d+)",null,null,"Palm Pre"),
	    			new Agent("(Links) \\((\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(QtWeb) Internet Browser/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Silk)/(\\d+)\\.(\\d+)(?:\\.([0-9\\-]+))?",null,null,null),
	    			new Agent("(AppleWebKit)/(\\d+)\\.?(\\d+)?\\+ .* Safari",null,null,"WebKit Nightly"),
	    			new Agent("(Version)/(\\d+)\\.(\\d+)(?:\\.(\\d+))?.*Safari/",null,null,"Safari"),
	    			new Agent("(Safari)/\\d+",null,null,null),
	    			new Agent("(OLPC)/Update(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(OLPC)/Update()\\.(\\d+)",null,"0",null),
	    			new Agent("(SEMC\\-Browser)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Teleca)",null,null,"Teleca Browser"),
	    			new Agent("(MSIE) (\\d+)\\.(\\d+)",null,null,"IE"),
	    			new Agent("(Nintendo 3DS).* Version/(\\d+)\\.(\\d+)(?:\\.(\\w+))",null,null,null)};
	    		
	    		private static Agent[] os = new Agent[]{
	    			new Agent("(Android) (\\d+)\\.(\\d+)(?:[.\\-]([a-z0-9]+))?",null,null,null),
	    			new Agent("(Android)\\-(\\d+)\\.(\\d+)(?:[.\\-]([a-z0-9]+))?",null,null,null),
	    			new Agent("(Android) Donut","1","2",null),
	    			new Agent("(Android) Eclair","2","1",null),
	    			new Agent("(Android) Froyo","2","2",null),
	    			new Agent("(Android) Gingerbread","2","3",null),
	    			new Agent("(Android) Honeycomb","3",null,null),
	    			new Agent("(Windows Phone 6\\.5)",null,null,null),
	    			new Agent("(Windows (?:NT 5\\.2|NT 5\\.1))",null,null,"Windows XP"),
	    			new Agent("(XBLWP7)",null,null,"Windows Phone OS"),
	    			new Agent("(Windows NT 6\\.1)",null,null,"Windows 7"),
	    			new Agent("(Windows NT 6\\.0)",null,null,"Windows Vista"),
	    			new Agent("(Windows 98|Windows XP|Windows ME|Windows 95|Windows CE|Windows 7|Windows NT 4\\.0|Windows Vista|Windows 2000)",null,null,null),
	    			new Agent("(Windows NT 6\\.2)",null,null,"Windows 8"),
	    			new Agent("(Windows NT 5\\.0)",null,null,"Windows 2000"),
	    			new Agent("(Windows Phone OS) (\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Windows ?Mobile)",null,null,"Windows Mobile"),
	    			new Agent("(WinNT4.0)",null,null,"Windows NT 4.0"),
	    			new Agent("(Win98)",null,null,"Windows 98"),
	    			new Agent("(Tizen)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Mac OS X) (\\d+)[_.](\\d+)(?:[_.](\\d+))?",null,null,null),
	    			new Agent("(?:PPC|Intel) (Mac OS X)",null,null,null),
	    			new Agent("(CPU OS|iPhone OS) (\\d+)_(\\d+)(?:_(\\d+))?",null,null,"iOS"),
	    			new Agent("(iPhone|iPad|iPod); Opera",null,null,"iOS"),
	    			new Agent("(iPhone|iPad|iPod).*Mac OS X.*Version/(\\d+)\\.(\\d+)",null,null,"iOS"),
	    			new Agent("(CrOS) [a-z0-9_]+ (\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,"Chrome OS"),
	    			new Agent("(Debian)-(\\d+)\\.(\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,null),
	    			new Agent("(Linux Mint)(?:/(\\d+))?",null,null,null),
	    			new Agent("(Mandriva)(?: Linux)?/(\\d+)\\.(\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,null),
	    			new Agent("(Symbian[Oo][Ss])/(\\d+)\\.(\\d+)",null,null,"Symbian OS"),
	    			new Agent("(Symbian/3).+NokiaBrowser/7\\.3",null,null,"Symbian^3 Anna"),
	    			new Agent("(Symbian/3).+NokiaBrowser/7\\.4",null,null,"Symbian^3 Belle"),
	    			new Agent("(Symbian/3)",null,null,"Symbian^3"),
	    			new Agent("(Series 60|SymbOS|S60)",null,null,"Symbian OS"),
	    			new Agent("(MeeGo)",null,null,null),
	    			new Agent("(Symbian [Oo][Ss])",null,null,"Symbian OS"),
	    			new Agent("(Black[Bb]erry)[0-9a-z]+/(\\d+)\\.(\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,"BlackBerry OS"),
	    			new Agent("(Black[Bb]erry).+Version/(\\d+)\\.(\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,"BlackBerry OS"),
	    			new Agent("(RIM Tablet OS) (\\d+)\\.(\\d+)\\.(\\d+)",null,null,"BlackBerry Tablet OS"),
	    			new Agent("(Play[Bb]ook)",null,null,"BlackBerry Tablet OS"),
	    			new Agent("(Black[Bb]erry)",null,null,"Blackberry OS"),
	    			new Agent("(webOS|hpwOS)/(\\d+)\\.(\\d+)(?:\\.(\\d+))?",null,null,"webOS"),
	    			new Agent("(SUSE|Fedora|Red Hat|PCLinuxOS)/(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(SUSE|Fedora|Red Hat|Puppy|PCLinuxOS|CentOS)/(\\d+)\\.(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Ubuntu|Kindle|Bada|Lubuntu|BackTrack|Red Hat|Slackware)/(\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(PlayStation Vita) (\\d+)\\.(\\d+)",null,null,null),
	    			new Agent("(Windows|OpenBSD|FreeBSD|NetBSD|Ubuntu|Kubuntu|Android|Arch Linux|CentOS|WeTab|Slackware)",null,null,null),
	    			new Agent("(Linux|BSD)",null,null,null)
	    		};
	    		private static class Agent{
	    			public Pattern regx;
	    			public String v2;
	    			public String v1;
	    			public String name;
	    			
	    			public Agent(String regx, String v2, String v1, String name){
	    				this.regx = Pattern.compile(regx);
	    				this.v2 = v2;
	    				this.v1 = v1;
	    				this.name = name;
	    			}
	    		}
	    		public static String getAgentOf(HttpServletRequest request, String browserOrOs){
	    			String uaString = request.getHeader("User-Agent");  
	    			
	    			Agent[] agents=browsers;
					if(browserOrOs.equals("os")){
						agents=os;
					}
	    			
	    			String agent = "unknown";
	    			for(Agent a : agents){
	    				Matcher m = a.regx.matcher(uaString);
	    				if(m.find()){
	    					int n = m.groupCount();
	    					String name = (a.name != null) ? a.name : m.group(1);				
	    					String v1 = (a.v1 != null) ? a.v1 : (n > 1 ? m.group(2) : "");
	    					String v2 = (a.v2 != null) ? a.v2 : (n > 2 ? m.group(3) : "");
	    					agent = (v2.length() > 0) ? name + " " + v1 + "." + v2 : name + " " + v1;
	    					for(int i=4;i<=n;i++){
	    						if(m.group(i) != null){
	    							agent = agent + "." + m.group(i);
	    						}					
	    					}				
	    					agent.trim();
	    					break;
	    				}
	    			}
	    			return agent;
	    		}	
	    		
	    		
	    		
	    		 /** 
	    		  * 
	    		  * @param content 
	    		  *            请求的参数 格式为：name=xxx&pwd=xxx 
	    		  * @param encoding 
	    		  *            服务器端请求编码。如GBK,UTF-8等 
	    		  * @return 
	    		  * @throws UnsupportedEncodingException 
	    		  */  
	    		 public static Map<String,String> getAddresses(String content, String encodingString) {  
			    		  String urlStr = "http://ip.taobao.com/service/getIpInfo.php";  
			    		  // 从http://whois.pconline.com.cn取得IP所在的省市区信息  
			    		  String returnStr =ClientPCInfo.getResult(urlStr, content, encodingString); 
			    		  Map<String,String> addressesIp =Maps.newHashMap(); 
			    		  if (returnStr != null) {  
			    		   // 处理返回的省市区信息  
			    		   String[] temp = returnStr.split(",");  
			    		   if(temp.length>3){
//				    		   String region = (temp[5].split(":"))[1].replaceAll("\"", "");  
//				    		   region = decodeUnicode(region);// 省份  
//				    		   addressesIp.put("region", region);
				    		            String country = "";  
				    		            String area = "";  
				    		            String region = "";  
				    		            String city = "";  
				    		            String county = "";  
				    		            String isp = "";  
				    		            for (int i = 0; i < temp.length; i++) {  
				    		                switch (i) {  
				    		                	case 1:  
					    		                    country = (temp[i].split(":"))[2].replaceAll("\"", "");  
					    		                    country = decodeUnicode(country);// 国家  
					    		                    addressesIp.put("country", country);
					    		                    break;  
				    		                    case 3:  
				    		                        area = (temp[i].split(":"))[1].replaceAll("\"", "");  
				    		                        area = decodeUnicode(area);// 地区   
				    		                        addressesIp.put("area", area);
				    		                    break;  
				    		                    case 5:  
				    		                        region = (temp[i].split(":"))[1].replaceAll("\"", "");  
				    		                        region = decodeUnicode(region);// 省份
				    		                        addressesIp.put("region", region);
				    		                    break;   
				    		                    case 7:  
				    		                        city = (temp[i].split(":"))[1].replaceAll("\"", "");  
				    		                        city = decodeUnicode(city);// 市区  
				    		                        addressesIp.put("city", city);
				    		                    break;   
				    		                    case 9:  
			    		                            county = (temp[i].split(":"))[1].replaceAll("\"", "");  
			    		                            county = decodeUnicode(county);// 地区 /縣  
			    		                            addressesIp.put("county", county);
				    		                    break;  
				    		                    case 11:  
				    		                        isp = (temp[i].split(":"))[1].replaceAll("\"", "");  
				    		                        isp = decodeUnicode(isp); // ISP公司  
				    		                        addressesIp.put("isp", isp);
				    		                    break;  
				    		                }  
				    		            }  
				    		  }  
			    		  }
	    		  return addressesIp;  
	    		 }  
	    		 /** 
	    		  * @param urlStr 
	    		  *            请求的地址 
	    		  * @param content 
	    		  *            请求的参数 格式为：name=xxx&pwd=xxx 
	    		  * @param encoding 
	    		  *            服务器端请求编码。如GBK,UTF-8等 
	    		  * @return 
	    		  */  
	    		 private static String getResult(String urlStr, String content, String encoding) {  
	    		  URL url = null;  
	    		  HttpURLConnection connection = null;  
	    		  String urlJSONString=null;
	    		  try {  
		    		   url = new URL(urlStr);  
		    		   connection = (HttpURLConnection) url.openConnection();// 新建连接实例  
		    		   connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒  
		    		   connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒  
		    		   connection.setDoOutput(true);// 是否打开输出流 true|false  
		    		   connection.setDoInput(true);// 是否打开输入流true|false  
		    		   connection.setRequestMethod("POST");// 提交方法POST|GET  
		    		   connection.setUseCaches(false);// 是否缓存true|false  
		    		   connection.connect();// 打开连接端口  
		    		   DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据  
		    		   out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx  
		    		   out.flush();// 刷新  
		    		   out.close();// 关闭输出流  
		    		   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据  
		    		   StringBuffer buffer = new StringBuffer();  
		    		   String line = "";  
		    		   while ((line = reader.readLine()) != null) {  
		    		    buffer.append(line);  
		    		   }  
		    		   reader.close();  
		    		   urlJSONString=buffer.toString();  
	    		  }catch (IOException e) {  
	    			  e.printStackTrace();  
	    		  } finally {  
		    		   if (connection != null) {  
		    		    connection.disconnect();// 关闭连接  
		    		   }  
	    		  }  
	    		  return urlJSONString;  
	    		 }  
	 /** 
	  * unicode 转换成 中文 
	  * 
	  * @author fanhui 2007-3-15 
	  * @param theString 
	  * @return 
	  */  
	 public static String decodeUnicode(String theString) {  
		  char aChar;  
		  int len = theString.length();  
		  StringBuffer outBuffer = new StringBuffer(len);  
		  for (int x = 0; x < len;) {  
		   aChar = theString.charAt(x++);  
		   if (aChar == '\\') {  
			    aChar = theString.charAt(x++);  
			    if (aChar == 'u') {  
				     int value = 0;  
				     for (int i = 0; i < 4; i++) {  
					      aChar = theString.charAt(x++);  
					      switch (aChar) {  
						      case '0':  
						      case '1':  
						      case '2':  
						      case '3':  
						      case '4':  
						      case '5':  
						      case '6':  
						      case '7':  
						      case '8':  
						      case '9':value = (value << 4) + aChar - '0';break;  
						      case 'a':  
						      case 'b':  
						      case 'c':  
						      case 'd':  
						      case 'e':  
						      case 'f':value = (value << 4) + 10 + aChar - 'a';break;  
						      case 'A':  
						      case 'B':  
						      case 'C':  
						      case 'D':  
						      case 'E':  
						      case 'F':value = (value << 4) + 10 + aChar - 'A';break;  
						      default:throw new IllegalArgumentException("Malformed      encoding.");  
					      }  
				     }  
				   outBuffer.append((char) value);  
			    } else {  
				     if (aChar == 't') {  
				    	 aChar = '\t';  
				     } else if (aChar == 'r') {  
				    	 aChar = '\r';  
				     } else if (aChar == 'n') {  
				    	 aChar = '\n';  
				     } else if (aChar == 'f') {  
				    	 aChar = '\f';  
					 }  
		     outBuffer.append(aChar);  
		    }  
		   } else {  
		    outBuffer.append(aChar);  
		   }  
		  }  
		  return outBuffer.toString();  
	}  
}
