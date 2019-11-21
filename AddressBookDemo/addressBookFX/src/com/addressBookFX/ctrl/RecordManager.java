/******************************************************************
 *
 * Copyright (C) Micro Focus 1984-2018. All rights reserved.
 *
 * This sample code is supplied for demonstration purposes only
 * on an "as is" basis and is for use at your own risk.
 *
 ******************************************************************/  

package com.addressBookFX.ctrl;

import com.addressBookFX.model.Record;
import com.addressBookWrapper.WrapperProvider;
import com.addressBookWrapper.IWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A singleton that manages all operations with the back-end
 * 
 * @author Micro Focus
 *
 */
public class RecordManager {

	private static RecordManager instance;
	private final IWrapper wrapper;
	private ObservableList<Record> recordList;
	
	/**
	 * Constructor
	 */
	private RecordManager(){
		wrapper = WrapperProvider.getWrapper();
		recordList = FXCollections.observableArrayList();
	}
	
	/**
	 * Get the recordManager instance.
	 * </br> The instance must be started before and stopped after usage is done
	 * @return the recordManager instance
	 */
	public static RecordManager getInstance(){
		if(instance == null){
			instance = new RecordManager();
		}
		return instance;
	}

	/**
	 * Starts the instance.
	 * </br> Must be called before using the CRUD operations.
	 */
	public void start(){
		wrapper.openConnection();
	}
	
	/**
	 * Stops the instance
 	 * </br> Must be called before quitting the application.
	 */
	public void stop(){
		wrapper.closeConnection();
	}

	/**
	 * Create and persist a new record if possible, <code>null</code> otherwise
	 * @return a new record if possible, <code>null</code> otherwise
	 */
	public Record createRecord(){
		if(recordList.size() >= wrapper.maxNumberOfRecords()){
			System.out.println("The max number of records is already reached");
			return null;
		}
		Record record = new Record(wrapper.createRecord());
		recordList.add(record);
		return record;
	}
	
	/**
	 * Persist updated record
	 * @param record the record to be persisted
	 */
	public void updateRecord(Record record){
		wrapper.updateRecord(record.getValue());
	}

	/**
	 * Delete given record
	 * @param record the record to be deleted
	 */
	public void deleteRecord(Record record){
		recordList.remove(record);
		wrapper.deleteRecord(record.getValue());
	}
	
	/**
	 * Get all records
	 * @return all records
	 */
	public ObservableList<Record> getAll(){
		recordList.clear();
		wrapper.getAllRecords().forEach((dto) -> recordList.add(new Record(dto)));
		return recordList;
	}
	
}
