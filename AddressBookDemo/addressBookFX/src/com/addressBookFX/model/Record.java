/******************************************************************
 *
 * Copyright (C) Micro Focus 1984-2018. All rights reserved.
 *
 * This sample code is supplied for demonstration purposes only
 * on an "as is" basis and is for use at your own risk.
 *
 ******************************************************************/  
package com.addressBookFX.model;

import com.addressBookWrapper.RecordDTO;
import javafx.beans.property.*;

/**
 * A record property. Encapsulates a {@link RecordDTO} object to be used within JavaFX.
 * 
 * @author Micro Focus
 *
 */
public class Record {

	private final IntegerProperty id;
	private final StringProperty firstName;
    private final StringProperty lastName;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final StringProperty street;
    private final StringProperty phoneNumber;
    private final RecordDTO dto;
	
    /**
     * Constructor of {@link Record}
     * @param dto the {@link RecordDTO} object to be encapsulated
     */
    public Record(RecordDTO dto) {
    	this.dto = dto;
    	this.id = new ReadOnlyIntegerWrapper(dto.getRecordID());
        this.firstName = new SimpleStringProperty(dto.getName());
        this.lastName = new SimpleStringProperty(dto.getFamily());
        this.postalCode = new SimpleIntegerProperty(dto.getPostalCode());
        this.city = new SimpleStringProperty(dto.getCity());
        this.street = new SimpleStringProperty(dto.getStreet());
        this.phoneNumber = new SimpleStringProperty(dto.getPhone());
    }
    
    /**
     * Get the underlying {@link RecordDTO} object
     * @return the underlying {@link RecordDTO} object
     */
    public RecordDTO getValue(){
    	dto.setName(firstName.get());
    	dto.setFamily(lastName.get());
    	dto.setPostalCode(postalCode.get());
    	dto.setCity(city.get());
    	dto.setStreet(street.get());
    	dto.setPhone(phoneNumber.get());
    	return dto;
    }
    
    public IntegerProperty idProperty() {
    	return id;
    }
    
    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public StringProperty cityProperty() {
    	return city;
    }
    
    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }
    
    public StringProperty streetProperty() {
    	return street;
    }
    
    public StringProperty phoneNumberProperty() {
    	return phoneNumber;
    }
    
    @Override
    public String toString(){
    	String toStr = "id: " + id.get()
    				+ " name: " + firstName.get() + " " + lastName.get()
    				+ " address: " + street.get() + " " + postalCode.get() + " " + city.get()
    				+ " phone: " + phoneNumber.get();
    	return toStr;
    }
}
