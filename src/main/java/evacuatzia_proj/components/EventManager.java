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
import org.springframework.validation.annotation.Validated;

import com.vividsolutions.jts.geom.Coordinate;

import evacuatzia_proj.exceptions.EvacuatziaException;
import evacuatzia_proj.exceptions.EventFullException;
import evacuatzia_proj.exceptions.IllegalEventCapacity;
import evacuatzia_proj.exceptions.MissingInDatabaseException;
import evacuatzia_proj.exceptions.missingParam.NameException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.EvacuationEvent;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;
import evacuatzia_proj.sqlhelpers.common.Utils;

public class EventManager extends LocationBasedItemManager {
	private static final SessionFactory sf = SessionFactoryUtil.getSessionFactory();

	/*
	 * use "event" to get required information in order to locate the
	 * event in the database and update with the new info. return an updated
	 * version or Event
	 */
	public static Event editEvent(Event event, String title, Geometry location, Date estimatedTime,
			String meansOfEvacuation, int capacity) throws IllegalEventCapacity {
		if (null == event) {
			throw new EvacuatziaException("user must not be null");
		}
		CommonUtils.validateTitleSupplied(title);
		CommonUtils.validateGeometrySupplied(location);
		CommonUtils.validateDateSupplied(estimatedTime);
		CommonUtils.validateMeansOfEvac(meansOfEvacuation);
		
		Event retEvent;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			EvacuationEvent dbEvent = getDbEventByApiEvent(event, s);
			ensureEventFoundInDb(dbEvent);
			if (dbEvent.getRegisteredUsers().size() > capacity) {
				throw new IllegalEventCapacity("New capacity is lower than the number of registered users");
			}
			dbEvent.setTitle(title);
			dbEvent.setLocation(Utils.getPointFromDecimalValues(location.getLongitude(), location.getLatitude()));
			dbEvent.setRadius(location.getRadius());
			dbEvent.setTime(estimatedTime);
			dbEvent.setMeans(meansOfEvacuation);
			dbEvent.setCapacity(capacity);
			s.update(dbEvent);
			retEvent = createEventOutOfDbEvent(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
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
			if (dbEvent.getCapacity() <= dbEvent.getRegisteredUsers().size()) {
				throw new EventFullException("Event maximum capacity reached.");
			}
			UserInfo userInfo = getDbUserByApiUser(user, s);
			if (null == userInfo) {
				throw new MissingInDatabaseException("User was not found in Database. Was account deleted?");
			}
			unregisterUserFromEvents(userInfo, s);
			dbEvent.registerUser(userInfo);
			s.update(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
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
				if (null != dbUser && dbEvent.getRegisteredUsers().contains(dbUser)) {
					// just making sure the user still exists. and is registered to
					// the event.
					// if the user exists but not registered - no need to update.
					dbEvent.getRegisteredUsers().remove(dbUser);
					s.update(dbEvent);
				}
			}
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
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
			EvacuationEvent dbEvent = getDbEventByUserId(user.getId(), s);
			if (null == dbEvent) {
				t.commit();
				return null;
			}
			retEvent = createEventOutOfDbEvent(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return retEvent;
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
			throw e;
		} finally {
			s.close();
		}
		return retUserList;
	}

	// package protected
	static Event getDbEventByAllInfo(String title, Geometry location, Date time, String meansOfEvacuation, int capacity) {
		Event retEvent;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(EvacuationEvent.class);
			com.vividsolutions.jts.geom.Geometry jtsGeom = Utils.getPointFromDecimalValues(location.getLongitude(),
					location.getLatitude());
			cr.add(Restrictions.eq("title", title));
			cr.add(Restrictions.eq("time", time));
			cr.add(Restrictions.eq("location", jtsGeom));
			cr.add(Restrictions.eq("radius", location.getRadius()));
			cr.add(Restrictions.eq("means", meansOfEvacuation));
			cr.add(Restrictions.eq("capacity", capacity));
			EvacuationEvent dbEvent = (EvacuationEvent) cr.uniqueResult();
			ensureEventFoundInDb(dbEvent);
			retEvent = createEventOutOfDbEvent(dbEvent);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
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
		Geometry geom = Utils.createOurGeometryFromJtsAndRadius(dbEvent.getLocation(), dbEvent.getRadius());
		return new Event(dbEvent.getId(), dbEvent.getTitle(), geom, dbEvent.getTime(), dbEvent.getMeans(),
				dbEvent.getCapacity(), dbEvent.getRegisteredUsers().size());
	}

	static void unregisterUserFromEvents(UserInfo dbUser, Session s) {
		EvacuationEvent dbEvent = getDbEventByUserId(dbUser.getId(), s);
		if (null == dbEvent) {
			return;
		}
		dbEvent.removeUser(dbUser);
		s.update(dbEvent);
	}

	private static EvacuationEvent getDbEventByUserId(Long userId, Session s) {
		String hql = "select distinct e from EvacuationEvent e " + "join e.registeredUsers u " + "where u.id = :userId";
		Query q = s.createQuery(hql);
		q.setParameter("userId", userId);
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
