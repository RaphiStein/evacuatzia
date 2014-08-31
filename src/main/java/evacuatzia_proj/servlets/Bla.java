package evacuatzia_proj.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Bla extends HttpServlet {

	private static final long serialVersionUID = 16252534;

	@Override
	public void init() throws ServletException {
		System.out.println("init");
	}
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><h1>It works!!</h1></html>");
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		System.out.println("service");

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost");
	}

	@Override
	public void destroy() {
		System.out.println("Destroy servlet");
	}
}	
