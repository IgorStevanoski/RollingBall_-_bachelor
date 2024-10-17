package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.*;
import com.example.rollingBall.entities.Terrain;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class NewGameSubScene extends SubSceneMenu {

    private Label labelBallSelect;
    private ImageView []imageView;
    private Label labelBallType;
    private Label labelMultiplier;

    private Label labelTitle;
    private Label labelPreview;
    private Label labelCreator;
    private Label labelDate;
    private Button backButton;
    private List<String> terrains = new ArrayList<>();
    private static String currentTerrain = "TEST";
    private PreviewTerrain preview;
    private Terrain terrain;
    private int difficulty = 0;

    private static final String[] ballSpeed = {"SLOW", "NORMAL", "FAST"};
    private static final double[] ballMultiplier = {1.0, 1.2, 1.5};
    private static final int CHOICE_COUNT = 3;
    private static final double IMAGE_WIDTH = 64.;
    private static final double IMAGE_WIDTH_PICKED = 80.;

    public NewGameSubScene(Group root, Stage stage) {
        super(root, stage);

        List<Terrain> terrainz = Main.getTerrains();
        for (Terrain t: terrainz) {
            this.terrains.add( t.getName() );
        }
        if (Main.getCurrentPlayer().getCurrentTerrain() != null){
            currentTerrain = terrains.get(0);
            this.terrain = terrainz.get(0);
            for (String s: terrains){// moguce da se desi da ostane teren koji je obrisan
                if (s.equals(Main.getCurrentPlayer().getCurrentTerrain().getName())){
                    currentTerrain = s;
                    break;
                }
            }
        }

        labelTitle = new LabelTitleC("Choose Terrain");

        ComboBox comboBox = new ComboBoxC(FXCollections.observableArrayList(terrains));
        comboBox.setValue(currentTerrain);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        currentTerrain = (String)comboBox.getValue();
                        terrain = Main.getTerrain(currentTerrain);
                        //Main.setCurrentPlayerTerrain(currentTerrain);
                        //preview.update(Main.getCurrentPlayer().getCurrentTerrain());
                        preview.update(terrain);
                        labelCreator.setText("Creator: \n" + Main.getCurrentPlayer().getCurrentTerrain().getPlayer());
                        labelDate.setText("Date modified: \n" + Main.getCurrentPlayer().getCurrentTerrain().getDateString());
                        Main.getMediaPlayer().playButton4Sound();
                    }
                };
        comboBox.setOnAction(event);

        Button startGameButton = new ButtonC("Start game");
        startGameButton.addEventHandler( MouseEvent.ANY, this::handleStartGameButton);
        startGameButton.addEventHandler( KeyEvent.ANY, this::handleStartGameButton);

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton);

        preview = new PreviewTerrain(Main.getTerrain(currentTerrain), Const.MENU_BUTTON_WIDTH);

        BorderPane borderPane = new BorderPane(preview.getSubScene());
        borderPane.setBackground(new Background(new BackgroundImage(
                Const.PANE2_TEXTURE,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));
        borderPane.setBorder(new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(8, 0, 8, 0))));

        labelPreview = new LabelC("Preview");
        labelCreator = new LabelSmallC("Creator: \n" + Main.getCurrentPlayer().getCurrentTerrain().getPlayer());
        labelCreator.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelCreator.setTextAlignment(TextAlignment.CENTER);
        labelDate = new LabelSmallC("Date modified: \n" + Main.getCurrentPlayer().getCurrentTerrain().getDateString());
        labelDate.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelDate.setTextAlignment(TextAlignment.CENTER);

        //Flowpane
        imageView = new ImageView[CHOICE_COUNT];
        this.difficulty = Main.getCurrentPlayer().getDifficulty();

        labelBallSelect = new LabelC("Ball Select");
        for (int i = 0; i < CHOICE_COUNT; i++) {
            imageView[i] = new ImageView(Const.IMAGE_BALL);
            imageView[i].setFitWidth(IMAGE_WIDTH);
            imageView[i].setFitHeight(IMAGE_WIDTH);
            imageView[i].addEventHandler( MouseEvent.ANY, this::handleBallChoice);
        }
        imageView[1].setImage(Const.IMAGE_BALL_TYPE2);
        imageView[2].setImage(Const.IMAGE_BALL_TYPE3);
        imageView[difficulty].setFitWidth(IMAGE_WIDTH_PICKED);
        imageView[difficulty].setFitHeight(IMAGE_WIDTH_PICKED);
        labelBallType = new LabelSmallC("Ball type: " + ballSpeed[difficulty]);
        labelMultiplier = new LabelSmallC("Speed & Points\nMultiplier: x " + ballMultiplier[difficulty]);

        AnchorPaneC anchorPaneLeft = new AnchorPaneC(false, Const.MENU_PANE_WIDTH, Const.MENU_PANE_WIDTH * 2);
        double mainWidth = Main.getCurrent_window_width();
        double left = mainWidth / 2 - Const.MENU_BUTTON_WIDTH / 2 - Const.MENU_PANE_WIDTH;
        double top = (Main.getCurrent_window_height() - anchorPaneLeft.getPrefHeight()) / 2;
        double topRight = (Main.getCurrent_window_height() - Const.MENU_PANE_WIDTH * 2) / 2;
        AnchorPane.setLeftAnchor(anchorPaneLeft, left / 2);
        AnchorPane.setTopAnchor(anchorPaneLeft, top);
        anchorPaneLeft.getFlowPane().setHgap(20);
        anchorPaneLeft.getFlowPane().setAlignment(Pos.CENTER);
        anchorPaneLeft.addAllToFlowPane(labelBallSelect);
        anchorPaneLeft.addAllToFlowPane(imageView);
        anchorPaneLeft.addToFlowPane(labelBallType);
        anchorPaneLeft.addToFlowPane(labelMultiplier);

        AnchorPaneC anchorPaneRight = new AnchorPaneC(false, Const.MENU_PANE_WIDTH, Const.MENU_PANE_WIDTH * 3);
        anchorPaneRight.getFlowPane().setPrefHeight(Const.MENU_PANE_WIDTH * 2);
        AnchorPane.setLeftAnchor(anchorPaneRight, mainWidth / 2 + left / 2 + Const.MENU_BUTTON_WIDTH / 2);
        AnchorPane.setTopAnchor(anchorPaneRight, topRight);
        anchorPaneRight.getFlowPane().setHgap(20);
        anchorPaneRight.getFlowPane().setAlignment(Pos.CENTER);
        anchorPaneRight.addAllToFlowPane(labelPreview);
        anchorPaneRight.addAllToFlowPane(borderPane);
        anchorPaneRight.addToFlowPane(labelCreator);
        anchorPaneRight.addToFlowPane(labelDate);

        FlowPane flowPaneMiddle = new FlowPane(Orientation.VERTICAL, VGAP, Const.MENU_VGAP);
        flowPaneMiddle.setColumnHalignment(HPos.CENTER);
        flowPaneMiddle.setAlignment(Pos.CENTER);
        flowPaneMiddle.setPrefWrapLength(Main.getCurrent_window_height());
        flowPaneMiddle.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        AnchorPane.setLeftAnchor(flowPaneMiddle, Main.getCurrent_window_width() / 2 - flowPaneMiddle.getPrefWidth() / 2);
        AnchorPane.setTopAnchor(flowPaneMiddle, 0.);
        flowPaneMiddle.getChildren().addAll(
                labelTitle, comboBox, startGameButton, backButton
        );

        anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(
                flowPaneMiddle,
                anchorPaneRight,
                anchorPaneLeft
        );

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );

        focusedButton = backButton;
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(0);
        }
    }
    private void handleBackButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(0);
        }
    }
    private void handleStartGameButton (MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleStartGameButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }

    private void handleBallChoice(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            int index = 0;
            for (int i = 0; i < CHOICE_COUNT; i++) {
                if (event.getSource().equals(imageView[i])){
                    index = i;
                    imageView[i].setFitWidth(IMAGE_WIDTH_PICKED);
                    imageView[i].setFitHeight(IMAGE_WIDTH_PICKED);
                    //Main.getCurrentPlayer().setDifficulty(index);
                    difficulty = index;
                } else {
                    imageView[i].setFitWidth(IMAGE_WIDTH);
                    imageView[i].setFitHeight(IMAGE_WIDTH);
                }
            }
            labelBallType.setText("Ball type: " + ballSpeed[index]);
            labelMultiplier.setText("Speed & Points\nMultiplier: x " + ballMultiplier[index]);
        }
    }

    private void handleButton(int code){
        if (code == 0){ // backButton
            navigateMainMenuSubscene();
            Main.getMediaPlayer().playButtonSound();
        } else if (code == 1){ // gameButton
            Main.getMediaPlayer().playButtonSound();
            Main.getCurrentPlayer().setDifficulty(difficulty);
            Main.setCurrentPlayerTerrain(currentTerrain);
            Main.resetTerrain();
            Main.setActiveGame(true);
            navigateGameSubscene();
        }
    }
}
