package com.example.rollingBall;

import com.example.rollingBall.entities.*;
import com.example.rollingBall.menu.MainMenuSubScene;
import com.example.rollingBall.menu.PauseGameOverMenu;
import com.example.rollingBall.prepare.GamePrepare;
import com.example.rollingBall.prepare.MediaPlayerObject;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final double WINDOW_WIDTH  = Const.WINDOW_WIDTH;
    private static final double WINDOW_HEIGHT = Const.WINDOW_HEIGHT;

    private static double current_window_width = WINDOW_WIDTH;
    private static double current_window_height = WINDOW_HEIGHT;

    private Group root;
    //private static Parent rootSubScene2D;
    private Group rootSubScene2D;
    private Group rootSubScene3D;
    private Scene scene;
    private SubScene subScene2D;
    private SubScene subScene3D;

    private static Scale mainMenuScale;

    private static List<Player> players = new ArrayList<>();
    private static List<Terrain> terrains = new ArrayList<>();
    private static Player currentPlayer;
    private static Terrain currentTerrain; // za editor

    private static Scale scale;
    private static double screen_positionX;
    private static double screen_positionY;
    private static boolean fullscreen;
    private static boolean maximized;

    private static MediaPlayerObject mediaPlayer;

    @Override
    public void start ( Stage stage ) throws IOException {
        // TEST
//        terrains.add(TestTerrain.testTerrain());
//        terrains.add(TestTerrain.testTerrain2());
//        players.add( new Player("Guest", new Terrain(terrains.get(0))));
//        players.add( new Player("Player1", new Terrain(terrains.get(0))));
//        players.add( new Player("Player2", new Terrain(terrains.get(0))));
        terrains = new GamePrepare().readTerrains();
        players = new GamePrepare().readPlayers();
        new GamePrepare().readMisc();
        new GamePrepare().readConfig();

//        currentPlayer = players.get(0);
//        currentTerrain = new Terrain(terrains.get(0));
        // ------------------------

        //rootSubScene2D = FXMLLoader.load(getClass().getResource("/main2.fxml"));
        this.root = new Group ( );
        MainMenuSubScene mainMenuSubScene = new MainMenuSubScene(root, stage);
        rootSubScene2D = mainMenuSubScene.getRoot();
        rootSubScene3D = mainMenuSubScene.getRoot3D();

        this.scene = new Scene (
                this.root,
                current_window_width,
                current_window_height,
                true,
                SceneAntialiasing.BALANCED
        );
        scene.getStylesheets().add("style.css");
        this.subScene2D = new SubScene(
                rootSubScene2D,
                current_window_width,
                current_window_height
        );
        this.subScene3D = new SubScene(
                this.rootSubScene3D,
                current_window_width,
                current_window_height,
                true,
                SceneAntialiasing.BALANCED
        );

        scale = new Scale(1,1);
        root.getTransforms().addAll(scale);
        mainMenuScale = new Scale(); // za animaciju
        rootSubScene2D.getTransforms().addAll(
                new Translate( current_window_width / 2, current_window_height / 2),
                mainMenuScale,
                new Translate( - current_window_width / 2, - current_window_height / 2)
        );

        scene.widthProperty().addListener(
                (observableValue, number, t1) -> {
                    double newScalex = t1.doubleValue() / current_window_width;
                    scale.setX(newScalex);
                }
        );
        scene.heightProperty().addListener(
                (observableValue, number, t1) -> {
                    double newScaley = t1.doubleValue() / current_window_height;
                    scale.setY(newScaley);
                }
        );

        root.getChildren().add( subScene2D );
        mainMenuSubScene.setSubScene(subScene2D);
        mainMenuSubScene.setSubScene3D(subScene3D);

        scene.setFill( new ImagePattern(Const.GREENWALL_TEXTURE));

        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setWidth(Const.WINDOW_WIDTH);
        stage.setHeight(Main.getCurrent_window_height() * Const.WINDOW_WIDTH / Main.getCurrent_window_width());
        stage.setTitle ( "Rolling Ball" );
        stage.setScene ( scene );
        stage.setMaximized(maximized);
        stage.setFullScreen(fullscreen);
        stage.show ( );
        if (screen_positionX == -1 && screen_positionY == -1) {
            stage.centerOnScreen();
        } else if (!maximized && !fullscreen){
        //stage.setX(Main.getScreen_positionX());
//        stage.setY(Main.getScreen_positionY());
//        if (screen_positionX != 0) {
            stage.setX(screen_positionX);
//        }
//        if (screen_positionY != 0) {
            stage.setY(screen_positionY);
//        }
        }

        mediaPlayer = new MediaPlayerObject();
        mainMenuSubScene.setFocus();
    }

    public static void main ( String[] args ) {
        launch ( );
    }

    public static boolean isActiveGame() {
        return currentPlayer.isActiveGame();
    }

    public static void setActiveGame(boolean activeGame) {
        Main.currentPlayer.setActiveGame(activeGame);
    }

    public static double getScreen_positionX() {
        return screen_positionX;
    }

    public static void setScreen_positionX(double screen_positionX) {
        Main.screen_positionX = screen_positionX;
    }

    public static double getScreen_positionY() {
        return screen_positionY;
    }

    public static void setScreen_positionY(double screen_positionY) {
        Main.screen_positionY = screen_positionY;
    }

    public static double getMusic_volume() {
        return currentPlayer.getMusicVolume();
    }

    public static void setMusic_volume(double music_volume) {
        currentPlayer.setMusicVolume(music_volume);
        mediaPlayer.changeMusicVolume();
    }

    public static double getSound_volume() {
        return currentPlayer.getSoundVolume();
    }

    public static void setSound_volume(double sound_volume) {
        currentPlayer.setSoundVolume(sound_volume);
    }

    public static boolean isFullscreen() {
        return fullscreen;
    }
    public static boolean isMaximized() {
        return maximized;
    }

    public static void setFullscreen(boolean fullscreen) {
        Main.fullscreen = fullscreen;
    }
    public static void setMaximized(boolean maximized) {
        Main.maximized = maximized;
    }

    public static boolean isMusic_on() {
        return currentPlayer.isMusic_on();
    }

    public static void setMusic_on(boolean music_on) {
        currentPlayer.setMusic_on(music_on);
        mediaPlayer.playStopMusic(music_on);
    }

    public static boolean isSound_on() {
        return currentPlayer.isSound_on();
    }

    public static void setSound_on(boolean sound_on) {
        currentPlayer.setSound_on(sound_on);
    }

    public static MediaPlayerObject getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setCurrent_window_width(double current_window_width) {
        Main.current_window_width = current_window_width;
    }

    public static void setCurrent_window_height(double current_window_height) {
        Main.current_window_height = current_window_height;
    }

    public static double getCurrent_window_width() {
        return current_window_width;
    }

    public static double getCurrent_window_height() {
        return current_window_height;
    }

    public static List<Player> getPlayers() {
        return players;
    }
    public static void addNewPlayer(String playername) {
        players.add(new Player(playername, new Terrain(terrains.get(0))));
        currentPlayer = players.get(players.size() - 1);
    }
    public static void addNewTerrain(String terrainName) {
        Terrain terrain = new Terrain(terrainName);
        terrain.addLevel(new Level());
        terrain.setPlayer(Main.currentPlayer.getName());
        terrains.add(terrain);
        currentTerrain = getTerrain(terrainName);
    }

    public static List<Terrain> getTerrains() {
        return terrains;
    }

    public static Terrain getTerrain(String name) {
        for (Terrain t: terrains){
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public static void setTerrains(List<Terrain> terrains) {
        Main.terrains = terrains;
    }

    public static void resetTerrain() {
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(currentPlayer.getCurrentTerrain().getName())){
                currentPlayer.setCurrentTerrain(new Terrain(terrains.get(i)));
            }
        }
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static Terrain getCurrentTerrain() {
        return currentTerrain;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Main.currentPlayer = currentPlayer;
    }
    public static void setCurrentPlayer(String currentPlayerName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(currentPlayerName)){
                currentPlayer = players.get(i);
            }
        }
    }
    public static void setCurrentPlayerTerrain(String currentTerrainName) {
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(currentTerrainName)){
                currentPlayer.setCurrentTerrain(terrains.get(i));
            }
        }
    }
    // ZA EDITOR
    public static void setCurrentTerrain(String currentTerrainName) {
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(currentTerrainName)){
                currentTerrain = terrains.get(i);
            }
        }
    }
    public static boolean changeTerrainName(String oldName, String newName) {
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(newName)){
                return false;
            }
        }
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(oldName)){
                terrains.get(i).setName(newName);
                for (int j = 0; j < players.size(); j++) {
                    if (players.get(j).getCurrentTerrain().getName().equals(oldName)){
                        players.get(j).getCurrentTerrain().setName(newName);
                    }
                }
                return true;
            }
        }
        return false;
    }
    public static boolean isDeletableTerrain(String name){
        for (int j = 0; j < players.size(); j++) {
            if (players.get(j).getCurrentTerrain().getName().equals(name) && players.get(j).isActiveGame()){
                return false;
            }
        }
        return true;
    }
    public static void deleteTerrain(String name){
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(name)){
                terrains.remove(i);
            }
        }
    }

    public static void deleteLevel(String name, int level){
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(name)){
//                    terrains.remove(i);
                System.out.println("delete level " + level);
            }
        }
    }

    public static void updateLeaderboard() {
        for (int i = 0; i < terrains.size(); i++) {
            if (terrains.get(i).getName().equals(currentPlayer.getCurrentTerrain().getName())){
                Leaderboard leaderboard = terrains.get(i).getLeaderboard();
                LeaderboardEntry entry = new LeaderboardEntry(currentPlayer, currentPlayer.getCurrentTerrain().getPoints());
                if (leaderboard.addEntry(entry)) {
                    PauseGameOverMenu.newRecord = true;
                }
            }
        }
    }

    public static void setResolution(String resolution) {
        String str = resolution;
        String[] arrOfStr = str.split("x", 2);
        current_window_width = Integer.parseInt(arrOfStr[0]);
        current_window_height = Integer.parseInt(arrOfStr[1]);
    }

    public static void resetScale(boolean maximized) {
        if (maximized){
//            System.out.println(Screen.getPrimary().getVisualBounds().getWidth());
//            System.out.println(Screen.getPrimary().getVisualBounds().getHeight());
//            System.out.println(current_window_width);
//            System.out.println(current_window_height);
//            System.out.println("x");
            scale.setX(Screen.getPrimary().getVisualBounds().getWidth() / current_window_width);
            scale.setY(Screen.getPrimary().getVisualBounds().getHeight() / current_window_height);
        } else {
            scale.setX(1);
            scale.setY(1);
        }
    }
}
