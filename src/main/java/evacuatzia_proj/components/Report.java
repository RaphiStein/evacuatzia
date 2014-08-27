package evacuatzia_proj.components;

import java.util.Date;

public class Report extends LocationBasedItem {

	private final User user;
	private final Date reportTime;

	public Report(int reportID, String title, Geometry location, Date reportTime, User user) {
		super(reportID, title, location);
		this.user = user;
		this.reportTime = reportTime;
	}

	public User getUser() {
		return user;
	}

//	public void setUser(User user) {
//		this.user = user;
//	}
	
	public Date getReportTime() {
		return reportTime;
	}
}
