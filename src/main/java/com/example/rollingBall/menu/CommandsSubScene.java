package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.AnchorPaneC;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.LabelTitleC;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class CommandsSubScene extends SubSceneMenu {

    private AnchorPaneC anchorPaneC;
    private Label labelTitle;
    private ImageView imageView;
    private ButtonC backButton;
    private ButtonC gameButton;
    private ButtonC editorButton;

    public CommandsSubScene(Group root, Stage stage) {
        super(root, stage);

        double imageWidth  = 1200;
        double imageHeight = 500;

        flowPane.setPrefWidth(Main.getCurrent_window_width());

        FlowPane flowPaneMini = new FlowPane(Orientation.HORIZONTAL, VGAP, Const.MENU_VGAP);
        flowPaneMini.setColumnHalignment(HPos.CENTER);
        flowPaneMini.setAlignment(Pos.CENTER);
        flowPaneMini.setPrefWrapLength(Main.getCurrent_window_height());
        flowPaneMini.setPrefWidth(Main.getCurrent_window_width());

        labelTitle = new LabelTitleC("Game commands");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        imageView = new ImageView(Const.CommandsImage);
        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);

        anchorPaneC = new AnchorPaneC(true);
        anchorPaneC.setMaxWidth(imageWidth);
        anchorPaneC.setMaxHeight(imageHeight);
//        double mainWidth = Main2.getCurrent_window_width();
//        double left = mainWidth / 2 - imageWidth / 2;
//        double top = (Main2.getCurrent_window_height() - imageHeight) / 2;
//        AnchorPane.setRightAnchor(anchorPaneC, left);
//        AnchorPane.setTopAnchor(anchorPaneC, top);
        anchorPaneC.addToFlowPane(imageView);

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton2);

        gameButton = new ButtonC("Game");
        gameButton.addEventHandler( MouseEvent.ANY, this::handleGameButton);
        gameButton.addEventHandler( KeyEvent.ANY, this::handleGameButton2);

        editorButton = new ButtonC("Editor");
        editorButton.addEventHandler( MouseEvent.ANY, this::handleEditorButton);
        editorButton.addEventHandler( KeyEvent.ANY, this::handleEditorButton2);

        flowPaneMini.getChildren().addAll(
                gameButton,
                editorButton
        );

        flowPane.getChildren().addAll(
                labelTitle,
                anchorPaneC,
                flowPaneMini,
                backButton
        );

        rootSubScene = new Group();
        anchorPane.getChildren().addAll(
                flowPane
        );
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = backButton;
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleBackButton2(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }

    private void handleGameButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(2);
        }
    }
    private void handleGameButton2(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(2);
        }
    }

    private void handleEditorButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(3);
        }
    }
    private void handleEditorButton2(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(3);
        }
    }

    private void handleButton(int code){
        if (code == 1){ // backButton
            Main.getMediaPlayer().playButtonSound();
            navigateMainMenuSubscene();
        } else if (code == 2){ // gameButton
            Main.getMediaPlayer().playButtonSound();
            imageView.setImage(Const.CommandsImage);
            labelTitle.setText("Game commands");
        } else if (code == 3){ // editorButton
            Main.getMediaPlayer().playButtonSound();
            imageView.setImage(Const.CommandsEditorImage);
            labelTitle.setText("Editor commands");
        }
    }
}
