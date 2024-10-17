package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.controls.ButtonC;
import com.example.rollingBall.controls.ComboBoxC;
import com.example.rollingBall.controls.LabelTitleC;
import com.example.rollingBall.controls.TableViewC;
import com.example.rollingBall.entities.Leaderboard;
import com.example.rollingBall.entities.LeaderboardEntry;
import com.example.rollingBall.entities.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardsSubScene extends SubSceneMenu{

    private AnchorPane anchorPane;
    private Label      labelTitle;
    private ComboBox comboBox;
    private Button backButton;

    private TableViewC<LeaderboardEntry> table = new TableViewC<>();
    private Leaderboard leaderboard = new Leaderboard();
    private ObservableList<LeaderboardEntry> data;
    private List<Terrain> terrains = new ArrayList<Terrain>();
    private List<String> terrainsNames = new ArrayList<String>();
    private String currentTerrain;

    public LeaderboardsSubScene(Group root, Stage stage) {
        super(root, stage);

        flowPane = new FlowPane();
        anchorPane = new AnchorPane();

        this.terrains.addAll(Main.getTerrains());
        for (Terrain t: terrains) {
            terrainsNames.add(t.getName());
        }
        currentTerrain = Main.getCurrentPlayer().getCurrentTerrain().getName();

        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(currentTerrain)){
                leaderboard = terrains.get(i).getLeaderboard();
            }
        }

        data = FXCollections.observableArrayList(leaderboard.getEntries());

        labelTitle = new LabelTitleC("Leaderboards");

        comboBox = new ComboBoxC(FXCollections.observableArrayList(terrainsNames));
        comboBox.getTransforms().add(
                new Translate(Main.getCurrent_window_width() / 2 + BUTTON_WIDTH * 2 + BUTTON_HEIGHT,
                        Main.getCurrent_window_height() * 3 / 8)
        );
        comboBox.setValue(currentTerrain);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        changeTerrain();
                        Main.getMediaPlayer().playButton4Sound();
                    }
                };
        comboBox.setOnAction(event);

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton2);

        table.setItems(data);

        flowPane = new FlowPane(Orientation.VERTICAL, VGAP, Const.MENU_VGAP);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPrefWrapLength(Main.getCurrent_window_height());
        flowPane.setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        AnchorPane.setLeftAnchor(flowPane, Main.getCurrent_window_width() / 2 - Const.MENU_BUTTON_WIDTH);
        AnchorPane.setTopAnchor(flowPane, 0.);

        flowPane.getChildren().addAll(
                labelTitle,
                table,
                backButton
        );

        anchorPane.getChildren().addAll(
                flowPane,
                comboBox
        );

        rootSubScene = new Group();
        rootSubScene.getChildren().add(
            anchorPane
        );

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
    private void changeTerrain() {
        currentTerrain = (String)comboBox.getValue();
        for (Terrain t: terrains) {
            if (t.getName().equals(currentTerrain)){
                leaderboard = t.getLeaderboard();
            }
        }
        data = FXCollections.observableArrayList(leaderboard.getEntries());
        table.setItems(data);
    }

}
