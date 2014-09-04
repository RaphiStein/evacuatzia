package evacuatzia_proj.sqlhelpers.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
// This will be a table in the DB
@Table(name = "user_info")
public class UserInfo {
	Long id;
	String userName;
	String name;	
//	EvacuationEvent evacEvent;
	
	public UserInfo() {
		super();
	}

	public UserInfo(String userName, String name) {
		super();
		this.userName = userName;
		this.name = name;
//		evacEvent = null;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public EvacuationEvent getEvacEvent() {
//		return evacEvent;
//	}
//
//	public void setEvacEvent(EvacuationEvent evacEvent) {
//		this.evacEvent = evacEvent;
//	}

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
		UserInfo other = (UserInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
