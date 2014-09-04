package evacuatzia_proj.sqlhelpers.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
// This will be a table in the DB
@Table(name = "evac_event")
public class EvacuationEvent extends LocationBasedItem { 
	Long id;

	Integer capacity;
	String means;
	Set<UserInfo> registeredUsers = new HashSet<UserInfo>();

	public EvacuationEvent() {
		super();
	}

	public EvacuationEvent(String title, Double geoLongitude, Double geoLatitude, Double radius, Date time,
			String meansOfEvac, Integer capacity) {
		super(title, geoLongitude, geoLatitude, radius, time);
		this.capacity = capacity;
		this.means = meansOfEvac;
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
