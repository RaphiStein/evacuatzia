package evacuatzia_proj.components;

public class LocationBasedItem {
	private int eventID;
	private String title;
	private Geometry location;
	
	
	public LocationBasedItem(int eventID, String title, Geometry location) {
		super();
		this.eventID = eventID;
		this.title = title;
		this.location = location;
	}
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Geometry getLocation() {
		return location;
	}
	public void setLocation(Geometry location) {
		this.location = location;
	}
	
	
}
