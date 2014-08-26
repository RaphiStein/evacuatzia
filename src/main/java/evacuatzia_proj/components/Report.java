package evacuatzia_proj.components;

public class Report extends LocationBasedItem {

	private User user;

	public Report(int eventID, String title, Geometry location, User user) {
		super(eventID, title, location);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
