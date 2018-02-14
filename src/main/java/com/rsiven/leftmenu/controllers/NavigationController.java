package com.rsiven.leftmenu.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.FontAwesome.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import com.jfoenix.controls.JFXButton;

import javafx.beans.binding.ObjectBinding;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Ron Siven
 */
public class NavigationController implements Initializable {

	@FXML private AnchorPane root;
	@FXML private VBox toolBar;
	@FXML public JFXButton btnSystems;
	@FXML public JFXButton btnUsers;
	@FXML public JFXButton btnSettings;

	private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		setupButton(btnSystems, FontAwesome.Glyph.SERVER);
		setupButton(btnUsers, FontAwesome.Glyph.USERS);
		setupButton(btnSettings, FontAwesome.Glyph.GEAR);
	}

	private void setupButton(JFXButton button, Glyph graphic) {

		// Set the text fill color to grey if not selected
		ColorAdjust grey = new ColorAdjust(0.5, -0.5, -0.5, -1);

		button.setAlignment(Pos.CENTER_LEFT);
		button.setGraphic(fontAwesome.create(graphic).color(Color.web("#FFFFFF")).size(18.0));

//		button.effectProperty().bind(new ObjectBinding<Effect>() {
//			{ bind(button.selectedProperty()); }
//			@Override protected Effect computeValue() {
//				return button.isSelected() ? null : grey;
//			}
//		});
//
//		// Set the graphic color to gray if not selected
//		button.getGraphic().effectProperty().bind(new ObjectBinding<Effect>() {
//			{ bind(button.selectedProperty()); }
//			@Override protected Effect computeValue() {
//				return button.isSelected() ? null : grey;
//			}
//		});
	}

}
