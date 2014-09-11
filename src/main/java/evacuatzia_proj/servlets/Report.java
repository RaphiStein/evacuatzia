package evacuatzia_proj.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.ReportManager;

/**
 * Servlet implementation class Report
 */
public class Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		evacuatzia_proj.utils.DebugUtils.servletWorkingPrintout(getServletName(), "doGet");

		//Find report id in the path by pattern matching
		String pathInfo = request.getPathInfo();
		Pattern urlPattern = Pattern.compile("^/(\\d+)$"); // starts with slash, then anything other than another slash
		Matcher matcher = urlPattern.matcher(pathInfo);
		if (matcher.matches()) {
			String reportIdStr = matcher.group(1);
			Long reportIdLong = Long.parseLong(reportIdStr);
			System.out.println("ReportIDStr: " + reportIdStr);
			System.out.println("Report ID from uri: " + reportIdStr);
			evacuatzia_proj.components.Report report = ReportManager.getReportById(reportIdLong);
			request.setAttribute("report", report);
			request.getRequestDispatcher("/resources/jsp/report_view.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
		}
	}

	private evacuatzia_proj.components.User generateFakeUser1() {
		return new evacuatzia_proj.components.User("raphis", "Raphi Stein", 1L);
	}

	private evacuatzia_proj.components.User generateFakeUser2() {
		return new evacuatzia_proj.components.User("giladb", "Gilad Bretter", 2L);
	}
	
	private evacuatzia_proj.components.Report generateFakeReport(evacuatzia_proj.components.User user) {
		return new evacuatzia_proj.components.Report(new Long(0001), "Report 1", "content", new Geometry(29.0, 49.0), new Date(), user);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
