package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.*;
import com.example.rollingBall.entities.Terrain;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditorChooseSubScene extends SubSceneMenu {

    private LabelTitleC labelTitle;
    private Label labelDate;
    private ButtonC newTerrainButton;
    private ButtonC editButton;
    private ButtonC backButton;
    private ComboBox comboBox;
    private List<String> terrains = new ArrayList<>();
    private static String currentTerrain = "";
    private AnchorPaneC anchorPaneC;

    public EditorChooseSubScene(Group root, Stage stage) {
        super(root, stage);

        List<Terrain> terrainz = Main.getTerrains();
        for (Terrain t: terrainz) {
            if (t.getName().equals("STANDARD")) continue;
            if (t.getPlayer().equals(Main.getCurrentPlayer().getName()))
            this.terrains.add( t.getName() );
        }

        if (terrains.size() == 0) {
            Main.setCurrentTerrain(null);
            currentTerrain = "";
        } else {
            currentTerrain = terrains.get(0);
            Main.setCurrentTerrain(currentTerrain);
        }

        labelTitle = new LabelTitleC("Choose Terrain");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        comboBox = new ComboBoxC(FXCollections.observableArrayList(terrains));
        comboBox.setValue(currentTerrain);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        currentTerrain = (String)comboBox.getValue();
                        labelDate.setText("Date modified: \n" + Main.getTerrain(currentTerrain).getDateString());
                        Main.setCurrentTerrain(currentTerrain);
                        Main.getMediaPlayer().playButton4Sound();
                    }
                };
        comboBox.setOnAction(event);

        labelDate = new LabelC("");
        labelDate.setMaxWidth(Const.MENU_BUTTON_WIDTH);
        labelDate.setTextAlignment(TextAlignment.CENTER);

        editButton = new ButtonC("Edit");
        editButton.addEventHandler( MouseEvent.ANY, this::handleEditButton);
        editButton.addEventHandler( KeyEvent.ANY, this::handleEditButton);
        if (terrainz.size() == 0) editButton.setDisable(true);

        newTerrainButton = new ButtonC("New Terrain");
        newTerrainButton.addEventHandler( MouseEvent.ANY, this::handleNewTerrainButton);
        newTerrainButton.addEventHandler( KeyEvent.ANY, this::handleNewTerrainButton);

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton);

        anchorPaneC = new AnchorPaneC(true);
        double mainWidth = Main.getCurrent_window_width();
        double left = mainWidth / 2 - Const.MENU_BUTTON_WIDTH / 2 - Const.MENU_PANE_WIDTH;
        double top = (Main.getCurrent_window_height() - anchorPaneC.getPrefHeight()) / 2;
        AnchorPane.setRightAnchor(anchorPaneC, left / 2);
        AnchorPane.setTopAnchor(anchorPaneC, top);
        anchorPaneC.addToFlowPane(labelDate);

        if (terrains.size() == 0){
            labelDate.setText("There are no available terrains.");
            editButton.setDisable(true);
            comboBox.setDisable(true);
        } else {
            labelDate.setText("Date modified: \n" +  Main.getTerrain(currentTerrain).getDateString());
        }

        flowPane.getChildren().addAll(
                labelTitle,
                comboBox,
                editButton,
                newTerrainButton,
                backButton
        );

        anchorPane.getChildren().addAll(
                flowPane,
                anchorPaneC
        );

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = editButton.isDisabled() ? newTerrainButton : editButton;
    }

    private void handleEditButton (MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(0);
        }
    }
    private void handleEditButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(0);
        }
    }

    private void handleNewTerrainButton (MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleNewTerrainButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(2);
        }
    }
    private void handleBackButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(2);
        }
    }

    private void handleButton(int code){
            Main.getMediaPlayer().playButtonSound();
        if (code == 0){ // edit
            navigateEditorSubscene();
        } else if (code == 1) { // new
            navigateNewTerrainSubscene();
        } else if (code == 2) { // back
            navigateMainMenuSubscene();
        }
    }
}
