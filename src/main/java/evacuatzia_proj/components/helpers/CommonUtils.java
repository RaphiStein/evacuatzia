package evacuatzia_proj.components.helpers;

import java.util.Date;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.exceptions.EventCapacityExcpetion;
import evacuatzia_proj.exceptions.TextTooLongException;
import evacuatzia_proj.exceptions.missingParam.DateException;
import evacuatzia_proj.exceptions.missingParam.GeometryException;
import evacuatzia_proj.exceptions.missingParam.NameException;
import evacuatzia_proj.exceptions.missingParam.PasswordException;
import evacuatzia_proj.exceptions.missingParam.TitleException;
import evacuatzia_proj.exceptions.missingParam.UsernameException;
import evacuatzia_proj.sqlhelpers.beans.EvacuationEvent;
import evacuatzia_proj.sqlhelpers.beans.Report;

public class CommonUtils {
	public static void validateNameSupplied(String name) {
		if (!stringSupplied(name))
			throw new NameException("Must supply a name");
	}

	public static void validatePasswordSupplied(String password) {
		if (!stringSupplied(password))
			throw new PasswordException("Must supply a pasword");
	}

	public static void validateUsernameSupplied(String username) {
		if (!stringSupplied(username))
			throw new UsernameException("Must supply a username");
	}
	
	public  static void validateReportTitleSupplied(String title) {
		if (!stringSupplied(title))
			throw new TitleException("Must supply a title");
		if (title.length() > Report.TITLE_TEXT_LENGTH)
			throw new TextTooLongException(generateTooLongErrMsg("Report", "title", Report.TITLE_TEXT_LENGTH));
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
		if (meansOfEvacuation.length() > EvacuationEvent.MEANS_TEXT_LENGTH) {
			throw new TextTooLongException(generateTooLongErrMsg("Event", "means of evacuation", EvacuationEvent.MEANS_TEXT_LENGTH));
		}
	}
	
	public static boolean stringSupplied(String s) {
		if (null == s || s.equals("")) {
			return false;
		}
		return true;
	}

	public static void validateEventCapacity(int capacity) {
		if (capacity < 0) {
			throw new EventCapacityExcpetion("Can't have negative capacity.");
		}
	}

	public static void validateReportContent(String content) {
		if (null != content && content.length() > Report.CONTENT_TEXT_LENGTH)
			throw new TextTooLongException(generateTooLongErrMsg("Report", "content", Report.CONTENT_TEXT_LENGTH));
	}

	private static String generateTooLongErrMsg(String objectName, String fieldName, int maxSize) {
		return objectName  + "'s " + fieldName + " supplied is too long. Please keep it within "  + Integer.toString(maxSize) + " characters.";
	}
}
