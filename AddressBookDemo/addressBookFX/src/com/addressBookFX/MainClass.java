/******************************************************************
 *
 * Copyright (C) Micro Focus 1984-2018. All rights reserved.
 *
 * This sample code is supplied for demonstration purposes only
 * on an "as is" basis and is for use at your own risk.
 *
 ******************************************************************/  

package com.addressBookFX;

import com.addressBookFX.ctrl.RecordManager;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starting point for the application
 * 
 * @author Micro Focus
 *
 */
public class MainClass extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		RecordManager.getInstance().start();
		ViewLoader loader = new ViewLoader(primaryStage);
		loader.openMainView();
	}
	
	@Override
	public void stop() throws Exception {
		RecordManager.getInstance().stop();
		super.stop();
	}
}
