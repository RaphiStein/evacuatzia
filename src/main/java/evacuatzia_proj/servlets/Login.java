package evacuatzia_proj.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;





/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Login doGet");
//		request.getSession().setAttribute("name", 123);
		request.getRequestDispatcher("/resources/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO process user input
		String username = request.getParameter("user");
        String pwd = request.getParameter("password");
        User user;
        
        if (UserManager.login(username, pwd)){ //login is sucessful
        	//instantiate user object
        	user = UserManager.getUserByUsername(username);
        	request.getSession().setAttribute("user", user);
        	request.getRequestDispatcher("resources/jsp/user/" + user.getUsername() + ".jsp").forward(request, response);
        }
        else{
        	request.setAttribute("error", "Bad username or password. Please retry");
            request.getRequestDispatcher("/resources/jsp/login.jsp").forward(request, response);
        }
        
	}
	


}
