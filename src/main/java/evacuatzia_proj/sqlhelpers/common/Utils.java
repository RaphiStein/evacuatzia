package evacuatzia_proj.sqlhelpers.common;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import evacuatzia_proj.components.Geometry;

public class Utils {
	public static Point getPointFromDecimalValues(Double geoLongitude, Double geoLatitude) {
		Coordinate coor = new Coordinate(geoLongitude, geoLatitude);
		GeometryFactory gf = new GeometryFactory();
		return gf.createPoint(coor);
	}
	
	public static Geometry createOurGeometryFromJts(com.vividsolutions.jts.geom.Geometry loc) {
		Coordinate coor = loc.getCoordinate();
		Double longitude = coor.x;
		Double latitude = coor.y;
		Geometry geom = new Geometry(longitude, latitude);
		return geom;
	}
}
