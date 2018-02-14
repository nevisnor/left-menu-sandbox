
package com.rsiven.leftmenu.gui;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
		StackPane root = loader.load();
		
		JFXDecorator decorator = new JFXDecorator(primaryStage, root, true, true, true);
		decorator.setText("LeftMenu Example");
		
		root.setPrefWidth(800);
		root.setPrefHeight(540);
		Scene scene = new Scene(decorator);

		scene.getStylesheets().add(Main.class.getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
