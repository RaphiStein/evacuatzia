package evacuatzia_proj;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;

public class HibernateSimpleConnection {

	@Test
	public void test() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		createUserInfo(session);
		queryUserInfo(session);

	}

	private static void queryUserInfo(Session session) {
		Query query = session.createQuery("from user_info");
		List<UserInfo> list = query.list();
		java.util.Iterator<UserInfo> iter = list.iterator();
		while (iter.hasNext()) {

			UserInfo person = iter.next();
			System.out.println("user ID: \"" + person.getId() + "\", userName: \"" + person.getUserName()
					+ "\", name: \"" + person.getName() + "\"");

		}

		session.getTransaction().commit();

	}

	public static void createUserInfo(Session session) {
		UserInfo user = new UserInfo();

		user.setName("Barak Obhama");
		user.setUserName("BarakO");
		user.setId(99999L);

		session.save(user);
	}

}
