package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.arena.*;
import com.example.rollingBall.entities.Terrain;
import com.example.rollingBall.prepare.MapPrepare;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.List;

public class PreviewTerrain {

    private final SubScene subScene;
    private final Group root;
    private Translate cameraDistance;
    private Camera defaultCamera;
    private double distance;

    private Terrain terrain;

    private final double sceneWidth;

    public PreviewTerrain(Terrain terrain, double width) {
        root = new Group();
        sceneWidth = width;

        this.subScene = new SubScene(
                root,
                sceneWidth,
                sceneWidth,
                true,
                SceneAntialiasing.BALANCED
        );

        this.terrain = terrain;
        this.cameraDistance = new Translate( 0, 0, 4000);
        preview();

        Rotate cameraRotateY = new Rotate(0, Rotate.Y_AXIS);
        Rotate cameraRotateX = new Rotate(0, Rotate.X_AXIS);
        this.defaultCamera = MapPrepare.addDefaultCamera ( Const.CAMERA_FAR_CLIP, Const.CAMERA_X_ANGLE,
                cameraDistance, cameraRotateY, cameraRotateX, root);
        this.subScene.setCamera(defaultCamera);
    }

    public void preview() {
        fillTerrain();
    }

    private void fillTerrain() {
        Arena arena = terrain.getCurrentLevel().getArena();
        Podium podium = terrain.getCurrentLevel().getPodium();
        double podium_width = podium.getWidth();
        double podium_depth = podium.getDepth();
        Ball[] ball = terrain.getCurrentLevel().getBall();
        Hole[] hole = terrain.getCurrentLevel().getHole();
        Translate[] ballPosition = new Translate[Const.MAX_BALL_CNT];
        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
            if (ball[i] != null) {
                ballPosition[i] = ball[i].getPosition();
                root.getChildren().add(ball[i]);
            }
            if (hole[i] != null) {
                root.getChildren().add(hole[i]);
            }
        }
        Lamp lamp = terrain.getCurrentLevel().getLamp();
        List<Wall> walls = terrain.getCurrentLevel().getWalls();
        List<RoundWall> roundWalls = terrain.getCurrentLevel().getRoundWalls();
        List<Coin> coins = terrain.getCurrentLevel().getCoins();
        List<BounceWall> bounceWalls = terrain.getCurrentLevel().getBounceWalls();
        List<BadHole> badHoles = terrain.getCurrentLevel().getBadHoles();
        List<SpikyBall> spikyBalls = terrain.getCurrentLevel().getSpikyBalls();
        List<BonusBall> bonusBalls = terrain.getCurrentLevel().getBonusBalls();
        List<Stopwatch> stopwatches = terrain.getCurrentLevel().getStopwatches();
        List<IceToken> iceTokens = terrain.getCurrentLevel().getIceTokens();

        this.distance = podium_width > podium_depth ? -podium_width * 5 / 2 : -podium_depth * 5 / 2;
        if (cameraDistance != null){
            cameraDistance.setZ(distance);
        }
        root.getChildren().addAll(podium);
        root.getChildren().addAll(lamp);
        root.getChildren().addAll(walls);
        root.getChildren().addAll(roundWalls);
        root.getChildren().addAll(coins);
        root.getChildren().addAll(bounceWalls);
        root.getChildren().addAll(badHoles);
        root.getChildren().addAll(spikyBalls);
        root.getChildren().addAll(bonusBalls);
        root.getChildren().addAll(stopwatches);
        root.getChildren().addAll(iceTokens);
    }

    public void update(Terrain terrain) {
        this.terrain = terrain;
        root.getChildren().clear();
        preview();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public double getSceneWidth() {
        return sceneWidth;
    }
}
