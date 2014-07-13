package me.dec7.address_book;

import junit.framework.TestCase;

public class AddressTest extends TestCase{
	
	
	public void testGetterSetter() {
		
		Address addr = new Address("key1", "value1");
		
		assertEquals("key1", addr.getKey());
		assertEquals("value1", addr.getValue());
		
	}
}
