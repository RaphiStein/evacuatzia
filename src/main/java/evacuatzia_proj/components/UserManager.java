package evacuatzia_proj.components;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import evacuatzia_proj.components.helpers.CommonUtils;
import evacuatzia_proj.exceptions.EvacuatziaException;
import evacuatzia_proj.exceptions.missingParam.PasswordException;
import evacuatzia_proj.exceptions.missingParam.UsernameException;
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
		CommonUtils.validateUsernameSupplied(username);
		CommonUtils.validatePasswordSupplied(password);
		CommonUtils.validateNameSupplied(name);
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
		} catch (EvacuatziaException e) {
			t.rollback();
			throw e;
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Couldn't register account. Please try again later");
		} finally {
			s.close();
		}
		return getUserByUsername(username);
	}
	
	public static void changePassword(String username, String prevPassword, String newPassword) {
		CommonUtils.validateUsernameSupplied(username);
		CommonUtils.validatePasswordSupplied(prevPassword);
		CommonUtils.validatePasswordSupplied(newPassword);
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
		} catch (EvacuatziaException e) {
			t.rollback();
			throw e;
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Couldn't change password. Please try again later");
		} finally {
			s.close();
		}
	}

	public static boolean isUsernameAvailable(String username) {
		CommonUtils.validateUsernameSupplied(username);
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			boolean avail = (null == getLoginAccountByUsername(username, s));
			t.commit();
			return avail;
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
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
			UserInfo dbUser = getUserInfoByUsername(username, s);
			ReportManager.removeReportsByDbUser(dbUser, s);
			EventManager.unregisterUserFromFutureEvents(dbUser, s);
			LoginAccounts account = getLoginAccountByUsername(username, s);
			deleteIfNotNull(account, s);
			deleteIfNotNull(dbUser, s);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
	}

	public static boolean login(String username, String password) {
		
		try {
			CommonUtils.validateUsernameSupplied(username);
			CommonUtils.validatePasswordSupplied(password);
		} catch (RuntimeException e) {
			return false;
		}
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			LoginAccounts account = getLoginAccountByUsername(username, s);
			t.commit();
			if (null == account) {
				return false;
			}
			return StringHashingUtils.stringMatchMD5(password, account.getUserPass());
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
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
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return createUserListFromUserInfoList(userInfos);
	}

	public static User getUserByUsername(String username) {
		CommonUtils.validateUsernameSupplied(username);
		UserInfo info;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			info = getUserInfoByUsername(username, s);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw new EvacuatziaException("Error occurred, please try again later.");
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
		CommonUtils.validateUsernameSupplied(name);
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
			throw new EvacuatziaException("Error occurred, please try again later.");
		} finally {
			s.close();
		}
		return createUserListFromUserInfoList(userInfoList);
	}

	// package protected
	static User createApiUserFromDbUser(UserInfo user) {
		return new User(user.getUserName(), user.getName(), user.getId());
	}
	
	static UserInfo getUserInfoByUsername(String username, Session s) {
		Criteria cr = s.createCriteria(UserInfo.class);
		return (UserInfo) findByUsernameUsingCriteria(username, cr);
	}
	
	private static List<User> createUserListFromUserInfoList(List<UserInfo> userInfos) {
		List<User> retUsers = new ArrayList<>();
		for (UserInfo info: userInfos) {
			retUsers.add(new User(info.getUserName(), info.getName(), info.getId()));
		}
		return retUsers;
	}

	private static LoginAccounts getLoginAccountByUsername(String username, Session s) {
		Criteria cr = s.createCriteria(LoginAccounts.class);
		return (LoginAccounts) findByUsernameUsingCriteria(username, cr);
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
