package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.AnchorPaneC;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.LabelC;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class PauseGameLevelDoneMenu {

    private static final double BUTTON_WIDTH = 150;
    private static final double BUTTON_HEIGHT = 30;
    private static final double FONT_SIZE_TITLE = 40;
    private static final double FONT_SIZE = 30;
    private static final double FONT_SIZE_SMALL = 20;
    private static final double VGAP = 50.0;
    private static final double buttonHeight = 30.0;

    private Group  root;
    private Label  labelTitle;
    private Button continueButton;
    private Button newGameButton;
    private Button mainMenuButton;
    GameSubScene gameSubScene;

    public PauseGameLevelDoneMenu(GameSubScene gameSubScene) {
        this.gameSubScene = gameSubScene;

        labelTitle = new LabelC("Level Done!");

        continueButton = new ButtonC("Continue");
        continueButton.addEventHandler( MouseEvent.ANY, this::handleContinueButton);
        continueButton.addEventHandler( KeyEvent.ANY, this::handleContinueButton);

        newGameButton = new ButtonC("New Game");
        newGameButton.addEventHandler( MouseEvent.ANY, this::handleNewGameButton);
        newGameButton.addEventHandler( KeyEvent.ANY, this::handleNewGameButton);

        mainMenuButton = new ButtonC("Main Menu");
        mainMenuButton.addEventHandler( MouseEvent.ANY, this::handleMainMenuButton);
        mainMenuButton.addEventHandler( KeyEvent.ANY, this::handleMainMenuButton);

        Stop [] stops = new Stop[] {
                new Stop(0, Color.BLACK),
                new Stop(1, Color.TRANSPARENT)
        };
        RadialGradient rg = new RadialGradient(0, 0, 0.5, 0.5, 0.8,
                true, CycleMethod.NO_CYCLE, stops);
        Rectangle background = new Rectangle(Main.getCurrent_window_width(), Main.getCurrent_window_height(), rg);

        AnchorPaneC anchorPaneC = new AnchorPaneC(true);
        AnchorPane.setLeftAnchor(anchorPaneC, (Main.getCurrent_window_width() - Const.MENU_PANE_WIDTH) / 2);
        AnchorPane.setTopAnchor(anchorPaneC, (Main.getCurrent_window_height() - Const.MENU_PANE_WIDTH) / 2);
        anchorPaneC.addAllToFlowPane(
                labelTitle,
                continueButton,
                newGameButton,
                mainMenuButton
        );

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(anchorPaneC);

        root = new Group();
        root.getChildren().addAll(
                background,
                anchorPane
        );
    }

    public Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public void setFocus() {
        continueButton.requestFocus();
    }

    public void handleContinueButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(0);
        }
    }
    private void handleContinueButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(0);
        }
    }
    public void handleNewGameButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleNewGameButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }
    public void handleMainMenuButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(2);
        }
    }
    private void handleMainMenuButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(2);
        }
    }

    private void handleButton(int code){
        if (code == 0){ // continue
            Main.getMediaPlayer().playButtonSound();
            gameSubScene.handleLevelContinueButton();
        } else if (code == 1){ // newGame
            Main.getMediaPlayer().playButtonSound();
            gameSubScene.handleNewGameButton();
        } else if (code == 2){ // mainMenu
            Main.getMediaPlayer().playButtonSound();
            gameSubScene. handleMainMenuButton();
        }
    }
}
