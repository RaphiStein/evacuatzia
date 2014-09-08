package evacuatzia_proj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import evacuatzia_proj.components.Geometry;
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
	
	public static Geometry parseGeocode(String geoRaw) throws Exception {
		String[] latLon = geoRaw.split(", ");
		if (latLon.length != 2) throw new Exception(); //if String is not a proper geocode
		double lat = Double.parseDouble(latLon[0]);
		double lon = Double.parseDouble(latLon[1]);
		Geometry geometry = new Geometry(lat, lon);
		return geometry;
	}
}
