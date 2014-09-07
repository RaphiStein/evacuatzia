package evacuatzia_proj.components;

import java.util.Date;

public class Report extends LocationBasedItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6086686538525757602L;
	private final User user;
	private final Date reportTime;

	public Report(Long reportID, String title, Geometry location, Date reportTime, User user) {
		super(reportID, title, location);
		this.user = user;
		this.reportTime = reportTime;
	}

	public User getUser() {
		return user;
	}
	
	public Date getReportTime() {
		return reportTime;
	}
	
}
