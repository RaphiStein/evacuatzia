package evacuatzia_proj.components;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9197083065908908140L;
	private final String username, name;
	private final Long id;
	
	public User(String username, String name, Long id) {
		super();
		this.username = username;
		this.name = name;
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getId() {
		return id;
	}
	
	public Report createReport(String title, Geometry location, Date reportTime) {
		return ReportManager.createNewReport(this, title, location, reportTime);
	}
	
	public void deleteReport(Report report) {
		ReportManager.removeReport(report);
	}
	
	public List<Report> getReports() {
		return ReportManager.getReportsByUser(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
