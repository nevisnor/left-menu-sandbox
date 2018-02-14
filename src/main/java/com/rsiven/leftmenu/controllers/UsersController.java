package com.rsiven.leftmenu.controllers;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import com.rsiven.leftmenu.core.busobj.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXRippler.RipplerMask;
import com.jfoenix.controls.JFXRippler.RipplerPos;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Callback;

public class UsersController {

    @FXML private JFXButton btnAdd;
    @FXML private JFXTreeTableView<User> ttv;
	@FXML private JFXTreeTableColumn<User, String> username;
	@FXML private JFXTreeTableColumn<User, String> system;
	@FXML private JFXTreeTableColumn<User, String> buttoncol;
	@FXML private JFXTreeTableColumn<User, String> fillercol;

    StringProperty snackMessage = new SimpleStringProperty();
    
	private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

	public UsersController() {
	}

	@FXML
	private void initialize() {

		btnAdd.setGraphic(fontAwesome.create(FontAwesome.Glyph.PLUS).color(Color.web("#FFFFFF")).size(18.0));
		
		username.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("username"));
		system.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("system"));

		// buttoncol.setCellValueFactory(new TreeItemPropertyValueFactory<User,
		// String>("dummy"));

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		
		Callback<TreeTableColumn<User, String>, TreeTableCell<User, String>> cellFactory = new Callback<TreeTableColumn<User, String>, TreeTableCell<User, String>>() {
			@Override
			public TreeTableCell<User, String> call(final TreeTableColumn<User, String> param) {
				final TreeTableCell<User, String> cell = new TreeTableCell<User, String>() {

					JFXHamburger hb = new JFXHamburger();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							JFXListView<StackPane> list = new JFXListView<StackPane>();
							list.setPrefWidth(120);
							StackPane removeMenu = new StackPane();
							HBox hboxRemove = new HBox(12.0);
							hboxRemove.setAlignment(Pos.CENTER_LEFT);
							hboxRemove.getChildren().add(fontAwesome.create(FontAwesome.Glyph.REMOVE).color(Color.RED).size(18.0));
							hboxRemove.getChildren().add(new Text("Remove"));
							removeMenu.getChildren().add(hboxRemove);
							list.getItems().add(removeMenu);
							
							StackPane editMenu = new StackPane();
							HBox hboxEdit = new HBox(12.0);
							hboxEdit.setAlignment(Pos.CENTER_LEFT);
							hboxEdit.getChildren().add(fontAwesome.create(FontAwesome.Glyph.EDIT).color(Color.web("#0072C6")).size(18.0));
							hboxEdit.getChildren().add(new Text("Edit"));
							editMenu.getChildren().add(hboxEdit);
							list.getItems().add(editMenu);
							
							StackPane copyMenu = new StackPane();
							HBox hboxCopy = new HBox(12.0);
							hboxCopy.setAlignment(Pos.CENTER_LEFT);
							hboxCopy.getChildren().add(fontAwesome.create(FontAwesome.Glyph.COPY).color(Color.web("#0072C6")).size(18.0));
							hboxCopy.getChildren().add(new Text("Copy"));
							copyMenu.getChildren().add(hboxCopy);
							list.getItems().add(copyMenu);
							
							hb.setMaxWidth(2);
							StackPane burgerPane = new StackPane(hb);
							burgerPane.getStyleClass().add("row-action-button");
							burgerPane.setPrefWidth(28);
							burgerPane.setPadding(new Insets(3, 12, 3, 12));
							JFXRippler popupSource = new JFXRippler(burgerPane, RipplerMask.CIRCLE, RipplerPos.BACK);

							JFXPopup popup = new JFXPopup(list);
							popupSource.setOnMouseClicked((e) -> {
								if (e.getScreenY() > (primaryScreenBounds.getMaxY() - 100)) {
									popup.show(hb, PopupVPosition.BOTTOM, PopupHPosition.RIGHT);
								} else {
									popup.show(hb, PopupVPosition.TOP, PopupHPosition.RIGHT);
								}
							});
							popup.setHideOnEscape(true);

							removeMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
								User user = getTreeTableRow().getItem();
								popup.hide();
								snackMessage.set("User " + user.getUsername() + " on system " + user.getSystem() + " removed.");
							});

							setGraphic(popupSource);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		buttoncol.setCellFactory(cellFactory);

		DoubleBinding usedWidth = username.widthProperty().add(system.widthProperty()).add(buttoncol.widthProperty());
		fillercol.prefWidthProperty().bind(ttv.widthProperty().subtract(usedWidth));
		
		// data
		User userRoot = new User("GWASHINGTON", null);
		User userSys1 = new User("GWASHINGTON", "SYSTEM1");
		User userSys2 = new User("GWASHINGTON", "SYSTEM2");
		User userSys3 = new User("GWASHINGTON", "SYSTEM3");

		// root
		TreeItem<User> root = new TreeItem<User>(new User(null, null));
		ttv.setRoot(root);

		TreeItem<User> itemRoot = new TreeItem<User>(userRoot);
		itemRoot.getChildren().add(new TreeItem<User>(userSys1));
		itemRoot.getChildren().add(new TreeItem<User>(userSys2));
		itemRoot.getChildren().add(new TreeItem<User>(userSys3));
		root.getChildren().add(itemRoot);

		TreeItem<User> nextRoot = new TreeItem<User>(new User("ALINCOLN", null));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM1")));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM2")));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM1")));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM2")));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM1")));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM2")));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM1")));
		nextRoot.getChildren().add(new TreeItem<User>(new User("ALINCOLN", "SYSTEM2")));
		root.getChildren().add(nextRoot);

		TreeItem<User> anotherRoot = new TreeItem<User>(new User("TJEFFERSON", null));
		anotherRoot.getChildren().add(new TreeItem<User>(new User("TJEFFERSON", "SYSTEM1")));
		anotherRoot.getChildren().add(new TreeItem<User>(new User("TJEFFERSON", "SYSTEM2")));
		anotherRoot.getChildren().add(new TreeItem<User>(new User("TJEFFERSON", "SYSTEM1")));
		anotherRoot.getChildren().add(new TreeItem<User>(new User("TJEFFERSON", "SYSTEM2")));
		root.getChildren().add(anotherRoot);

		ttv.setShowRoot(false);
	}

    public StringProperty snackMessageProperty() {
    	return snackMessage;
    }
}
