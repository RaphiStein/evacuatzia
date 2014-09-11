package evacuatzia_proj.servlets.restful;

import java.io.IOException;
import java.util.List;
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

/**
 * Servlet implementation class EventRemove
 */
public class EventRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		evacuatzia_proj.utils.DebugUtils.servletWorkingPrintout(getServletName(), "doGet");


		if ((Boolean) request.getSession().getAttribute("isAdmin")){
			String pathInfo = request.getPathInfo();
			Pattern urlPattern = Pattern.compile("^/(\\d+)$"); // starts with slash, then anything other than another slash
			Matcher matcher = urlPattern.matcher(pathInfo);
			if (matcher.matches()) {
				String eventIdStr = matcher.group(1);
				Long eventIdLong = Long.parseLong(eventIdStr);
				System.out.println("EventIDStr: " + eventIdStr);
				
				Event event = EventManager.getEventById(eventIdLong);
				System.out.println("Event: " + event);
				
				// Unregister users from the event
				List<User> users = event.getRegisteredUsers();
				System.out.println("Unregistering " + users.size() + "users from this event....");
				for (int i = 0; i < users.size(); i++){
					EventManager.unregisterFromEvent(users.get(i), event);
					System.out.println("User " + users.get(i).getUsername() + " unregistered from event");
				}
				// Delete event
				Administrator.INSTANCE.deleteEvent(event);
				System.out.println("Event Deleted!");
			
	        	response.sendRedirect("/evacuatzia/event/all");

			}
		}
		else {
			request.getRequestDispatcher("/evacuatzia/resources/jsp/404.jsp").forward(request, response);
		}
	}

}
