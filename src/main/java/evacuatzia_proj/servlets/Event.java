package evacuatzia_proj.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.Geometry;

/**
 * Servlet implementation class Event
 */
public class Event extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO if user is logging in, 
		//	if admin is logged in, enable deleting user
		
		System.out.println("Servlet \"Event\" doGet working");
		String pathInfo = request.getPathInfo();
		Pattern urlPattern = Pattern.compile("^/(\\d+)$");
		Matcher matcher = urlPattern.matcher(pathInfo);
		if (matcher.matches()) {
			// TODO: use this to get the real report by id later
			String eventIdStr = matcher.group(1);
			System.out.println("event id from uri: " + eventIdStr);
			evacuatzia_proj.components.Event event = generateFakeEvent();
			List<evacuatzia_proj.components.User> userList = generateFakeUsersList();
			request.getSession().setAttribute("isAdmin", false); // is the current user logged as admin?
			request.getSession().setAttribute("isRegisteredToEvent", false); // is the current user registerd to this event
			request.getSession().setAttribute("event", event);
			request.getSession().setAttribute("registeredUsers", userList);
			request.getRequestDispatcher("/resources/jsp/event_view.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
		}
	}

	private evacuatzia_proj.components.Event generateFakeEvent() {
		return new evacuatzia_proj.components.Event(1L, new Geometry(29.0, 49.0), new Date(), "some means of evacuation", 300, 2);
	}
	
	private List<evacuatzia_proj.components.User> generateFakeUsersList() {
		evacuatzia_proj.components.User user1 = new evacuatzia_proj.components.User("username1", "name1", 1111L);
		evacuatzia_proj.components.User user2 = new evacuatzia_proj.components.User("username2", "name2", 2222L);
		List<evacuatzia_proj.components.User> usersList = new ArrayList<>();
		usersList.add(user1);
		usersList.add(user2);
		return usersList;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"Event\" doPost working");
			if ((boolean) request.getSession().getAttribute("isAdmin")){
				String username = request.getParameter("username");
				// Remove USERNAME from Event
				//After performing work, call the doGet to reload the page
				doGet(request, response);
			}
			else {
				request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
			}
		
		
	}

}
