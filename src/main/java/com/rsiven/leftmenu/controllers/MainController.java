/**
 * 
 */
package com.rsiven.leftmenu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class MainController implements Initializable {

    private static final double TRANSITION_TIMER = 200;

	@FXML private StackPane root;
    @FXML private StackPane contentPane;
    @FXML private StackPane navigationPane;
    @FXML private StackPane statusPane;
    @FXML private StackPane toolPane;    
    @FXML private JFXSnackbar snkMessage;
    
    private StackPane systemsPane;
    private StackPane usersPane;
    private StackPane settingsPane;
    
    StringProperty snackMessage = new SimpleStringProperty();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		snkMessage.registerSnackbarContainer(root);
		
		// Load Navigation Content
		FXMLLoader navigationLoader = new FXMLLoader(getClass().getResource("/views/navigation.fxml"));
		try {
			navigationLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NavigationController navigationController = navigationLoader.getController();
		navigationPane.getChildren().add(navigationLoader.getRoot());
		
		// Load Systems page
		FXMLLoader systemsLoader = new FXMLLoader(getClass().getResource("/views/systems.fxml"));
		try {
			systemsLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SystemsController systemsController = systemsLoader.getController();
		systemsPane = systemsLoader.getRoot();
		systemsPane.setOpacity(1.0d);
		systemsPane.toFront();
		contentPane.getChildren().add(systemsPane);
		
		// Load Users page
		FXMLLoader usersLoader = new FXMLLoader(getClass().getResource("/views/users.fxml"));
		try {
			usersLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UsersController usersController = usersLoader.getController();
		usersController.snackMessageProperty().bindBidirectional(snackMessageProperty());
		usersPane = usersLoader.getRoot();
		
		// Load Settings page
		FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/views/settings.fxml"));
		try {
			settingsLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SettingsController settingsController = settingsLoader.getController();
		settingsPane = settingsLoader.getRoot();
		
		
		
		navigationPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			Timeline timeline = new Timeline(
			        new KeyFrame(Duration.ZERO, new KeyValue(navigationPane.prefWidthProperty(), navigationPane.getWidth())),
			        new KeyFrame(Duration.millis(100), new KeyValue(navigationPane.prefWidthProperty(), 175)));
			timeline.play();
		});
		navigationPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			Timeline timeline = new Timeline(
			        new KeyFrame(Duration.ZERO, new KeyValue(navigationPane.prefWidthProperty(), navigationPane.getWidth())),
			        new KeyFrame(Duration.millis(100), new KeyValue(navigationPane.prefWidthProperty(), 45)));
			timeline.play();
		});
		
		navigationController.btnSystems.setOnAction(e ->{
			setScreen(systemsPane);
		});
		navigationController.btnUsers.setOnAction(e ->{
			setScreen(usersPane);
		});
		navigationController.btnSettings.setOnAction(e ->{
			setScreen(settingsPane);
		});
		
		snackMessage.addListener((obs, ov, nv) -> {
			if (!nv.isEmpty()) {
				snkMessage.enqueue(new SnackbarEvent(nv));
			}
		});
				
	}

    /**
     * Set Screen 
     * 
     * @param name
     * @return
     */
    public boolean setScreen(Parent view) {       
    	final DoubleProperty opacity = contentPane.opacityProperty();

    	if (!contentPane.getChildren().isEmpty()) {    //if there is more than one screen
    		Timeline fade = new Timeline(
    				new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
    				new KeyFrame(new Duration(TRANSITION_TIMER), new EventHandler<ActionEvent>() {
    					@Override
    					public void handle(ActionEvent t) {
    						contentPane.getChildren().remove(0);        //remove the displayed screen
    						contentPane.getChildren().add(0, view);     //add the screen
    						Timeline fadeIn = new Timeline(
    								new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
    								new KeyFrame(new Duration(TRANSITION_TIMER), new KeyValue(opacity, 1.0)));
    						fadeIn.play();
    					}
    				}, new KeyValue(opacity, 0.0)));
    		fade.play();

    	} else {
    		contentPane.setOpacity(0.0);
    		contentPane.getChildren().add(view);       //no one else been displayed, then just show
    		Timeline fadeIn = new Timeline(
    				new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
    				new KeyFrame(new Duration(TRANSITION_TIMER), new KeyValue(opacity, 1.0)));
    		fadeIn.play();
    	}
    	return true;
    }
    
    public StringProperty snackMessageProperty() {
    	return snackMessage;
    }
}
