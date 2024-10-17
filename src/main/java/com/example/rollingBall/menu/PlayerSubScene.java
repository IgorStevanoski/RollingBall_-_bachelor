package com.example.rollingBall.menu;

import com.example.rollingBall.Main;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.ComboBoxC;
import com.example.rollingBall.controls.LabelTitleC;
import com.example.rollingBall.entities.Player;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PlayerSubScene extends SubSceneMenu {

    private LabelTitleC labelTitle;
    private ButtonC backButton;
    private ButtonC newPlayerButton;
    private ComboBoxC comboBox;
    private List<String> players = new ArrayList<>();
    private static String currentPlayer = "Guest";

    public PlayerSubScene(Group root, Stage stage) {
        super(root, stage);

        currentPlayer = Main.getCurrentPlayer().getName();

        List<Player> playerz = Main.getPlayers();
        for (Player p: playerz) {
            this.players.add( p.getName() );
        }

        labelTitle = new LabelTitleC("Choose Player");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        comboBox = new ComboBoxC(FXCollections.observableArrayList(players));
        comboBox.setValue(currentPlayer);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        currentPlayer = (String)comboBox.getValue();
                        Main.setCurrentPlayer(currentPlayer);
                        Main.getMediaPlayer().playButton4Sound();
                        Main.getMediaPlayer().playStopMusic(Main.isMusic_on());
                    }
                };
        comboBox.setOnAction(event);

        newPlayerButton = new ButtonC("Add New Player");
        newPlayerButton.addEventHandler( MouseEvent.ANY, this::handleNewPlayerButton);
        newPlayerButton.addEventHandler( KeyEvent.ANY, this::handleNewPlayerButton);

        backButton = new ButtonC("OK");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton);

        flowPane.getChildren().addAll(
                labelTitle,
                comboBox,
                newPlayerButton,
                backButton
        );

        anchorPane.getChildren().add(flowPane);

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = backButton;
    }

    private void handleNewPlayerButton (MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(0);
        }
    }
    private void handleNewPlayerButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(0);
        }
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleBackButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }

    private void handleButton(int code){
        Main.getMediaPlayer().playButtonSound();
        if (code == 0) { // new
            navigateNewPlayerSubscene();
        } else if (code == 1) { // back
            navigateMainMenuSubscene();
        }
    }
}
