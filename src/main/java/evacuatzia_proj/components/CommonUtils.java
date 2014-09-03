package evacuatzia_proj.components;

import java.util.Date;

import evacuatzia_proj.exceptions.missingParam.DateException;
import evacuatzia_proj.exceptions.missingParam.GeometryException;
import evacuatzia_proj.exceptions.missingParam.NameException;
import evacuatzia_proj.exceptions.missingParam.PasswordException;
import evacuatzia_proj.exceptions.missingParam.TitleException;
import evacuatzia_proj.exceptions.missingParam.UsernameException;

public class CommonUtils {
	public static void validateNameSupplied(String name) {
		if (!stringSupplied(name))
			throw new NameException("Must supply a pasword");
	}

	public static void validatePasswordSupplied(String password) {
		if (!stringSupplied(password))
			throw new PasswordException("Must supply a pasword");
	}

	public static void validateUsernameSupplied(String username) {
		if (!stringSupplied(username))
			throw new UsernameException("Must supply a username");
	}
	
	public  static void validateTitleSupplied(String title) {
		if (!stringSupplied(title))
			throw new TitleException("Must supply a title");
	}
	
	public  static void validateGeometrySupplied(Geometry geo) {
		if (null == geo)
			throw new GeometryException("Must supply proper geografic information");
	}

	public static void validateDateSupplied(Date date) {
		if (null == date) {
			throw new DateException("Must supply a date");
		}
	}
	
	public static void validateMeansOfEvac(String meansOfEvacuation) {
		if (!stringSupplied(meansOfEvacuation)) {
			throw new DateException("Must supply means of evactuation");
		}
	}
	
	public static boolean stringSupplied(String s) {
		if (null == s || s.equals("")) {
			return false;
		}
		return true;
	}


}
