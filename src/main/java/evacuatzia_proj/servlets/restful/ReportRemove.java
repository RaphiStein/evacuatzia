package evacuatzia_proj.servlets.restful;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Event;
import evacuatzia_proj.components.EventManager;
import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.User;

/**
 * Servlet implementation class ReportRemove
 */
public class ReportRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportRemove() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
evacuatzia_proj.utils.DebugUtils.servletWorkingPrintout(getServletName(), "doGet");

		
		if ((Boolean) request.getSession().getAttribute("isLoggedIn")){
			String pathInfo = request.getPathInfo();
			Pattern urlPattern = Pattern.compile("^/(\\d+)$"); // starts with slash, then anything other than another slash
			Matcher matcher = urlPattern.matcher(pathInfo);
			if (matcher.matches()) {
				String reportIdStr = matcher.group(1);
				Long reportIdLong = Long.parseLong(reportIdStr);
				System.out.println("ReportIDStr: " + reportIdStr);
				
				//check if report belongs to current user
				User user = (User) request.getSession().getAttribute("user");
				Report report = ReportManager.getReportById(reportIdLong);
				System.out.println("Report: " + report);
				List<Report> usersReports = user.getReports();
				if (!usersReports.contains(report)){ // if the report you're deleting does not belong to logged in user (sabotage attempt perhaps)
					request.setAttribute("message", "You cannot delete a report that does not belong to you");
					request.getRequestDispatcher("/evacuatzia/resources/jsp/result.jsp");
					return;
				}
				ReportManager.removeReport(report);
				
				System.out.println(report.getEventID() + " has been removed");
				
				response.sendRedirect("/evacuatzia/user/" + user.getUsername());
			}
		}
	}


}
