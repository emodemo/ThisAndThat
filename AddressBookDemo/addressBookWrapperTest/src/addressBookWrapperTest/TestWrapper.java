/******************************************************************
 *
 * Copyright (C) Micro Focus 1984-2018. All rights reserved.
 *
 * This sample code is supplied for demonstration purposes only
 * on an "as is" basis and is for use at your own risk.
 *
 ******************************************************************/  
package addressBookWrapperTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.addressBookWrapper.RecordDTO;
import com.addressBookWrapper.IWrapper;
import com.addressBookWrapper.WrapperProvider;

/**
 * Test class for the wrapped COBOL functionality
 * 
 * @author Micro Focus
 *
 */
public class TestWrapper {

	private IWrapper wrapper;
	
	@Before
	public void setUp(){
		wrapper = WrapperProvider.getWrapper();
		// open connection
		wrapper.openConnection();
	}
	
	@After
	public void tearDown(){
		// clear all results
		for(RecordDTO dto : wrapper.getAllRecords()){
			wrapper.deleteRecord(dto);
		}
		// close the connection
		wrapper.closeConnection();
	}
	
	/**
	 * <b>Configuration</b>
	 * <ol>
	 * see the @Before and @After methods for setting up and tearing down the test case
	 * </ol>
	 * <b>Steps</b>:
	 * <ol>
	 * <li>Create 1 record
	 * <li>Update the record
	 * <li>Create 2nd record
	 * <li>Delete the 1st record
	 * <li>Delete the 2nd record
	 * </ol>
	 * <b>Verifications</b>:
	 * <ol>
	 * Verify each step
	 * </ol>
	 */
	@Test
	public void testWrapper(){
		
		// create 1st record
		RecordDTO dto1 = wrapper.createRecord();
		dto1.setName("John");
		dto1.setFamily("Smith");
		dto1.setCity("Sofia");
		dto1.setPostalCode(1000);
		dto1.setStreet("blvd. Tsar Asen");
		dto1.setPhone("00359123");
		wrapper.updateRecord(dto1);
		List<RecordDTO> allRecords = wrapper.getAllRecords();
		assertEquals(1, allRecords.size());
		assertTrue(compareRecords(dto1, allRecords.get(0)));
		
		// create 2nd record
		RecordDTO dto2 = wrapper.createRecord();
		dto2.setName("Salvador");
		dto2.setFamily("Mirono");
		dto2.setCity("Roma");
		dto2.setPostalCode(1001);
		dto2.setStreet("blvd. Cesario");
		dto2.setPhone("");
		wrapper.updateRecord(dto2);
		allRecords = wrapper.getAllRecords();
		assertEquals(2, allRecords.size());
		
		// delete the 1st record
		wrapper.deleteRecord(dto1);
		allRecords = wrapper.getAllRecords();
		assertEquals(1, allRecords.size());
		assertTrue(compareRecords(dto2, allRecords.get(0)));
		
		// delete the 2nd record
		wrapper.deleteRecord(dto2);
		allRecords = wrapper.getAllRecords();
		assertEquals(0, allRecords.size());
	}
	
	private boolean compareRecords(RecordDTO dto1, RecordDTO dto2){
		if(dto1.getRecordID() != dto2.getRecordID()) return false;
		if(! dto1.getName().trim().equals(dto2.getName().trim())) return false;
		if(! dto1.getFamily().trim().equals(dto2.getFamily().trim())) return false;
		if(dto1.getPostalCode() != dto2.getPostalCode()) return false;
		if(! dto1.getCity().trim().equals(dto2.getCity().trim())) return false;
		if(! dto1.getStreet().trim().equals(dto2.getStreet().trim())) return false;
		if(! dto1.getPhone().trim().equals(dto2.getPhone().trim())) return false;
		return true;
	}
}
