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
		Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
		Boolean isLoggedIn = (Boolean) request.getSession().getAttribute("isLoggedIn");
		
		// TODO Auto-generated method stub
		System.out.println("Context Path:" + request.getContextPath());
		System.out.println("--------------------------------------------------");
		System.out.println("Servlet \"Home\" doGet working");
		System.out.println("PathInfo: " + request.getRequestURL());
		
		if ((isAdmin != null) && isAdmin){
			request.getRequestDispatcher("/resources/jsp/home_admin.jsp").forward(request, response);
		}
		else if ((isLoggedIn != null) && isLoggedIn) {
			request.getRequestDispatcher("/resources/jsp/home.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("/resources/jsp/home_login_register.jsp").forward(request, response);			
		}

		
	}

}
