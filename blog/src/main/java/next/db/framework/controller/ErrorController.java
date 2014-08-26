package next.db.framework.controller;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.db.framework.model.Model;

import org.springframework.stereotype.Component;

@Component("error")
public class ErrorController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, Model model) {
		ServletContext context = request.getServletContext();
		Map<Integer, String> errorCodeMap = (Map<Integer, String>)context.getAttribute("errorCodeMap");
		
		int status = 500;
		String message = "failure";
		
		status = 404;
		message = errorCodeMap.get(status);
		
		model.put("status", status);
		model.put("message", message);
		
		return "error";
		
	}

}