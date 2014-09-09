package evacuatzia_proj.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;

/**
 * Servlet implementation class AllReports
 */
public class AllReports extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AllReports() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"AllReports\" doGet working");

		Boolean isLoggedIn = (Boolean) request.getSession().getAttribute("isLoggedIn");
		if (isLoggedIn != null && isLoggedIn){
			List<Report> allReports = ReportManager.getAllReports();
			request.setAttribute("reports", allReports);
			request.getRequestDispatcher("../resources/jsp/see_all_reports.jsp").forward(request, response);
		}
		else {
			request.setAttribute("message", "Please log in to access this page");
			request.getRequestDispatcher("/resources/jsp/result.jsp").forward(request, response);
		}
	}

}
