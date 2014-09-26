package next.db.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.db.framework.model.Model;

public interface Controller {
	
	public String execute(HttpServletRequest request, HttpServletResponse response, Model model);
	
}
