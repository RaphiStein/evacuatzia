package evacuatzia_proj.components;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import evacuatzia_proj.exceptions.NameException;
import evacuatzia_proj.exceptions.PasswordException;
import evacuatzia_proj.exceptions.UsernameException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.LoginAccounts;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;
import evacuatzia_proj.sqlhelpers.beans.UserRoles;
import evacuatzia_proj.sqlhelpers.beans.UserRolesId;
import evacuatzia_proj.utils.StringHashingUtils;

public class UserManager {
	private static final SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	private static final String sUserRole = "user";

	public static User register(String username, String password, String name) {
		validateUsernameSupplied(username);
		validatePasswordSupplied(password);
		validateNameSupplied(name);
		String hashedPassword = StringHashingUtils.toMD5(password);
		LoginAccounts loginAccount = new LoginAccounts(username, hashedPassword);
		UserRoles role = new UserRoles(new UserRolesId(username, sUserRole), loginAccount);
		UserInfo userInfo = new UserInfo(username, name);
		Session s = sf.openSession();
		s.beginTransaction();
		try {
			s.save(loginAccount);
			s.save(role);
			s.save(userInfo);
			s.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			s.getTransaction().rollback();
			throw new UsernameException("Username " + username + " already in use.");
		} finally {
			s.close();
		}
		return getUserByUsername(username);
	}

	private static void validateNameSupplied(String name) {
		if (!stringSupplied(name))
			throw new NameException("Must supply a pasword");
	}

	private static void validatePasswordSupplied(String password) {
		if (!stringSupplied(password))
			throw new PasswordException("Must supply a pasword");
	}

	private static void validateUsernameSupplied(String username) {
		if (!stringSupplied(username))
			throw new UsernameException("Must supply a username");
	}

	private static boolean stringSupplied(String s) {
		if (null == s || s.equals("")) {
			return false;
		}
		return true;
	}

	public static void changePassword(String username, String prevPassword, String newPassword) {
		validateUsernameSupplied(username);
		validatePasswordSupplied(prevPassword);
		validatePasswordSupplied(newPassword);
		Session s = sf.openSession();
		s.beginTransaction();
		try {
			// get user account, check prevpassword and if ok, update it.
			Criteria cr = s.createCriteria(LoginAccounts.class);
			cr.add(Restrictions.eq("username", username));
			LoginAccounts account = (LoginAccounts) cr.uniqueResult();
			if (StringHashingUtils.stringMatchMD5(prevPassword, account.getUserPass())) {
				account.setUserPass(StringHashingUtils.toMD5(newPassword));
			} else {
				throw new PasswordException("Existing password incorrect");
			}
			s.getTransaction().commit();
		} catch (HibernateException e ) {
			// TODO: refine this
			System.out.println("GILAD: error in changePassword - got message: " + e.getMessage());
		} finally {
			s.close();
		}
	}

	public static boolean isUsernameAvailable(String username) {
		// TODO implement
		return false;
	}

	public static void unregisterUser(User user) {
		// TODO implement
	}

	public static boolean login(String username, String password) {
		// TODO implement
		return false;
	}

	public static void logout(User user) {
		// TODO implement
	}

	public static List<User> getAllUsers() {
		// TODO implement
		return null;
	}

	public static User getUserByUsername(String username) {
		// TODO: implement
		return null;
	}

	public static List<User> getUsersByName(String name) {
		// TODO: implement
		return null;
	}

}
