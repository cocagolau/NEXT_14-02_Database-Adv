package next.db.dao;

import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DBListener implements ServletContextListener {
	
	private MongoClient mongoClient;
	
	   public void contextInitialized(ServletContextEvent event)  {
    	ServletContext context = event.getServletContext();
    	
//    	String ip = "10.73.45.73";
    	String ip = "10.73.45.72";
    	String db = "blog";

    	DB blogDB = null;
    	
		try {
			mongoClient = new MongoClient(ip);
			blogDB = mongoClient.getDB(db);
			
			System.out.println("name: " + blogDB.getName());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		}

		context.setAttribute("blogDB", blogDB);
    }

    public void contextDestroyed(ServletContextEvent event)  {     	
    	mongoClient.close();

    }
	
}
