package evacuatzia_proj.components;

import java.io.Serializable;

public class LocationBasedItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5378174757991626352L;
	private final Long eventID;
	private final Geometry location;

	public LocationBasedItem(Long eventID, Geometry location) {
		super();
		this.eventID = eventID;
		this.location = location;
	}

	public Long getEventID() {
		return eventID;
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
		return true;
	}
	
}
