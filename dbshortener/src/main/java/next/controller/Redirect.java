package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.Logic;

public class Redirect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String urlId = request.getRequestURI();
	
		System.out.println("urlId: " + urlId);
		
		Logic logic = new Logic();
		try {
			String url = logic.getLongUrl(urlId);
			System.out.println("url: " + url);
			response.sendRedirect("http://" + url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
