package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsSubScene extends SubSceneMenu {

    private LabelTitleC labelTitle;
    private Label  labelVideo;
    private Label  labelAudio;
    private CheckBox checkBoxWindowed;
    private CheckBox checkOnOff1;
    private CheckBox checkOnOff2;
    private Label labelResolution;
    private ComboBox comboBox;
    private ButtonC backButton;
    private Slider sliderSoundEffects;
    private Slider sliderMusic;
    private AnchorPaneC anchorPaneVideo;
    private AnchorPaneC anchorPaneAudio;
    private LabelSmallC labelSoundEffects;
    private LabelSmallC labelMusic;

    private static double leftAnchorVal;
    private static double topAnchorVal;
    private static final double paneWidth = 800;
    private static final double paneHeight = 300;
    private static final double spaceHeight = 100;

    private boolean fullscreenOn;
    private boolean soundEffectsOn = true;
    private boolean musicOn = true;
    private double soundEffectsVolume = 100.0;
    private double musicVolume = 100.0;

    private static String[] resolutions = Const.RESOLUTIONS;

//    private static String[] resolutions = {
//            "1280x800",
//            "1280x960",
//            "1600x900",
//            "1600x1024",
//            "1680x1050",
//            "1920x1080"
//    };
    private static String currentResoultion = resolutions[0];

    static {
        leftAnchorVal = (Main.getCurrent_window_width() - paneWidth) / 2;
        topAnchorVal = (Main.getCurrent_window_height() - paneHeight) / 2;
    }

    public SettingsSubScene(Group root, Stage stage) {
        super(root, stage);

        fullscreenOn = stage.isFullScreen();
        musicOn = Main.isMusic_on();
        soundEffectsOn = Main.isSound_on();

        rootSubScene = new Group();

        loadContent();
    }

    private void loadContent(){
        anchorPane = new AnchorPane();
        flowPane = new FlowPane(Orientation.VERTICAL, Const.VGAP, Const.MENU_VGAP);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setRowValignment(VPos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane.setPrefHeight(Main.getCurrent_window_height());

        currentResoultion = (int) Main.getCurrent_window_width() + "x" + (int) Main.getCurrent_window_height();

        labelTitle = new LabelTitleC("Settings");
        labelTitle.setPrefWidth(Main.getCurrent_window_width());

        labelVideo = new LabelC("Video");
        labelVideo.setMinWidth(Const.MENU_PANE_WIDTH * 3);

        checkBoxWindowed = new CheckBoxC("Fullscreen");
        checkBoxWindowed.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        checkBoxWindowed.setSelected(stage.isFullScreen());
        checkBoxWindowed.addEventHandler(ActionEvent.ANY, this::handleFullscreenCheck);

        labelResolution = new LabelSmallC("Resolution");

        comboBox = new ComboBoxC(FXCollections.observableArrayList(resolutions));
        comboBox.setValue(currentResoultion);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        handleResolutionChange((String)comboBox.getValue());
                        Main.getMediaPlayer().playButton4Sound();
                    }
                };
        comboBox.setOnAction(event);

        labelAudio = new LabelC("Audio");
        labelAudio.setMinWidth(Const.MENU_PANE_WIDTH * 3);
        labelSoundEffects = new LabelSmallC("Sound effects");
        labelSoundEffects.setPrefWidth(Const.MENU_BUTTON_WIDTH - Const.VGAP);
        labelMusic = new LabelSmallC("Music");
        labelMusic.setPrefWidth(Const.MENU_BUTTON_WIDTH - Const.VGAP);

        this.sliderSoundEffects = new SliderC(0, 100, Main.getSound_volume());
        sliderSoundEffects.setDisable(!soundEffectsOn);

        this.sliderMusic = new SliderC(0, 100, Main.getMusic_volume());
        sliderMusic.setDisable(!musicOn);

        sliderSoundEffects.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {

                        soundEffectsVolume = newValue.doubleValue();
                        Main.setSound_volume(soundEffectsVolume);
                    }
                });
        sliderSoundEffects.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleSliderRelease);
        sliderMusic.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {

                        musicVolume = newValue.doubleValue();
                        Main.setMusic_volume(musicVolume);
                    }
                });
        sliderMusic.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleSliderRelease);

        checkOnOff1 = new CheckBoxC("on/off");
        checkOnOff1.setPrefWidth(Const.MENU_BUTTON_WIDTH - Const.VGAP);
        checkOnOff1.setSelected(soundEffectsOn);
        checkOnOff1.addEventHandler(ActionEvent.ANY, this::handleSoundEffectsCheck);

        checkOnOff2 = new CheckBoxC("on/off");
        checkOnOff2.setPrefWidth(Const.MENU_BUTTON_WIDTH - Const.VGAP);
        checkOnOff2.setSelected(musicOn);
        checkOnOff2.addEventHandler(ActionEvent.ANY, this::handleMusicCheck);

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton2);

        FlowPane flowPaneSound = new FlowPane(Orientation.HORIZONTAL, Const.VGAP, Const.VGAP);
        flowPaneSound.setPrefWidth(Const.MENU_PANE_WIDTH * 3);
        flowPaneSound.setAlignment(Pos.CENTER);
        flowPaneSound.getChildren().addAll(labelSoundEffects, sliderSoundEffects, checkOnOff1);
        FlowPane flowPaneMusic = new FlowPane(Orientation.HORIZONTAL, Const.VGAP, Const.VGAP);
        flowPaneMusic.setPrefWidth(Const.MENU_PANE_WIDTH * 3);
        flowPaneMusic.setAlignment(Pos.CENTER);
        flowPaneMusic.getChildren().addAll(labelMusic, sliderMusic, checkOnOff2);

        anchorPaneVideo = new AnchorPaneC(false, Const.MENU_PANE_WIDTH * 3, Const.MENU_PANE_HEIGHT);
        anchorPaneVideo.addToFlowPane(labelVideo);
        anchorPaneVideo.addToFlowPane(checkBoxWindowed);
        anchorPaneVideo.addToFlowPane(labelResolution);
        anchorPaneVideo.addToFlowPane(comboBox);
        anchorPaneAudio = new AnchorPaneC(false, Const.MENU_PANE_WIDTH * 3, Const.MENU_PANE_HEIGHT);
        anchorPaneAudio.addToFlowPane(labelAudio);
        anchorPaneAudio.addToFlowPane(flowPaneSound);
        anchorPaneAudio.addToFlowPane(flowPaneMusic);

        flowPane.getChildren().addAll(
                labelTitle,
                anchorPaneVideo,
                anchorPaneAudio,
                backButton
        );

        anchorPane.getChildren().addAll(
                flowPane
        );

        rootSubScene.getChildren().addAll(
                anchorPane
        );

        stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                handleMaximize(t1.booleanValue());
            }
        });

        focusedButton = backButton;
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            navigateMainMenuSubscene();
            Main.getMediaPlayer().playButtonSound();
        }
    }
    private void handleBackButton2(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            navigateMainMenuSubscene();
            Main.getMediaPlayer().playButtonSound();
        }
    }

    private void handleMaximize(boolean maximized){
        if (!maximized){
//            this.stage.setWidth(subScene.getWidth());
//            this.stage.setHeight(subScene.getHeight());
            setStageSize();
            this.stage.centerOnScreen();
        }
    }
    private void handleFullscreenCheck(ActionEvent event) {
        if (event != null)
            Main.getMediaPlayer().playButtonSound();
        this.fullscreenOn = !this.fullscreenOn;
        stage.setFullScreen(fullscreenOn);
        Main.setFullscreen(fullscreenOn);

        this.subScene.setWidth(Main.getCurrent_window_width());
        this.subScene.setHeight(Main.getCurrent_window_height());
        if (!stage.isMaximized() && !fullscreenOn){
//            this.stage.setWidth(subScene.getWidth());
//            this.stage.setHeight(subScene.getHeight());
            setStageSize();
            this.stage.centerOnScreen();
        }

        rootSubScene.getChildren().clear();
        anchorPane.getChildren().clear();

        loadContent();
        focusedButton = backButton;
        focusedButton.requestFocus();

//        System.out.println("widht= " + Screen.getPrimary().getBounds().getWidth());
//        System.out.println("height= " + Screen.getPrimary().getBounds().getHeight());
//        System.out.println("minx= " + Screen.getPrimary().getBounds().getMinX());
//        System.out.println("maxx= " + Screen.getPrimary().getBounds().getMaxX());
//        System.out.println("miny= " + Screen.getPrimary().getBounds().getMinY());
//        System.out.println("maxy= " + Screen.getPrimary().getBounds().getMaxY());
    }
    private void handleResolutionChange(String resolution) {
        Main.setResolution(resolution);
        currentResoultion = resolution;

        boolean maximized = stage.isMaximized();

        this.subScene.setWidth(Main.getCurrent_window_width());
        this.subScene.setHeight(Main.getCurrent_window_height());
        if (!maximized && !fullscreenOn){
//            this.stage.setWidth(subScene.getWidth());
//            this.stage.setHeight(subScene.getHeight());
            setStageSize();
            this.stage.centerOnScreen();
        }

        rootSubScene.getChildren().clear();
        anchorPane.getChildren().clear();

        loadContent();
        Main.resetScale(maximized || fullscreenOn);

        handleFullscreenCheck(null);
        handleFullscreenCheck(null);

        System.out.println(stage.getWidth() + " " + stage.getHeight());

        focusedButton = backButton;
        focusedButton.requestFocus();
    }

    private void handleSoundEffectsCheck(ActionEvent event) {
        this.soundEffectsOn = !this.soundEffectsOn;
        Main.setSound_on(soundEffectsOn);
        if (!soundEffectsOn) {
            sliderSoundEffects.setDisable(true);
        } else {
            sliderSoundEffects.setDisable(false);
        }
        Main.getMediaPlayer().playButtonSound();

        backButton.requestFocus();
    }
    private void handleMusicCheck(ActionEvent event) {
        this.musicOn = !this.musicOn;
        Main.setMusic_on(musicOn);
        if (!musicOn) {
            sliderMusic.setDisable(true);
        } else {
            sliderMusic.setDisable(false);
        }
        Main.getMediaPlayer().playButtonSound();

        backButton.requestFocus();
    }

    private void handleSliderRelease(MouseEvent event) {
        Main.getMediaPlayer().playButtonSound();

        backButton.requestFocus();
    }

    private void setStageSize() {
        double width = Const.WINDOW_WIDTH;
        double height = Main.getCurrent_window_height() * Const.WINDOW_WIDTH / Main.getCurrent_window_width();
        while (width > Screen.getPrimary().getBounds().getWidth()){
            width *= 0.9;
            height *= 0.9;
        }
        while (height > Screen.getPrimary().getBounds().getHeight()){
            width *= 0.9;
            height *= 0.9;
        }

        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }

}
