package evacuatzia_proj.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Administrator;
import evacuatzia_proj.components.EventManager;
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
		Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");

		System.out.println("Servlet \"CreateEvent\" doGet working");
		if (isAdmin != null && isAdmin){
			request.getRequestDispatcher("/resources/jsp/create_event.jsp").forward(request, response);			
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
		Geometry geoParsed = null;
		Date dateParsed = null;
		Date timeParsed = null;
		int capacityParsed = 0;		
		try {
			geoParsed = ParsingUtils.parseGeocode(geoRaw);
		}
		catch (Exception e){
			ParsingUtils.badInputTryAgain(request, response, "Geocode");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			dateParsed = format.parse(dateRaw);
		} catch (ParseException e) {
			ParsingUtils.badInputTryAgain(request, response, "Date");
			return;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("H:mm");
			timeParsed = format.parse(timeRaw);
		} catch (ParseException e) {
			ParsingUtils.badInputTryAgain(request, response, "Time");
			return;
		}
		try {
			capacityParsed = Integer.parseInt(capacityRaw);
		}
		catch (Exception e){
			ParsingUtils.badInputTryAgain(request, response, "Time");
			return;
		}

		if (inputIsValid){
			Administrator.INSTANCE.createEvent(geoParsed, timeParsed, means, capacityParsed);
			request.setAttribute("message", "Success! Your Event Has Been Successfully Added");
			request.getRequestDispatcher("/resources/jsp/result.jsp").forward(request, response);
		}
		else {
			String message = "Your input was not valid. Please try again";
			request.setAttribute("message", message);
			request.getRequestDispatcher("/resources/jsp/create_event.jsp").forward(request, response);
		}
	}



}
