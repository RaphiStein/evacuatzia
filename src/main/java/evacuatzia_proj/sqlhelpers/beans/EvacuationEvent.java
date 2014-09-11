package evacuatzia_proj.sqlhelpers.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

import evacuatzia_proj.sqlhelpers.common.Utils;

@Entity
// This will be a table in the DB
@Table(name = "evac_event")
public class EvacuationEvent {
	public static final int MEANS_TEXT_LENGTH = 500;
	private Long id;
	private Integer version;
	private Integer capacity;
	private String means;
	private Integer registrationCount;
	private Set<UserInfo> registeredUsers = new HashSet<UserInfo>();
	private Date time;
	// private Double radius;
	private Geometry location;

	public EvacuationEvent() {
		super();
	}

	public EvacuationEvent(Double geoLongitude, Double geoLatitude, Date time, String meansOfEvac, Integer capacity) {
		super();
		this.capacity = capacity;
		registrationCount = new Integer(0);
		this.means = meansOfEvac;
		this.time = time;
		location = Utils.getPointFromDecimalValues(geoLongitude, geoLatitude);
		// this.radius = radius;
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

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(nullable = false, length = MEANS_TEXT_LENGTH)
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

	public Integer getRegistrationCount() {
		return registrationCount;
	}

	public void setRegistrationCount(Integer registrationCount) {
		this.registrationCount = registrationCount;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "evac_registration", joinColumns = @JoinColumn(name = "Event_ID"), inverseJoinColumns = @JoinColumn(name = "User_ID"))
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
		++registrationCount;
		registeredUsers.add(user);
	}

	public void removeUser(UserInfo user) {
		if (registeredUsers.remove(user)) {
			// if user was really listed to event, decrease registration count.
			--registrationCount;
		}
	}

	@Temporal(TemporalType.TIMESTAMP)
	// Setting up a specific DB date type
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Type(type = "org.hibernate.spatial.GeometryType")
	public Geometry getLocation() {
		return location;
	}

	public void setLocation(Geometry location) {
		this.location = location;
	}

	//
	// public Double getRadius() {
	// return radius;
	// }
	//
	// public void setRadius(Double radius) {
	// this.radius = radius;
	// }

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
