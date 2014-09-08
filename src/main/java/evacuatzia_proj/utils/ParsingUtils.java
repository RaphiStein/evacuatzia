package evacuatzia_proj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import evacuatzia_proj.exceptions.EvacuatziaException;

public class ParsingUtils {
	public static Date parseJsonDate(String sDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss XXX");
		try {
			return sdf.parse(sDate);
		} catch (ParseException e) {
			throw new EvacuatziaException("Bad date fromat");
		}
	}
}
