package sys.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;

import sys.model.controller.FileEntity;

/**
 * 基于springMVC的文件写入
 * 
 * @author Administrator
 *
 */
public class FileUpload {
    private static final Logger logger = Logger.getLogger(FileUpload.class.getName());
    private static String initSystemUrl = "admin" + File.separator + "fileupload";
    private static String initHttpUrl = "admin/fileupload";

    /**
     * 保存新增
     * 
     * @param request
     * @param initStr
     * @param fileNameOld
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static List<FileEntity> writeFile(HttpServletRequest request, String initStr, String fileNameOld)
	    throws IllegalStateException, IOException {
	List<FileEntity> fel = Lists.newArrayList();
	String initSystemUrl = MessageFormat.format(initStr, File.separator);
	String initHttpUrl = MessageFormat.format(initStr, "/");
	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
		request.getSession().getServletContext());
	if (multipartResolver.isMultipart(request)) {
	    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	    // 取得request中的所有文件名
	    Iterator<String> iter = multiRequest.getFileNames();
	    while (iter.hasNext()) {
		FileEntity fe = new FileEntity();
		// 取得上传文件
		MultipartFile file = multiRequest.getFile(iter.next());
		if (file != null) {
		    String myFileName = file.getOriginalFilename();
		    if (myFileName.trim() != "") {
			String fileName = file.getOriginalFilename();
			fe.setInitName(fileName);
			Long _l = System.nanoTime();
			ServletContext sc = request.getSession().getServletContext();
			if (StringUtil.isEmpty(initSystemUrl))
			    FileUpload.initSystemUrl = initSystemUrl;
			if (StringUtil.isEmpty(initHttpUrl))
			    FileUpload.initHttpUrl = initHttpUrl;
			// 文件

			String _extName = fileName.substring(fileName.indexOf("."));
			fileName = _l + _extName;
			fe.setName(fileName);
			String dir = sc.getRealPath(File.separator) + initSystemUrl;
			File localFile = new File(dir, fileName);
			logger.debug(localFile);
			if (!localFile.exists())
			    localFile.getParentFile().mkdirs();
			// 删除就的图片
			if (!StringUtil.isEmpty(fileNameOld)) {
			    File localFileOld = new File(dir, fileNameOld);
			    if (localFileOld.exists())
				localFileOld.delete();
			}
			file.transferTo(localFile);
			fe.setHttpUrl(initHttpUrl);
			fe.setSystemUrl(initSystemUrl);
			fe.setDateTime(new Date());
			fel.add(fe);
		    }
		}
	    }
	}
	return fel;
    }

    public static byte[] fileToByteArray(String filename) throws IOException {

	FileChannel fc = null;
	try {
	    fc = new RandomAccessFile(filename, "r").getChannel();
	    MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
	    System.out.println(byteBuffer.isLoaded());
	    byte[] result = new byte[(int) fc.size()];
	    if (byteBuffer.remaining() > 0) {
		// System.out.println("remain");
		byteBuffer.get(result, 0, byteBuffer.remaining());
	    }
	    return result;
	} catch (IOException e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    try {
		fc.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public static boolean deleteFile(HttpServletRequest request, String foldStr, String fileName) {
	boolean bl = false;
	ServletContext sc = request.getSession().getServletContext();
	String dir = sc.getRealPath(File.separator) + MessageFormat.format(foldStr, File.separator);
	File localFile = new File(dir, fileName);
	logger.debug(localFile);
	if (localFile.exists()) {
	    bl = localFile.delete();
	}
	return bl;
    }

}
