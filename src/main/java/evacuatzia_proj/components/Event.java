package evacuatzia_proj.components;

import java.util.Date;
import java.util.List;

public class Event extends LocationBasedItem {

	private final Date estimatedTime;
	private final String meansOfEvacuation;
	private final int capacity;
	private final int registrationCount;
	
	public Event(Long eventID, String title, Geometry location,
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

	public String getMeansOfEvacuation() {
		return meansOfEvacuation;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getRegistrationCount() {
		return registrationCount;
	}

	public List<User> getRegisteredUsers() {
		return EventManager.getRegisteredUsers(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + capacity;
		result = prime * result + ((estimatedTime == null) ? 0 : estimatedTime.hashCode());
		result = prime * result + ((meansOfEvacuation == null) ? 0 : meansOfEvacuation.hashCode());
		result = prime * result + registrationCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (capacity != other.capacity)
			return false;
		if (estimatedTime == null) {
			if (other.estimatedTime != null)
				return false;
		} else if (!estimatedTime.equals(other.estimatedTime))
			return false;
		if (meansOfEvacuation == null) {
			if (other.meansOfEvacuation != null)
				return false;
		} else if (!meansOfEvacuation.equals(other.meansOfEvacuation))
			return false;
		if (registrationCount != other.registrationCount)
			return false;
		return true;
	}
}
