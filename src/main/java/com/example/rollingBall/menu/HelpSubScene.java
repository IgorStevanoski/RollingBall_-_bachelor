package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.LabelC;
import com.example.rollingBall.controls.LabelSmallC;
import com.example.rollingBall.controls.LabelTitleC;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelpSubScene extends SubSceneMenu{

    private AnchorPane anchorPane;
    private Label      labelTitle;
    private Label label;
    private ButtonC backButton;

    private static final double labelPaddingHeading = 10;
    private static final double labelPaddingDescription = 20;

    public HelpSubScene(Group root, Stage stage) {
        super(root, stage);

        Border borderTopLess = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(4, 8, 8, 8)));
        Border borderBottomless = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(8, 8, 4, 8)));
        Border borderNOrightBottom = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(8, 4, 4, 8)));
        Border borderNOrighttop = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(4, 4, 8, 8)));
        Border borderNOleftBottom = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(8, 8, 4, 4)));
        Border borderNOlefttop = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(4, 8, 8, 4)));
        Border borderNObottomTop = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(4, 8, 4, 8)));
        Border borderFull = new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(8, 8, 8, 8)));

        anchorPane = new AnchorPane();
        AnchorPane anchorPaneInner = new AnchorPane();
        anchorPaneInner.setMaxWidth(Const.MENU_HELP_WIDTH);

        labelTitle = new LabelTitleC("Help");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setBackground( new Background( new BackgroundFill(Color.TRANSPARENT, null, null)));
        scrollPane.setMaxWidth(Const.MENU_HELP_WIDTH);
        scrollPane.setPrefHeight(Main.getCurrent_window_height() - 4 * Const.MENU_BUTTON_HEIGHT);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setBorder(borderTopLess);

        // FLOWPANE1

        Label labelHowToPlay = new LabelC("How to play", 0);
        labelHowToPlay.setPrefWidth(Const.MENU_HELP_WIDTH);
        labelHowToPlay.setBackground(Const.PLANK_HUGE_BACKGROUND);
        labelHowToPlay.setPadding( new Insets(labelPaddingHeading, labelPaddingHeading, labelPaddingHeading, labelPaddingHeading));

        label = new LabelSmallC("Lead the ball to the right hole and advance to the next level by doing so. " +
                "Be careful, higher levels contain multiple balls. Each ball must be put in the matching hole. ", true);
        label.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        label.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        label.setPadding( new Insets(20, 20, 20, 20));
        label.setBackground(Const.PANE_BACKGROUND);
        label.setBorder(borderBottomless);

        ImageView imageView = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage1.png")));
        imageView.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView.setFitHeight(Const.MENU_BUTTON_WIDTH);

        Label labelTilt = new LabelSmallC("Move the ball by tilting the terrain in specific direction using arrow keys. ", true);
        labelTilt.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        labelTilt.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelTilt.setPadding( new Insets(20, 20, 20, 20));
        labelTilt.setBackground(Const.PANE_BACKGROUND);
        labelTilt.setBorder(borderNObottomTop);

        ImageView imageView2 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage2.png")));
        imageView2.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView2.setFitHeight(Const.MENU_BUTTON_WIDTH);

        Label labelMainGoal = new LabelSmallC("Main goal is to get as many points as possible and beat other players' records. " +
                "Gather points by advancing to the next level.", true);
        labelMainGoal.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        labelMainGoal.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelMainGoal.setPadding( new Insets(20, 20, 20, 20));
        labelMainGoal.setBackground(Const.PANE_BACKGROUND);
        labelMainGoal.setBorder(borderTopLess);

        ImageView imageView3 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage3.png")));
        imageView3.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView3.setFitHeight(Const.MENU_BUTTON_WIDTH);

        // FLOWPANE2

        Label labelInterface = new LabelC("Interface", 0);
        labelInterface.setPrefWidth(Const.MENU_HELP_WIDTH);
        labelInterface.setBackground(Const.PLANK_HUGE_BACKGROUND);
        labelInterface.setPadding( new Insets(labelPaddingHeading, labelPaddingHeading, labelPaddingHeading, labelPaddingHeading));

        ImageView imageView41 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage4.png")));
        imageView41.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView41.setFitHeight(Const.MENU_BUTTON_WIDTH);
        ImageView imageView42 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage5.png")));
        imageView42.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView42.setFitHeight(Const.MENU_BUTTON_WIDTH);

        Label labelLives = new LabelSmallC("You have a limited number of lives. When the ball falls off the terrain or is put " +
                "in the wrong hole you lose life.", true);
        labelLives.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelLives.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelLives.setPadding( new Insets(20, 20, 20, 20));
        labelLives.setBackground(Const.PANE_BACKGROUND);
        labelLives.setBorder(borderNOrightBottom);

        Label labelAcceleration = new LabelSmallC("On the bottom left, you can track acceleration.", true);
        labelAcceleration.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelAcceleration.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelAcceleration.setPadding( new Insets(20, 20, 20, 20));
        labelAcceleration.setBackground(Const.PANE_BACKGROUND);
        labelAcceleration.setBorder(borderNOleftBottom);

        Label labelTimer = new LabelSmallC("Timer on bottom shows how much time is left. Game is over when you run out of time.", true);
        labelTimer.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        labelTimer.setPrefHeight(Const.MENU_BUTTON_WIDTH / 2);
        labelTimer.setPadding( new Insets(20, 20, 20, 20));
        labelTimer.setBackground(Const.PANE_BACKGROUND);
        labelTimer.setBorder(borderTopLess);

        ImageView imageView5 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage6.png")));
        imageView5.setFitWidth(Const.MENU_HELP_WIDTH);
        imageView5.setFitHeight(Const.MENU_BUTTON_WIDTH / 2);

        // FLOWPANE3

        Label labelObstacles = new LabelC("Obstacles",0);
        labelObstacles.setPrefWidth(Const.MENU_HELP_WIDTH);
        labelObstacles.setBackground(Const.PLANK_HUGE_BACKGROUND);
        labelObstacles.setPadding( new Insets(labelPaddingHeading, labelPaddingHeading, labelPaddingHeading, labelPaddingHeading));

        ImageView imageView61 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage7.png")));
        imageView61.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView61.setFitHeight(Const.MENU_BUTTON_WIDTH);
        ImageView imageView62 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage8.png")));
        imageView62.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView62.setFitHeight(Const.MENU_BUTTON_WIDTH);
        ImageView imageView71 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage9.png")));
        imageView71.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView71.setFitHeight(Const.MENU_BUTTON_WIDTH);
        ImageView imageView72 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage10.png")));
        imageView72.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView72.setFitHeight(Const.MENU_BUTTON_WIDTH);

        Label labelWalls = new LabelSmallC("Walls are neutral elements which bounce the ball. There are square and round walls.", true);
        labelWalls.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelWalls.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelWalls.setPadding( new Insets(20, 20, 20, 20));
        labelWalls.setBackground(Const.PANE_BACKGROUND);
        labelWalls.setBorder(borderNOrightBottom);

        Label labelBounce = new LabelSmallC("Bouncy walls will bounce the ball much more than the regular ones.", true);
        labelBounce.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelBounce.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelBounce.setPadding( new Insets(20, 20, 20, 20));
        labelBounce.setBackground(Const.PANE_BACKGROUND);
        labelBounce.setBorder(borderNOleftBottom);

        Label labelSpiky = new LabelSmallC("Spiky balls take away one life upon colliding with the ball.", true);
        labelSpiky.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelSpiky.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelSpiky.setPadding( new Insets(20, 20, 20, 20));
        labelSpiky.setBackground(Const.PANE_BACKGROUND);
        labelSpiky.setBorder(borderNOrighttop);

        Label labelFalse = new LabelSmallC("False holes take away one life when the ball gets in and give negative points.", true);
        labelFalse.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelFalse.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelFalse.setPadding( new Insets(20, 20, 20, 20));
        labelFalse.setBackground(Const.PANE_BACKGROUND);
        labelFalse.setBorder(borderNOlefttop);

        // FLOWPANE 4

        Label labelBonuses = new LabelC("Bonuses", 0);
        labelBonuses.setPrefWidth(Const.MENU_HELP_WIDTH);
        labelBonuses.setBackground(Const.PLANK_HUGE_BACKGROUND);
        labelBonuses.setPadding( new Insets(labelPaddingHeading, labelPaddingHeading, labelPaddingHeading, labelPaddingHeading));

        ImageView imageView81 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage11.png")));
        imageView81.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView81.setFitHeight(Const.MENU_BUTTON_WIDTH);
        ImageView imageView82 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage12.png")));
        imageView82.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView82.setFitHeight(Const.MENU_BUTTON_WIDTH);
        ImageView imageView91 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage13.png")));
        imageView91.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView91.setFitHeight(Const.MENU_BUTTON_WIDTH);
        ImageView imageView92 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage14.png")));
        imageView92.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView92.setFitHeight(Const.MENU_BUTTON_WIDTH);

        Label labelCoins = new LabelSmallC("Coins grant extra points.", true);
        labelCoins.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelCoins.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelCoins.setPadding( new Insets(20, 20, 20, 20));
        labelCoins.setBackground(Const.PANE_BACKGROUND);
        labelCoins.setBorder(borderNOrightBottom);

        Label labelStopWatch = new LabelSmallC("Stopwatch grants extra time.", true);
        labelStopWatch.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelStopWatch.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelStopWatch.setPadding( new Insets(20, 20, 20, 20));
        labelStopWatch.setBackground(Const.PANE_BACKGROUND);
        labelStopWatch.setBorder(borderNOleftBottom);

        Label labelBonusBall = new LabelSmallC("Bonus ball grants extra life.", true);
        labelBonusBall.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelBonusBall.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelBonusBall.setPadding( new Insets(20, 20, 20, 20));
        labelBonusBall.setBackground(Const.PANE_BACKGROUND);
        labelBonusBall.setBorder(borderNOrighttop);

        Label labelIceToken = new LabelSmallC("Ice Token freezes spiky balls and lets the ball pass through " +
                "false holes without falling in for a limited time.", true);
        labelIceToken.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        labelIceToken.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelIceToken.setPadding( new Insets(20, 20, 20, 20));
        labelIceToken.setBackground(Const.PANE_BACKGROUND);
        labelIceToken.setBorder(borderNOlefttop);

        // FLOWPANE 5

        Label labelOther = new LabelC("Other", 0);
        labelOther.setPrefWidth(Const.MENU_HELP_WIDTH);
        labelOther.setBackground(Const.PLANK_HUGE_BACKGROUND);
        labelOther.setPadding( new Insets(labelPaddingHeading, labelPaddingHeading, labelPaddingHeading, labelPaddingHeading));

        Label labelLamp = new LabelSmallC("There is a lamp above terrain that can be turn off/on by pressing button 0.", true);
        labelLamp.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        labelLamp.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelLamp.setPadding( new Insets(20, 20, 20, 20));
        labelLamp.setBackground(Const.PANE_BACKGROUND);
        labelLamp.setBorder(borderBottomless);

        ImageView imageView10 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage15.png")));
        imageView10.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView10.setFitHeight(Const.MENU_BUTTON_WIDTH);

        Label labelCamera = new LabelSmallC("Switch beetwen two points of view: basic view (button 1) and bird's eye view " +
                "(buttons 2, 3, 4 - one for each ball).", true);
        labelCamera.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        labelCamera.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelCamera.setPadding( new Insets(20, 20, 20, 20));
        labelCamera.setBackground(Const.PANE_BACKGROUND);
        labelCamera.setBorder(borderNObottomTop);

        ImageView imageView11 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage16.png")));
        imageView11.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView11.setFitHeight(Const.MENU_BUTTON_WIDTH);

        Label labelPause = new LabelSmallC("Pause the game anytime by pressing the ESC button.", true);
        labelPause.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        labelPause.setPrefHeight(Const.MENU_BUTTON_WIDTH);
        labelPause.setPadding( new Insets(20, 20, 20, 20));
        labelPause.setBackground(Const.PANE_BACKGROUND);
        labelPause.setBorder(borderTopLess);

        ImageView imageView12 = new ImageView( new Image(Main.class.getClassLoader().getResourceAsStream("HelpImage17.png")));
        imageView12.setFitWidth(Const.MENU_BUTTON_WIDTH);
        imageView12.setFitHeight(Const.MENU_BUTTON_WIDTH);

        // --------------------

        FlowPane flowPaneInner = new FlowPane(Orientation.HORIZONTAL, 0,0);
        flowPaneInner.setMaxWidth(Const.MENU_HELP_WIDTH);
        flowPaneInner.setBackground( new Background(new BackgroundImage(
                Const.PANE2_TEXTURE,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));

        FlowPane flowPane1 = new FlowPane(Orientation.HORIZONTAL, 0,0);
        flowPane1.setColumnHalignment(HPos.CENTER);
        flowPane1.setAlignment(Pos.CENTER);
        flowPane1.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane1.setMaxWidth(Const.MENU_BUTTON_WIDTH * 3);

        FlowPane flowPane2 = new FlowPane(Orientation.HORIZONTAL, 0,0);
        flowPane2.setColumnHalignment(HPos.CENTER);
        flowPane2.setAlignment(Pos.CENTER);
        flowPane2.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane2.setMaxWidth(Const.MENU_BUTTON_WIDTH * 3);

        FlowPane flowPane3 = new FlowPane(Orientation.HORIZONTAL, 0,0);
        flowPane3.setColumnHalignment(HPos.CENTER);
        flowPane3.setAlignment(Pos.CENTER);
        flowPane3.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane3.setMaxWidth(Const.MENU_BUTTON_WIDTH * 3);

        FlowPane flowPane4 = new FlowPane(Orientation.HORIZONTAL, 0,0);
        flowPane4.setColumnHalignment(HPos.CENTER);
        flowPane4.setAlignment(Pos.CENTER);
        flowPane4.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane4.setMaxWidth(Const.MENU_BUTTON_WIDTH * 3);

        FlowPane flowPane5 = new FlowPane(Orientation.HORIZONTAL, 0,0);
        flowPane5.setColumnHalignment(HPos.CENTER);
        flowPane5.setAlignment(Pos.CENTER);
        flowPane5.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane5.setMaxWidth(Const.MENU_BUTTON_WIDTH * 3);

        flowPane1.getChildren().addAll(
                labelHowToPlay,
                label, imageView,
                labelTilt, imageView2,
                labelMainGoal, imageView3
        );
        flowPane2.getChildren().addAll(
                labelInterface,
                imageView41, labelLives, labelAcceleration, imageView42,
                labelTimer, imageView5
        );
        flowPane3.getChildren().addAll(
                labelObstacles,
                imageView61,  labelWalls, labelBounce, imageView62,
                imageView71, labelSpiky, labelFalse, imageView72
        );
        flowPane4.getChildren().addAll(
                labelBonuses,
                imageView81, labelCoins, labelStopWatch, imageView82,
                imageView91, labelBonusBall, labelIceToken, imageView92
        );
        flowPane5.getChildren().addAll(
                labelOther,
                labelLamp, imageView10,
                labelCamera, imageView11,
                labelPause, imageView12
        );

        flowPaneInner.getChildren().addAll(
                flowPane1,
                flowPane2,
                flowPane3,
                flowPane4,
                flowPane5
        );

        scrollPane.setContent(flowPaneInner);

        flowPane.getChildren().addAll(
                labelTitle,
                scrollPane,
                backButton
        );

        anchorPane.getChildren().addAll(
                flowPane
        );

        rootSubScene = new Group();
        rootSubScene.getChildren().add(anchorPane);

        focusedButton = backButton;
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            navigateMainMenuSubscene();
            Main.getMediaPlayer().playButtonSound();
        }
    }
    private void handleBackButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            navigateMainMenuSubscene();
            Main.getMediaPlayer().playButtonSound();
        }
    }
}
