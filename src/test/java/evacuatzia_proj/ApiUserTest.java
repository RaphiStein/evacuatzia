package evacuatzia_proj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import evacuatzia_proj.common.TestUtils;
import evacuatzia_proj.components.Administrator;
import evacuatzia_proj.components.Event;
import evacuatzia_proj.components.EventManager;
import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.exceptions.MissingInDatabaseException;
import evacuatzia_proj.exceptions.missingParam.PasswordException;
import evacuatzia_proj.exceptions.missingParam.UsernameException;

public class ApiUserTest {
	
	@Before
	public void setup() {
		TestUtils.dropAllTables();
	}
	
	@Test
	public void canRegisterNewAccount() {
		UserManager.register("myUsername", "mypass", "myName");
	}
	
	@Test(expected=UsernameException.class)
	public void cantRegisterTwoAccountsWithSameUsername() {
		UserManager.register("myUsername", "mypass1", "myName1");
		UserManager.register("myUsername", "mypass2", "myName2");
	}
	
	@Test(expected=PasswordException.class)
	public void changePasswordSupplyingBadPassword() {
		String pass1 = "hello";
		String username = "Raphi";
		String name = "Raphi Stein";
		UserManager.register(username, pass1, name);
		// wrong password
		UserManager.changePassword(username, "mistake", "nevermind");
	}
	
	@Test(expected=UsernameException.class)
	public void changePasswordSupplyingBadUsername() {
		String pass1 = "hello";
		String username = "Gilad";
		String name = "Gilad Bretter";
		UserManager.register(username, pass1, name);
		// bad username
		UserManager.changePassword("mistake", pass1, "nevermind");
	}
	
	@Test
	public void changePasswordWorking() {
		String pass1 = "hello";
		String username = "Donald4";
		String name = "Donald Duck";
		UserManager.register(username, pass1, name);
		String pass2 = "myNewPass";
		UserManager.changePassword(username, pass1, pass2);
		// to check if it worked - we'll try to change it back and see if we succeed.
		UserManager.changePassword(username, pass2, pass1);
	}

	@Test
	public void isUsernameAvailableWorking() {
		String pass1 = "BOOM";
		String username = "Man";
		String name = "Man Super";
		assertTrue(UserManager.isUsernameAvailable(username));
		UserManager.register(username, pass1, name);
		assertFalse(UserManager.isUsernameAvailable(username));
	}
	
	@Test
	public void canGetAllRegisteredAccounts() {
		User user1 = UserManager.register("1", "mypass1", "myName1");
		User user2 = UserManager.register("2", "mypass2", "myName2");
		User user3 = UserManager.register("3", "mypass3", "myName3");
		List<User> usersList = UserManager.getAllUsers();
		assertEquals(3, usersList.size());
		assertTrue(usersList.contains(user1));
		assertTrue(usersList.contains(user2));
		assertTrue(usersList.contains(user3));
	}
	
	@Test
	public void unregisterAccountRemovesAccount() {
		String username = "myUsaName"; 
		User user = UserManager.register(username, "12312", "Usa");
		UserManager.unregisterUser(user);
		assertNull(UserManager.getUserByUsername(user.getUsername()));
	}
	
	@Test(expected=MissingInDatabaseException.class)
	public void unregisterAccountRemovesUsersReports() {
		Report report;
		try { 
		String username = "myUsaName"; 
		User user = UserManager.register(username, "12312", "Usa");
		report = ReportManager.createNewReport(user, "title", new Geometry(1.0, 2.0, 3.0), new Date());
		UserManager.unregisterUser(user);
		} catch (RuntimeException e) {
			fail("unexpected exception thrown: " + e.getMessage());
			return; // only to make static analysis succeed...
		}
		ReportManager.editReport(report, "bla", new Geometry(2.0, 4.4, 324.0), new Date());
	}
	
	@Test
	public void unregisterAccountRemovesEventRegistration() {
		Event event;
		try { 
		String username = "myUsaName"; 
		User user = UserManager.register(username, "12312", "Usa");
		event = Administrator.INSTANCE.createEvent("title", new Geometry(1.0, 2.0, 3.0), new Date(), "boat", 5);
		EventManager.registerToEvent(user, event);
		// Sanity check:
		assertEquals(1, EventManager.getRegisteredUsers(event).size());
		UserManager.unregisterUser(user);
		} catch (RuntimeException e) {
			fail("unexpected exception thrown: " + e.getMessage());
			return; // only to make static analysis succeed...
		}
		assertEquals(0, EventManager.getRegisteredUsers(event).size());
	}
	
	@Test
	public void loginReturnValueIsCorrect() {
		String username = "hello";
		String realPass = "myPass";
		String wrongPass = "wrongPass";
		UserManager.register("hello", realPass, "name");
		assertTrue(UserManager.login(username, realPass));
		assertFalse(UserManager.login(username, wrongPass));
	}
	
	@Test
	public void getAllUsersReturnsAllUsers() {
		User user1 = UserManager.register("1", "ma", "name");
		User user2 = UserManager.register("2", "ma", "name");
		User user3 = UserManager.register("3", "ma", "name");
		List<User> returnedUsers = UserManager.getAllUsers();
		assertEquals(3, returnedUsers.size());
		assertTrue(returnedUsers.contains(user1));
		assertTrue(returnedUsers.contains(user2));
		assertTrue(returnedUsers.contains(user3));
	}
	
	@Test
	public void getUserByUserNameReturnsCorrectUser() {
		String userName1 = "maName";
		User user1 = UserManager.register(userName1, "ma", "name");
		User user2 = UserManager.register("stam", "ma", "name");
		User returnedUser = UserManager.getUserByUsername(userName1);
		assertEquals(user1, returnedUser);
	}
	
	@Test
	public void getUsersByNameReturnsCorrectUsers() {
		String name = "bla";
		User user1 = UserManager.register("1", "ma", name);
		User user2 = UserManager.register("2", "nish", name);
		User user3 = UserManager.register("3", "ma", "name");
		List<User> returnedUsers = UserManager.getUsersByName(name);
		assertEquals(2, returnedUsers.size());
		assertTrue(returnedUsers.contains(user1));
		assertTrue(returnedUsers.contains(user2));
		assertFalse(returnedUsers.contains(user3));
	}
}
