package evacuatzia_proj.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.ReportManager;

/**
 * Servlet implementation class Map
 */
public class Map extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 
		System.out.println("Servlet \"Map\" doGet working");
		System.out.println("PathInfo: " + request.getRequestURL());
		request.getRequestDispatcher("/resources/jsp/search_by_location_reports_events.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO receive lat/lon from form
		// TODO retrieve nearby events and reports from the database
		// TODO add to List of LocationBasedItems and set as attribute in session
		// TODO return the page search_by_location_reports_events.jsp
		
		
	}	

}
