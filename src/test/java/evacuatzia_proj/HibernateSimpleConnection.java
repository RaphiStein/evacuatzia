package evacuatzia_proj;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	Session session;
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
	public void addUserInfoAndQueryForItSucceeds() {
		String username = "BarakO";
		String name = "Barak Obamaba";
		session.save(new UserInfo(username, name));
		UserInfo user = queryUserInfo();
		assertEquals(username, user.getUserName());
		assertEquals(name, user.getName());
	}

	@Test
	public void addEventAndQueryForItSucceeds() throws ParseException {
		String title = "a title";
		Long geoID = 999L;
		Double geoLong = 12.3456789;
		Double geoLat = 12.3456789;
		Date time = sdf.parse("13.12.2011");
		Integer capacity = 20;
		EvacuationEvent evac = new EvacuationEvent(title, geoID, geoLong, geoLat, time, capacity);
		session.save(evac);
		EvacuationEvent event = queryEvacuationEvent();
		assertEquals(title, event.getTitle());
		assertEquals(geoID, event.getGeoId());
		assertEquals(geoLong, event.getGeoLongitude());
		assertEquals(geoLat, event.getGeoLatitude());
		assertEquals(time, event.getTime());
		assertEquals(capacity, event.getCapacity());
	}

	private EvacuationEvent queryEvacuationEvent() {
		Query query = session.createQuery("from EvacuationEvent");
		List<EvacuationEvent> list = query.list();
		java.util.Iterator<EvacuationEvent> iter = list.iterator();
		assertTrue(iter.hasNext());
		return iter.next();
//			System.out.println("Event ID: \"" + event.getId() + "\", title:" + event.getTitle() + "\", geoId:"
//					+ event.getGeoId() + "\", geoLongitude:" + event.getGeoLongitude() + "\", geoLatitude:"
//					+ event.getGeoLatitude() + "\", time:" + sdf.format(event.getTime()) + "\", capacity:"
//					+ event.getCapacity() + "\"");
	}

	private UserInfo queryUserInfo() {
		Query query = session.createQuery("from UserInfo");
		List<UserInfo> list = query.list();
		java.util.Iterator<UserInfo> iter = list.iterator();
		assertTrue(iter.hasNext());
		return iter.next();
//      System.out.println("user ID: \"" + person.getId() + "\", userName: \"" + person.getUserName()
//			+ "\", name: \"" + person.getName() + "\"");
	}


}
