package evacuatzia_proj;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.EvacuationEvent;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;

public class HibernateSimpleConnection {
	static Session session;
	SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");

	@Before
	public void setup() {
		session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
	}

	@After
	public void teardown() {
		session.getTransaction().commit();
	}

	@Test
	public void addUserInfoFinishWithoutExceptions() {
		createUserInfo();
		queryUserInfo();
	}

	@Test
	public void addEventFinishWithoutExceptions() throws ParseException {
		createEvacuationEvent();
		queryEvacuationEvent();
	}

	private void createEvacuationEvent() throws ParseException {
		EvacuationEvent evac = new EvacuationEvent("a title", (long) 999, 12.3456789, 12.3456789,
				sdf.parse("13.12.2011"), 20);
		session.save(evac);
	}

	private void queryEvacuationEvent() {
		Query query = session.createQuery("from evac");
		List<EvacuationEvent> list = query.list();
		java.util.Iterator<EvacuationEvent> iter = list.iterator();
		while (iter.hasNext()) {
			EvacuationEvent event = iter.next();
			System.out.println("Event ID: \"" + event.getId() + "\", title:" + event.getTitle() + "\", geoId:"
					+ event.getGeoId() + "\", geoLongitude:" + event.getGeoLongitude() + "\", geoLatitude:"
					+ event.getGeoLatitude() + "\", time:" + sdf.format(event.getTime()) + "\", capacity:"
					+ event.getCapacity() + "\"");
		}
	}

	private static void queryUserInfo() {
		Query query = session.createQuery("from UserInfo");
		List<UserInfo> list = query.list();
		java.util.Iterator<UserInfo> iter = list.iterator();
		while (iter.hasNext()) {
			UserInfo person = iter.next();
			System.out.println("user ID: \"" + person.getId() + "\", userName: \"" + person.getUserName()
					+ "\", name: \"" + person.getName() + "\"");
		}
	}

	public static void createUserInfo() {
		UserInfo user = new UserInfo("BarakO", "Barak Obamba");
		session.save(user);
	}

}
