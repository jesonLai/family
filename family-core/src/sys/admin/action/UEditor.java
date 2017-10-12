package sys.admin.action;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.baidu.ueditor.ActionEnter;
import com.google.common.collect.Maps;

import net.sf.json.JSONObject;
import sys.SpringAnnotationDef;
import sys.util.JSONUtils;
import sys.util.StringUtil;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("admin/ueditor")
/**
 * 百度编辑器
 * 
 * @author lxr {"state":"SUCCESS","list":[{"url":
 *         "\/server\/ueditor\/upload\/image\/13.jpg","mtime":1482398749}],
 *         "start":"0","total":29}
 */
public class UEditor extends BaseAction {
    private static final Logger logger = Logger.getLogger(UEditor.class.getName());
    private static final String UEDITOR_PATH_HOME = "monkey{0}image{0}ueditor";
    private static final String UEDITOR_PATH_IMAGE = "monkey{0}image{0}ueditor{0}image";
    private static final String UEDITOR_META_LIST_IMAGE = "listimage";
    private static final String UEDITOR_META_CONFIG = "config";
    private static final String UEDITOR_META_UPLOAD_IMAGE = "uploadimage";

    @RequestMapping(value = "/controller")
    public void ueHome(HttpServletRequest request, HttpServletResponse response,
	    @ModelAttribute("action") String actionName) throws IOException {
	String responseJSON = "";
	try {
	    if (UEDITOR_META_CONFIG.equals(actionName)) {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		responseJSON = new ActionEnter(request, rootPath).exec();
	    } else if (UEDITOR_META_LIST_IMAGE.equals(actionName)) {
		responseJSON = listimage(request);
	    } else if (UEDITOR_META_UPLOAD_IMAGE.equals(actionName)) {
		responseJSON = saveUeditorUploadImage(request);
	    } else {
		responseJSON = "'{'\"state\":\"Fail\",message:\"action not found\"}";

	    }
	} catch (Exception e) {
	    logger.debug("Error occur while ueHome()", e);
	    responseJSON = e.getMessage();
	}
	response.getWriter().write(responseJSON);
	response.getWriter().flush();
    }

    @RequestMapping(value = "/ueditorPage")
    public void ueditorPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
	request.setCharacterEncoding("utf-8");
	response.setHeader("Content-Type", "text/html");
	String rootPath = request.getSession().getServletContext().getRealPath("/");
	response.getWriter().write(new ActionEnter(request, rootPath).exec());
	response.getWriter().flush();
    }

    /**
     * 在线管理图片
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    String listimage(HttpServletRequest request) throws IOException {
	ServletContext sc = request.getSession().getServletContext();
	String dir = sc.getRealPath(File.separator) + MessageFormat.format(UEDITOR_PATH_IMAGE, File.separator);
	File folder = new File(dir);
	StringBuilder sb = new StringBuilder();
	String listImage = "',{'\"url\":\"" + getBasePath(request) + MessageFormat.format(UEDITOR_PATH_IMAGE, "/")
		+ "/{0}\",\"mtime\":\"{1}\"'}'";
	String responseJSON = "'{'\"state\":\"{0}\",\"list\":[{1}],\"start\":{2},\"total\":{3}'}'";
	String ParameterStart = request.getParameter("start");
	String ParameterSize = request.getParameter("size");
	int start = (!StringUtil.isEmpty(ParameterStart) && ParameterStart.matches("[0-9]+")
		? Integer.parseInt(ParameterStart) : 0);
	int size = !StringUtil.isEmpty(ParameterSize) && ParameterSize.matches("[0-9]+")
		? Integer.parseInt(ParameterSize) : 20;
	int total = 0;
	if (folder.exists()) {
	    File[] fileImage = folder.listFiles();
	    total = fileImage.length;
	    if (fileImage.length >= start) {
		int endSize = total > size + start ? size : total;
		for (int i = start; i < endSize; i++) {
		    File file = fileImage[i];
		    sb.append(MessageFormat.format(listImage, file.getName(), file.lastModified()));
		}
	    }
	}
	return MessageFormat.format(responseJSON, "SUCCESS", sb.delete(0, 1), start, total);
    }

    /**
     * 上传图片入口,method=RequestMethod.POST
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    String saveUeditorUploadImage(HttpServletRequest request) throws IllegalStateException, IOException {
	Map<String, Object> maps = Maps.newHashMap();
	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
		request.getSession().getServletContext());
	if (multipartResolver.isMultipart(request)) {
	    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	    Iterator<String> iter = multiRequest.getFileNames();

	    while (iter.hasNext()) {
		Map<String, Object> map = Maps.newHashMap();
		// 取得上传文件
		MultipartFile file = multiRequest.getFile(iter.next());
		if (file != null) {
		    // 取得当前上传文件的文件名称
		    String myFileName = file.getOriginalFilename();
		    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
		    if (myFileName.trim() != "") {
			// 重命名上传后的文件名
			String fileName = file.getOriginalFilename();
			// 获取上传的路径、防止重名覆盖
			ServletContext sc = request.getSession().getServletContext();
			String dir = sc.getRealPath(File.separator)
				+ MessageFormat.format(UEDITOR_PATH_IMAGE, File.separator);
			Long _l = System.nanoTime();
			String _extName = fileName.substring(fileName.indexOf("."));
			fileName = _l + _extName;

			File folder = new File(dir);

			// 判断文件夹是否存在，没有就创建
			if (!folder.exists())
			    folder.mkdirs();
			file.transferTo(new File(dir, fileName));
			map.put("url", getBasePath(request,
				MessageFormat.format(UEDITOR_PATH_IMAGE, "/").concat("/".concat(fileName))));
			map.put("title", fileName);
			map.put("state", "SUCCESS");
			map.put("original", fileName);
			maps = map;
		    }
		}
	    }

	}
	return JSONUtils.toJSONString(JSONObject.fromObject(maps));
    }

}
