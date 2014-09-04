package evacuatzia_proj;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;

public class ApiReportTest {

	@Ignore
	@Test
	public void gettingAllReportsInARectangle() {
		User u = UserManager.register("hello", "nopass", "bla");
		u.createReport("report1", new Geometry(1D, 1D, 1D), new Date());
		u.createReport("report1", new Geometry(4D, 4D, 1D), new Date());
		u.createReport("report1", new Geometry(25D, 25D, 1D), new Date());
		ReportManager.getAllReportsRectangle(0D,0D,20D,20D);
	}

}
