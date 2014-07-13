package me.dec7.address_book;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Filebase {
	private Map<String, File> keyFiles;
	private FileController fileController;
	public static final String ROOT = "/Users/Dec7/Documents/workspace/address-book/filebase";
	

	public Filebase() {
		this.keyFiles = new HashMap<String, File>();
		this.fileController = new FileController();
		this.loadKeyFiles();
	}

	public int size() {

		return this.keyFiles.entrySet().size();
		
	}
	
	public void insert(final String key, final String value) {
		setKeyFile(key);
		setValueAtKeyFile(getKeyFile(key), value);
		
	}
	
	public String select(String key) {
		File file = getKeyFile(key);
		
		return this.fileController.load(file).toString();
	}
	
	File getKeyFile(String key) {
		
		return this.keyFiles.get(key);
		
	}

	private boolean setKeyFile(String key) {
		File newFile = new File(Filebase.ROOT, key);
		boolean isSuccess = false;

		try {
			isSuccess = newFile.createNewFile();

			if (isSuccess) {
				this.keyFiles.put(key, newFile);
			} else {
				isSuccess = true;
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return isSuccess;
		
	}
	
	private void setValueAtKeyFile(File file, String value) {
		
		this.fileController.save(file, value);
		
	}
	
	
	
	private void loadKeyFiles() {
		File[] files = loadFileList();
		for (File file : files) {
			this.keyFiles.put(file.getName(), file);
		}
	}

	private File[] loadFileList() {
		File dir = new File(Filebase.ROOT);
		FilenameFilter nameFilter = new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.charAt(0) != '.';
			}
		};

		return dir.listFiles(nameFilter);
	}

}
