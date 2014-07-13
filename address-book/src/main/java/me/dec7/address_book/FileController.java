package me.dec7.address_book;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileController {
		
	public void save(final File file, final String value) {
		BufferedWriter out = null;
		
		try {			
			out = new BufferedWriter(new FileWriter(file, true));
			out.write(value);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			
		} finally {
			Utility.closeOut(out);
			
		}

	}
	
	public StringBuilder load(File file) {
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		
		try {			
			in = new BufferedReader(new FileReader(file));
			String data;
			while ( (data=in.readLine()) != null) {
				sb.append(data);
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			
		} finally {
			Utility.closeIn(in);

		}
		
		return sb;
	}
}
