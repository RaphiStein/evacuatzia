package evacuatzia_proj.components;

import java.util.Date;
import java.util.List;

/** Class for the Administrator of the website. Implemented in the Singleton to allow for only one administrator.
 * 
 * @author Raphi Stein
 *
 */
public enum Administrator {
	INSTANCE;
	
	/** Singleton object for Administrator */
//	private static Administrator admin;
	

//	private Administrator(){}

//	public static Administrator getAdmin(){
//		if (admin == null){
//			admin = new Administrator();
//		}
//		return admin;
//	}	
	
	public Event createEvent(String title, Geometry location, Date estimatedTime, String meansOfEvacuation, int capacity ){
		// TODO: implement this
		return null;
	}
	public void deleteEvent(Event event){
		//TODO Remove all users from this event
	}
	public Event addUserToEvent(Event event, User user){
		//TODO implement
		return null;
	}
	public List<Event> getAllEvents(){
		// TODO implement
		return null;
	}
	
	

}
