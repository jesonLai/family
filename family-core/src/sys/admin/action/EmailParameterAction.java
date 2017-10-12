package sys.admin.action;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sys.BaseHint;
import sys.SpringAnnotationDef;
import sys.model.controller.SenderEmail;
import sys.util.XMLOperation;

@Scope(value = SpringAnnotationDef.SCOPE_PROTOTYPE)
@Controller
@RequestMapping("/admin/menus/emailParameter")
/**
 * 邮箱参数设置
 * 
 * @author lxr
 *
 */
public class EmailParameterAction extends BaseAction {
    protected final Log _Log = LogFactory.getLog(getClass());
    private final String xmlName = "email.xml";

    /**
     * 获取邮箱参数界面
     */
    @RequestMapping("/emailParameterPage")
    public ModelAndView emailPAllPage(HttpServletRequest request) {
	_Log.debug(">>>>>>>>>>>>>>>>>emailPAllPage()<<<<<<<<<<<<<<<<<");
	ModelAndView mav = new ModelAndView();
	SenderEmail senderEmail = new SenderEmail();
	ServletContext sc = request.getSession().getServletContext();
	senderEmail.setXmlName(xmlName);
	senderEmail.setXmlPath(sc.getRealPath("/WEB-INF/"));
	_Log.debug("senderEmail content:" + senderEmail);
	_Log.debug("GET emailParameter.vm");
	mav.setViewName("admin/menus/set/emailParameter/emailParameter.vm");
	XMLOperation.readXML(senderEmail);
	mav.addObject("SENDEREMAIL", senderEmail);
	return mav;
    }

    /**
     * 保存发送邮件基本参数:邮件服务地址、端口、发件人账号/密码、邮件协议
     * 
     * @param request
     * @param senderEmail
     * @return
     */
    @RequestMapping("/COM_EMAILPARAMETER_SAVE")
    @ResponseBody
    Map<String, Object> emailInfoSave(HttpServletRequest request,
	    @ModelAttribute("SenderEmail") SenderEmail senderEmail) {
	try {
	    _Log.debug(">>>>>>>>>>>>>>>>>emailInfoSave()<<<<<<<<<<<<<<<<<");
	    _Log.debug("parameter1[senderEmail]:" + senderEmail);
	    ServletContext sc = request.getSession().getServletContext();
	    senderEmail.setXmlName(xmlName);
	    senderEmail.setXmlPath(sc.getRealPath("/WEB-INF/"));
	    _Log.debug("senderEmail content:" + senderEmail);
	    XMLOperation.printXml(senderEmail);
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_TRUE);
	    ht.put(BaseHint.MESSAGE, BaseHint.SET_SUCCESS);
	} catch (Exception e) {
	    ht.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);
	    ht.put(BaseHint.MESSAGE, e.getMessage());
	    _Log.error("Error occur while emailInfoSave()", e);
	    e.printStackTrace();
	}
	return ht;
    }
}
