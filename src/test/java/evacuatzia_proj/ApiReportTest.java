package evacuatzia_proj;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.components.helpers.OurPoint;
import evacuatzia_proj.utils.ClearDatabase;

public class ApiReportTest {

	@Before
	public void setup() {
		ClearDatabase.dropAllTables();
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

	@Test
	public void partialMatchInReportTitleWorks() {
		User u = UserManager.register("hello", "nopass", "bla");
		u.createReport("some report of mine", "content", new Geometry(1D, 1D), new Date()); // has port
		u.createReport("my name is mr rapoport!", "content", new Geometry(4D, 4D), new Date()); // has port
		u.createReport("hello to you", "content", new Geometry(25D, 25D), new Date());
		u.createReport("adfasdfawefar", "content", new Geometry(25D, 25D), new Date());
		u.createReport("argaergerportaefaef", "content", new Geometry(25D, 25D), new Date()); // has port
		List<Report> matchingReports = ReportManager.getReportsByPartialTitle("port");
		assertEquals(3, matchingReports.size());
	}
}
