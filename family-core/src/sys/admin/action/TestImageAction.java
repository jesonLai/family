package sys.admin.action;

import java.io.File;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;

import sys.SpringAnnotationDef;
import sys.model.controller.FileEntity;
import sys.util.FileUpload;
import sys.util.StringUtil;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/test")
/**
 * 图片
 * 
 * @author lxr
 *
 */
public class TestImageAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(TestImageAction.class.getName());

	/**
	 * 获取图片上传界面
	 */
	@RequestMapping("/upload/image")
	@ResponseBody
	Map<String, Object> usersAllPage(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()){
				FileEntity fe = new FileEntity();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null){
					String uploadFileName = file.getOriginalFilename();
					logger.debug("上传的图片名称:" + uploadFileName);
				}
			}
		}
		map.put("satuts","SUCCESS");
		return map;
	}
}
