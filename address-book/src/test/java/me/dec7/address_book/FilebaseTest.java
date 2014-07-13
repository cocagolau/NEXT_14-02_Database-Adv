package me.dec7.address_book;

import junit.framework.TestCase;

public class FilebaseTest extends TestCase {
	
	public static final int COUNT = 100000;
	
	
	public void testInsertHundThou() {
		System.out.println("Start Simulation Count: " + COUNT);
		
		Filebase filebase = new Filebase();
		
		long startTime = System.currentTimeMillis();
		for (int i=0; i<COUNT; i++) {
			filebase.insert("name" + i, "addr" + i);
		}
		long endTime = System.currentTimeMillis();
		
		assertEquals(COUNT, filebase.size());
		
		System.out.println("insert runtime: " + (endTime-startTime)/1000.0f +"초");
	}
	
	public void testSelectHundThou() {
		Filebase filebase = new Filebase();
		int i;
		long startTime = System.currentTimeMillis();
		for (i=0; i<COUNT; i++) {
			filebase.select("name" + i);
		}
		long endTime = System.currentTimeMillis();
		
		assertEquals(COUNT, i);
		
		System.out.println("select runtime: " + (endTime-startTime)/1000.0f +"초");
		
	}
}
