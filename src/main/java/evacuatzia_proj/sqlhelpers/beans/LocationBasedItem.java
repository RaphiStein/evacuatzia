package evacuatzia_proj.sqlhelpers.beans;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import evacuatzia_proj.sqlhelpers.common.Utils;

public class LocationBasedItem {
	private String title;
	private Date time;
	@Type(type="org.hibernate.spatial.GeometryType")
	private Geometry location;
	private Double radius;

	public LocationBasedItem() {
		super();
	}

	public LocationBasedItem(String title, Double geoLongitude, Double geoLatitude, Double radius, Date time) {
		super();
		this.title = title;
		this.time = time;
		location = Utils.getPointFromDecimalValues(geoLongitude, geoLatitude);
		this.radius = radius;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	// Setting up a specific DB date type
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Geometry getLocation() {
		return location;
	}

	public void setLocation(Geometry location) {
		this.location = location;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}
}
