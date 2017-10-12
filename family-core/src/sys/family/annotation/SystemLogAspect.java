package sys.family.annotation;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sys.model.RecordFamilyLog;
import sys.model.controller.UserInfoContext;
import sys.set.service.RecordFamilyLogService;
import sys.springframework.security.service.impl.PropertyPlaceHolder;
import sys.util.ClientPCInfo;
import sys.util.JSONUtils;
import sys.util.StringUtil;
import sys.util.func.UtilFactory;
/**
 * 本地和系统设置为false 将不执行
 * @author lxr
 *
 */
@Aspect    
@Component    
public  class SystemLogAspect {    
     @Resource    
     private RecordFamilyLogService recordFamilyLogService;
     private  static  final Logger logger = Logger.getLogger(SystemLogAspect. class);    
    //Service层切点    
    @Pointcut("@annotation(sys.family.annotation.SystemServiceLog)")    
     public  void serviceAspect() {    
    }    
    //Controller层切点    
    @Pointcut("@annotation(sys.family.annotation.SystemControllerLog)")    
     public  void controllerAspect() {    
    }    
    
    /**  
     * 前置通知 用于拦截Controller层记录用户的操作  
     *  
     * @param joinPoint 切点  
     */    
    @Before("controllerAspect()")    
     public  void doBefore(JoinPoint joinPoint) {    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
//      System.out.println("=====前置通知开始=====");
         try {    
        	 	UserInfoContext ufc= UtilFactory.getSpringContext().getUserInfoContext();
	        	String ip = ClientPCInfo.getIpAddr(request);  
	        	String description= getControllerMethodDescription(joinPoint);
	        	description=String.valueOf(PropertyPlaceHolder.getProperty(description));
	        	String requestMethod=joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
	        	if(!ip.equals("127.0.0.1")){
	        		RecordFamilyLog recordFamilyLoged=recordFamilyLogService.findOneByRequestIpAndRequestMethodAndDescriptionAndCreateMan(ip,requestMethod,description,ufc.getUser());
	        		if(StringUtil.isEmpty(recordFamilyLoged)){
	        			recordFamilyLoged=new RecordFamilyLog();
	        			recordFamilyLoged.setDescription(description);    
	        			recordFamilyLoged.setRequestMethod(requestMethod);    
	        			recordFamilyLoged.setRequestIp(ip);    
	        			recordFamilyLoged.setExceptionCode( null);    
	        			recordFamilyLoged.setExceptionDetail( null);    
	        			recordFamilyLoged.setRquestParams( null);    
	        			recordFamilyLoged.setCreateMan(ufc.getUser());    
	        			recordFamilyLoged.setCreateDate(new Date());
	        		}else{
	        			recordFamilyLoged.setStatistical(recordFamilyLoged.getStatistical()+1);
	        			recordFamilyLoged.setUpdateDate(new Date());
	        			
	        		}
	        		 recordFamilyLogService.save(recordFamilyLoged);
	        	}
//            System.out.println("=====前置通知结束=====");    
        }  catch (Exception e) {    
            //记录本地异常日志    
            logger.error("==前置通知异常==");    
            logger.error(e.getMessage());    
        }    
    }    
    
    /**  
     * 异常通知 用于拦截service层记录异常日志  
     *  
     * @param joinPoint  
     * @param e  
     */    
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")    
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
//        //获取用户请求方法的参数并序列化为JSON格式字符串    
        String params = "";    
         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
             for ( int i = 0; i < joinPoint.getArgs().length; i++) {    
                params += JSONUtils.toJSONString(joinPoint.getArgs()[i]) + ";";    
            }    
        }    
         try {    
//              /*========控制台输出=========*/    
//            System.out.println("=====异常通知开始=====");    
//            System.out.println("异常代码:" + e.getClass().getName());    
//            System.out.println("异常信息:" + e.getMessage());    
//            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
//            System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));    
//            System.out.println("请求人:" + user.getName());    
//            System.out.println("请求IP:" + ip);    
//            System.out.println("请求参数:" + params); 
        	 String ip = request.getRemoteAddr();  
        	 UserInfoContext ufc= UtilFactory.getSpringContext().getUserInfoContext();
		        String description= getControllerMethodDescription(joinPoint);
		        description=String.valueOf(PropertyPlaceHolder.getProperty(description));
		        String requestMethod=joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
        	 if(!ip.equals("127.0.0.1")){
	        	RecordFamilyLog recordFamilyLoged=recordFamilyLogService.findOneByRequestIpAndRequestMethodAndDescriptionAndCreateMan(ip,requestMethod,description,ufc.getUser());
	        		if(StringUtil.isEmpty(recordFamilyLoged)){
			//               /*==========数据库日志=========*/    
			        	RecordFamilyLog recordFamilyLog=new RecordFamilyLog();
				        recordFamilyLog.setDescription(description);    
			            recordFamilyLog.setRequestMethod(requestMethod);    
			            recordFamilyLog.setRequestIp(ip);    
			            recordFamilyLog.setExceptionCode(e.getClass().getName());    
			            recordFamilyLog.setExceptionDetail(e.getMessage());    
			            recordFamilyLog.setRquestParams(params);    
			            recordFamilyLog.setCreateMan(ufc.getUser());    
			            recordFamilyLog.setCreateDate(new Date());
			            recordFamilyLogService.save(recordFamilyLog);  
	        		}else{
	        			recordFamilyLoged.setStatistical(recordFamilyLoged.getStatistical()+1);
	        			recordFamilyLoged.setUpdateDate(new Date());
	        		}
        	 }
//            //保存数据库    
//            logService.add(log);    
//            System.out.println("=====异常通知结束=====");    
        }  catch (Exception ex) {    
            //记录本地异常日志    
            logger.error("==异常通知异常==");    
            logger.error(ex.getMessage());    
        }    
//         /*==========记录本地异常日志==========*/    
//        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);    
    
    }    
    
    /**  
     * 获取注解中对方法的描述信息 用于service层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static String getServiceMthodDescription(JoinPoint joinPoint)    
             throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SystemServiceLog. class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    }    
    
    /**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SystemControllerLog. class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    }    
}    