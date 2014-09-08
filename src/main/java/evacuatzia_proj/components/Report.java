package evacuatzia_proj.components;

import java.util.Date;

public class Report extends LocationBasedItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6086686538525757602L;
	private final User user;
	private final Date reportTime;
	private final String title;
	private final String content;
	
	public Report(Long reportID, String title, String content, Geometry location, Date reportTime, User user) {
		super(reportID, location);
		this.user = user;
		this.reportTime = reportTime;
		this.title = title;
		this.content = content;
	}

	public User getUser() {
		return user;
	}
	
	public Date getReportTime() {
		return reportTime;
	}
	
	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
}
