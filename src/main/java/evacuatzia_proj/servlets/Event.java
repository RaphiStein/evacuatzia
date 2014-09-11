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

import com.mchange.v1.util.DebugUtils;

import evacuatzia_proj.components.EventManager;
import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;

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
		evacuatzia_proj.utils.DebugUtils.servletWorkingPrintout(getServletName(), "doGet");
		
		String pathInfo = request.getPathInfo();
		Pattern urlPattern = Pattern.compile("^/(\\d+)$");
		Matcher matcher = urlPattern.matcher(pathInfo);
		if (matcher.matches()) {
			// TODO: use this to get the real report by id later
			String eventIdStr = matcher.group(1);
			Long eventIdInt = Long.parseLong(eventIdStr);
			System.out.println("event id from uri: " + eventIdStr);
			evacuatzia_proj.components.Event event = EventManager.getEventById(eventIdInt);
			List<User> users = event.getRegisteredUsers();
//			evacuatzia_proj.components.Event event = generateFakeEvent();
//			List<evacuatzia_proj.components.User> userList = generateFakeUsersList();
			boolean userIsRegisteredToThisEvent = userIsRegisteredToEvent(users, (User) request.getSession().getAttribute("user"));
			System.out.println("userIsRegisteredToEvent: " + userIsRegisteredToThisEvent);
			request.setAttribute("userIsRegisteredToThisEvent", userIsRegisteredToThisEvent);
			request.setAttribute("event", event);
			request.setAttribute("users", users);
			request.getRequestDispatcher("/resources/jsp/event_view.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
		}
	}

	private boolean userIsRegisteredToEvent(
			List<User> users, User user) {
		users.contains(user);
		if (users.contains(user)){
			return true;
		}
		else{
			return false;
		}
		/*
		if (user == null){
			return false;
		}
		else {
			evacuatzia_proj.components.Event usersEvent = EventManager.getEventByUser(user);
			System.out.println("User " + user.getUsername() + " is registered to " + usersEvent);
			for (int i = 0; i < event.get)
			if ((usersEvent != null) && usersEvent.equals(event)){ // if the users event and the event of the page is the same
				return true;
			}
			else
				return false;
		}
		*/
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
		if ((Boolean) request.getSession().getAttribute("isAdmin")){
			request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
		}
		else if ((Boolean) request.getSession().getAttribute("isLoggedIn")){
			User user = (User) request.getSession().getAttribute("user");
			// get user
		}
		else {
			request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
		}
				
				// Remove USERNAME from Event
				//After performing work, call the doGet to reload the page
				doGet(request, response);
		
	
				request.getRequestDispatcher("/resources/jsp/404.jsp").forward(request, response);
			
		
		
	}

}
