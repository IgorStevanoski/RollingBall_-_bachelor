package com.example.rollingBall.prepare;

import com.example.rollingBall.Const;
import com.example.rollingBall.arena.*;
import com.example.rollingBall.entities.Level;
import com.example.rollingBall.entities.Terrain;
import javafx.geometry.Point3D;
import javafx.scene.transform.Translate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InitTerrain {

    public static Terrain initTerrain() {

        Terrain terrain = new Terrain("STANDARD");
        terrain.setPlayer("IGOR");
        terrain.setDate(LocalDate.of(2023, 9, 10));

        // LEVEL 1
        Level level1 = new Level();

        Translate ballPosition1 = new Translate (
                - ( 2000 / 2 - 2 * 50 ),
                - ( 50 + 10 / 2 ),
                2000 / 2 - 2 * 50
        );
        Translate ballStartPosition1 = new Translate (
                - ( 2000 / 2 - 2 * 50 ),
                - ( 50 + 10 / 2 ),
                2000 / 2 - 2 * 50
        );

        Ball ball1 = MapPrepare.addBall ( 50, ballPosition1, 0);
        Podium podium1 = new Podium(2000, 10, 2000);
        Hole hole1 = MapPrepare.addHole( 2000, 2000, 10, 100, 0);
        Lamp lamp1 = MapPrepare.addLamp( Const.BALL_RADIUS * 2 );
        List<Wall> walls1 = new ArrayList<Wall>();
        MapPrepare.addWalls( Const.WALL_WIDTH, Const.WALL_HEIGHT, Const.WALL_DEPTH, Const.PODIUM_WIDTH, Const.PODIUM_DEPTH, walls1, 0);
        List<RoundWall> roundWalls1 = new ArrayList<RoundWall>();
        MapPrepare.addRoundWalls( Const.BALL_RADIUS, Const.WALL_HEIGHT * 2, Const.PODIUM_WIDTH, Const.PODIUM_DEPTH, roundWalls1, 0);
        List<BounceWall> bounceWalls1 = new ArrayList<>();
        MapPrepare.addBounceWalls( Const.BALL_RADIUS * 3, Const.WALL_HEIGHT, Const.PODIUM_WIDTH, Const.PODIUM_DEPTH, bounceWalls1, 0);
        List<Coin> coins1 = new ArrayList<Coin>();
        MapPrepare.addCoins( Const.BALL_RADIUS, Const.PODIUM_WIDTH, Const.PODIUM_DEPTH, Const.PODIUM_HEIGHT, coins1, 0);
        List<BadHole> badHoles1 = new ArrayList<>();
        MapPrepare.addBadHoles( Const.PODIUM_WIDTH, Const.PODIUM_DEPTH, Const.HOLE_HEIGHT, Const.HOLE_RADIUS, badHoles1, 0);

        level1.getArena().getChildren().addAll(
                ball1, podium1, hole1, lamp1
        );
        level1.getArena().getChildren().addAll(walls1);
        level1.getArena().getChildren().addAll(roundWalls1);
        level1.getArena().getChildren().addAll(coins1);
        level1.getArena().getChildren().addAll(bounceWalls1);
        level1.getArena().getChildren().addAll(badHoles1);

        level1.setBall(ball1, 0);
        level1.setPodium(podium1);
        level1.setHole(hole1, 0);
        level1.setLamp(lamp1);
        level1.setWalls(walls1);
        level1.setRoundWalls(roundWalls1);
        level1.setCoins(coins1);
        level1.setBounceWalls(bounceWalls1);
        level1.setBadHoles(badHoles1);
        level1.setBallStartPosition(ballStartPosition1, 0);

        terrain.addLevel(level1);

        // LEVEL 2

        Level level2 = new Level();

        Translate ballPosition21 = new Translate (
                 ( -300 ),
                 ( -55 ),
                700
        );
        Translate ballStartPosition21 = new Translate (
                 ( -300 ),
                 ( -55 ),
                700
        );
        Translate ballPosition22 = new Translate (
                 ( 300 ),
                 ( -55 ),
                700
        );
        Translate ballStartPosition22 = new Translate (
                 ( 300 ),
                 ( -55 ),
                700
        );

        Ball ball21 = new Ball(Const.BALL_RADIUS, ballPosition21, 0, 0);
        Ball ball22 = new Ball(Const.BALL_RADIUS, ballPosition22, 1, 0);
        Podium podium2 = new Podium(2000, 10, 2000);
        Hole hole21 = new Hole(Const.HOLE_RADIUS, Const.HOLE_HEIGHT, new Translate(0, -30, -300), 0);
        Hole hole22 = new Hole(Const.HOLE_RADIUS, Const.HOLE_HEIGHT, new Translate(0, -30, -550), 1);
        Lamp lamp2 = MapPrepare.addLamp( Const.LAMP_WIDTH );
        List<Wall> walls2 = new ArrayList<Wall>();
        walls2.add(new Wall(2000, 100, 30, new Translate(0, -50, 1000)));
        walls2.add(new Wall(30, 100, 500, new Translate(1000, -50, 0)));
        walls2.add(new Wall(800, 100, 30, new Translate(-600, -50, -1000)));
        walls2.add(new Wall(800, 100, 30, new Translate(600, -50, -1000)));
        walls2.add(new Wall(30, 100, 500, new Translate(-1000, -50, 0)));
        walls2.add(new Wall(30, 100, 500, new Translate(0, -50, 800)));
        List<RoundWall> roundWalls2 = new ArrayList<RoundWall>();
        roundWalls2.add(new RoundWall(50, 200, new Translate(-450, -100, -50)));
        roundWalls2.add(new RoundWall(50, 200, new Translate(450, -100, -50)));
        roundWalls2.add(new RoundWall(50, 200, new Translate(0, -100, 500)));
        roundWalls2.add(new RoundWall(50, 200, new Translate(-800, -100, 800)));
        roundWalls2.add(new RoundWall(50, 200, new Translate(800, -100, 800)));
        roundWalls2.add(new RoundWall(50, 200, new Translate(0, -100, -900)));
        List<BounceWall> bounceWalls2 = new ArrayList<>();
        bounceWalls2.add(new BounceWall(150, 100, 150, new Translate(-350, -100, -50)));
        bounceWalls2.add(new BounceWall(150, 100, 150, new Translate(350, -100, -50)));
        List<BadHole> badHoles2 = new ArrayList<>();
        badHoles2.add(new BadHole(100, 10, new Translate(800, -30, -800)));
        badHoles2.add(new BadHole(100, 10, new Translate(-800, -30, -800)));
        badHoles2.add(new BadHole(120, 10, new Translate(-220, -30, 250)));
        List<Coin> coins2 = new ArrayList<Coin>();
        coins2.add(new Coin(new Translate(-800, -55, 0)));
        coins2.add(new Coin(new Translate(800, -55, 0)));
        coins2.add(new Coin(new Translate(-700, -55, -500)));
        coins2.add(new Coin(new Translate(700, -55, -500)));
        coins2.add(new Coin(new Translate(-300, -55, -700)));
        coins2.add(new Coin(new Translate(300, -55, -700)));
        List<Stopwatch> stopwatches2 = new ArrayList<Stopwatch>();
        stopwatches2.add(new Stopwatch(new Translate(-600, -55, 250)));
        stopwatches2.add(new Stopwatch(new Translate(600, -55, 250)));
        List<BonusBall> bonusBalls2 = new ArrayList<BonusBall>();
        bonusBalls2.add(new BonusBall(new Translate(0, -43.33, 0)));

        level2.getArena().getChildren().addAll(
                ball21, ball22, podium2, hole21, hole22, lamp2
        );
        level2.getArena().getChildren().addAll(walls2);
        level2.getArena().getChildren().addAll(roundWalls2);
        level2.getArena().getChildren().addAll(coins2);
        level2.getArena().getChildren().addAll(bounceWalls2);
        level2.getArena().getChildren().addAll(badHoles2);
        level2.getArena().getChildren().addAll(stopwatches2);
        level2.getArena().getChildren().addAll(bonusBalls2);

        level2.setBall(ball21, 0);
        level2.setBall(ball22, 1);
        level2.setPodium(podium2);
        level2.setHole(hole21, 0);
        level2.setHole(hole22, 1);
        level2.setLamp(lamp2);
        level2.setWalls(walls2);
        level2.setRoundWalls(roundWalls2);
        level2.setCoins(coins2);
        level2.setBounceWalls(bounceWalls2);
        level2.setBadHoles(badHoles2);
        level2.setStopwatches(stopwatches2);
        level2.setBonusBalls(bonusBalls2);
        level2.setBallStartPosition(ballStartPosition21, 0);
        level2.setBallStartPosition(ballStartPosition22, 1);

        terrain.addLevel(level2);

        // LEVEL 3


        Level level3 = new Level();

        Translate ballPosition31 = new Translate (
                ( -1000 ),
                ( -55 ),
                -800
        );
        Translate ballStartPosition31 = new Translate (
                ( -1000 ),
                ( -55 ),
                -800
        );
        Translate ballPosition32 = new Translate (
                ( 1200 ),
                ( -55 ),
                -500
        );
        Translate ballStartPosition32 = new Translate (
                ( 1200 ),
                ( -55 ),
                -500
        );
        Translate ballPosition33 = new Translate (
                ( -950 ),
                ( -55 ),
                800
        );
        Translate ballStartPosition33 = new Translate (
                ( -950 ),
                ( -55 ),
                800
        );

        Ball ball31 = new Ball(Const.BALL_RADIUS, ballPosition31, 0, 0);
        Ball ball32 = new Ball(Const.BALL_RADIUS, ballPosition32, 1, 0);
        Ball ball33 = new Ball(Const.BALL_RADIUS, ballPosition33, 2, 0);
        Podium podium3 = new Podium(3000, 10, 2000);
        Hole hole31 = new Hole(Const.HOLE_RADIUS, Const.HOLE_HEIGHT, new Translate(1200, -30, -800), 0);
        Hole hole32 = new Hole(Const.HOLE_RADIUS, Const.HOLE_HEIGHT, new Translate(-1200, -30, -500), 1);
        Hole hole33 = new Hole(Const.HOLE_RADIUS, Const.HOLE_HEIGHT, new Translate(1200, -30, 600), 2);
        Lamp lamp3 = MapPrepare.addLamp( Const.LAMP_WIDTH );
        List<Wall> walls3 = new ArrayList<Wall>();
        walls3.add(new Wall( 30, 100, 2000, new Translate(-1500, -50,0)));
        walls3.add(new Wall( 600, 100, 30, new Translate(-1200, -50,-1000)));
        walls3.add(new Wall( 1000, 100, 30, new Translate(30, -50,-1000)));
        walls3.add(new Wall( 3000, 100, 30, new Translate(0, -50,1000)));
        walls3.add(new Wall( 600, 100, 30, new Translate(1200, -50,-1000)));
        walls3.add(new Wall( 30, 100, 2000, new Translate(1500, -50,0)));
        walls3.add(new Wall( 600, 100, 30, new Translate(-400, -50,0)));
        walls3.add(new Wall( 30, 100, 300, new Translate(-500, -50,850)));
        walls3.add(new Wall( 800, 100, 30, new Translate(1100, -50,0)));
        walls3.add(new Wall( 600, 100, 30, new Translate(1200, -50,-600)));
        walls3.add(new Wall( 600, 100, 30, new Translate(-800, -50,650)));
        walls3.add(new Wall( 600, 100, 30, new Translate(250, -50,650)));
        List<RoundWall> roundWalls3 = new ArrayList<RoundWall>();
        roundWalls3.add(new RoundWall(50, 200, new Translate(-150, -100, -650)));
        roundWalls3.add(new RoundWall(50, 200, new Translate(-500, -100, 650)));
        roundWalls3.add(new RoundWall(50, 200, new Translate(-650, -100, -650)));
        roundWalls3.add(new RoundWall(50, 200, new Translate(250, -100, 0)));
        List<BounceWall> bounceWalls3 = new ArrayList<>();
        bounceWalls3.add(new BounceWall(150, 100, 150, new Translate(-650, -100,-250 )));
        bounceWalls3.add(new BounceWall(150, 100, 150, new Translate(-150, -100,-250 )));
        bounceWalls3.add(new BounceWall(150, 100, 150, new Translate(650, -100,-250 )));
        List<BadHole> badHoles3 = new ArrayList<>();
        badHoles2.add(new BadHole(100, 10, new Translate(250, -30, -250)));
        badHoles2.add(new BadHole(100, 10, new Translate(250, -30, 500)));
        List<Coin> coins3 = new ArrayList<Coin>();
        coins3.add(new Coin(new Translate( -1200.0, -55,  400.0)));
        coins3.add(new Coin(new Translate( -200.0, -55,  400.0)));
        coins3.add(new Coin(new Translate( -450.0, -55,  -450.0)));
        coins3.add(new Coin(new Translate( 800.0, -55,  -800.0)));
        coins3.add(new Coin(new Translate( 800.0, -55,  -450.0)));
        coins3.add(new Coin(new Translate( 300.0, -55,  800.0)));
        coins3.add(new Coin(new Translate( 300.0, -55,  300.0)));
        coins3.add(new Coin(new Translate( 800.0, -55,  800.0)));
        List<SpikyBall> spikyBalls3 = new ArrayList<SpikyBall>();
        spikyBalls3.add(new SpikyBall(new Translate( 450.0, -105.0, -850.0), new Point3D( 0., 0.0, -400.0)));
        spikyBalls3.add(new SpikyBall(new Translate( -50.0, -105.0, 800.0), new Point3D( 400.0, 0.0, 0.0)));
        spikyBalls3.add(new SpikyBall(new Translate( 50.0, -105.0, -850.0), new Point3D( 0., 0.0, -400.0)));
        spikyBalls3.add(new SpikyBall(new Translate( -400.0, -105.0, -850.0), new Point3D( 0., 0.0, -400.0)));
        List<Stopwatch> stopwatches3 = new ArrayList<Stopwatch>();
        stopwatches3.add(new Stopwatch(new Translate( -1200.0,  -55.0,  100.)));
        stopwatches3.add(new Stopwatch(new Translate( 750.0,  -55.0,  250.)));
        List<BonusBall> bonusBalls3 = new ArrayList<BonusBall>();
        bonusBalls3.add(new BonusBall(new Translate( -1350.0,  -45,  -800.)));
        bonusBalls3.add(new BonusBall(new Translate( 1200.0,  -45,  250.)));
        List<IceToken> iceTokens = new ArrayList<IceToken>();
        iceTokens.add(new IceToken(new Translate( -500.0, -55.0, 300.)));
        iceTokens.add(new IceToken(new Translate( 250.0, -55.0, -450.)));

        level3.getArena().getChildren().addAll(
                ball31, ball32, ball33, podium3, hole31, hole32, hole33, lamp3
        );
        level3.getArena().getChildren().addAll(walls3);
        level3.getArena().getChildren().addAll(roundWalls3);
        level3.getArena().getChildren().addAll(coins3);
        level3.getArena().getChildren().addAll(bounceWalls3);
        level3.getArena().getChildren().addAll(badHoles3);
        level3.getArena().getChildren().addAll(stopwatches3);
        level3.getArena().getChildren().addAll(bonusBalls3);
        level3.getArena().getChildren().addAll(spikyBalls3);
        level3.getArena().getChildren().addAll(iceTokens);

        level3.setBall(ball31, 0);
        level3.setBall(ball32, 1);
        level3.setBall(ball33, 2);
        level3.setPodium(podium3);
        level3.setHole(hole31, 0);
        level3.setHole(hole32, 1);
        level3.setHole(hole33, 2);
        level3.setLamp(lamp3);
        level3.setWalls(walls3);
        level3.setRoundWalls(roundWalls3);
        level3.setCoins(coins3);
        level3.setBounceWalls(bounceWalls3);
        level3.setBadHoles(badHoles3);
        level3.setStopwatches(stopwatches3);
        level3.setBonusBalls(bonusBalls3);
        level3.setSpikyBalls(spikyBalls3);
        level3.setIceTokens(iceTokens);
        level3.setBallStartPosition(ballStartPosition31, 0);
        level3.setBallStartPosition(ballStartPosition32, 1);
        level3.setBallStartPosition(ballStartPosition33, 2);

        terrain.addLevel(level3);

        return terrain;
    }

}
