package com.example.rollingBall.entities;

import com.example.rollingBall.Const;
import com.example.rollingBall.arena.*;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private PointLight light;
    private Podium           podium;
    private Arena            arena;
    private Lamp             lamp;
    private Ball[]           ball = new Ball[Const.MAX_BALL_CNT];
    private Hole[]           hole = new Hole[Const.MAX_BALL_CNT];
    private List<Wall>       walls       = new ArrayList<>();
    private List<RoundWall>  roundWalls  = new ArrayList<>();
    private List<BounceWall> bounceWalls = new ArrayList<>();
    private List<BadHole>    badHoles    = new ArrayList<>();
    private List<Coin>       coins       = new ArrayList<>();
    private List<SpikyBall>  spikyBalls  = new ArrayList<>();
    private List<Stopwatch>  stopwatches = new ArrayList<>();
    private List<BonusBall>  bonusBalls  = new ArrayList<>();
    private List<IceToken>   iceTokens   = new ArrayList<>();

    private Translate[] ballStartPosition = new Translate[Const.MAX_BALL_CNT];

    public Level(Level level) { // duboka kopija
        light = new PointLight(Color.WHITE);
        try {
            light.getTransforms().add(
                    level.light.getTransforms().get(0)
            );
        } catch (IndexOutOfBoundsException e){
        }

        this.podium = new Podium(level.getPodium().getWidth(), level.getPodium().getHeight(),
                level.getPodium().getDepth(), level.getPodium().getMaterial());
        this.arena = new Arena();
        if (level.lamp != null) {
            Translate lampPosition = new Translate(
                    level.lamp.getPosition().getX(),
                    level.lamp.getPosition().getY(),
                    level.lamp.getPosition().getZ()
            );
            this.light.getTransforms().add(lampPosition);
            this.lamp = new Lamp(level.lamp.getWidth(), level.lamp.getHeight(), level.lamp.getDepth(),
                    level.lamp.getMaterial(), lampPosition);
            this.arena.getChildren().add(this.lamp);
        }
        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
            if (level.ball[i] != null) {
                ballStartPosition[i] = new Translate(
                        level.ball[i].getPosition().getX(),
                        level.ball[i].getPosition().getY(),
                        level.ball[i].getPosition().getZ()
                );
                Translate ballPosition = new Translate(
                        level.ball[i].getPosition().getX(),
                        level.ball[i].getPosition().getY(),
                        level.ball[i].getPosition().getZ()
                );
                this.ball[i] = new Ball(level.ball[i].getRadius(), ballPosition, level.ball[i].getIndex(), 0);
                this.arena.getChildren().add(this.ball[i]);
            }
        }
        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
            if (level.hole[i] != null) {
                Translate holePosition = new Translate(
                        level.hole[i].getPosition().getX(),
                        level.hole[i].getPosition().getY(),
                        level.hole[i].getPosition().getZ()
                );
                this.hole[i] = new Hole(level.hole[i].getRadius(), level.hole[i].getHeight(), holePosition, level.hole[i].getIndex());
                this.arena.getChildren().add(this.hole[i]);
            }
        }
        for (Wall w : level.walls) {
            this.walls.add( new Wall(w));
        }
        for (RoundWall r : level.roundWalls) {
            this.roundWalls.add(new RoundWall(r));
        }
        for (BounceWall b : level.bounceWalls) {
            this.bounceWalls.add(new BounceWall(b));
        }
        for (BadHole b : level.badHoles) {
            this.badHoles.add(new BadHole(b));
        }
        for (Coin c : level.coins) {
            Translate coinPosition = new Translate(
                    c.getPosition().getX(),
                    c.getPosition().getY(),
                    c.getPosition().getZ()
            );
            this.coins.add(new Coin(c.getRadius(), c.getHeight(), c.getMaterial(), coinPosition));
        }
        for (SpikyBall s : level.spikyBalls) {
            this.spikyBalls.add(new SpikyBall(s));
        }
        for (Stopwatch sw : level.stopwatches) {
            this.stopwatches.add(new Stopwatch(sw));
        }
        for (BonusBall bb : level.bonusBalls) {
            this.bonusBalls.add(new BonusBall(bb));
        }
        for (IceToken it : level.iceTokens) {
            this.iceTokens.add(new IceToken(it));
        }

        this.arena.getChildren().addAll(
                this.light,
                this.podium
        );
        this.arena.getChildren().addAll(this.walls);
        this.arena.getChildren().addAll(this.roundWalls);
        this.arena.getChildren().addAll(this.bounceWalls);
        this.arena.getChildren().addAll(this.badHoles);
        this.arena.getChildren().addAll(this.coins);
        this.arena.getChildren().addAll(this.spikyBalls);
        this.arena.getChildren().addAll(this.stopwatches);
        this.arena.getChildren().addAll(this.bonusBalls);
        this.arena.getChildren().addAll(this.iceTokens);

        light.getScope().add(arena);
    }

    public Level() {
        // treba da se promeni da svetlo nije fiksno u 0, -1200, 0
        light = new PointLight(Color.WHITE);
        Translate lampPosition = new Translate(0, -1200, 0);
        light.getTransforms().add(lampPosition);
        this.lamp = new Lamp(lampPosition);

        this.podium = new Podium();

        this.ball[0] = new Ball();
        this.ballStartPosition[0] = ball[0].getPosition();
        this.hole[0] = new Hole();

        this.arena = new Arena();
        this.arena.getChildren().add(this.light);
        this.arena.getChildren().add(this.lamp);
        this.arena.getChildren().add(this.podium);
        this.arena.getChildren().add(this.ball[0]);
        this.arena.getChildren().add(this.hole[0]);

        light.getScope().add(arena);
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public PointLight getLight() {
        return light;
    }

    public void setLight(PointLight light) {
        this.light = light;
    }

    public Podium getPodium() {
        return podium;
    }

    public void setPodium(Podium podium) {
        this.podium = podium;
    }

    public Lamp getLamp() {
        return lamp;
    }

    public void setLamp(Lamp lamp) {
        this.lamp = lamp;
        this.light.getTransforms().add(lamp.getPosition());
    }

    public Ball[] getBall() {
        return ball;
    }

    public void setBall(Ball[] ball) {
        this.ball = ball;
    }

    public Hole[] getHole() {
        return hole;
    }

    public void setHole(Hole[] hole) {
        this.hole = hole;
    }

    public Translate[] getBallStartPosition() {
        return ballStartPosition;
    }

    public void setBallStartPosition(Translate[] ballStartPosition) {
        this.ballStartPosition = ballStartPosition;
    }

    public Ball getBall(int index) {
        return ball[index];
    }

    public void setBall(Ball ball, int index) {
        this.ball[index] = ball;
    }

    public Hole getHole(int index) {
        return hole[index];
    }

    public void setHole(Hole hole, int index) {
        this.hole[index] = hole;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public List<RoundWall> getRoundWalls() {
        return roundWalls;
    }

    public void setRoundWalls(List<RoundWall> roundWalls) {
        this.roundWalls = roundWalls;
    }

    public List<BounceWall> getBounceWalls() {
        return bounceWalls;
    }

    public void setBounceWalls(List<BounceWall> bounceWalls) {
        this.bounceWalls = bounceWalls;
    }

    public List<BadHole> getBadHoles() {
        return badHoles;
    }

    public void setBadHoles(List<BadHole> badHoles) {
        this.badHoles = badHoles;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public List<SpikyBall> getSpikyBalls() {
        return spikyBalls;
    }

    public void setSpikyBalls(List<SpikyBall> spikyBalls) {
        this.spikyBalls = spikyBalls;
    }

    public List<Stopwatch> getStopwatches() {
        return stopwatches;
    }

    public void setStopwatches(List<Stopwatch> stopwatches) {
        this.stopwatches = stopwatches;
    }

    public List<BonusBall> getBonusBalls() {
        return bonusBalls;
    }

    public void setBonusBalls(List<BonusBall> bonusBalls) {
        this.bonusBalls = bonusBalls;
    }

    public List<IceToken> getIceTokens() {
        return iceTokens;
    }

    public void setIceTokens(List<IceToken> iceTokens) {
        this.iceTokens = iceTokens;
    }

    public Translate getBallStartPosition(int index) {
        return ballStartPosition[index];
    }

    public void setBallStartPosition(Translate ballStartPosition, int index) {
        this.ballStartPosition[index] = ballStartPosition;
    }

    public void remove(Shape3D body){
        if (body instanceof Ball){
            for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                if (ball[i] == body) this.ball[i] = null;
            }
        } else if (body instanceof Lamp){
            this.lamp = null;
        } else if (body instanceof Hole){
            for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                if (hole[i] == body) this.hole[i] = null;
            }
        } else if (body instanceof Wall){
            this.walls.remove(body);
        } else if (body instanceof RoundWall){
            this.roundWalls.remove(body);
        } else if (body instanceof BounceWall){
            this.bounceWalls.remove(body);
        } else if (body instanceof BadHole){
            this.badHoles.remove(body);
        } else if (body instanceof Coin){
            this.coins.remove(body);
        } else if (body instanceof Stopwatch){
            this.stopwatches.remove(body);
        } else if (body instanceof BonusBall){
            this.bonusBalls.remove(body);
        } else if (body instanceof IceToken){
            this.iceTokens.remove(body);
        }
    }
    public void remove(SpikyBall ball){
        this.spikyBalls.remove(ball);
    }
}