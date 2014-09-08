package evacuatzia_proj.sqlhelpers.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

import evacuatzia_proj.sqlhelpers.common.Utils;

@Entity
//This will be a table in the DB
@Table(name = "report")
public class Report {
	private Long id;
	private UserInfo userReported;
	private Double radius;
	private Geometry location;
	private Date time;
	private String title;
	private String content;
	
	public Report(UserInfo userReported, String title, String content, Double geoLongitude, Double geoLatitude, Double radius, Date time) {
		super();
		this.userReported = userReported;
		this.title = title;
		this.content = content;
		this.time = time;
		location = Utils.getPointFromDecimalValues(geoLongitude, geoLatitude);
		this.radius = radius;
	}

	public Report() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="user_reported")
	public UserInfo getUserReported() {
		return userReported;
	}

	public void setUserReported(UserInfo userReported) {
		this.userReported = userReported;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	// Setting up a specific DB date type
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Type(type="org.hibernate.spatial.GeometryType")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Report other = (Report) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
