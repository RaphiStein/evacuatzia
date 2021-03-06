package evacuatzia_proj.components;

import java.io.Serializable;

/** Bean that stores and provides data about points
 * 
 * @author Raphi Stein
 *
 */
public class Geometry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5658227831728369278L;
	private final Double longitude;
	private final Double latitude;
//	private final Double radius;
	
	public Geometry(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
//		this.radius = radius;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}
//
//	public double getRadius() {
//		return radius;
//	}
	
	

	@Override
	public String toString() {
		return "Geometry [longitude=" + longitude + ", latitude=" + latitude + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geometry other = (Geometry) obj;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}
	
	
	
	
}
