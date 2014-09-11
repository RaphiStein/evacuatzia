package evacuatzia_proj.servlets.restful;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Event;
import evacuatzia_proj.components.EventManager;
import evacuatzia_proj.components.User;

/**
 * Servlet implementation class EventJoin
 */
public class EventJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventJoin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"EventJoin\" doGet working");
		
		String pathInfo = request.getPathInfo();
		Pattern urlPattern = Pattern.compile("^/(\\d+)$"); // starts with slash, then anything other than another slash
		Matcher matcher = urlPattern.matcher(pathInfo);
		if (matcher.matches()) {
			// TODO: use this to get the real report by id later
			String eventIdStr = matcher.group(1);
			Long eventIdLong = Long.parseLong(eventIdStr);
			System.out.println("EventIDStr: " + eventIdStr);
			User user = (User) request.getSession().getAttribute("user");
			Event event = EventManager.getEventById(eventIdLong);
			EventManager.registerToEvent(user, event);
			
			String eventPage = "/evacuatzia/event/" + eventIdStr;
        	response.sendRedirect(eventPage);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"EventJoin\" doPost working");
	}

}
