package evacuatzia_proj;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.User;

public class MockitoExample {

	@Ignore
	@Test
	public void simpleExample() {
		String title = "some report title";
		String content = "content";
		Geometry loc = new Geometry(-73.65, 45.30);
		Date date = new Date();
		User user = Mockito.mock(User.class);
		Report report = ReportManager.createNewReport(user, title, content, loc, date);
		Mockito.when(user.createReport(title, content, loc, date)).thenReturn(report);
		
		Report example = user.createReport(title, content, loc, date);
		assertTrue(example == report);
	}
}
