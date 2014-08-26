package next.db.framework.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PutMethodRequestWrapper extends HttpServletRequestWrapper{
	
	private Map<String, String[]> parameters;
	
	public PutMethodRequestWrapper(HttpServletRequest request) {
		super(request);
		parseParameters(request);
	}

	private void parseParameters(HttpServletRequest request) {
		StringBuilder raw = getRawParams(request);
		this.parameters = parseRawRarams(raw.toString()); 
		
	}
	
	private Map<String, String[]> parseRawRarams(String raw) {
		Map<String, String[]> params = new HashMap<String, String[]>();
		
		String[] rawArr = raw.split("&");
		for (int i=0; i<rawArr.length; ++i) {
			String[] keyAndValues = rawArr[i].split("=");
			String key = keyAndValues[0];
			String value = keyAndValues[1];
			String[] values = null;
			
			// 존재시
			if (params.containsKey(key)) {
				values = params.get(key);
				
				List<String> v = Arrays.asList(values);
				v.add(value);
				values = v.toArray(new String[0]);
				
			// 비존재시
			} else {
				values = new String[1];
				values[0] = value;
			}
			
			params.put(key, values);
			
		}
		
		return params;
	}

	private StringBuilder getRawParams(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String data = br.readLine();
			while (data != null) {
				sb.append(data);
				data = br.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if (br != null) {
				try {
					br.close();
					
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
		}
		return sb;
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		return this.parameters;
	}

	@Override
	public String getParameter(String name) {

		return this.parameters.get(name)[0];
	}

	@Override
	public Enumeration<String> getParameterNames() {

		return Collections.enumeration(this.parameters.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		
		return this.parameters.get(name);
	}
	
	

}
