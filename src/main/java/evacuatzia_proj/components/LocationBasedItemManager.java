package evacuatzia_proj.components;

import java.util.Date;
import java.util.List;

public abstract class LocationBasedItemManager {

//	public static List<LocationBasedItem> getById(int id){
//		return null;
//	}
	
	public static List<LocationBasedItem> getIntersectingWith(Geometry geo) {
		// TODO: see how searching for geo-location in the database can be done and then edit this mechanism
		//			might need to add a protected abstract function that will be used here to separate between Reports and Events
		return null;
	}
	
	public static List<LocationBasedItem> getByDate(Date start, Date end) {
		// TODO: similar to the above function
		return null;
	}
}
