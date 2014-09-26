package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.Logic;

/**
 * Servlet implementation class Home
 */
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String longUrl = request.getParameter("longUrl");

		System.out.println("->" + request.getRequestURI());
		System.out.println("shortening " + longUrl);
		
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String contextPath = request.getContextPath();
		System.out.println("contextPath: " + contextPath);
		String shortUrl = null;
		
		try {
			shortUrl = new Logic().getShort(serverName, port, contextPath,longUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("short url: " + shortUrl);
		request.getSession().setAttribute("shortUrl", shortUrl);
		response.sendRedirect("/");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
