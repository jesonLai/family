package sys.interceptors;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Maps;

import sys.BaseHint;
import sys.family.annotation.Token;
import sys.util.JSONUtils;

/**
 * 表单重复提交
 * @author lxr
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token. class );
            if (annotation != null ) {
                boolean needSaveSession = annotation.bulidToken();
                if (needSaveSession) {
                    request.getSession( false ).setAttribute( "token" , UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.removeToken();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return handleInvalidToken(request,response,handler) ;
                    }
                    request.getSession( false ).removeAttribute( "token" );
                }
            }
            return true ;
        } else {
            return super .preHandle(request, response, handler);
        }
    }
    /** 
     * 当出现一个非法令牌时调用 
     */  
    protected boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{  
        Map<String , Object> data = Maps.newHashMap();
        data.put(BaseHint.RESULT, BaseHint.RESULT_FALSE);  
        data.put(BaseHint.MESSAGE, BaseHint.FREQUENT_OPERATION);  
        writeMessageUtf8(response, data);  
        return false;  
    } 
    private void writeMessageUtf8(HttpServletResponse response, Map<String , Object> map) throws IOException{  
        try  
        {  
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/javascript; charset=utf-8");
            response.getWriter().print(JSONUtils.toJSONObject(map));  
        }  
        finally  
        {  
            response.getWriter().close();  
        }  
    }  
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession( false ).getAttribute( "token" );
        if (serverToken == null ) {
            return true ;
        }
        String clinetToken = request.getParameter( "token" );
        if (clinetToken == null ) {
            return true ;
        }
        if (!serverToken.equals(clinetToken)) {
            return true ;
        }
        return false ;
    }
}