package me.dec7.address_book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Utility {

	public static void closeOut(BufferedWriter out) {
		
		if (out != null) {
			try {
				out.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
				
			}
		}
		
	}
	

	public static void closeIn(BufferedReader in) {
		
		if (in != null) {
			try {
				in.close();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
				
			}
		}
		
	}

}
