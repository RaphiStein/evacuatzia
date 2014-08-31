package evacuatzia_proj;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.exceptions.UsernameException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;

public class ApiUserTest {
	
	private void dropAllTables() {
		Session s = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		String[] toClear = new String[]{"EvacuationEvent", "Report", "UserInfo", "UserRoles", "LoginAccounts"};
		for (String type: toClear) {
			hqlTruncate(type, s);
		}
		s.getTransaction().commit();
	}
	private void hqlTruncate(String myType, Session s){
		List<Object> objs = queryAllObjectsInDB(myType, s);
		for (Object obj: objs) {
			s.delete(obj);
		}
	}
	private List<Object> queryAllObjectsInDB(String className, Session s) {
		Query query = s.createQuery("FROM " + className);
		if (query != null) {
			return query.list();
		}
		return null;
	}
	
	@Test
	public void canRegisterNewAccount() {
		UserManager.register("myUsername", "mypass", "myName");
	}
	
	@Test(expected=UsernameException.class)
	public void cantRegisterTwoAccountsWithSameUsername() {
		UserManager.register("myUsername", "mypass1", "myName1");
		UserManager.register("myUsername", "mypass2", "myName2");
	}

}
