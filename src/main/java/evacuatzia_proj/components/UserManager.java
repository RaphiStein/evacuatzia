package evacuatzia_proj.components;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import evacuatzia_proj.exceptions.EvacuatziaException;
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
	private static final String sUserRole = "user"; // TODO: take this out to some other class

	public static User register(String username, String password, String name) {
		validateUsernameSupplied(username);
		validatePasswordSupplied(password);
		validateNameSupplied(name);
		String hashedPassword = StringHashingUtils.toMD5(password);
		LoginAccounts loginAccount = new LoginAccounts(username, hashedPassword);
		UserRoles role = new UserRoles(new UserRolesId(username, sUserRole), loginAccount);
		UserInfo userInfo = new UserInfo(username, name);
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			s.save(loginAccount);
			s.save(role);
			s.save(userInfo);
			t.commit();
		} catch (ConstraintViolationException e) {
			t.rollback();
			throw new UsernameException("Username " + username + " already in use.");
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return getUserByUsername(username);
	}
	
	public static void changePassword(String username, String prevPassword, String newPassword) {
		validateUsernameSupplied(username);
		validatePasswordSupplied(prevPassword);
		validatePasswordSupplied(newPassword);
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			// get user account, check previous password and if ok, update it.
			LoginAccounts account = getLoginAccountByUsername(username, s);
			if (null == account) {
				throw new UsernameException("Username not in database");
			}
			if (StringHashingUtils.stringMatchMD5(prevPassword, account.getUserPass())) {
				account.setUserPass(StringHashingUtils.toMD5(newPassword));
				s.update(account);
				t.commit();
			} else {
				throw new PasswordException("Existing password incorrect");
			}
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
	}

	public static boolean isUsernameAvailable(String username) {
		validateUsernameSupplied(username);
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			boolean avail = (null == getLoginAccountByUsername(username, s));
			t.commit();
			return avail;
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
	}

	// We don't check for password here. we assume either it was checked before
	// or the caller this function was called by the admin
	public static void unregisterUser(User user) {
		if (null == user) {
			throw new EvacuatziaException("user must not be null");
		}
		String username = user.getUsername();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			// TODO: make sure reports and event registration are removed
			LoginAccounts account = getLoginAccountByUsername(username, s);
			UserInfo userInfo = getUserInfoByUsername(username, s);
			deleteIfNotNull(account, s);
			deleteIfNotNull(userInfo, s);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
		} finally {
			s.close();
		}
	}

	public static boolean login(String username, String password) {
		validateUsernameSupplied(username);
		validatePasswordSupplied(password);
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			LoginAccounts account = getLoginAccountByUsername(username, s);
			if (null == account) {
				throw new UsernameException("Username not in database");
			}
			t.commit();
			return StringHashingUtils.stringMatchMD5(password, account.getUserPass());
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
	}

	public static void logout(User user) {
		// TODO: do we need it? (maybe logout from tomcat somehow)
		// unless we change our implementation somehow - there is nothing to do here.
	}

	public static List<User> getAllUsers() {
		List<UserInfo> userInfos;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(UserInfo.class);
			userInfos = cr.list();
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return createUserListFromUserInfoList(userInfos);
	}

	public static User getUserByUsername(String username) {
		validateUsernameSupplied(username);
		UserInfo info;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			info = getUserInfoByUsername(username, s);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		// if a user with such username doesn't exists in the database,
		// we won't treat it as error - we'll return null
		if (null == info) {
			return null;
		}
		return createApiUserFromDbUser(info);
	}

	@SuppressWarnings("unchecked")
	public static List<User> getUsersByName(String name) {
		validateUsernameSupplied(name);
		List<UserInfo> userInfoList;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(UserInfo.class);
			cr.add(Restrictions.eq("name", name));
			userInfoList = cr.list();
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return createUserListFromUserInfoList(userInfoList);
	}

	// package protected
	static User createApiUserFromDbUser(UserInfo user) {
		return new User(user.getUserName(), user.getName(), user.getId());
	}
	
	private static List<User> createUserListFromUserInfoList(List<UserInfo> userInfos) {
		List<User> retUsers = new ArrayList<>();
		for (UserInfo info: userInfos) {
			retUsers.add(new User(info.getUserName(), info.getName(), info.getId()));
		}
		return retUsers;
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

	private static LoginAccounts getLoginAccountByUsername(String username, Session s) {
		Criteria cr = s.createCriteria(LoginAccounts.class);
		return (LoginAccounts) findByUsernameUsingCriteria(username, cr);
	}
	
	private static UserInfo getUserInfoByUsername(String username, Session s) {
		Criteria cr = s.createCriteria(UserInfo.class);
		return (UserInfo) findByUsernameUsingCriteria(username, cr);
	}

	private static Object findByUsernameUsingCriteria(String username, Criteria cr) {
		cr.add(Restrictions.eq("userName", username));
		return cr.uniqueResult();
	}
	
	private static void deleteIfNotNull(Object obj, Session s) {
		if (null != obj) {
			s.delete(obj);
		}
	}
}
