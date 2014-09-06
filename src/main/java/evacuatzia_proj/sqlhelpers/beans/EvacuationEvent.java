package evacuatzia_proj.sqlhelpers.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

import evacuatzia_proj.sqlhelpers.common.Utils;

@Entity
// This will be a table in the DB
@Table(name = "evac_event")
public class EvacuationEvent { 
	Long id;
	Integer capacity;
	String means;
	Set<UserInfo> registeredUsers = new HashSet<UserInfo>();
	private String title;
	private Date time;
	private Double radius;
	private Geometry location;
	
	public EvacuationEvent() {
		super();
	}

	public EvacuationEvent(String title, Double geoLongitude, Double geoLatitude, Double radius, Date time,
			String meansOfEvac, Integer capacity) {
		super();
		this.capacity = capacity;
		this.means = meansOfEvac;
		this.title = title;
		this.time = time;
		location = Utils.getPointFromDecimalValues(geoLongitude, geoLatitude);
		this.radius = radius;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMeans() {
		return means;
	}

	public void setMeans(String means) {
		this.means = means;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name = "evac_registration",
	joinColumns = @JoinColumn(name="Event_ID"),
	inverseJoinColumns = @JoinColumn(name="User_ID"))
	public Set<UserInfo> getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(Set<UserInfo> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

	public int availablePlaces() {
		return capacity - registeredUsers.size();
	}
	
	public void registerUser(UserInfo user) {
		// TODO: change the assert to check and throw statement
		assert(registeredUsers.size() < capacity);
		registeredUsers.add(user);
	}

	public void removeUser(UserInfo user) {
		registeredUsers.remove(user);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	// Setting up a specific DB date type
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Type(type="org.hibernate.spatial.GeometryType")
	public Geometry getLocation() {
		return location;
	}

	public void setLocation(Geometry location) {
		this.location = location;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EvacuationEvent other = (EvacuationEvent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
