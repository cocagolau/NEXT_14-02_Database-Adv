package next.dao;
import redis.clients.jedis.Jedis;

public class Logic {
	
	private static String url = "localhost";
	public static int count;
	private Jedis jedis = new Jedis(Logic.url);
	
	public Logic() {
		String tem = jedis.get("url:count");
		if (tem == null) {
			count = 0;
		} else {
			count = Integer.parseInt(tem);
		}
	}

	public String getId(String longUrl) throws Exception {
		// Stirng id = find id
		String id = jedis.get("url:" + longUrl);

		return id;
	}

	public String getShort(String serverName, int port, String contextPath, String longUrl) throws Exception {

		String id = getId(longUrl);// check if URL has been shorten already
		if (id != null) {
			// if id is not null, this link has been shorten already.
			// nothing to do

		} else {
			// insert
			jedis.set("url:" + longUrl, Logic.count+"");
			jedis.set("urlId:" + Logic.count, longUrl);
			id = Logic.count + "";
			
			Logic.count++;
			jedis.set("url:count", Logic.count+"");

		}
		return "http://" + serverName + ":" + port + contextPath + "/" + id;
	}

	public String getLongUrl(String urlId) throws Exception {
		if (urlId.startsWith("/")) {
			urlId = urlId.replace("/", "");
		}
		//String longUrl = findValue
		String longUrl = jedis.get("urlId:" + urlId);

		return longUrl;
	}
}
