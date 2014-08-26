package evacuatzia_proj.components;

import java.util.List;

/** Class for the Administrator of the website. Implemented in the Singleton to allow for only one administrator.
 * 
 * @author Raphi Stein
 *
 */
public class Administrator {
	/** Singleton object for Administrator */
	private static Administrator admin;
	

	private Administrator(){}

	public static Administrator getAdmin(){
		if (admin == null){
			admin = new Administrator();
		}
		return admin;
	}	
	
	public void createEvent(Event event){
		
	}
	public void deleteEvent(int id){
		//TODO Remove all users from this event
	}
	public void addUserToEvent(int eventID, int userID){
		
	}
	public List<Event> getAllEvents(){
		return null;
	}
	
	

}
