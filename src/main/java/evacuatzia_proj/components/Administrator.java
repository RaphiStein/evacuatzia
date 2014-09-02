package evacuatzia_proj.components;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.EvacuationEvent;

/**
 * Class for the Administrator of the website. Implemented in the Singleton to
 * allow for only one administrator.
 * 
 * @author Raphi Stein
 * 
 */
public enum Administrator {
	INSTANCE;

	private static final SessionFactory sf = SessionFactoryUtil.getSessionFactory();

	public Event createEvent(String title, Geometry location, Date estimatedTime, String meansOfEvacuation, int capacity) {
		EvacuationEvent dbEvent = new EvacuationEvent(title, location.getLongitude(), location.getLatitude(),
				location.getRadius(), estimatedTime, meansOfEvacuation, capacity);
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			s.save(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return EventManager.getDbEventByAllInfo(title, location, estimatedTime, meansOfEvacuation, capacity);
	}

	public void deleteEvent(Event event) {
		// TODO Remove all users from this event
	}

	public Event addUserToEvent(Event event, User user) {
		// TODO implement
		return null;
	}

	public List<Event> getAllEvents() {
		// TODO implement
		return null;
	}

}
