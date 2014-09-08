package evacuatzia_proj.common;

import java.util.Date;

public class TestUtils {
	public static Date createFutureDate() {
		Date now = new Date();
		return new Date(now.getTime()+60000L); // 60 seconds from now
	}
}
