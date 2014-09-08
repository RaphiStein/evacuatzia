package evacuatzia_proj.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateReport
 */
public class CreateReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Check if user is loggedin
		System.out.println("Servlet \"CreateReport\" doGet working");
		request.getRequestDispatcher("/resources/jsp/create_report.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean inputIsValid = false;
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String geocode = request.getParameter("geocode");
		
		System.out.println(title + "\n" + content + "\n" + geocode);
		
		// validate input
		if (
				(title != null) &&
				(content != null) &&
				(geocode != null)
			){
			inputIsValid = true;
		}
				
		if (inputIsValid){
			request.setAttribute("message", "Success! Your Report Has Been Successfully Added");
			request.getRequestDispatcher("/resources/jsp/result.jsp").forward(request, response);
		}
		else {
			request.setAttribute("message", "There was an error with you input. Please try again");
			request.getRequestDispatcher("/resources/jsp/create_report.jsp").forward(request, response);
		}
	}

}
