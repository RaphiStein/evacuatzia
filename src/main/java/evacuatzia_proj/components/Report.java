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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((reportTime == null) ? 0 : reportTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Report other = (Report) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (reportTime == null) {
			if (other.reportTime != null)
				return false;
		} else if (!reportTime.equals(other.reportTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
