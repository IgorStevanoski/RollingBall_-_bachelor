package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.*;
import com.example.rollingBall.entities.Terrain;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.List;

public class EditorNewTerrainSubScene extends SubSceneMenu {

    private LabelTitleC labelTitle;
    private Label labelError;
    private ButtonC backButton;
    private ButtonC newTerrainButton;
    private TextField textField;
    private AnchorPaneC anchorPaneC;

    public EditorNewTerrainSubScene(Group root, Stage stage) {
        super(root, stage);

        labelTitle = new LabelTitleC("Add New Terrain");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        textField = new TextFieldC();

        labelError = new LabelC("");
        labelError.setMaxWidth(Const.MENU_BUTTON_WIDTH);
        labelError.setTextAlignment(TextAlignment.CENTER);

        newTerrainButton = new ButtonC("Enter");
        newTerrainButton.addEventHandler( MouseEvent.ANY, this::handleEnterButton);
        newTerrainButton.addEventHandler( KeyEvent.ANY, this::handleEnterButton2);

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton2);

        anchorPaneC = new AnchorPaneC(true);
        double mainWidth = Main.getCurrent_window_width();
        double left = mainWidth / 2 - Const.MENU_BUTTON_WIDTH / 2 - Const.MENU_PANE_WIDTH;
        double top = (Main.getCurrent_window_height() - anchorPaneC.getPrefHeight()) / 2;
        AnchorPane.setRightAnchor(anchorPaneC, left / 2);
        AnchorPane.setTopAnchor(anchorPaneC, top);
        anchorPaneC.addToFlowPane(labelError);

        flowPane.getChildren().addAll(
                labelTitle,
                textField,
                newTerrainButton,
                backButton
        );

        anchorPane.getChildren().addAll(flowPane, anchorPaneC);

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = newTerrainButton;
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(0);
        }
    }
    private void handleBackButton2(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(0);
        }
    }
    private void handleEnterButton (MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleEnterButton2 (KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }

    private void handleButton(int code){
        if (code == 0){ // back
            navigateEditorChooseSubscene();
            Main.getMediaPlayer().playButtonSound();
        } else if (code == 1) { // enter
            addTerrain();
        }
    }

    private void addTerrain() {
        if (textField.getText().equals("")){
            Main.getMediaPlayer().playButton3Sound();
        } else if (textField.getText().length() > Const.MAX_CHARACTERS_IN_NAME){
            Main.getMediaPlayer().playButton3Sound();
            labelError.setText("Terrain name can't be longer than " + Const.MAX_CHARACTERS_IN_NAME + " characters!");
        } else if (!addNewTerrain(textField.getText())){
            labelError.setText("This terrain already exists!");
            Main.getMediaPlayer().playButton3Sound();
        } else {
            navigateEditorChooseSubscene();
            Main.getMediaPlayer().playButtonSound();
        }
    }

    public boolean addNewTerrain(String terrain) {
        terrain = terrain.toUpperCase();
        List<Terrain> terrains = Main.getTerrains();
        for (Terrain t: terrains) {
            if (terrain.equals(t.getName())){
                return false;
            }
        }
        Main.addNewTerrain(terrain);
        return true;
    }
}
