package sys.util;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.mchange.lang.StringUtils;



/**
 *
 * HTTP 请求工具类
 */
public class HttpUtils extends PostMethod{
	// 获得Get请求对象request
	 public static HttpGet getHttpGet(String url){
	  HttpGet request = new HttpGet(url);
	   return request;
	 }
	 // 获得Post请求对象request
	 public static HttpPost getHttpPost(String url){
	   HttpPost request = new HttpPost(url);
	   return request;
	 }
	 // 根据请求获得响应对象response
	 public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
	  HttpResponse response = new DefaultHttpClient().execute(request);
	  return response;
	 }
	 // 根据请求获得响应对象response
	 public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
	  HttpResponse response = new DefaultHttpClient().execute(request);
	  return response;
	 }
	 
	 // 发送Post请求，获得响应查询结果
	 public static String queryStringForPost(String url){
	  // 根据url获得HttpPost对象
	  HttpPost request = HttpUtils.getHttpPost(url);
	 
	  String result = null;
	  try {
	   // 获得响应对象
	   HttpResponse response = HttpUtils.getHttpResponse(request);
	   // 判断是否请求成功
	   if(response.getStatusLine().getStatusCode()==200){
	    // 获得响应
	    result = EntityUtils.toString(response.getEntity(),"UTF-8");
	    return result;
	   }
	  } catch (ClientProtocolException e) {
	   e.printStackTrace();
	   result = "网络异常！";
	   return result;
	  } catch (IOException e) {
	   e.printStackTrace();
	   result = "网络异常！";
	   return result;
	  }
	        return null;
	    }
	 // 获得响应查询结果
	 public static String queryStringForPost(HttpPost request){
	  String result = null;
	  try {
	   // 获得响应对象
	   HttpResponse response = HttpUtils.getHttpResponse(request);
	   // 判断是否请求成功
	   if(response.getStatusLine().getStatusCode()==200){
	    // 获得响应
	    result = EntityUtils.toString(response.getEntity());
	    return result;
	   }
	  } catch (ClientProtocolException e) {
	   e.printStackTrace();
	   result = "网络异常！";
	   return result;
	  } catch (IOException e) {
	   e.printStackTrace();
	   result = "网络异常！";
	   return result;
	  }
	        return null;
	    }
	 // 发送Get请求，获得响应查询结果
	 public static  String queryStringForGet(String url){
	  // 获得HttpGet对象
	  HttpGet request = HttpUtils.getHttpGet(url);
	  String result = null;
	  try {
	   // 获得响应对象
	   HttpResponse response = HttpUtils.getHttpResponse(request);
	   // 判断是否请求成功
	   if(response.getStatusLine().getStatusCode()==200){
	    // 获得响应
	    result = EntityUtils.toString(response.getEntity());
	    return result;
	   }
	  } catch (ClientProtocolException e) {
	   e.printStackTrace();
	   result = "网络异常！";
	   return result;
	  } catch (IOException e) {
	   e.printStackTrace();
	   result = "网络异常！";
	   return result;
	  }
	        return null;
	    }
	 
	 
	 //表单提交POST
	 public static  String submitPost(String stringURL,String filename,String mediaFileUrl) throws IOException{
		 URL url = new URL(stringURL);

         String result = null;

         File file = new File(mediaFileUrl);

         if (!file.exists() || !file.isFile()) {

                 System.out.println("上传的文件不存在");

         }

         HttpURLConnection con = (HttpURLConnection) url.openConnection();

         con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式

         con.setDoInput(true);

         con.setDoOutput(true);

         con.setUseCaches(false); // post方式不能使用缓存

         // 设置请求头信息

         con.setRequestProperty("Connection", "Keep-Alive");

         con.setRequestProperty("Charset", "UTF-8");

         // 设置边界

         String BOUNDARY = "----------" + System.currentTimeMillis();

         con.setRequestProperty("Content-Type",
                         "multipart/form-data; boundary="

                         + BOUNDARY);

         // 请求正文信息

         // 第一部分：

         StringBuilder sb = new StringBuilder();

         sb.append("--"); // 必须多两道线

         sb.append(BOUNDARY);

         sb.append("\r\n");

         sb.append("Content-Disposition:form-data;name=\"media\";filename=\""

                         + filename + "\" \r\n");

         sb.append("Content-Type:application/octet-stream\r\n\r\n");

         byte[] head = sb.toString().getBytes("utf-8");

         // 获得输出流

         OutputStream out = new DataOutputStream(con.getOutputStream());

         // 输出表头
         
         out.write(head);

         // 文件正文部分

         // 把文件已流文件的方式 推入到url中

         DataInputStream in = new DataInputStream(new FileInputStream(file));

         int bytes = 0;

         byte[] bufferOut = new byte[1024];

         while ((bytes = in.read(bufferOut)) != -1) {

                 out.write(bufferOut, 0, bytes);

         }

         in.close();

         // 结尾部分

         byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

         out.write(foot);

         out.flush();

         out.close();
         
         
         StringBuffer buffer = new StringBuffer();
        
         BufferedReader reader = null;

         // 定义BufferedReader输入流来读取URL的响应
         reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

         String line = null;
         while ((line = reader.readLine()) != null) {

                 buffer.append(line);

         }
         if (result == null) {
             result = buffer.toString();
             System.out.println(result);
         }
		 return result;
	 }
	 
	 public static String uploadFodder(String uploadurl, String access_token,String data){
		 System.out.println(data);
		HttpClient client =new HttpClient();
	   String posturl = String.format("%s?access_token=%s", uploadurl, access_token);
		        PostMethod post = new PostMethod(posturl);
			        post.setRequestHeader( "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
			          post.setRequestHeader("Host", "file.api.weixin.qq.com");
			          post.setRequestHeader("Connection", "Keep-Alive");
			          post.setRequestHeader("Cache-Control", "no-cache");
			          String result = null;
			          try
			          {
			              post.setRequestBody(data);
			              int status = client.executeMethod(post);
			              if (status == HttpStatus.SC_OK)
			              {
			                  String responseContent = post.getResponseBodyAsString();
			                  System.out.println(responseContent);
//			                  JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
//			                  JsonObject json = jsonparer.parse(responseContent)
//			                          .getAsJsonObject();
//			                  if (json.get("errcode") == null)
//			                  {// 正确 { "type":"news", "media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ","created_at":1391857799}
//			                      result = json.get("media_id").getAsString();
//			                  }
			              }
			          }
			          catch (Exception e)
			          {
			              e.printStackTrace();
			          }
			            return result;
			     }
	 
	 
	//上传图片素材
//	 private static final String UPLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
//		public String sendUploadImage(String uploadUrl,String access_token,String type,String filename, File fileUrl){
//			 if(fileUrl==null||access_token==null||type==null){
//		            return null;
//		        }
//
//		        if(!fileUrl.exists()){
//		        	System.out.println("上传文件不存在,请检查!");
//		            return null;
//		        }
//
//		        String url = UPLOAD_MEDIA;
//		        JSONObject jsonObject = null;
//		        PostMethod post = new PostMethod(url);
//		        post.setRequestHeader("Connection", "Keep-Alive");
//		        post.setRequestHeader("Cache-Control", "no-cache");
//		        FilePart media = null;
//		        HttpClient httpClient = new HttpClient();
//		        //信任任何类型的证书
//		        Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443); 
//		        Protocol.registerProtocol("https", myhttps);
//
//		        try {
//		            media = new FilePart("media", fileUrl);
//		            Part[] parts = new Part[] { new StringPart("access_token", access_token),
//		                    new StringPart("type", type), media };
//		            MultipartRequestEntity entity = new MultipartRequestEntity(parts,post.getParams());
//		            post.setRequestEntity(entity);
//		            int status = httpClient.executeMethod(post);
//		            if (status == HttpStatus.SC_OK) {
//		                String text = post.getResponseBodyAsString();
//		                jsonObject = JSONObject.fromObject(text);
//		            } else {
//		                logger.info("upload Media failure status is:" + status);
//		            }
//		        } catch (FileNotFoundException execption) {
//		            logger.error(execption);
//		        } catch (HttpException execption) {
//		            logger.error(execption);
//		        } catch (IOException execption) {
//		            logger.error(execption);
//		        }
//		        return jsonObject;
//		}	 
	 
	 
	 
	 public static String getMaterialData(String uploadurl, String access_token, String data){
			 HttpClient client =new HttpClient();
		     String posturl = String.format("%s?access_token=%s", uploadurl, access_token);
	         PostMethod post = new HttpUtils(posturl);
		        post.setRequestHeader( "User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
		        //  post.setRequestHeader("Host", "file.api.weixin.qq.com");
		          post.setRequestHeader("Connection", "Keep-Alive");
		          post.setRequestHeader("Cache-Control", "no-cache");
		       post.setRequestHeader("content-type","application/x-www-form-urlencoded;charset=utf-8");
		       post.addRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
		          String result = null;
		          try
		          {
		              post.setRequestBody(data);
		              int status = client.executeMethod(post);

		              if (status == HttpStatus.SC_OK)
		              {
		                 result = new String(post.getResponseBodyAsString().getBytes("ISO8859-1"),"UTF-8");
		              }
		          }
		          catch (Exception e)
		          {
		              e.printStackTrace();
		          }
		            return result;
		     }
	 @Override
	public String getRequestCharSet() {
		// TODO Auto-generated method stub
		return "UTF-8";
	}
	 public HttpUtils() {
		// TODO Auto-generated constructor stub
	}
	 private HttpUtils(String url) {
		super(url);

	}
}