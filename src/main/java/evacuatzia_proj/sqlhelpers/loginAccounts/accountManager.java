package evacuatzia_proj.sqlhelpers.loginAccounts;

public interface accountManager {
	public enum AccountResult {
		SUCCESS, USERNAME_AVAILABLE, USERNAME_ALREADY_IN_USE, PASSWORD_TOO_WEAK, INTERNAL_ERROR; 
	}
	public AccountResult registerNewAccount(String username, String password);
	public AccountResult changeAccountPassword(String username, String password);
	public AccountResult isUsernameAvailable(String username);
}
