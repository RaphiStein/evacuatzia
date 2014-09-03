package evacuatzia_proj;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import evacuatzia_proj.common.TestUtils;
import evacuatzia_proj.components.Administrator;
import evacuatzia_proj.components.Event;
import evacuatzia_proj.components.EventManager;
import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.exceptions.EvacuatziaException;

public class ApiEventTest {
	private Geometry geom = new Geometry(10.0, 20.0, 5.0);
	
	@Before
	public void setup() {
		TestUtils.dropAllTables();
	}

	@Test
	public void canAddUserToEvent() {
		User originalUser = UserManager.register("1", "p", "name");
		Event event = Administrator.INSTANCE.createEvent("title", geom, new Date(), "raft", 3);
		assertEquals(0, event.getRegistrationCount());
		Event retEvent = EventManager.registerToEvent(originalUser, event);
		assertEquals(1, retEvent.getRegistrationCount());
	}
	
	@Test
	public void userIsAddedOnceToEvent() {
		User user = UserManager.register("1", "p", "name");
		Event event = Administrator.INSTANCE.createEvent("title", geom, new Date(), "raft", 3);
		Event ExpectedEvent = EventManager.registerToEvent(user, event);
		Event ResultEvent = EventManager.registerToEvent(user, event);
		assertEquals(ExpectedEvent, ResultEvent);
	}
	
	@Test
	public void getEventByUserWorks() {
		User user = UserManager.register("un", "p", "name");
		Event e = Administrator.INSTANCE.createEvent("title", geom, new Date(), "raft", 3);
		EventManager.registerToEvent(user, e);
		Event returnedEvent = EventManager.getEventByUser(user);
		Event expectedEvent = new Event(e.getEventID(), e.getTitle(), e.getLocation(),e.getEstimatedTime(), e.getMeansOfEvacuation(), e.getCapacity(), 1);
		assertEquals(expectedEvent, returnedEvent);
	}
	
	@Test
	public void canEditEvent() {
		String title1 = "title1";
		Geometry geom1 = new Geometry(10.0, 20.0, 5.0);
		Date date1 = new Date();
		String means1 = "hot air baloon";
		int cap1 = 5;
		String title2 = "title2";
		Geometry geom2 = new Geometry(100.0, 200.0, 50.0);
		Date date2 = new Date(date1.getTime()+10000L);
		String means2 = "snorkling";
		int cap2 = 20;
		Event e = Administrator.INSTANCE.createEvent(title1, geom1, date1, means1, cap1);
		Event actualEvent = EventManager.editEvent(e, title2, geom2, date2, means2, cap2);
		Event expectedEvent = new Event(e.getEventID(), title2, geom2, date2, means2, cap2, 0);
		assertEquals(expectedEvent, actualEvent);
	}
	
	@Test
	public void canGetRegisteredUsersFromEvent() {
		User user1 = UserManager.register("1", "p", "name");
		User user2 = UserManager.register("2", "p", "name");
		User user3 = UserManager.register("3", "p", "name");
		Event event = Administrator.INSTANCE.createEvent("title", geom, new Date(), "raft", 3);
		EventManager.registerToEvent(user1, event);
		EventManager.registerToEvent(user2, event);
		event = EventManager.registerToEvent(user3, event);
		assertEquals(3, event.getRegistrationCount());
		List<User> retUsersList = EventManager.getRegisteredUsers(event);
		assertEquals(3, retUsersList.size());
		assertTrue(retUsersList.contains(user1));
		assertTrue(retUsersList.contains(user2));
		assertTrue(retUsersList.contains(user3));
	}
	
	@Test
	public void canUnregisterFromEvent() {
		User user = UserManager.register("1", "p", "name");
		Event event = Administrator.INSTANCE.createEvent("title", geom, new Date(), "bit kite", 4);
		EventManager.registerToEvent(user, event);
		Event retEvent = EventManager.unregisterFromEvent(user, event);
		assertEquals(0, retEvent.getRegistrationCount());
	}
	
	@Test(expected=EvacuatziaException.class)
	public void cantRegisterUserToDeletedEvent() {
		User user;
		Event event;
		try {
			user = UserManager.register("1", "p", "name");
			event = Administrator.INSTANCE.createEvent("title", geom, new Date(), "swimming", 4);
			Administrator.INSTANCE.deleteEvent(event);
		} catch (RuntimeException e) {
			fail("Failed but not where expected");
			return; // just for static analysis
		}
		EventManager.registerToEvent(user, event);
	}
	
	@Test
	public void deleteEventRemovesItForRegisteredUsers() {
		User user1 = UserManager.register("1", "p", "name");
		User user2 = UserManager.register("2", "p", "name");
		Event event = Administrator.INSTANCE.createEvent("title", geom, new Date(), "swimming", 4);
		EventManager.registerToEvent(user1, event);
		EventManager.registerToEvent(user2, event);
		Administrator.INSTANCE.deleteEvent(event);
		assertNull(EventManager.getEventByUser(user1));
		assertNull(EventManager.getEventByUser(user2));
	}
}
