package next.db.framework.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.db.framework.model.Model;

public interface View {
	
	void show (HttpServletRequest request, HttpServletResponse response, Model model, String viewName) throws ServletException, IOException;
	
}
