package evacuatzia_proj.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.Report;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		System.out.println("Servlet \"User\" doGet working");
		System.out.println("PathInfo: " + pathInfo);
		System.out.println("URL: " + request.getRequestURL());
		if (request.getPathInfo().equalsIgnoreCase("/")){
			//if url is "user/" with no id provided, show 404
			request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
		}
		
		//Find user id in the path by pattern matching
		Pattern urlPattern = Pattern.compile("^/([^/]+)$");
		Matcher matcher = urlPattern.matcher(pathInfo);
		if (matcher.matches()){
			String userPath = matcher.group(1);
			//evacuatzia_proj.components.User user = UserManager.getUserByUsername(userPath);
			evacuatzia_proj.components.User user = new evacuatzia_proj.components.User("raphis", "Raphi Stein", new Long(0001));
			ArrayList<Report> reports = generateFakeReports(user);
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("reports", reports);
			request.getRequestDispatcher("/resources/jsp/user_home.jsp").forward(request, response);
		}
		
	}

	private ArrayList<evacuatzia_proj.components.Report> generateFakeReports(evacuatzia_proj.components.User user) {
		evacuatzia_proj.components.Report report1 = new evacuatzia_proj.components.Report(new Long(0001), "Report 1", new Geometry(29.0, 49.0), new Date(), user);
		evacuatzia_proj.components.Report report2 = new evacuatzia_proj.components.Report(new Long(0001), "Report 2", new Geometry(30.0, 40.0), new Date(), user);
		evacuatzia_proj.components.Report report3 = new evacuatzia_proj.components.Report(new Long(0001), "Report 3", new Geometry(29.0, 50.0), new Date(), user);
		
		ArrayList<evacuatzia_proj.components.Report> reports = new ArrayList<evacuatzia_proj.components.Report>();
		reports.add(report1);
		reports.add(report2);
		reports.add(report3);
		
		return reports;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
