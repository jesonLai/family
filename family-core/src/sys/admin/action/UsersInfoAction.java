package sys.admin.action;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.Sys_Const;
import sys.exception.handing.BusinessException;
import sys.family.annotation.SystemControllerLog;
import sys.family.annotation.Token;
import sys.model.UserInfo;
import sys.model.controller.TableRquestData;
import sys.set.service.impl.IUsersServiceImpl;
import sys.util.FileUpload;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/users")
public class UsersInfoAction extends BaseAction {
    private static final Logger logger = Logger.getLogger(UsersInfoAction.class.getName());
    @Resource(name = SpringAnnotationDef.SER_USERS)
    IUsersServiceImpl usersService;

    /**
     * 获取全部用户的界面
     */
    @RequestMapping("/usersAllPage")
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFOMAINPAGE)
    public ModelAndView usersMainPage(ModelAndView modelAndView) {
	modelAndView.setViewName("admin/menus/set/users/user.vm");
	return modelAndView;
    }

    /**
     * 获取用户及分页信息
     */
    @RequestMapping("/usersList")
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFOMAINLIST)
    @ResponseBody
    TableRquestData usersInfoMainList(@ModelAttribute("TableRquestData") TableRquestData tableRquestData,
	    @RequestParam(value = "search[value]", defaultValue = "") String search, HttpServletRequest request) {
	return usersService.getUserPagination(tableRquestData, search);

    }

    /**
     * 获取excel数据page
     */
    @RequestMapping("/usersExcelImportPage")
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFOIMPORTEXCELPAGE)
    public ModelAndView userInfoImportExcelPage(ModelAndView modelAndView) {
	modelAndView.setViewName("admin/menus/set/users/user-excel-import.vm");
	return modelAndView;
    }

    /**
     * 获取excel数据page
     */
    @RequestMapping("/usersDataExcelImportPage")
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFOIMPORTEXCELDATAPAGE)
    public ModelAndView userInfoExcelDataPage(ModelAndView modelAndView,
	    @RequestParam(value = "filename") MultipartFile file, HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    modelAndView.addObject("EXCELDATA", usersService.getExcelData(file.getInputStream()));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	modelAndView.setViewName("admin/menus/set/users/user-excel-import-data.vm");
	return modelAndView;
    }

    /**
     * 查询父亲信息
     * 
     * @param q
     * @param pageLimit
     * @return,method=RequestMethod.POST
     */
    @RequestMapping(value = "/fatherName")
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFOFATHERSELET)
    @ResponseBody
    List<Map<String, Object>> userInfoFatherSelect(@RequestParam(value = "q", defaultValue = "") String q,
	    @RequestParam(value = "page_limit", defaultValue = "") int pageLimit) {
	return usersService.getInitFather(q, pageLimit);
    }

    /**
     * 初始化信息
     * 
     * @param id
     * @return
     */
    @RequestMapping("/initFatherName")
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFOFATHERSELETINIT)
    @ResponseBody
    Map<String, Object> userInfoFatherSelectInit(@RequestParam(value = "id", defaultValue = "") int id) {
	return usersService.getUserInfoParent(id);
    }

    /**
     * 新增
     * 
     * @param user
     * @param response
     * @return
     */
    @RequestMapping(value = "/userAddNew", method = RequestMethod.POST)
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFOADDNEWORUPDATEPAGE)
    @Token(removeToken = true)
    @ResponseBody
    Map<String, Object> saveUser(@ModelAttribute("UserInfo") UserInfo userInfo,
	    @RequestParam(value = "fatherId", defaultValue = "") String fatherId, HttpServletResponse response) {
	try {
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
	    usersService.addNew(userInfo, fatherId);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    logger.info(be.getMessage());
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return ht;
    }

    /**
     * 删除用户信息
     * 
     * @param familyNew
     * @return
     */
    @RequestMapping(value = "/COM_USERINFO_DEL", method = RequestMethod.POST)
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFODELETE)
    @ResponseBody
    Map<String, Object> userInfoDelete(@ModelAttribute("UserInfo") UserInfo userInfo) {
	try {
	    usersService.delUserInfo(userInfo);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    logger.info(be.getMessage());
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return ht;
    }

    /**
     * 保存批量导入的用户信息
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "/COM_BATCH_USER_EXCEL", method = RequestMethod.POST)
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_USERINFODADDNEWBATCH)
    @ResponseBody
    Map<String, Object> userInfoAddNewBatch(HttpServletRequest request, HttpServletResponse response) {
	try {
	    usersService.addNewBatch();
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.MESSAGE_SUCCESS);
	} catch (BusinessException be) {
	    ht.put(BaseHint.MESSAGE, be.getMessage());
	    logger.info(be.getMessage());
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return ht;
    }

    /**
     * 下载批量导入模板
     * 
     * @throws IOException
     */
    @RequestMapping(value = "/COM_DOWLOAD_EXCEL_USERINFO", method = RequestMethod.POST)
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_DOENLOADEXCELUSERINFOTEMPLATE)
    public ResponseEntity<byte[]> dowloadExcelUserInfo(HttpServletRequest request) throws IOException {
	StringBuilder builder = new StringBuilder(File.separator);
	builder.append("monkey").append(File.separator).append("excel").append(File.separator).append("宗族成员信息.xls");
	String dowloadPath = request.getSession().getServletContext().getRealPath(builder.toString());
	HttpHeaders headers = new HttpHeaders();
	String fileName = new String("宗族成员信息.xls".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
	headers.setContentDispositionFormData("attachment", fileName);
	headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	return new ResponseEntity<byte[]>(FileUpload.fileToByteArray(dowloadPath), headers, HttpStatus.CREATED);
    }

    /**
     * 获取需要更新的数据
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "/userUpdateData", method = RequestMethod.POST)
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_GETUPDATEUSERINFODATE)
    @Token(bulidToken = true)
    ModelAndView getUpdateUserInfoData(ModelAndView modelAndView, @ModelAttribute("UserInfo") UserInfo userInfo) {
	try {
	    modelAndView.setViewName("admin/menus/set/users/user-add.vm");
	    if (!StringUtil.isEmpty(userInfo.getUserInfoId())) {
		modelAndView.addObject("USERINFO", usersService.getUserInfo(userInfo.getUserInfoId()));
	    } else {
		modelAndView.addObject("USERINFO", null);
		modelAndView.addObject("ACCOUNT", null);
		modelAndView.addObject("ROLE", null);
	    }
	} catch (Exception e) {
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	    e.printStackTrace();
	}
	return modelAndView;
    }

    /**
     * 头像上传
     * 
     * @param request
     * @param response
     * @param familyImage
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("/headImage")
    @SystemControllerLog(description = Sys_Const.META_LOG_CONTROLLER_UPLOADUSERINFOHEADPORTRAIT)
    @ResponseBody
    Map<String, Object> uploadHeadPortrait(HttpServletRequest request, HttpServletResponse response,
	    HttpSession session, @ModelAttribute("UserInfo") UserInfo userInfo)
	    throws IllegalStateException, IOException {
	try {
	    usersService.updateCurrHeadImg(request, ht, userInfo.getHeadImageName());
	    session.setAttribute("HEANDIMAGESRC", StringUtil.isEmpty(ht.get("imgPath")) ? "" : ht.get("imgPath"));
	    session.setAttribute("USERINFOPERSON",
		    UtilFactory.getSpringContext().getUserInfoContext().getUser().getUserInfo());
	} catch (BusinessException e) {
	    // TODO Auto-generated catch block
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    logger.info(e.getMessage());
	}
	return ht;
    }

    /**
     * 检验用户信息 邮箱、手机号、QQ、微信是否唯一
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "/checkUserEmail", method = RequestMethod.POST)
    @ResponseBody
    boolean isUserEmail(@ModelAttribute("UserInfo") UserInfo ui) {
	return usersService.isUserEmail(ui.getUserEmail(), ui.getUserInfoId());
    }

    @RequestMapping(value = "/checkUserPhone", method = RequestMethod.POST)
    @ResponseBody
    boolean isUserPhone(@ModelAttribute("UserInfo") UserInfo user) {
	return usersService.isUserPhone(user.getUserPhone(), user.getUserInfoId());
    }

    @RequestMapping(value = "/checkUserQq", method = RequestMethod.POST)
    @ResponseBody
    boolean isUserQq(@ModelAttribute("UserInfo") UserInfo user) {
	return usersService.isUserQq(user.getUserQq(), user.getUserInfoId());
    }

    @RequestMapping(value = "/checkUserWeixin", method = RequestMethod.POST)
    @ResponseBody
    boolean isUserWeixin(@ModelAttribute("UserInfo") UserInfo user) {
	return usersService.isUserWeixin(user.getUserWeixin(), user.getUserInfoId());
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
	dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
	    public void setAsText(String value) {
		try {
		    if (StringUtil.isEmpty(value)) {
			setValue(null);
		    } else {
			setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
		    }

		} catch (ParseException e) {
		    setValue(null);
		}
	    }

	    public String getAsText() {
		return new SimpleDateFormat("yyyy-MM-dd").format((Date) getValue());
	    }

	});
    }
}
