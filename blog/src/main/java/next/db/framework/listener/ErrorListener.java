package next.db.framework.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ErrorListener implements ServletContextListener {

	public static final String ERROR_404 = "존재하지 않는 주소입니다";
	public static final String ERROR_401 = "로그인이 필요합니다";
	public static final String ERROR_500 = "예상치 못한 문제가 발생했습니다";

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		Map<Integer, String> errorCodeMap = new HashMap<Integer, String>();

		errorCodeMap.put(404, ERROR_404);
		errorCodeMap.put(401, ERROR_401);

		errorCodeMap.put(500, ERROR_500);

		context.setAttribute("errorCodeMap", errorCodeMap);
	}

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

}
