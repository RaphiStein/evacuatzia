package evacuatzia_proj.components.helpers;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

public class CompUtils {
	public static Polygon createRectangleFromTwoPoints(OurPoint p1, OurPoint p2) {
		OurPoint viaPoint1 = new OurPoint(p1.getLongitude(), p2.getLatitude());
		OurPoint viaPoint2 = new OurPoint(p2.getLongitude(), p1.getLatitude());
		List<OurPoint> pointsList = new ArrayList<>();
		pointsList.add(p1);
		pointsList.add(viaPoint1);
		pointsList.add(p2);
		pointsList.add(viaPoint2);
		return createPolygonFromOurPointList(pointsList);
	}
	
	public static Polygon createPolygonFromOurPointList(List<OurPoint> points) {
		if (points.size() < 3) {
			// can't create polygon
			return null;
		}
		// create polygon
		Coordinate[] coors = new Coordinate[points.size()+1];
		int i = 0;
		for (OurPoint p: points) {
			coors[i] = new Coordinate(p.getLongitude(), p.getLatitude());
			++i;
		}
		// need to close the polygon
		OurPoint p = points.get(0);
		coors[i] = new Coordinate(p.getLongitude(), p.getLatitude());
		
		GeometryFactory fact = new GeometryFactory();
		LinearRing linear = new GeometryFactory().createLinearRing(coors);
		return new Polygon(linear, null, fact);
	}
}
