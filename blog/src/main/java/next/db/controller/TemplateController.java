package next.db.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.db.autility.Constants;
import next.db.framework.controller.MainController;
import next.db.framework.model.Model;
import next.db.framework.resource.Uri;
import next.db.framework.support.ResourceLoader;

import org.springframework.stereotype.Component;

@Component("templates")
public class TemplateController extends MainController {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, Model model) {
		ServletContext context = request.getServletContext();
		Uri uri = new Uri(request);
		
		String templateFileName = uri.get(1);
		String root = context.getRealPath(Constants.RESOURCE_ROOT);
		String path = root +"WEB-INF/templates/html/"+ templateFileName;
		
		StringBuilder htmlDocumentSB = ResourceLoader.load(path);
		if (htmlDocumentSB != null) {
			int status = 200;
			
			model.put("status", status);
			model.put("template", htmlDocumentSB.toString());
			
			return null;
			
		}
		
		return null;
		
	}
	
}
