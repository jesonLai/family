package sys.springframework.security.util;

import java.math.BigDecimal;
import java.util.Date;
public class PropertyFilter {
    private String filedName;
    private Object filedValue;
	public PropertyFilter(String key,Object value){
		this.filedName=key;
		this.filedValue=value;
	}
	public  static enum Operator{
		 EQ, NEQ, LIKE,NLIKE,LT, GT, LE, GE, IN, NIN,GTE,LTE
	}
	public static enum Connector{
		OR,AND,BETWEEN
	}
	public  static enum Types{
		 S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), G(BigDecimal.class),AS(String[].class), AI(Integer[].class), AL(Long[].class), AN(Double[].class), AD(Date[].class), AB(Boolean[].class), AG(BigDecimal[].class);
		 private Class cls;
		 Types(){}
		 Types(Class cls){
			 this.cls=cls;
		 }
		public Class getCls() {
			return cls;
		}
		public void setCls(Class cls) {
			this.cls = cls;
		}
		 
	}
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public Object getFiledValue() {
		return filedValue;
	}
	public void setFiledValue(Object filedValue) {
		this.filedValue = filedValue;
	}
	
}
