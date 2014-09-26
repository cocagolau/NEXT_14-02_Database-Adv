package next.db.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ParamFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    	
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String method = request.getMethod();
		
		if ("PUT".equals(method)) {
			PutMethodRequestWrapper wrappedRequest = new PutMethodRequestWrapper(request);
			chain.doFilter(wrappedRequest, res);
			
		} else {
			chain.doFilter(req, res);
			
		}

		
		
	}


}
