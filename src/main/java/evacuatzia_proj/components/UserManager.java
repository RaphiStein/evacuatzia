package evacuatzia_proj.components;

import java.util.Date;
import java.util.List;

public class UserManager {

	public static void register(User user){
		
	}
	public static boolean login(String username, String password){
		return false;
	}
	public static List<User> getAllUsers(){
		return null;
	}
	
	public static User getUserByUsername(String username) {
		// TODO: implement
		return null;
	}
	
	public static Report createNewReport(User user, String title, Geometry location, Date reportTime) {
		// TODO: implement
		return null;
	}
	
	public static Event registerToEvent(User user, Event event) {
		// TODO: implement
		return null;
	}
	
	public static void unregisterFromEvent(User user) {
		// TODO: implement
	}
	
	public static Event getEventByUser(User user) {
		// TODO: implement
		return null;
	}
	
	public static List<Report> getReportsByUser(User user) {
		// TODO: implement
		return null;
	}
}
