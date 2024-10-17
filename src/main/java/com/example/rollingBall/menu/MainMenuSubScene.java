package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.AnchorPaneC;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.LabelC;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainMenuSubScene extends SubSceneMenu {

    private AnchorPaneC anchorPaneC;
    private FlowPane flowPane;
    private ButtonC newGameButton;
    private ButtonC continueButton;
    private ButtonC leaderBoardsButton;
    private ButtonC settingsButton;
    private ButtonC editorButton;
    private ButtonC helpButton;
    private ButtonC commandsButton;
    private ButtonC aboutButton;
    private ButtonC quitButton;

    private ButtonC changePlayerButton;
    private Label labelPlayer;
    private Label labelName;
    private Label labelRollingBall;
    private List<ButtonC> buttonCList;
    private static int lastButtonClicked = -1;

    public MainMenuSubScene(Group root, Stage stage) {
        super(root, stage);

        anchorPane = new AnchorPane();
        rootSubScene = new Group();

        loadContent();
    }

    public void loadContent() {

        anchorPane.setPrefWidth(Main.getCurrent_window_width());
        anchorPane.setPrefHeight(Main.getCurrent_window_height());

        buttonCList = new ArrayList<>();

        labelRollingBall = new Label("Rolling Ball");
        Font font = Const.ROLLINGBALL_FONT;
        if (Main.getCurrent_window_height() < 900) {
            font = Const.ROLLINGBALL_FONT_SMALL;
        }
        labelRollingBall.setFont(font);
        labelRollingBall.setTextFill(Color.DARKRED);
        labelRollingBall.setAlignment(Pos.CENTER);
        labelRollingBall.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        //labelRollingBall.setEffect(new DropShadow(BlurType.ONE_PASS_BOX, Const.BUTTON_COLOR, 5, 5, 2, 2));
        newGameButton = new ButtonC("New Game");
        newGameButton.addEventHandler( MouseEvent.ANY, this::handleNewGameButton);
        newGameButton.addEventHandler( KeyEvent.ANY, this::handleNewGameButton);
        continueButton = new ButtonC("Continue");
        continueButton.addEventHandler( MouseEvent.ANY, this::handleContinueButton);
        continueButton.addEventHandler( KeyEvent.ANY, this::handleContinueButton);
        continueButton.setDisable(!Main.isActiveGame());
        leaderBoardsButton = new ButtonC("Leaderboards");
        leaderBoardsButton.addEventHandler( MouseEvent.ANY, this::handleLeaderBoardsButton);
        leaderBoardsButton.addEventHandler( KeyEvent.ANY, this::handleLeaderBoardsButton);
        settingsButton = new ButtonC("Settings");
        settingsButton.addEventHandler( MouseEvent.ANY, this::handleSettingsButton);
        settingsButton.addEventHandler( KeyEvent.ANY, this::handleSettingsButton);
        editorButton = new ButtonC("Editor");
        editorButton.addEventHandler( MouseEvent.ANY, this::handleEditorButton);
        editorButton.addEventHandler( KeyEvent.ANY, this::handleEditorButton);
        helpButton = new ButtonC("Help");
        helpButton.addEventHandler( MouseEvent.ANY, this::handleHelpButton);
        helpButton.addEventHandler( KeyEvent.ANY, this::handleHelpButton);
        commandsButton = new ButtonC("Commands");
        commandsButton.addEventHandler( MouseEvent.ANY, this::handleCommandsButton);
        commandsButton.addEventHandler( KeyEvent.ANY, this::handleCommandsButton);
        aboutButton = new ButtonC("About");
        aboutButton.addEventHandler( MouseEvent.ANY, this::handleAboutButton);
        aboutButton.addEventHandler( KeyEvent.ANY, this::handleAboutButton);
        quitButton = new ButtonC("Quit");
        quitButton.addEventHandler( MouseEvent.ANY, this::handleQuitButton);
        quitButton.addEventHandler( KeyEvent.ANY, this::handleQuitButton);

        flowPane = new FlowPane(Orientation.VERTICAL, VGAP, Const.MENU_VGAP);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        AnchorPane.setLeftAnchor(flowPane, Main.getCurrent_window_width() / 2 - flowPane.getPrefWidth() / 2);
        AnchorPane.setTopAnchor(flowPane, 0.);

        labelPlayer = new LabelC("Player:");
        labelName = new LabelC(Main.getCurrentPlayer().getName());

        changePlayerButton = new ButtonC("Change Player");
        changePlayerButton.addEventHandler( MouseEvent.ANY, this::handlePlayerButton);
        changePlayerButton.addEventHandler( KeyEvent.ANY, this::handlePlayerButton);

        anchorPaneC = new AnchorPaneC(true);
        double mainWidth = Main.getCurrent_window_width();
        double left = mainWidth / 2 - Const.MENU_BUTTON_WIDTH / 2 - Const.MENU_PANE_WIDTH;
        double top = (Main.getCurrent_window_height() - anchorPaneC.getPrefHeight()) / 2;
        AnchorPane.setRightAnchor(anchorPaneC, left / 2);
        AnchorPane.setTopAnchor(anchorPaneC, top);
        anchorPaneC.addToFlowPane(labelPlayer);
        anchorPaneC.addToFlowPane(labelName);
        anchorPaneC.addToFlowPane(changePlayerButton);

        flowPane.getChildren().addAll(
                labelRollingBall,
                newGameButton,
                continueButton,
//                playerButton,
                leaderBoardsButton,
                settingsButton,
                editorButton,
                helpButton,
                commandsButton,
                aboutButton,
                quitButton
        );

        this.anchorPane.getChildren().addAll(
                anchorPaneC,
                flowPane
        );

        //rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );
        rootSubScene3D = new Group();

        buttonCList.add(newGameButton);
        buttonCList.add(continueButton);
        buttonCList.add(changePlayerButton);
        buttonCList.add(leaderBoardsButton);
        buttonCList.add(settingsButton);
        buttonCList.add(editorButton);
        buttonCList.add(helpButton);
        buttonCList.add(commandsButton);
        buttonCList.add(aboutButton);
        buttonCList.add(quitButton);

        if (lastButtonClicked >= 0)
            focusedButton = buttonCList.get(lastButtonClicked);
    }

    private void handleNewGameButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(0);
        }
    }
    private void handleNewGameButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(0);
        }
    }
    private void handleContinueButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleContinueButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }
    private void handlePlayerButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(2);
        }
    }
    private void handlePlayerButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(2);
        }
    }
    private void handleLeaderBoardsButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(3);
        }
    }
    private void handleLeaderBoardsButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(3);
        }
    }
    private void handleSettingsButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(4);
        }
    }
    private void handleSettingsButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(4);
        }
    }
    private void handleEditorButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(5);
        }
    }
    private void handleEditorButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(5);
        }
    }
    private void handleHelpButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(6);
        }
    }
    private void handleHelpButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(6);
        }
    }
    private void handleCommandsButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(7);
        }
    }
    private void handleCommandsButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(7);
        }
    }
    private void handleAboutButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(8);
        }
    }
    private void handleAboutButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(8);
        }
    }
    private void handleQuitButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(9);
        }
    }
    private void handleQuitButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(9);
        }
    }

    private void handleButton(int code){
        Main.getMediaPlayer().playButtonSound();
        lastButtonClicked = code;
        if (code == 0){         // handleNewGameButton
            navigateNewGameSubscene();
        } else if (code == 1) { // handleContinueButton
            navigateContinueSubscene();
        } else if (code == 2) { // handlePlayerButton
            navigatePlayerSubscene();
        } else if (code == 3) { // handleLeaderBoardsButton
            navigateLeaderboardsSubscene();
        } else if (code == 4) { // handleSettingsButton
            navigateSettingsSubscene();
        } else if (code == 5) { // handleEditorButton
            navigateEditorChooseSubscene();
        } else if (code == 6) { // handleHelpButton
            navigateHelpSubscene();
        } else if (code == 7) { // handleCommandsButton
            navigateCommandsSubscene();
        } else if (code == 8) { // handleAboutButton
            navigateAboutSubscene();
        } else if (code == 9) { // handleQuitButton
            navigateQuitSubscene();
        }
    }
}
