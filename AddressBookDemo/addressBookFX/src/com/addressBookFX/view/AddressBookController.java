/******************************************************************
 *
 * Copyright (C) Micro Focus 1984-2018. All rights reserved.
 *
 * This sample code is supplied for demonstration purposes only
 * on an "as is" basis and is for use at your own risk.
 *
 ******************************************************************/  

package com.addressBookFX.view;

import java.io.IOException;
import com.addressBookFX.ViewLoader;
import com.addressBookFX.ctrl.RecordManager;
import com.addressBookFX.model.Record;
import com.addressBookFX.view.RecordDetailsController.DialogType;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * A controller for the AddressBook.fxml view.
 * 
 * @author Micro Focus
 *
 */
public class AddressBookController {

	@FXML
	private TableView<Record> recordsTable;
	@FXML
	private TableColumn<Record, String> nameColumn;
	@FXML
	private TableColumn<Record, String> familyColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label familyLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label phoneNumberLabel;

	private ViewLoader loader;
	private final static String EMPTY_STRING = "";

	// automatically called by the JavaFX API
	public void initialize() {
		initBindings();
		loadData();
	}

	/**
	 * Add a viewLoader
	 * 
	 * @param loader the view loader
	 */
	public void addViewLoader(ViewLoader loader) {
		this.loader = loader;
	}

	@FXML
	public void add() throws IOException {
		Record record = RecordManager.getInstance().createRecord();
		if (record != null) {
			showDetialsDialog(DialogType.ADD, record);
		}
	}

	@FXML
	public void edit() throws IOException {
		Record record = recordsTable.getSelectionModel().getSelectedItem();
		if (record != null) {
			showDetialsDialog(DialogType.EDIT, record);
		}
	}

	@FXML
	public void delete() {
		Record record = recordsTable.getSelectionModel().getSelectedItem();
		if (record != null) {
			RecordManager.getInstance().deleteRecord(record);
		}
	}

	@FXML
	public void quit() {
		Platform.exit();
	}

	private void initBindings() {
		nameColumn.setCellValueFactory(item -> item.getValue().firstNameProperty());
		familyColumn.setCellValueFactory(item -> item.getValue().lastNameProperty());
		recordsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDetails(newValue));
	}

	private void showDetails(Record record) {
		nameLabel.textProperty().set((record == null) ? EMPTY_STRING : record.firstNameProperty().get());
		familyLabel.textProperty().set((record == null) ? EMPTY_STRING : record.lastNameProperty().get());
		postalCodeLabel.textProperty().set((record == null) ? EMPTY_STRING : record.postalCodeProperty().asString().get());
		cityLabel.textProperty().set((record == null) ? EMPTY_STRING : record.cityProperty().get());
		streetLabel.textProperty().set((record == null) ? EMPTY_STRING : record.streetProperty().get());
		phoneNumberLabel.textProperty().set((record == null) ? EMPTY_STRING : record.phoneNumberProperty().get());
	}

	private void loadData() {
		recordsTable.setItems(RecordManager.getInstance().getAll());
	}

	private void showDetialsDialog(DialogType type, Record record) throws IOException {
		loader.openDetailsView(type, record);
	}
}
