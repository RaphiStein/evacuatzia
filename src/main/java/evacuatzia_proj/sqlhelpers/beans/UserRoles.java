package evacuatzia_proj.sqlhelpers.beans;

// Generated Aug 24, 2014 8:25:09 PM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserRoles generated by hbm2java
 */
@Entity
@Table(name = "user_roles")
public class UserRoles {
	public static final int ROLE_TEXT_LENGTH = 15;
	private UserRolesId id;
	private LoginAccounts loginAccounts;

	public UserRoles() {
	}

	public UserRoles(UserRolesId id, LoginAccounts loginAccounts) {
		this.id = id;
		this.loginAccounts = loginAccounts;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userName", column = @Column(name = "userName", nullable = false, length = LoginAccounts.USERNAME_TEXT_LENGTH)),
			@AttributeOverride(name = "roleName", column = @Column(name = "roleName", nullable = false, length = ROLE_TEXT_LENGTH)) })
	public UserRolesId getId() {
		return this.id;
	}

	public void setId(UserRolesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userName", nullable = false, insertable = false, updatable = false)
	public LoginAccounts getLoginAccounts() {
		return this.loginAccounts;
	}

	public void setLoginAccounts(LoginAccounts loginAccounts) {
		this.loginAccounts = loginAccounts;
	}

	// For debugging
	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loginAccounts == null) ? 0 : loginAccounts.hashCode());
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
		UserRoles other = (UserRoles) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loginAccounts == null) {
			if (other.loginAccounts != null)
				return false;
		} else if (!loginAccounts.equals(other.loginAccounts))
			return false;
		return true;
	}

	
}
