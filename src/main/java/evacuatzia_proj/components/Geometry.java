package evacuatzia_proj.components;

/** Bean that stores and provides data about points
 * 
 * @author Raphi Stein
 *
 */
public class Geometry {
	private final double longitude;
	private final double latitude;
	private final double radius;
	
	public Geometry(double longitude, double latitude, double radius) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.radius = radius;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getRadius() {
		return radius;
	}
	
	
	
	
}
