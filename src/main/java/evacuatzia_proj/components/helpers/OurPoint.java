package evacuatzia_proj.components.helpers;

public class OurPoint {
	private final Double longitude;
	private final Double latitude;
	public OurPoint(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
}
