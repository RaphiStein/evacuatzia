package evacuatzia_proj.servlets.restful;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Administrator;
import evacuatzia_proj.components.Event;
import evacuatzia_proj.components.EventManager;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;

/**
 * Servlet implementation class EventLeave
 */
public class EventLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventLeave() {
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
				String eventIdStr = matcher.group(1);
				Long eventIdLong = Long.parseLong(eventIdStr);
				System.out.println("EventIDStr: " + eventIdStr);
				
				User user = (User) request.getSession().getAttribute("user");
				Event event = EventManager.getEventById(eventIdLong);
				EventManager.unregisterFromEvent(user, event);
				System.out.println("User " + user.getUsername() + " has been unregistered from Event " + event.getEventID());
				
				response.sendRedirect("/evacuatzia/user/" + user.getUsername());
			}
		}
	}
}
