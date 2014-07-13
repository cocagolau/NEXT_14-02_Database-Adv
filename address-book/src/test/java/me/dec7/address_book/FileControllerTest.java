package me.dec7.address_book;

import java.io.File;

import junit.framework.TestCase;

public class FileControllerTest extends TestCase {
	
	public void testSaveAndLoad() {
		File file = new File(Filebase.ROOT, "test1");		
		FileController fileController = new FileController();
		
		fileController.save(file, "test");
		assertEquals("test", fileController.load(file).toString());
		
		file.delete();
	}
	
	public void testLoad() {
		
	}
}
