package next.db.cache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static long querytime = 0;
	static int ccc = 0;
//	static final String IP = "10.73.45.71";
//	static final String IP = "127.0.0.1";
	static final String IP = "10.73.45.72";
	static final String DB_NAME = "cacheTest";
	static final String TALBE_NAME = "test";
	static final String USER_ID = "root";
//	static final String USER_PW = "next!!@@##$$";
	static final String USER_PW = "88";
	static final int CACHE_SIZE = 700;
	
	public static void main(String[] args) {
		
		List<Integer> keys = new ArrayList<Integer>();
		Cache cache = new Cache(CACHE_SIZE);
		
		// driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// file read
		BufferedReader br = null;
		try {
			System.out.println("here");
			br = new BufferedReader(new FileReader("input.txt"));
			try {
				String line = br.readLine();
				
				while (line != null) {
					keys.add(Integer.parseInt(line));
					line = br.readLine();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		
		// db
		Connection conn = null;
		PreparedStatement psmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		String value = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + Main.IP + ":3306/" + Main.DB_NAME, Main.USER_ID, Main.USER_PW);
			psmt = conn.prepareStatement("select * from " + Main.TALBE_NAME + " where k = ?");
//			stmt = conn.createStatement();
			
			int count = 0;
			
			// 여기서 시작
			long startTime = System.currentTimeMillis();
			
			for(int i=0; i<keys.size(); i++) {
//			for(int i=0; i<1000; i++) {
				count++;
				int key = keys.get(i);

				// key가 존재하는 경우 - cache hit
				if (cache.has(key)) {
//					System.out.println("key hit: " + key);

					// cache의 처음에 추가
					cache.addFirst(key);
//					System.out.println();
					
					
				// key가 존재하지 않는 경우 - db접근
				} else {
//					System.out.println("key miss: " + key);
					
					long startqtime = System.currentTimeMillis();
					ccc++;
					// db접근 k, v가져옴
					psmt.setInt(1, key);
					rs = psmt.executeQuery();
//					rs = stmt.executeQuery("select * from " + Main.TALBE_NAME + " where k = " + key + ";");
					
					while(rs.next()) {
						value = rs.getString(2);
					}
					long endqtime = System.currentTimeMillis();
					querytime += (endqtime - startqtime);

					// cache의 처음에 추가
					cache.addFirst(key, value);
//					System.out.println();

				}
				rs.close();

				if (count%100 == 0) {
					System.out.println("count: " + count);
				}
				
			}
			long endTime = System.currentTimeMillis();
			System.out.println("--------------------------------");
			System.out.println("cache size: " + Main.CACHE_SIZE);
			System.out.println("time: " + (endTime - startTime) + "ms");
			System.out.println("query time: " + querytime + "ms");
			System.out.println("miss count: " + cache.getMissCount());
			System.out.println("hit count: " + cache.getHitCount());
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		
	}
}
