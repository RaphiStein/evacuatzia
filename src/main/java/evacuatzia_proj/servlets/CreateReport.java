package evacuatzia_proj.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.Report;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.utils.ParsingUtils;

/**
 * Servlet implementation class CreateReport
 */
public class CreateReport extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isLoggedIn = (Boolean) request.getSession().getAttribute("isLoggedIn");

		System.out.println("Servlet \"CreateReport\" doGet working");
		if ((isLoggedIn != null) && isLoggedIn){
			request.getRequestDispatcher("/resources/jsp/create_report.jsp").forward(request, response);			
		}
		else {
			request.setAttribute("message", "Sorry, you cannot access that page.");
			request.getRequestDispatcher("/resources/jsp/result.jsp").forward(request, response);			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean inputIsValid = false;

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String geoRaw = request.getParameter("geocode");

		//Parse Data
		Geometry geoParsed = null;	
		try {
			geoParsed = ParsingUtils.parseGeocode(geoRaw);
		}
		catch (Exception e){
			ParsingUtils.badInputTryAgain(request, response, "Geocode");
		}

		System.out.println(title + "\n" + content + "\n" + geoRaw);

		// validate input
		if (
				(title != null) &&
				(content != null) &&
				(geoRaw != null)
				){
			inputIsValid = true;
		}

		if (inputIsValid){
			// Get current User
			User user = (User) request.getSession().getAttribute("user");
			// Create the report and add it to his profile
			ReportManager.createNewReport(user, title, content, geoParsed, null);
			
			request.setAttribute("message", "Success! Your Report Has Been Successfully Added");
			request.getRequestDispatcher("/resources/jsp/result.jsp").forward(request, response);
		}
		else {
			request.setAttribute("message", "There was an error with you input. Please try again");
			request.getRequestDispatcher("/resources/jsp/create_report.jsp").forward(request, response);
		}
	}

}
