package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.AnchorPaneC;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.LabelC;
import com.example.rollingBall.controls.LabelTitleC;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AboutSubScene extends SubSceneMenu {

    private Label labelTitle;
    private Label label;
    private ButtonC backButton;
    private AnchorPaneC anchorPaneC;

    public AboutSubScene(Group root, Stage stage) {
        super(root, stage);

        labelTitle = new LabelTitleC("About");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        label = new LabelC("RollingBall v0.5\n " +
                "Diplomski rad osnovnih akademskih studija\n" +
                "Video igra u kojoj je osnovni cilj ubaciti lopticu u " +
                "određenu rupu i pritom sakupiti što veći broj poena\n" +
                "Autor: Igor Stevanoski\n" +
                "Mentor: Igor Tartalja\n" +
                "Univerzitet u Beogradu - Elektrotehnički fakultet, 2023.");
        label.setPrefWidth(Const.MENU_BUTTON_WIDTH * 3);
        label.setFont(new Font("Segoe UI Black", Const.FONT_SIZE));

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton2);

        anchorPaneC = new AnchorPaneC(true);
        anchorPaneC.setMaxWidth(Const.MENU_BUTTON_WIDTH * 2);
        double mainWidth = Main.getCurrent_window_width();
        double left = mainWidth / 2 - Const.MENU_BUTTON_WIDTH / 2;
        double top = (Main.getCurrent_window_height() - anchorPaneC.getPrefHeight()) / 2;
        AnchorPane.setRightAnchor(anchorPaneC, left);
        AnchorPane.setTopAnchor(anchorPaneC, top);
        anchorPaneC.addToFlowPane(label);

        flowPane.getChildren().addAll(
                labelTitle,
                anchorPaneC,
                backButton
        );
        anchorPane.getChildren().add(flowPane);

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = backButton;
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            Main.getMediaPlayer().playButtonSound();
            navigateMainMenuSubscene();
        }
    }
    private void handleBackButton2(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            Main.getMediaPlayer().playButtonSound();
            navigateMainMenuSubscene();
        }
    }
}
