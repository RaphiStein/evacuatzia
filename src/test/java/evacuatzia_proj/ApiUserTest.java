package evacuatzia_proj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import evacuatzia_proj.common.TestUtils;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.exceptions.PasswordException;
import evacuatzia_proj.exceptions.UsernameException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;

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
}
