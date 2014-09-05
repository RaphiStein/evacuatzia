package evacuatzia_proj.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.exceptions.EvacuatziaException;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public Register() {
//        super();
//        // TODO Auto-generated constructor stub
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/resources/jsp/register_new_account.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if username is available if not - write message and stay on page
		// check password and username are not empty
		
		// FIRST: try to register using API and if fails, write the error message
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
//		System.out.println("name: " + name + " username: " + username + " password: " + password);
		String error = null;
		evacuatzia_proj.components.User user = null; 
		try {
			user = UserManager.register(username, password, name);
		} catch (EvacuatziaException e) {
			error = e.getMessage();
		} catch (RuntimeException e) {
			error = "Error has occurred. Please try again.";
		}
		if (null == user) {
			request.setAttribute("error", error);
            request.getRequestDispatcher("/resources/jsp/register_new_account.jsp").forward(request, response);
		} else {
			request.getSession().setAttribute("user", user);
            response.sendRedirect("home");
		}
	}

}
