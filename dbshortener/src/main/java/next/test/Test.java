package next.test;

import java.util.Random;

import next.dao.Logic;
import redis.clients.jedis.Jedis;

public class Test {
	private static long time;
	static final String NUM_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static Jedis jedis = new Jedis("localhost");
	private static Logic logic = new Logic();
	
    public static void main(String[] args) {
        int count = 50000;
        
        try {
			insertTest(count);
			searchTest(count);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			jedis.close();
			
		}   
    }
 
    public static void insertTest(int count) throws Exception {
        for(int i = 0; i < count ; i++) {
        	logic.getId(genRandomString(10));
            printProgress(i, count);
        }
    }
 
    public static void searchTest(int count) throws Exception {
        for(int i = 0; i < count ; i++) {
            logic.getLongUrl(i+"");
            printProgress(i, count);
        }
    }
    
    public static void printProgress(int current, int total) {
    	if (current == 0) {
    		time = System.currentTimeMillis();
    		System.out.println("Start");
    	}
    	else if ( current * 10 % total == 0)
    		System.out.print("*");
    	else if (current == (total - 1)) {
    		time = System.currentTimeMillis() - time;
    		System.out.printf("\nFinished. Time: %d QPS: %d\n", time, total * 1000 / (time+1));
    	}
    }
    
    static String genRandomString(int len) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < len; i++) {
			sb.append(NUM_ALPHABET.charAt( r.nextInt(NUM_ALPHABET.length())));
		}
		return sb.toString();
	}

}
