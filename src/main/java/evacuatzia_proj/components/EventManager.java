package evacuatzia_proj.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import evacuatzia_proj.components.helpers.CommonUtils;
import evacuatzia_proj.exceptions.EvacuatziaException;
import evacuatzia_proj.exceptions.EventFullException;
import evacuatzia_proj.exceptions.EventTimePassed;
import evacuatzia_proj.exceptions.IllegalEventCapacity;
import evacuatzia_proj.exceptions.MissingInDatabaseException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.EvacuationEvent;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;
import evacuatzia_proj.sqlhelpers.common.Utils;

public class EventManager {
	private static final SessionFactory sf = SessionFactoryUtil.getSessionFactory();

	/*
	 * use "event" to get required information in order to locate the
	 * event in the database and update with the new info. return an updated
	 * version or Event
	 */
	public static Event editEvent(Event event, Geometry location, Date estimatedTime,
			String meansOfEvacuation, int capacity) throws IllegalEventCapacity {
		if (null == event) {
			throw new EvacuatziaException("Event must not be null");
		}
		CommonUtils.validateGeometrySupplied(location);
		CommonUtils.validateDateSupplied(estimatedTime);
		CommonUtils.validateMeansOfEvac(meansOfEvacuation);
		
		Event retEvent;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			EvacuationEvent dbEvent = getDbEventByApiEvent(event, s);
			ensureEventFoundInDb(dbEvent);
			if (dbEvent.getRegistrationCount() > capacity) {
				throw new IllegalEventCapacity("New capacity is lower than the number of registered users");
			}
			dbEvent.setLocation(Utils.getPointFromDecimalValues(location.getLongitude(), location.getLatitude()));
			dbEvent.setTime(estimatedTime);
			dbEvent.setMeans(meansOfEvacuation);
			dbEvent.setCapacity(capacity);
			s.update(dbEvent);
			retEvent = createEventOutOfDbEvent(dbEvent);
			t.commit();
		} catch (EvacuatziaException e) {
			t.rollback();
			throw e;
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return retEvent;
	}
	
