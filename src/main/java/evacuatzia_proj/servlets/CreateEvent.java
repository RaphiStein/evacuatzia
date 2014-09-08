package evacuatzia_proj.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.utils.ParsingUtils;

/**
 * Servlet implementation class CreateEvent
 */
public class CreateEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"CreateEvent\" doGet working");
		request.getRequestDispatcher("/resources/jsp/create_event.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean inputIsValid = false;
		System.out.println("Servlet \"CreateEvent\" doPost working");

		String geoRaw = request.getParameter("geocode");
		String dateRaw = request.getParameter("date");
		String timeRaw = request.getParameter("time");
		String means = request.getParameter("means");
		String capacityRaw = request.getParameter("capacity");

		System.out.println(geoRaw + "\n" + 
				dateRaw + "\n" +
				timeRaw + "\n" +
				means + "\n" +
				capacityRaw);

		// validate input
		if (
				(geoRaw != null) &&
				(dateRaw != null) &&
				(timeRaw != null) &&
				(means != null) &&
				(capacityRaw != null)
				){
			inputIsValid = true;
		}

		//Parse Data
		try {
			Geometry geometry = ParsingUtils.parseGeocode(geoRaw);
		}
		catch (Exception e){
			badInputTryAgain(request, response, "Geocode");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dateParsed = format.parse(dateRaw);
		} catch (ParseException e) {
			badInputTryAgain(request, response, "Date");
			return;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("H:mm");
			Date timeParsed = format.parse(timeRaw);
		} catch (ParseException e) {
			badInputTryAgain(request, response, "Time");
			return;
		}
		try {
			int capacityParsed = Integer.parseInt(capacityRaw);
		}
		catch (Exception e){
			badInputTryAgain(request, response, "Time");
			return;
		}
		
		
		if (inputIsValid){
			request.setAttribute("message", "Success! Your Event Has Been Successfully Added");
			request.getRequestDispatcher("/resources/jsp/result.jsp").forward(request, response);
		}
		else {
			String message = "Your input was not valid. Please try again";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/resources/jsp/create_event.jsp").forward(request, response);
		}
	}

	private void badInputTryAgain(HttpServletRequest request,
			HttpServletResponse response, String badInputItem) throws ServletException, IOException {
		String message = badInputItem + " not entered in the correct format. Please try again";
		request.setAttribute("message", message);
		request.getRequestDispatcher("/resources/jsp/create_event.jsp").forward(request, response);
	}

}
