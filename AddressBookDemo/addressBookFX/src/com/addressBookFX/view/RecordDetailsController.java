/******************************************************************
 *
 * Copyright (C) Micro Focus 1984-2018. All rights reserved.
 *
 * This sample code is supplied for demonstration purposes only
 * on an "as is" basis and is for use at your own risk.
 *
 ******************************************************************/  

package com.addressBookFX.view;

import com.addressBookFX.ctrl.RecordManager;
import com.addressBookFX.model.Record;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * A controller for the RecordDetails.fxml view.
 * 
 * @author Micro Focus
 *
 */
public class RecordDetailsController {
	
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField familyTextField;
	@FXML
	private TextField postalCodeTextField;
	@FXML
	private TextField cityTextField;
	@FXML
	private TextField streetTextField;
	@FXML
	private TextField phoneNumberTextField;

	private Record record;
	private DialogType type = DialogType.ADD;
	
	/**
	 * Set the {@link DialogType} to define the usage of the dialog
	 * @param type the {@link DialogType}
	 */
	public void setType(DialogType type){
		this.type = type;
	}
	
	/**
	 * Set a record to be viewed in the dialog
	 * @param record a record to be viewed in the dialog
	 */
	public void setRecord(Record record){
		this.record = record;
		nameTextField.setText(record.firstNameProperty().get());
		familyTextField.setText(record.lastNameProperty().get());
		postalCodeTextField.setText(Integer.toString(record.postalCodeProperty().get()));
		cityTextField.setText(record.cityProperty().get());
		streetTextField.setText(record.streetProperty().get());
		phoneNumberTextField.setText(record.phoneNumberProperty().get());
	}
	
	@FXML
	public void save(ActionEvent event){
		record.firstNameProperty().set(nameTextField.getText());
		record.lastNameProperty().set(familyTextField.getText());
		record.postalCodeProperty().set(Integer.parseInt(postalCodeTextField.getText()));
		record.cityProperty().set(cityTextField.getText());
		record.streetProperty().set(streetTextField.getText());
		record.phoneNumberProperty().set(phoneNumberTextField.getText());
		
		RecordManager manager = RecordManager.getInstance();
		manager.updateRecord(record);
		
		Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void cancel(ActionEvent event){
		if(type.equals(DialogType.ADD)){
			RecordManager manager = RecordManager.getInstance();
			manager.deleteRecord(record);
		}
		
		Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Defines the usage of the dialog - for adding or editing a record
	 * 
	 * @author Micro Focus
	 *
	 */
	public enum DialogType{
		ADD, EDIT;
	}
}
