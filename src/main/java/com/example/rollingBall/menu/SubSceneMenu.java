package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public abstract class SubSceneMenu {

    protected static final double BUTTON_WIDTH = Const.BUTTON_WIDTH;
    protected static final double BUTTON_HEIGHT = Const.BUTTON_HEIGHT;
    protected static final double FONT_SIZE_TITLE = Const.FONT_SIZE_TITLE;
    protected static final double FONT_SIZE = Const.FONT_SIZE;
    protected static final double FONT_SIZE_SMALL = Const.FONT_SIZE_SMALL;
    protected static final double VGAP = 50.0;
    protected static final double buttonHeight = 30.0;

//    protected static final double TIME_LIMIT = Const.TIME_LIMIT;
//    protected static final double BALL_DAMP  = Const.BALL_DAMP;
//    protected static final double ARENA_DAMP = Const.ARENA_DAMP;
//    protected static final double MAX_ACCELERATION   = Const.MAX_ACCELERATION;
//    protected static final int    POINTS_COIN        = Const.POINTS_COIN;
//    protected static final int    POINTS_HOLE        = Const.POINTS_HOLE;
//    protected static final int    MAX_LIFE_POINT     = Const.MAX_LIFE_POINTS;

    protected static final double MAX_ANGLE_OFFSET   = 30;
    protected static final double LIFE_POINT_RADIUS  = 10;
    protected static final double BALL_RADIUS        = Const.BALL_RADIUS;
    protected static final double TIMER_BAR_HEIGHT   = Const.TIMER_BAR_HEIGHT;
    protected static final double VECTOR_WIDTH       = 160;
    protected static final double VECTOR_LINE_LENGTH = VECTOR_WIDTH / 2;

    protected Group    root;
    protected Stage    stage;
    protected Group    rootSubScene;
    protected Group    rootSubScene3D;
    protected SubScene subScene;
    protected SubScene subScene3D;
    protected Control focusedButton;
    protected AnchorPane anchorPane;
    protected FlowPane flowPane;

    public SubSceneMenu(Group root) {
        this(root, null);
    }
    public SubSceneMenu(Group root, Stage stage) {
        this.root = root;
        this.stage = stage;
        this.focusedButton = null;

        anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(Main.getCurrent_window_width());
        anchorPane.setPrefHeight(Main.getCurrent_window_height());

        flowPane = new FlowPane(Orientation.VERTICAL, VGAP, Const.MENU_VGAP);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        AnchorPane.setLeftAnchor(flowPane, 0.);
        AnchorPane.setTopAnchor(flowPane, 0.);
    }

    public Group getRoot() {
        return rootSubScene;
    }
    public Group getRoot3D() { return rootSubScene3D; }

    public void setFocus(){
        try {
            focusedButton.requestFocus();
        } catch (NullPointerException e) {

        }
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }
    public void setSubScene3D(SubScene subScene) { this.subScene3D = subScene; }

    protected void navigateGameSubscene() {
        GameSubScene gss = new GameSubScene(root, stage);
        gss.setSubScene(subScene);
        gss.setSubScene3D(subScene3D);
        subScene.setRoot(gss.getRoot());
        subScene3D.setRoot(gss.getRoot3D());
        root.getChildren().remove(subScene); // mora da se ukloni pa doda zbog prikaza
        root.getChildren().add(subScene3D);
        root.getChildren().add(subScene);
        gss.setFocus();
    }
    protected void navigateMainMenuSubscene() {
        MainMenuSubScene mmss = new MainMenuSubScene(root, stage);
        mmss.setSubScene(subScene);
        mmss.setSubScene3D(subScene3D);
        subScene.setRoot(mmss.getRoot());
        subScene3D.setRoot(mmss.getRoot3D());
        mmss.setFocus();
    }
    protected void navigateNewGameSubscene() {
        NewGameSubScene ngss = new NewGameSubScene(root, stage);
        ngss.setSubScene(subScene);
        ngss.setSubScene3D(subScene3D);
        subScene.setRoot(ngss.getRoot());
        ngss.setFocus();
    }
    protected void navigateContinueSubscene() {
        navigateGameSubscene();
    }
    protected void navigateLeaderboardsSubscene() {
        LeaderboardsSubScene lbss = new LeaderboardsSubScene(root, stage);
        lbss.setSubScene(subScene);
        lbss.setSubScene3D(subScene3D);
        subScene.setRoot(lbss.getRoot());
        lbss.setFocus();
    }
    protected void navigatePlayerSubscene() {
        PlayerSubScene pss = new PlayerSubScene(root, stage);
        pss.setSubScene(subScene);
        pss.setSubScene3D(subScene3D);
        subScene.setRoot(pss.getRoot());
        pss.setFocus();
    }
    protected void navigateSettingsSubscene() {
        SettingsSubScene sss = new SettingsSubScene(root, stage);
        sss.setSubScene(subScene);
        sss.setSubScene3D(subScene3D);
        subScene.setRoot(sss.getRoot());
        sss.setFocus();
    }
    protected void navigateEditorChooseSubscene() {
        EditorChooseSubScene ectss = new EditorChooseSubScene(root, stage);
        ectss.setSubScene(subScene);
        ectss.setSubScene3D(subScene3D);
        subScene.setRoot(ectss.getRoot());
        ectss.setFocus();
    }
    protected void navigateNewTerrainSubscene() {
        EditorNewTerrainSubScene entss = new EditorNewTerrainSubScene(root, stage);
        entss.setSubScene(subScene);
        entss.setSubScene3D(subScene3D);
        subScene.setRoot(entss.getRoot());
        entss.setFocus();
    }
    protected void navigateEditorSubscene() {
        EditorSubScene ess = new EditorSubScene(root, stage);
        ess.setSubScene(subScene);
        ess.setSubScene3D(subScene3D);
        subScene.setRoot(ess.getRoot());
        subScene3D.setRoot(ess.getRoot3D());
        //root.getChildren().remove(subScene); // mora da se ukloni pa doda zbog prikaza
        if (!root.getChildren().contains(subScene3D)){
            root.getChildren().add(subScene3D);
        }
        ess.setFocus();
        //root.getChildren().add(subScene);
    }
    protected void navigateHelpSubscene() {
        HelpSubScene hss = new HelpSubScene(root, stage);
        hss.setSubScene(subScene);
        hss.setSubScene3D(subScene3D);
        subScene.setRoot(hss.getRoot());
        hss.setFocus();
    }
    protected void navigateCommandsSubscene() {
        CommandsSubScene css = new CommandsSubScene(root, stage);
        css.setSubScene(subScene);
        css.setSubScene3D(subScene3D);
        subScene.setRoot(css.getRoot());
        css.setFocus();
    }
    protected void navigateAboutSubscene() {
        AboutSubScene abss = new AboutSubScene(root, stage);
        abss.setSubScene(subScene);
        abss.setSubScene3D(subScene3D);
        subScene.setRoot(abss.getRoot());
        abss.setFocus();
    }
    protected void navigateQuitSubscene() {
        QuitSubScene qss = new QuitSubScene(root, stage);
        qss.setSubScene(subScene);
        qss.setSubScene3D(subScene3D);
        subScene.setRoot(qss.getRoot());
        qss.setFocus();
    }
    protected void navigateNewPlayerSubscene() {
        NewPlayerSubScene npss = new NewPlayerSubScene(root, stage);
        npss.setSubScene(subScene);
        npss.setSubScene3D(subScene3D);
        subScene.setRoot(npss.getRoot());
        npss.setFocus();

    }

    protected void exitProgram() {
        Stage stage = (Stage) subScene.getScene().getWindow();
        stage.close();
    }
}
