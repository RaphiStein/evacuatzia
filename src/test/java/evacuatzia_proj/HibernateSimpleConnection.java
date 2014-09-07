package evacuatzia_proj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import evacuatzia_proj.sqlhelpers.beans.LoginAccounts;
import evacuatzia_proj.sqlhelpers.beans.Report;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;
import evacuatzia_proj.sqlhelpers.beans.UserRoles;
import evacuatzia_proj.sqlhelpers.beans.UserRolesId;

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
//		dropAllTables();
	}
	
	private void dropAllTables() {
		session = startSessionAndTransaction();
		String[] toClear = new String[]{"EvacuationEvent", "Report", "UserInfo", "UserRoles", "LoginAccounts"};
		for (String type: toClear) {
			hqlTruncate(type);
		}
		session.getTransaction().commit();
	}


	private Session startSessionAndTransaction() {
		Session s = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		return s;
	}

	private void hqlTruncate(String myType){
//	    String hql = String.format("delete from %s",myType);
//	    Query query = s.createQuery(hql);
//	    query.executeUpdate();
		List<Object> objs = queryAllObjectsInDB(myType);
		for (Object obj: objs) {
			session.delete(obj);
		}
	}
	
	@Test
	public void simpleReportCreation() throws ParseException {
		UserInfo user = createNewUser();
		session.save(user);
		
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		
		user = (UserInfo) queryFirstObjectsInDB("UserInfo");
		Report report = createNewReportByUser(user);
		session.save(report);

		session.getTransaction().commit();
		session = startSessionAndTransaction();

		Report returnedReport = (Report) queryFirstObjectsInDB("Report");
		
		assertEquals(report, returnedReport);
		assertEquals(user, returnedReport.getUserReported());
		assertEquals(report.getTitle(), returnedReport.getTitle());
		assertEquals(report.getLocation(), returnedReport.getLocation());
		assertEquals(report.getRadius(), returnedReport.getRadius());
		assertEquals(report.getTime(), returnedReport.getTime());
	}
	
	private Report createNewReportByUser(UserInfo user) throws ParseException {
		Date time = sdf.parse("13.12.2011");
//		Double geoLong = 12.3456789;
//		Double geoLat = 12.3456789;
//		Double radius = 20.324;
		Double geoLong = 10.0;
		Double geoLat = 10.0;
		Double radius = 5.0;
		return new Report(user, "some report title" + uniqueNum++, geoLong, geoLat, radius, time);
	}


	@Test
	public void addUserInfoAndQueryForItSucceeds() {
		UserInfo origUser = createNewUser();
		session.save(origUser);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		UserInfo returnedUser = (UserInfo) queryFirstObjectsInDB("UserInfo");
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
	public void createUser() {
		session.save(createNewUser());
	}
	
	@Test
	public void linkUserToEvent() throws ParseException {
		UserInfo origUser = createNewUser();
		session.save(origUser);
		
		UserInfo returnedUser = (UserInfo) queryFirstObjectsInDB("UserInfo");
		EvacuationEvent evacEvent = createEvacEvent();
//		returnedUser.setEvacEvent(evacEvent);
		evacEvent.registerUser(returnedUser);
//		session.save(returnedUser);
		session.save(evacEvent);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		EvacuationEvent returnedEvacEvent = (EvacuationEvent) queryFirstObjectsInDB("EvacuationEvent");
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
		List<Object> allUsers = queryAllObjectsInDB("UserInfo");
		for (Object u: allUsers) {
			evacEvent.registerUser((UserInfo) u);
		}
		for (int i = 0; i<3; ++i) {
			// create some more users
			UserInfo origUser = createNewUser();
			session.save(origUser);
		}
		session.save(evacEvent);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		EvacuationEvent returnedEvacEvent = (EvacuationEvent) queryFirstObjectsInDB("EvacuationEvent");;
		assertFalse(evacEvent == returnedEvacEvent); // if actually the same event, the test worth nothing.
		Set<UserInfo> registeredUsers = returnedEvacEvent.getRegisteredUsers();
		assertEquals(allUsers.size(), registeredUsers.size());
		for (Object u: allUsers) {
			assertTrue(registeredUsers.contains(u));
		}
	}
	
	@Test
	public void addEventAndQueryForItSucceeds() throws ParseException {
		EvacuationEvent origEvent = createEvacEvent();
		session.save(origEvent);
		EvacuationEvent returnedEvent = (EvacuationEvent) queryFirstObjectsInDB("EvacuationEvent");;
		compareEvents(origEvent, returnedEvent);
	}

	private void compareEvents(EvacuationEvent origEvent, EvacuationEvent returnedEvent) {
		assertEquals(origEvent.getTitle(), returnedEvent.getTitle());
		assertEquals(origEvent.getMeans(), returnedEvent.getMeans());
		assertEquals(origEvent.getTime(), returnedEvent.getTime());
		assertEquals(origEvent.getCapacity(), returnedEvent.getCapacity());
		assertEquals(origEvent.getLocation(), returnedEvent.getLocation());
		assertEquals(origEvent.getRadius(), returnedEvent.getRadius());
	}

	private EvacuationEvent createEvacEvent() throws ParseException {
		String title = "a title";
		String means = "Helicopter";
		Double geoLong = 12.3456789;
		Double geoLat = 12.3456789;
		Double radius = 20.324;
//		Double geoLong = 30.0;
//		Double geoLat = 130.0;
//		Double radius = 10.0;
		Date time = sdf.parse("13.12.2011");
		Integer capacity = 20;
		EvacuationEvent origEvent = new EvacuationEvent(title, geoLong, geoLat, radius, time, means, capacity);
		return origEvent;
	}

	@Test
	public void registerNewAccount() {
		LoginAccounts login = new LoginAccounts("Serious", "Sam1234");
		session.save(login);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		LoginAccounts retLogin = (LoginAccounts) queryFirstObjectsInDB("LoginAccounts");
		compareLoginAccounts(login, retLogin);
	}

	private void compareLoginAccounts(LoginAccounts origAccount, LoginAccounts returnedAccount) {
		assertEquals(origAccount.getUserName(), returnedAccount.getUserName());
		assertEquals(origAccount.getUserPass(), returnedAccount.getUserPass());
	}
	
	@Test
	public void addRoleToOneAccountAndNotTheOther() {
		String un1 = "firstUser";
		String un2 = "secondUser";
		LoginAccounts first = new LoginAccounts(un1, "Sam1234");
		LoginAccounts second = new LoginAccounts(un2, "Sam1234");
		
		String role1 = "mySuperRole";
		UserRoles role = new UserRoles(new UserRolesId(first.getUserName(), role1), first);
		
		session.save(first);
		session.save(role);
		session.save(second);
		session.getTransaction().commit();
		session = startSessionAndTransaction();
		List<Object> loginAccounts = queryAllObjectsInDB("LoginAccounts");
		assertEquals(2, loginAccounts.size());
		for (Object accountObj: loginAccounts) {
			LoginAccounts account = (LoginAccounts) accountObj;
			String accountName = account.getUserName();
			if (accountName.equals(un1)) {
				assertEquals(1, account.getUserRoles().size());
				assertTrue(account.getUserRoles().contains(role));
			} else if (accountName.equals(un2)) {
				assertEquals(0, account.getUserRoles().size());
			} else {
				fail("Found account with an unrecognized name: "+ accountName);
			}
		}
	}
	
	// for debugging
	private void printRoles(Set userRolesId) {
		for(Object tmp: userRolesId) {
			UserRoles role = (UserRoles) tmp;
			System.out.println(role);
		}
	}

	private Object queryFirstObjectsInDB(String className) {
		java.util.Iterator<Object> iter = queryAllObjectsInDB(className).iterator();
		assertTrue(iter.hasNext());
		return iter.next();
	}
	
	
	private List<Object> queryAllObjectsInDB(String className) {
		Query query = session.createQuery("FROM " + className);
		if (query != null) {
			return query.list();
		}
		return null;
	}
	
//	@Test
//	public void tmpTest() {
//		Coordinate coor1 = new Coordinate(20.0, 10.0);
//		Coordinate coor2 = new Coordinate(20.0, 11.0);
//		GeometryFactory gf = new GeometryFactory();
//		Geometry p1 = gf.createPoint(coor1);
//		Geometry p2 = gf.createPoint(coor1);
//		Geometry p3 = gf.createPoint(coor2);
//		assertEquals(p1, p2);
//		assertFalse(p1.equals(p3));
//	}
}
