package evacuatzia_proj.components;

import java.util.Date;

public class ReportManager extends LocationBasedItemManager {
	public static Report editReport(Report report, String title, Geometry location, Date reportTime) {
		/* TODO: use "report" to get required information in order to locate the report in the database
				and update with the new info. return an updated version or Report */
		return null;
	}
	
	public static Report createNewReport(User user, String title, Geometry location, Date reportTime) {
		// TODO: implement
		return null;
	}
	
	public static boolean removeReport(User user, Report report) {
		// TODO: implement
		return false;		
	}
}
