package evacuatzia_proj.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.UserManager;

/**
 * Servlet implementation class AllUsers
 */
public class AllUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet \"AllUsers\" doGet working");
		
		List<evacuatzia_proj.components.User> users = UserManager.getAllUsers();
		System.out.println("Printout of " + users.size() + " users:");
		for (int i = 0; i < users.size(); i++){
			System.out.println(users.get(i));
		}
		request.setAttribute("users", users);
		request.getRequestDispatcher("../resources/jsp/see_all_users.jsp").forward(request, response);
	}

}
