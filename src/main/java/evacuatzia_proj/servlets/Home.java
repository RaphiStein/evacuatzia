package evacuatzia_proj.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 */
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Context Path:" + request.getContextPath());
		System.out.println("--------------------------------------------------");
		System.out.println("Servlet \"Home\" doGet working");
		System.out.println("PathInfo: " + request.getRequestURL());
		//If admin logged in, go to adminhome
		request.getRequestDispatcher("/resources/jsp/home_admin.jsp").forward(request, response);
		//Else If logged in, go to home page
		// TODO request.getRequestDispatcher("/resources/jsp/home.jsp").forward(request, response);
		//Else If, go to login/register page
		// TODO request.getRequestDispatcher("/resources/jsp/home_login_register.jsp").forward(request, response);

	}

}
