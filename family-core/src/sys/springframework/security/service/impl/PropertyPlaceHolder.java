package sys.springframework.security.service.impl;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;

import com.google.common.collect.Maps;

/**
 * *.properties
 * @author lxr
 *
 */
public class PropertyPlaceHolder  extends PropertyResourceConfigurer implements InitializingBean{
	 private static Map<String,String> propertyMap;
	    @Override
	    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
	        propertyMap = Maps.newHashMap();
	        for (Object key : props.keySet()) {
	            String keyStr = key.toString();
	            String value = props.getProperty(keyStr);
	            propertyMap.put(keyStr, value);
	        }
	    }
	    //static method for accessing context properties
	    public static Object getProperty(String name) {
	        return propertyMap.get(name);
	    }

		@Override
		public void afterPropertiesSet() throws Exception {
			// TODO Auto-generated method stub
			
		}

}
