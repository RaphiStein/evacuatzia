package evacuatzia_proj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.EvacuationEvent;
import evacuatzia_proj.sqlhelpers.beans.Report;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;

public class HibernateSimpleConnection {
	Session session;
	SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
	Integer uniqueNum;
	
	@Before
	public void setup() {
		dropAllTables();
		uniqueNum = 1;
		session = startSessionAndTransaction();
	}
	
	@After
	public void teardown() {
		session.getTransaction().commit();
		dropAllTables();
	}
	
	private void dropAllTables() {
		Session s = startSessionAndTransaction();
		String[] toClear = new String[]{"EvacuationEvent", "Report", "UserInfo"};
		for (String type: toClear) {
			hqlTruncate(type, s);
		}
		s.getTransaction().commit();
	}


	private Session startSessionAndTransaction() {
		Session s = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		return s;
	}

	private int hqlTruncate(String myType, Session s){
	    String hql = String.format("delete from %s",myType);
	    Query query = s.createQuery(hql);
	    return query.executeUpdate();
	}
	
	@Test
	public void simpleReportCreation() {
		UserInfo user = createNewUser();
		session.save(user);
		
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		
		user = queryFirstUserInfo();
		Report report = createNewReportByUser(user);
		session.save(report);

		session.getTransaction().commit();
		session = startSessionAndTransaction();

		Report returnedReport = queryFirstReport();
		
		assertEquals(report, returnedReport);
		assertEquals(user, returnedReport.getUserReported());
	}
	
	private Report queryFirstReport() {
		Query query = session.createQuery("from Report");
		List<Report> list = query.list();
		java.util.Iterator<Report> iter = list.iterator();
		assertTrue(iter.hasNext());
		return iter.next();
	}


	private Report createNewReportByUser(UserInfo user) {
		return new Report(user, "some report title" + uniqueNum++);
	}


	@Test
	public void addUserInfoAndQueryForItSucceeds() {
		UserInfo origUser = createNewUser();
		session.save(origUser);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		UserInfo returnedUser = queryFirstUserInfo();
		assertFalse(origUser == returnedUser); // if original and returned are not actually the same user the test worth nothing.
		compareUsers(origUser, returnedUser);
	}

	private void compareUsers(UserInfo origUser, UserInfo returnedUser) {
		assertEquals(origUser.getUserName(), returnedUser.getUserName());
		assertEquals(origUser.getName(), returnedUser.getName());
//		assertEquals(origUser.getEvacEvent(), returnedUser.getEvacEvent());
	}

	private UserInfo createNewUser() {
		String username = "BarakO" + (uniqueNum++);
		String name = "Barak Obamaba";
		UserInfo origUser = new UserInfo(username, name);
		return origUser;
	}

	@Test
	public void linkUserToEvent() throws ParseException {
		UserInfo origUser = createNewUser();
		session.save(origUser);
		
		UserInfo returnedUser = queryFirstUserInfo();
		EvacuationEvent evacEvent = createEvacEvent();
//		returnedUser.setEvacEvent(evacEvent);
		evacEvent.registerUser(returnedUser);
//		session.save(returnedUser);
		session.save(evacEvent);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		EvacuationEvent returnedEvacEvent = queryEvacuationEvent();
		assertFalse(evacEvent == returnedEvacEvent); // if actually the same event, the test worth nothing.
		Set<UserInfo> registeredUsers = returnedEvacEvent.getRegisteredUsers();
		assertEquals(1, registeredUsers.size());
		for (UserInfo u: registeredUsers) {
			assertEquals(returnedUser, u);
		}
	}
	
	@Test
	public void registerMultipuleUsersToEvent() throws ParseException {
		for (int i = 0; i<10; ++i) {
			UserInfo origUser = createNewUser();
			session.save(origUser);
		}
		EvacuationEvent evacEvent = createEvacEvent();
		List<UserInfo> allUsers = queryAllUsersInfo();
		for (UserInfo u: allUsers) {
			evacEvent.registerUser(u);
		}
		for (int i = 0; i<3; ++i) {
			// create some more users
			UserInfo origUser = createNewUser();
			session.save(origUser);
		}
		session.save(evacEvent);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		EvacuationEvent returnedEvacEvent = queryEvacuationEvent();
		assertFalse(evacEvent == returnedEvacEvent); // if actually the same event, the test worth nothing.
		Set<UserInfo> registeredUsers = returnedEvacEvent.getRegisteredUsers();
		assertEquals(allUsers.size(), registeredUsers.size());
		for (UserInfo u: allUsers) {
			assertTrue(registeredUsers.contains(u));
		}
	}
	
	@Test
	public void addEventAndQueryForItSucceeds() throws ParseException {
		EvacuationEvent origEvent = createEvacEvent();
		session.save(origEvent);
		EvacuationEvent returnedEvent = queryEvacuationEvent();
		compareEvents(origEvent, returnedEvent);
	}

	private void compareEvents(EvacuationEvent origEvent, EvacuationEvent returnedEvent) {
		assertEquals(origEvent.getTitle(), returnedEvent.getTitle());
		assertEquals(origEvent.getGeoId(), returnedEvent.getGeoId());
		assertEquals(origEvent.getGeoLongitude(), returnedEvent.getGeoLongitude());
		assertEquals(origEvent.getGeoLatitude(), returnedEvent.getGeoLatitude());
		assertEquals(origEvent.getTime(), returnedEvent.getTime());
		assertEquals(origEvent.getCapacity(), returnedEvent.getCapacity());
	}

	private EvacuationEvent createEvacEvent() throws ParseException {
		String title = "a title";
		Long geoID = 999L;
		Double geoLong = 12.3456789;
		Double geoLat = 12.3456789;
		Date time = sdf.parse("13.12.2011");
		Integer capacity = 20;
		EvacuationEvent origEvent = new EvacuationEvent(title, geoID, geoLong, geoLat, time, capacity);
		return origEvent;
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

	private UserInfo queryFirstUserInfo() {
		java.util.Iterator<UserInfo> iter = queryAllUsersInfo().iterator();
		assertTrue(iter.hasNext());
		return iter.next();
//      System.out.println("user ID: \"" + person.getId() + "\", userName: \"" + person.getUserName()
//			+ "\", name: \"" + person.getName() + "\"");
	}
	
	private List<UserInfo> queryAllUsersInfo() {
		Query query = session.createQuery("from UserInfo");
		return query.list();
	}


}
