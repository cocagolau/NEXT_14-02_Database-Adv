package next.db.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.db.framework.model.Model;

public class MainController implements Controller {
	
	protected String defaultValue = null;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, Model model) {

		String method = request.getMethod();
		
		if ("GET".equals(method)) {
			return GET(request, response, model);
			
		} else if ("POST".equals(method)) {
			return POST(request, response, model);
			
		} else if ("PUT".equals(method)) {
			return PUT(request, response, model);
			
		} else if ("DELETE".equals(method)) {
			return DELETE(request, response, model);
			
		}
		
		return defaultValue;

	}
	
	
	protected String GET(HttpServletRequest request, HttpServletResponse response, Model model) {		
		
		return null;
	}
	
	protected String POST(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return null;
	}
	
	protected String PUT(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return null;
	}
	
	protected String DELETE(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return null;
	}

	
	
}
	

