package evacuatzia_proj.components;

public class LocationBasedItem {
	private final Long eventID;
	private final String title;
	private final Geometry location;

	public LocationBasedItem(Long eventID, String title, Geometry location) {
		super();
		this.eventID = eventID;
		this.title = title;
		this.location = location;
	}

	public Long getEventID() {
		return eventID;
	}

	public String getTitle() {
		return title;
	}

	public Geometry getLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationBasedItem other = (LocationBasedItem) obj;
		if (eventID == null) {
			if (other.eventID != null)
				return false;
		} else if (!eventID.equals(other.eventID))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
}
