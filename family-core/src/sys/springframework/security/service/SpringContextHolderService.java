package sys.springframework.security.service;


import javax.persistence.EntityManagerFactory;

import sys.model.controller.UserInfoContext;
/**
 * session层
 * @author lxr
 *
 */
public interface SpringContextHolderService {
	/**
	 * 直接获取用户信息
	 * @return
	 */
	UserInfoContext getUserInfoContext();

	EntityManagerFactory getEntityManagerFactory();
	
	Object getBean(String beanName);
	
}
