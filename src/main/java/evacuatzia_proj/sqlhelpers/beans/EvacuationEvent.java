package evacuatzia_proj.sqlhelpers.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity //This will be a table in the DB
@Table( name = "evac_event" )
public class EvacuationEvent {
	Long id;
	String title;
	Long geoId; // ID in geometric database
	Double geoLongitude; // longitude
	Double geoLatitude;	// latitude
	Date time;
	Integer capacity;
//	Set<UserInfo> registeredUsers = new HashSet<UserInfo>();
	
	public EvacuationEvent() {
		super();
	}
	
	public EvacuationEvent(String title, Long geoId, Double geoLongitude, Double geoLatitude, Date time,
			Integer capacity) {
		super();
		this.title = title;
		this.geoId = geoId;
		this.geoLongitude = geoLongitude;
		this.geoLatitude = geoLatitude;
		this.time = time;
		this.capacity = capacity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getGeoId() {
		return geoId;
	}
	public void setGeoId(Long geoId) {
		this.geoId = geoId;
	}
	public Double getGeoLongitude() {
		return geoLongitude;
	}
	public void setGeoLongitude(Double geoLongitude) {
		this.geoLongitude = geoLongitude;
	}
	public Double getGeoLatitude() {
		return geoLatitude;
	}
	public void setGeoLatitude(Double geoLatitude) {
		this.geoLatitude = geoLatitude;
	}
	@Temporal(TemporalType.TIMESTAMP) //Setting up a specific DB date type
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
//	public void registerUser(UserInfo user) {
//		registeredUsers.add(user);
//	}
//	
//	public void removeUser(UserInfo user) {
//		registeredUsers.remove(user);
//	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
