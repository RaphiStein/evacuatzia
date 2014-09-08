package evacuatzia_proj.common;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;

public class TestUtils {
	public static void dropAllTables() {
		Session s = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		String[] toClear = new String[]{"EvacuationEvent", "Report", "UserInfo", "UserRoles", "LoginAccounts"};
		for (String type: toClear) {
			hqlTruncate(type, s);
		}
		s.getTransaction().commit();
	}

	public static Date createFutureDate() {
		Date now = new Date();
		return new Date(now.getTime()+60000L); // 60 seconds from now
	}
	
	private static void hqlTruncate(String myType, Session s){
		List<Object> objs = queryAllObjectsInDB(myType, s);
		for (Object obj: objs) {
			s.delete(obj);
		}
	}
	@SuppressWarnings("unchecked")
	private static List<Object> queryAllObjectsInDB(String className, Session s) {
		Query query = s.createQuery("FROM " + className);
		if (query != null) {
			return query.list();
		}
		return null;
	}
	
}
