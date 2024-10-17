package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.*;
import com.example.rollingBall.entities.Player;
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

public class NewPlayerSubScene extends SubSceneMenu {

    private LabelTitleC labelTitle;
    private Label labelError;
    private ButtonC backButton;
    private ButtonC newPlayerButton;
    private TextField textField;
    private AnchorPaneC anchorPaneC;

    public NewPlayerSubScene(Group root, Stage stage) {
        super(root, stage);

        labelTitle = new LabelTitleC("Add New Player");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        textField = new TextFieldC();
        textField.setMaxWidth(Const.MENU_BUTTON_WIDTH);

        labelError = new LabelC("");
        labelError.setMaxWidth(Const.MENU_BUTTON_WIDTH);
        labelError.setTextAlignment(TextAlignment.CENTER);

        newPlayerButton = new ButtonC("Enter");
        newPlayerButton.addEventHandler( MouseEvent.ANY, this::handleEnterButton);
        newPlayerButton.addEventHandler( KeyEvent.ANY, this::handleEnterButton2);

        backButton = new ButtonC("Cancel");
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
                newPlayerButton,
                backButton
        );

        anchorPane.getChildren().addAll(flowPane, anchorPaneC);

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = newPlayerButton;
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
            Main.getMediaPlayer().playButtonSound();
            navigatePlayerSubscene();
        } else if (code == 1) { // enter
            addPlayer();
        }
    }

    private void addPlayer() {
        if (textField.getText().equals("")){
            Main.getMediaPlayer().playButton3Sound();
        } else if (textField.getText().length() > Const.MAX_CHARACTERS_IN_NAME){
            Main.getMediaPlayer().playButton3Sound();
            labelError.setText("Player name can't be longer than " + Const.MAX_CHARACTERS_IN_NAME + " characters!");
        } else if (!addNewPlayer(textField.getText())){
            labelError.setText("This player already exists!");
            Main.getMediaPlayer().playButton3Sound();
        } else {
            navigatePlayerSubscene();
            Main.getMediaPlayer().playButtonSound();
        }
    }

    public boolean addNewPlayer(String player) {
        player = player.toUpperCase();
        List<Player> players = Main.getPlayers();
        for (Player p: players) {
            if (player.equals(p.getName())){
                return false;
            }
        }
        Main.addNewPlayer(player);
        return true;
    }
}
