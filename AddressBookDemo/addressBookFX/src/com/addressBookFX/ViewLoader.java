/******************************************************************
 *
 * Copyright (C) Micro Focus 1984-2018. All rights reserved.
 *
 * This sample code is supplied for demonstration purposes only
 * on an "as is" basis and is for use at your own risk.
 *
 ******************************************************************/ 

package com.addressBookFX;

import java.io.IOException;
import java.util.ResourceBundle;

import com.addressBookFX.model.Record;
import com.addressBookFX.view.AddressBookController;
import com.addressBookFX.view.RecordDetailsController;
import com.addressBookFX.view.RecordDetailsController.DialogType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Loader of the different views
 * 
 * @author Micro Focus
 *
 */
public class ViewLoader {

	private final static String RESOURCE_BUNDLE = "com.addressBookFX.Bundle";
	private final static String APP_TITLE = "app.title";
	private final static String MAIN_PAGE = "view/AddressBook.fxml";
	private final static String DETAIL_PAGE = "view/RecordDetails.fxml";
	private final static String CSS = "application.css";
	private final static String APP_IMAGE = "file:resources/group.png";
	private final static String DETAIL_IMAGE = "file:resources/clipboard.png";
	
	private final ResourceBundle resourceBundle;
	private final Stage primaryStage;
	
	/**
	 * Constructor of {@link ViewLoader}
	 * 
	 * @param primaryStage the application's primary stage
	 */
	public ViewLoader(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;
		resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
	}
	
	/**
	 * Loads the main view of the application
	 * @throws IOException load exception
	 */
	public void openMainView() throws IOException{
		
		primaryStage.setTitle(resourceBundle.getString(APP_TITLE));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(MAIN_PAGE));
		loader.setResources(resourceBundle);
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getResource(CSS).toExternalForm());
		
		AddressBookController controller = (AddressBookController)loader.getController();
		controller.addViewLoader(this);
		
		primaryStage.getIcons().add(new Image(APP_IMAGE));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	/**
	 * Loads the details dialog
	 * @param type ADD or EDIT dialog
	 * @param record the record to be added or edited
	 * @throws IOException load exception
	 */
	public void openDetailsView(DialogType type, Record record) throws IOException{
		String title = resourceBundle.getString(Resource.ADD_RECORD_TITLE);
		if (type.equals(DialogType.EDIT)) {
			title = resourceBundle.getString(Resource.EDIT_RECORD_TITLE);
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(DETAIL_PAGE));
		loader.setResources(resourceBundle);
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource(CSS).toExternalForm());

		Stage dialogStage = new Stage();
		dialogStage.getIcons().add(new Image(DETAIL_IMAGE));
		dialogStage.setTitle(title);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		dialogStage.setScene(scene);

		RecordDetailsController controller = loader.getController();
		controller.setRecord(record);
		controller.setType(type);

		dialogStage.showAndWait();
	}
	
}
