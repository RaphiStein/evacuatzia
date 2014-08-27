package evacuatzia_proj.components;

import java.util.Date;
import java.util.List;

public class Event extends LocationBasedItem {

	private final Date estimatedTime;
	private final String meansOfEvacuation;
	private final int capacity;
	private final int registrationCount;
	
	public Event(int eventID, String title, Geometry location,
			Date estimatedTime, String meansOfEvacuation, int capacity,
			int registrationCount) {
		super(eventID, title, location);
		this.estimatedTime = estimatedTime;
		this.meansOfEvacuation = meansOfEvacuation;
		this.capacity = capacity;
		this.registrationCount = registrationCount;
	}

	public Date getEstimatedTime() {
		return estimatedTime;
	}

//	public void setEstimatedTime(Date estimatedTime) {
//		this.estimatedTime = estimatedTime;
//	}

	public String getMeansOfEvacuation() {
		return meansOfEvacuation;
	}

//	public void setMeansOfEvacuation(String meansOfEvacuation) {
//		this.meansOfEvacuation = meansOfEvacuation;
//	}

	public int getCapacity() {
		return capacity;
	}

//	public void setCapacity(int capacity) {
//		this.capacity = capacity;
//	}

	public int getRegistrationCount() {
		return registrationCount;
	}

//	public void setRegistrationCount(int registrationCount) {
//		this.registrationCount = registrationCount;
//	}
	
	public List<User> getRegisteredUsers() {
		// TODO: implement - returns a list of all users registered to this event
		return null;
	}

}
