package evacuatzia_proj.sqlhelpers.beans;

// Generated Aug 24, 2014 8:25:09 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * LoginAccounts generated by hbm2java
 */
@Entity
@Table(name = "login_accounts")
public class LoginAccounts implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7715423562250531448L;
	private String userName;
	private String userPass;
	private Set<UserRoles> userRoles = new HashSet<UserRoles>();

	public LoginAccounts() {
	}

	public LoginAccounts(String userName, String userPass) {
		this.userName = userName;
		this.userPass = userPass;
	}

	public LoginAccounts(String userName, String userPass, Set<UserRoles> userRoles) {
		this.userName = userName;
		this.userPass = userPass;
		this.userRoles = userRoles;
	}

	@Id
	@Column(name = "userName", unique = true, nullable = false, length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "userPass", nullable = false, length = 32)
	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loginAccounts", cascade = {CascadeType.ALL})
	public Set<UserRoles> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		LoginAccounts other = (LoginAccounts) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	
}