	public static Event registerToEvent(User user, Event event) {
		if (null == user) {
			throw new EvacuatziaException("user must not be null");
		} else if (null == event) {
			throw new EvacuatziaException("event must not be null");
		}
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		EvacuationEvent dbEvent;
		try {
			dbEvent = getDbEventByApiEvent(event, s);
			ensureEventFoundInDb(dbEvent);
			if (dbEvent.getTime().before(new Date())) {
				throw new EventTimePassed("Event time passed. Can't register.");
			}
			if (dbEvent.getCapacity() <= dbEvent.getRegistrationCount()) {
				throw new EventFullException("Event maximum capacity reached.");
			}
			UserInfo userInfo = getDbUserByApiUser(user, s);
			if (null == userInfo) {
				throw new MissingInDatabaseException("User was not found in Database. Was account deleted?");
			}
			unregisterUserFromFutureEvents(userInfo, s);
			dbEvent.registerUser(userInfo);
			s.update(dbEvent);
			t.commit();
		} catch (EvacuatziaException e) {
			t.rollback();
			throw e;
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return createEventOutOfDbEvent(dbEvent);
	}

	public static Event unregisterFromEvent(User user, Event event) {
		if (null == user) {
			throw new EvacuatziaException("user must not be null");
		} else if (null == event) {
			throw new EvacuatziaException("event must not be null");
		}
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		EvacuationEvent dbEvent;
		try {
			dbEvent = getDbEventByApiEvent(event, s);
			// if this is null it must mean the event was removed already -
			if (null != dbEvent) {
				UserInfo dbUser = getDbUserByApiUser(user, s);
				if (null != dbUser) {
					// just making sure the user still exists
					dbEvent.removeUser(dbUser);
					s.update(dbEvent);
				}
			}
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return createEventOutOfDbEvent(dbEvent);
	}

	public static Event getEventByUser(User user) {
		if (null == user) {
			throw new EvacuatziaException("user must not be null");
		}
		Event retEvent;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			EvacuationEvent dbEvent = getFutureDbEventByUserId(user.getId(), s);
			if (null == dbEvent) {
				t.commit();
				return null;
			}
			retEvent = createEventOutOfDbEvent(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return retEvent;
	}

	public static List<Event> getAllEvents(){
		List<Event> resEvents;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(EvacuationEvent.class);
			List<EvacuationEvent> dbEventList = cr.list();
			resEvents = createEventOutOfDbEventList(dbEventList);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return resEvents;
	}

	
	public static List<Event> getEventsByLocation(double lat, double lon, int radius){
		return null;
		// TODO see comment about getReportsByLocation. will do the same here tomorrow (sunday).
	}
	public static Event getNearestEvent(double lat, double lon){
		// TODO will try to do it tomorrow (sunday).
		return null;
	}
	public static List<User> getRegisteredUsers(Event event) {
		if (null == event) {
			throw new EvacuatziaException("event must not be null");
		}
		List<User> retUserList = new ArrayList<>();
		Set<UserInfo> dbUsersSet;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			EvacuationEvent dbEvent = getDbEventByApiEvent(event, s);
			ensureEventFoundInDb(dbEvent);
			dbUsersSet = dbEvent.getRegisteredUsers();
			for (UserInfo dbUser : dbUsersSet) {
				retUserList.add(new User(dbUser.getUserName(), dbUser.getName(), dbUser.getId()));
			}
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return retUserList;
	}

	// package protected
	static Event getApiEventById(Long id) {
		Event retEvent;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(EvacuationEvent.class);
			cr.add(Restrictions.eq("id", id));
			EvacuationEvent dbEvent = (EvacuationEvent) cr.uniqueResult();
			ensureEventFoundInDb(dbEvent);
			retEvent = createEventOutOfDbEvent(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return retEvent;
	}
	
	static EvacuationEvent getDbEventByApiEvent(Event event, Session s) {
		Criteria cr = s.createCriteria(EvacuationEvent.class);
		cr.add(Restrictions.eq("id", event.getEventID()));
		return (EvacuationEvent) cr.uniqueResult();
	}

	static Event createEventOutOfDbEvent(EvacuationEvent dbEvent) {
		Geometry geom = Utils.createOurGeometryFromJts(dbEvent.getLocation());
		return new Event(dbEvent.getId(), geom, dbEvent.getTime(), dbEvent.getMeans(),
				dbEvent.getCapacity(), dbEvent.getRegistrationCount());
	}

	static void unregisterUserFromFutureEvents(UserInfo dbUser, Session s) {
		EvacuationEvent dbEvent = getFutureDbEventByUserId(dbUser.getId(), s);
		if (null == dbEvent) {
			return;
		}
		dbEvent.removeUser(dbUser);
		s.update(dbEvent);
	}

	private static List<Event> createEventOutOfDbEventList(List<EvacuationEvent> dbEventList) {
		List<Event> events = new ArrayList<>();
		for(EvacuationEvent dbEvent: dbEventList) {
			events.add(createEventOutOfDbEvent(dbEvent));
		}
		return events;
	}
	
	private static EvacuationEvent getFutureDbEventByUserId(Long userId, Session s) {
		String hql = "select distinct e from EvacuationEvent e " + 
					"join e.registeredUsers u " +
					"where u.id = :userId and e.time > :currentTime";
		Query q = s.createQuery(hql);
		q.setParameter("userId", userId);
		q.setTimestamp("currentTime", new Date());
		EvacuationEvent dbEvent = (EvacuationEvent) q.uniqueResult();
		return dbEvent;
	}
	
	private static UserInfo getDbUserByApiUser(User user, Session s) {
		Criteria cr = s.createCriteria(UserInfo.class);
		cr.add(Restrictions.eq("id", user.getId()));
		UserInfo userInfo = (UserInfo) cr.uniqueResult();
		return userInfo;
	}

	private static void ensureEventFoundInDb(EvacuationEvent dbEvent) {
		if (null == dbEvent) {
			throw new MissingInDatabaseException("Event was not found in Database. Was it Deleted?");
		}
	}

}
