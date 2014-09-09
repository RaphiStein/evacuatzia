package evacuatzia_proj.servlets;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import evacuatzia_proj.components.Administrator;
import evacuatzia_proj.components.Geometry;
import evacuatzia_proj.components.ReportManager;
import evacuatzia_proj.components.UserManager;
import evacuatzia_proj.exceptions.EvacuatziaException;
import evacuatzia_proj.utils.ClearDatabase;
import evacuatzia_proj.utils.ParsingUtils;
import evacuatzia_proj.utils.StringHashingUtils;

public class InitFileUpload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3186677171233274168L;
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 500 * 1024 * 1024; // 500 mb should be enough
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init() {
		// Get the file location where it would be stored.
		filePath = System.getProperty("user.dir") + File.separator
				+ getServletContext().getInitParameter("file-upload");
		createDirIfDoesntExist(filePath);
	}

	private void createDirIfDoesntExist(String path) {
		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			// this could fail due to security reasons, but its something we
			// have to take care of in advanced anyway...
			tmpDir.mkdir();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(filePath);
		Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
		if (null == isAdmin || !isAdmin) {
			// shouldn't be here on the first place, so we'll just throw him back home...
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		request.getRequestDispatcher("/resources/jsp/init_file_upload.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
		if (null == isAdmin || !isAdmin) {
			// shouldn't be here on the first place, so we'll just throw him back home...
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		// upload file
		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Init file upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body>");
			out.println("</html>");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		createDirIfDoesntExist("evacuatzia_temp_upload");
		factory.setRepository(new File("evacuatzia_temp_upload"));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator i = fileItems.iterator();

			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			// while (i.hasNext()) {
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fileName = fi.getName();
					// Write the file
					if (fileName.lastIndexOf(File.separator) >= 0) {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf(File.separator)));
					} else {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf(File.separator) + 1));
					}
					fi.write(file);
					out.println("Uploaded Filename: " + fileName + "<br>");
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		// file finished uploading correctly - see if in valid format

		JSONParser parser = new JSONParser();
		try {
			System.out.println(file.getCanonicalPath());
			System.out.println(file.getAbsoluteFile());
			Object obj = parser.parse(new FileReader(file.getCanonicalPath()));
			JSONObject jsonObject = (JSONObject) obj;
			// all good till here - clear database:
			ClearDatabase.dropAllTables();
			out.println("Cleared old data from database." + "<br>");
			// create all users
			createAllUsers(jsonObject);
			createAllEvents(jsonObject);
			createAllReports(jsonObject);
			out.println("Loaded data from json successfully");
		} catch (EvacuatziaException e) {
			out.println("Error: Parse problem in json file." + "<br>");
			out.println("Error: " + e.getMessage() + "<br>");
			return;
		} catch (ParseException e) {
			out.println("Error: Parse problem in json file." + "<br>");
			return;
		} finally {
			out.println("</body>");
			out.println("</html>");
		}
	}

	private void createAllUsers(JSONObject jsonObject) {
		JSONArray usersJAarray = (JSONArray) jsonObject.get("users");
		for (int i = 0; i < usersJAarray.size(); ++i) {
			String username;
			String password;
			String name;
			try {
				JSONObject jUser = (JSONObject) usersJAarray.get(i);
				username = (String) jUser.get("username");
				password = (String) jUser.get("password");
				name = (String) jUser.get("name");
			} catch (RuntimeException e) {
				throw new EvacuatziaException("Error parsing user number " + Integer.toString(i) + " from json file.");
			}
			UserManager.register(username, password, name);
		}
	}

	private void createAllEvents(JSONObject jsonObject) {
		JSONArray eventsJAarray = (JSONArray) jsonObject.get("evacuationEvents");
		for (int i = 0; i < eventsJAarray.size(); ++i) {
			Geometry geom;
			Date estimatedTime;
			String meanOfEvacuation;
			int capacity;
			try {
				JSONObject jEvent = (JSONObject) eventsJAarray.get(i);
				geom = getGeometryEvent(jEvent);
				estimatedTime = ParsingUtils.parseJsonDate((String) jEvent.get("estimatedTime"));
				meanOfEvacuation = (String) jEvent.get("meanOfEvacuation");
				capacity = ((Long) jEvent.get("capacity")).intValue();
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
				throw new EvacuatziaException("Error parsing event number " + Integer.toString(i) + " from json file.");
			}
			Administrator.INSTANCE.createEvent(geom, estimatedTime, meanOfEvacuation, capacity);
		}
	}

	private void createAllReports(JSONObject jsonObject) throws EvacuatziaException {
		JSONArray reportsJAarray = (JSONArray) jsonObject.get("reports");
		for (int i = 0; i < reportsJAarray.size(); ++i) {
			String username;
			Geometry geom;
			String title;
			String content;
			Date expirationTime;
			try {
				JSONObject jEvent = (JSONObject) reportsJAarray.get(i);
				username = (String) jEvent.get("user");
				// only points should be supported
				geom = getGeometryEvent(jEvent);
				title = (String) jEvent.get("title");
				content = (String) jEvent.get("content");
				expirationTime = ParsingUtils.parseJsonDate((String) jEvent.get("expirationTime"));
			} catch (RuntimeException e) {
				throw new EvacuatziaException("Error parsing report number " + Integer.toString(i) + " from json file.");
			}
			evacuatzia_proj.components.User u = UserManager.getUserByUsername(username);
			u.createReport(title, content, geom, expirationTime);
		}
	}

	private Geometry getGeometryEvent(JSONObject e) {
		Geometry geom;
		JSONArray jPoint = (JSONArray) ((JSONObject) e.get("geometry")).get("coordinates");
		Double longitude = (Double) jPoint.get(0);
		Double latitude = (Double) jPoint.get(1);
		geom = new Geometry(longitude, latitude);
		return geom;
	}
}
