package evacuatzia_proj.components;

import java.util.Date;

public class EventManager extends LocationBasedItemManager {
	public static Event editEvent(Event event, String title, Geometry location,
			Date estimatedTime, String meansOfEvacuation, int capacity) {
		/* TODO: use "event" to get required information in order to locate the event in the database
				and update with the new info. return an updated version or Event */
		return null;
	}
	
	public static Event registerToEvent(User user, Event event) {
		// TODO: implement
		return null;
	}
	
	public static Event unregisterFromEvent(User user, Event event) {
		// TODO: implement
		return null;
	}
	
	public static Event getEventByUser(User user) {
		// TODO: implement
		return null;
	}
}
