package sys.interceptors;

import org.hibernate.EmptyInterceptor;

/**
 * 公共字段拦截注入
 * @author lxr
 *
 */
public class CommonSQLFieldInterceptor extends EmptyInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Override
//	public void afterTransactionBegin(Transaction tx) {
//		// TODO Auto-generated method stub
//	System.out.println("tx="+tx);
//		super.afterTransactionBegin(tx);
//	}
//
//	@Override
//	public String onPrepareStatement(String sql) {
//		System.out.println("sql="+sql);
//		return super.onPrepareStatement(sql);
//	}
//
//	@Override
//	public Object getEntity(String entityName, Serializable id) {
//		// TODO Auto-generated method stub
//		return super.getEntity(entityName, id);
//	}
//
//	@Override
//	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
//		// TODO Auto-generated method stub
//		System.out.println("================onLoad()===============");
//		System.out.println("entity="+entity);
//		System.out.print(",id="+id);
//		System.out.print(",state="+state);
//		System.out.print(",propertyNames="+propertyNames);
//		System.out.print(",types="+types);
//		return super.onLoad(entity, id, state, propertyNames, types);
//	}
//
//	@Override
//	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
//			String[] propertyNames, Type[] types) {
//		// TODO Auto-generated method stub
//		System.out.println("================onFlushDirty()===============");
//		System.out.println("entity="+entity);
//		System.out.print(",id="+id);
//		System.out.print(",currentState="+currentState);
//		System.out.print(",previousState="+previousState);
//		System.out.print(",propertyNames="+propertyNames);
//		System.out.print(",types="+types);
//		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
//	}

}
