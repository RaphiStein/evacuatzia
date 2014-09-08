package evacuatzia_proj;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import evacuatzia_proj.common.TestUtils;
import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.components.helpers.OurPoint;

public class ApiReportTest {

	@Before
	public void setup() {
		TestUtils.dropAllTables();
	}
	
	@Test
	public void gettingAllReportsInARectangle() {
		User u = UserManager.register("hello", "nopass", "bla");
		u.createReport("report1", "content", new Geometry(1D, 1D), new Date());
		u.createReport("report1", "content", new Geometry(4D, 4D), new Date());
		u.createReport("report1", "content", new Geometry(25D, 25D), new Date());
		OurPoint p1 = new OurPoint(0D, 0D);
		OurPoint p2 = new OurPoint(20D, 20D);
		List<Report> matchingReports = ReportManager.getAllReportsInRectangle(p1,p2);
		assertEquals(2, matchingReports.size());
	}

}
