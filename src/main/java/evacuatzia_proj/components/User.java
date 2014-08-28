package evacuatzia_proj.components;

import java.util.Date;

public class User {
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
	
	public boolean deleteReport(Report report) {
		return ReportManager.removeReport(this, report);
	}
}
