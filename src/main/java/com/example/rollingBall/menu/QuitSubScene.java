package com.example.rollingBall.menu;

import com.example.rollingBall.Main;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.LabelTitleC;
import com.example.rollingBall.prepare.GamePrepare;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class QuitSubScene extends SubSceneMenu {

    private Label    label;
    private ButtonC   yesButton;
    private ButtonC   noButton;

    public QuitSubScene(Group root, Stage stage) {
        super(root, stage);

        label = new LabelTitleC("Are you sure you want to quit?");
        label.setPrefWidth(Main.getCurrent_window_width());

        yesButton = new ButtonC("Yes");
        yesButton.addEventHandler( MouseEvent.ANY, this::handleYesButton);
        yesButton.addEventHandler( KeyEvent.ANY, this::handleYesButton);

        noButton = new ButtonC("No");
        noButton.addEventHandler( MouseEvent.ANY, this::handleNoButton);
        noButton.addEventHandler( KeyEvent.ANY, this::handleNoButton);

        flowPane.getChildren().addAll(
                label,
                yesButton,
                noButton
        );

        anchorPane.getChildren().add(flowPane);

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = noButton;
    }

    public Group getRoot() {
        return rootSubScene;
    }

    private void handleYesButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            writeToFile();
        }
    }
    private void handleYesButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            writeToFile();
        }
    }

    private void handleNoButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            Main.getMediaPlayer().playButtonSound();
            navigateMainMenuSubscene();
        }
    }
    private void handleNoButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            Main.getMediaPlayer().playButtonSound();
            navigateMainMenuSubscene();
        }
    }

    private void writeToFile(){
        Main.getMediaPlayer().playButtonSound();
        new GamePrepare().writePlayers();
        new GamePrepare().writeTerrains();
        Main.setScreen_positionX(stage.getX());
        Main.setScreen_positionY(stage.getY());
        Main.setMaximized(stage.isMaximized());
        new GamePrepare().writeMisc();
        new GamePrepare().writeConfig();
        exitProgram();
    }
}
