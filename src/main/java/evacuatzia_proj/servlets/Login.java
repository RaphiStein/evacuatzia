package evacuatzia_proj.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evacuatzia_proj.components.User;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.utils.StringHashingUtils;





/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
       

	/**
	 * 
	 */
	private static final long serialVersionUID = 7108692250775487730L;

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
        if ((username != null) && (pwd != null) && username.equals("admin") && StringHashingUtils.stringMatchMD5(pwd, "3db5200f67f447507983c60dddb323b3")) {
        	request.getSession().setAttribute("isAdmin", new Boolean(true));
        	response.sendRedirect(request.getContextPath() + "/home");
        } else {
	        if ((username != null) && UserManager.login(username, pwd)){ //login is successful
	        	//instantiate user object
	        	System.out.println("Login was successful!");
	        	request.getSession().setAttribute("isLoggedIn", true); //set session to logged in
	        	user = UserManager.getUserByUsername(username);
	        	System.out.println("username: " + username);	  
	        	System.out.println("user: " + user);
	        	//request.getSession().setAttribute("user", user);
	        	//request.getSession().setAttribute("isLoggedIn", new Boolean(true));
	        	String userPage = "/evacuatzia/user/" + username;
	        	response.sendRedirect(userPage);
	        }
	        else{
	        	request.setAttribute("error", "Bad username or password. Please retry");
	            request.getRequestDispatcher("/resources/jsp/login.jsp").forward(request, response);
	        }
        }
        
	}
	


}
