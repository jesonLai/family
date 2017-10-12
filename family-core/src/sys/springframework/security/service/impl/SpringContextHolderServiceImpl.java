package sys.springframework.security.service.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import sys.model.controller.UserInfoContext;
import sys.springframework.security.service.SpringContextHolderService;
import sys.util.StringUtil;
/**
 * 获取上下文bean
 * @author lxr
 *
 */
@Service
public class SpringContextHolderServiceImpl  implements SpringContextHolderService,ApplicationContextAware{
	private static ApplicationContext applicationContext;
//	WebApplicationContext applicationContext=null;
//	public SpringContextHolderServiceImpl(){
//		if(StringUtil.isEmpty(applicationContext))
//			applicationContext=ContextLoader.getCurrentWebApplicationContext();	
//	}
	@Override
	public UserInfoContext getUserInfoContext() {
		// TODO Auto-generated method stub
		return !StringUtil.isEmpty(applicationContext)? (UserInfoContext) applicationContext.getBean("userInfoContext") : null;
	}
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		// TODO Auto-generated method stub
		return !StringUtil.isEmpty(applicationContext)? (EntityManagerFactory) applicationContext.getBean("entityManagerFactory"): null;
	}

//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		SpringContextHolderServiceImpl.applicationContext=applicationContext;
//		
//	}
//	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		SpringContextHolderServiceImpl.applicationContext=applicationContext;
	}
	@Override
	public Object  getBean(String beanName) {
		return !StringUtil.isEmpty(applicationContext)?(Object)applicationContext.getBean(beanName):null;
	
	}
}
