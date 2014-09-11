package evacuatzia_proj.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.EventManager;
import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.utils.ParsingUtils;

/**
 * Servlet implementation class SearchEvents
 */
public class SearchEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"SearchEvent\" doGet working");
		request.getRequestDispatcher("/resources/jsp/search_nearest_event.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"SearchReports\" doPost working");
		
		String geoRaw = request.getParameter("geocode");
		Geometry geoParsed;
		try {
			geoParsed = ParsingUtils.parseGeocode(geoRaw);
			evacuatzia_proj.components.Event event = EventManager.getNearestEvent(geoParsed.getLatitude(), geoParsed.getLongitude());
			System.out.println("Nearest Event: " + event);
			request.setAttribute("event", event);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "The Geocode was not submitted properly. Failed to search");
		}
		request.getRequestDispatcher("/resources/jsp/search_nearest_event.jsp").forward(request, response);
	}

}
