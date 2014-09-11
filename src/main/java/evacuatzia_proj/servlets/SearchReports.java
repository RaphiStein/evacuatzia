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
 * Servlet implementation class SearchReports
 */
public class SearchReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO 
		System.out.println("Servlet \"SearchReports\" doGet working");
		
		String partialTitle = request.getParameter("locationInput");
		List<Report> reports = ReportManager.getReportsByPartialTitle(partialTitle);
		request.setAttribute("reports", reports);
		request.getRequestDispatcher("/resources/jsp/search_reports.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"SearchReports\" doPost working");
		
		String partialTitle = request.getParameter("locationInput");
		List<Report> reports = ReportManager.getReportsByPartialTitle(partialTitle);
		System.out.println("Printing Reports of size " + reports.size());
		for (int i = 0; i < reports.size(); i++){
			System.out.println(reports.get(i));
		}
		request.setAttribute("reports", reports);
		request.getRequestDispatcher("/resources/jsp/search_reports.jsp").forward(request, response);
	}

}
