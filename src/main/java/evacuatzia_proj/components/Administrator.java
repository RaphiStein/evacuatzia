package evacuatzia_proj.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import evacuatzia_proj.exceptions.EvacuatziaException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.EvacuationEvent;

/**
 * Class for the Administrator of the website. Implemented in the Singleton to
 * allow for only one administrator.
 * 
 */
public enum Administrator {
	INSTANCE;

	private static final SessionFactory sf = SessionFactoryUtil.getSessionFactory();

	public Event createEvent(Geometry location, Date estimatedTime, String meansOfEvacuation, int capacity) {
		EvacuationEvent dbEvent = new EvacuationEvent(location.getLongitude(), location.getLatitude(), estimatedTime, meansOfEvacuation, capacity);
		Long id;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			id = (Long) s.save(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return EventManager.getApiEventById(id);
	}

	public void deleteEvent(Event event) {
		if (null == event) {
			throw new EvacuatziaException("event must not be null");
		}
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			EvacuationEvent dbEvent = EventManager.getDbEventByApiEvent(event, s);
			if (null != dbEvent) {
				s.delete(dbEvent);
			}
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
	}

	public Event addUserToEvent(User user, Event event) {
		return EventManager.registerToEvent(user, event);
	}

	public List<Event> getAllEvents() {
		List<Event> eventsList;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(EvacuationEvent.class);
			List<EvacuationEvent> dbEventsList = cr.list();
			eventsList = createListOfEventsFromListOfDbEvents(dbEventsList);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return eventsList;
	}
	
	private List<Event> createListOfEventsFromListOfDbEvents(List<EvacuationEvent> dbEvents) {
		List<Event> eventsList = new ArrayList<>();
		for (EvacuationEvent e: dbEvents) {
			eventsList.add(EventManager.createEventOutOfDbEvent(e));
		}
		return eventsList;
	}
}
