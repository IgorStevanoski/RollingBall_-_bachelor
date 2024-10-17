package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.Utilities;
import com.example.rollingBall.arena.*;
import com.example.rollingBall.controls.AnchorPaneC;
import com.example.rollingBall.entities.Terrain;
import com.example.rollingBall.prepare.MapPrepare;
import com.example.rollingBall.timer.Timer;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.List;

public class GameSubScene extends SubSceneMenu{

    private Stage stage;
    private SubScene pauseSubScene;
    private SubScene levelDoneSubScene;
    private SubScene gameOverSubScene;
    private Group rootPauseSubScene;
    private Group rootLevelDoneSubScene;
    private Group rootGameOverSubScene;
    private PauseGameMenu pauseGameMenu;
    private PauseGameLevelDoneMenu levelDoneMenu;
    private PauseGameOverMenu gameOverMenu;
    private EventHandler<KeyEvent> keyHandler;
    private EventHandler<KeyEvent> arenaHandler;
    private EventHandler<MouseEvent> cameraHandler;
    private EventHandler<ScrollEvent> cameraScrollHandler;
    private int[] wallHitCounter;      // Da se ne bi loptica izbagovala pri udaru sa zidom
    private int roundWallHitCounter; // Da se ne bi loptica izbagovala pri udaru sa zidom

    private Timer   timer;
    private Terrain terrain;
    private Boolean gamePaused    = false;
    private Boolean gameFinished  = false;
    private Boolean levelDone     = false;
    private Button  continueButton;
    private Button  newGameButton;
    private Button  mainMenuButton;

    private Translate cameraDistance;
    private Translate cameraDistance2D;
    private Rotate cameraRotateY;
    private Rotate cameraRotateX;
    private Camera defaultCamera;
    private Camera birdViewCamera0;
    private Camera birdViewCamera1;
    private Camera birdViewCamera2;
    private double previousX;
    private double previousY;
    private int cameraActive;
    private boolean lightOn;

    private double podium_width;
    private double podium_height;
    private double podium_depth;

    private Arena arena;
    private Box   podium;
    private Ball[]  ball;
    private Translate[] ballPosition;
    private Hole[] hole;
    private PointLight light;
    private Lamp lamp;
    private List<Wall> walls;
    private List<RoundWall> roundWalls;
    private List<Coin> coins;
    private List<BounceWall> bounceWalls;
    private List<BadHole> badHoles;
    private List<SpikyBall> spikyBalls;
    private List<Stopwatch> stopwatches;
    private List<BonusBall> bonusBalls;
    private List<IceToken> iceTokens;

    private Circle lifepoints[];
    private int remainingLifepoints;
    private double    remainingTime;
    private double    remainingFreezeTime;
    private boolean   frozen = false;
    private double    factor;       // difficulty factor
    private int       factor_index; // difficulty factor
    private Rectangle timerBar;
    private Rectangle timerFreezeBar;
    private Scale     timerScale = new Scale(0, 1);
    private Scale     timerFreezeScale = new Scale(0, 1);
    private Integer   points;
    private Text pointsText;
    private Text timerText;
    private Text timerFreezeText;
    private Line vectorLine;

    public GameSubScene(Group root, Stage stage) {
        super(root, stage);

        this.stage = stage;
        rootSubScene3D = new Group();
        rootSubScene = new Group();
        rootPauseSubScene = new Group();
        rootGameOverSubScene = new Group();

        pauseGameMenu = new PauseGameMenu(this);
        rootPauseSubScene = pauseGameMenu.getRoot();
        this.pauseSubScene = new SubScene(
                rootPauseSubScene,
                Main.getCurrent_window_width(),
                Main.getCurrent_window_height()
        );

        levelDoneMenu = new PauseGameLevelDoneMenu(this);
        rootLevelDoneSubScene = levelDoneMenu.getRoot();
        this.levelDoneSubScene = new SubScene(
                rootLevelDoneSubScene,
                Main.getCurrent_window_width(),
                Main.getCurrent_window_height()
        );

        gameOverMenu = new PauseGameOverMenu(this);
        rootGameOverSubScene = gameOverMenu.getRoot();
        this.gameOverSubScene = new SubScene(
                rootGameOverSubScene,
                Main.getCurrent_window_width(),
                Main.getCurrent_window_height()
        );

        gamePaused = false;
        gameFinished = false;
        levelDone = false;
        lightOn = true;
        wallHitCounter = new int[Const.MAX_BALL_CNT];
        previousX = previousY = 0;

        keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEvent(event);
            }
        };
        arenaHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                arena.handleKeyEvent2 ( event, MAX_ANGLE_OFFSET, gamePaused || gameFinished || levelDone);
            }
        };
         cameraHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleMouseEvent(event);
            }
        };
         cameraScrollHandler = new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                handleScrollEvent(event);
            }
        };

        factor_index = Main.getCurrentPlayer().getDifficulty();
        factor = Const.DIFFICULTY_FACTOR[factor_index];
        terrain = Main.getCurrentPlayer().getCurrentTerrain();
        arena = terrain.getCurrentLevel().getArena();
        podium = terrain.getCurrentLevel().getPodium();
        podium_width = podium.getWidth();
        podium_height = podium.getHeight();
        podium_depth = podium.getDepth();
        ball = terrain.getCurrentLevel().getBall();
        ballPosition = new Translate[Const.MAX_BALL_CNT];
        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
            if (ball[i] != null){
                ballPosition[i] = ball[i].getPosition();
                ((PhongMaterial)ball[i].getMaterial()).setDiffuseMap(Const.BALL_TEXTURE[factor_index]);
            }
        }
        hole = terrain.getCurrentLevel().getHole();
        lamp = terrain.getCurrentLevel().getLamp();
        light = terrain.getCurrentLevel().getLight();
        light.getTransforms().clear();
        light.getTransforms().add(lamp.getPosition());
        walls = terrain.getCurrentLevel().getWalls();
        roundWalls = terrain.getCurrentLevel().getRoundWalls();
        coins = terrain.getCurrentLevel().getCoins();
        bounceWalls = terrain.getCurrentLevel().getBounceWalls();
        badHoles = terrain.getCurrentLevel().getBadHoles();
        spikyBalls = terrain.getCurrentLevel().getSpikyBalls();
        int stageFactor = Main.getCurrentPlayer().getCurrentTerrain().getStageFactor();
        if (stageFactor > 0) {
            for (SpikyBall sb: spikyBalls) {
                sb.updateSpeed(Const.SPIKY_BALL_SPEED * Math.pow(Const.SPIKY_BALL_SPEED_INCREASE, stageFactor));
            }
            int val = stageFactor > 4 ? 100 : 255 - 30 * stageFactor;
            light.setColor(Color.rgb(255, val, val));
            ((PhongMaterial)lamp.getMaterial()).setSpecularColor(Color.rgb(255, val, val));
        }
        stopwatches = terrain.getCurrentLevel().getStopwatches();
        bonusBalls = terrain.getCurrentLevel().getBonusBalls();
        iceTokens = terrain.getCurrentLevel().getIceTokens();
        points = terrain.getPoints();
        pointsText = new Text( 0, 0, String.valueOf( points ));
        pointsText.setFont(Const.FONT);
        pointsText.setFill(Const.BUTTON_INVERSE_COLOR);
        AnchorPaneC anchorPanePoints = new AnchorPaneC(false, Const.MENU_BUTTON_WIDTH / 2, Const.BUTTON_HEIGHT);
        anchorPanePoints.getTransforms().add( new Translate(
                Main.getCurrent_window_width() - Const.MENU_BUTTON_WIDTH / 2, 0
        ));
        anchorPanePoints.addToFlowPane(pointsText);
        rootSubScene.getChildren().add( anchorPanePoints );

        if (arena.getChildren().isEmpty()){
            arena.getChildren().addAll(podium, lamp, light);
            for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                if (ball[i] != null){
                    arena.getChildren().add(ball[i]);
                }
                if (hole[i] != null){
                    arena.getChildren().add(hole[i]);
                }
            }
            arena.getChildren().addAll(walls);
            arena.getChildren().addAll(roundWalls);
            arena.getChildren().addAll(coins);
            arena.getChildren().addAll(bounceWalls);
            arena.getChildren().addAll(badHoles);
            arena.getChildren().addAll(spikyBalls);
            arena.getChildren().addAll(stopwatches);
            arena.getChildren().addAll(bonusBalls);
            arena.getChildren().addAll(iceTokens);
        }

//        this.cameraDistance = new Translate( 0, 0, -podium_width * 5 / 2);
        double distance = podium_width > podium_depth ? -podium_width * 5 / 2 : -podium_depth * 5 / 2;
        this.cameraDistance = new Translate( 0, 0, distance);
        this.cameraDistance2D = new Translate( 0, 0, -4800 );
        this.cameraRotateY = new Rotate(0, Rotate.Y_AXIS);
        this.cameraRotateX = new Rotate(0, Rotate.X_AXIS);
        this.defaultCamera = MapPrepare.addDefaultCamera ( Const.CAMERA_FAR_CLIP, Const.CAMERA_X_ANGLE,
                cameraDistance, cameraRotateY, cameraRotateX, rootSubScene3D);
        if (ball[0] != null)
            this.birdViewCamera0 = MapPrepare.addBirdViewCamera( Const.CAMERA_FAR_CLIP, ballPosition[0]);
        if (ball[1] != null)
            this.birdViewCamera1 = MapPrepare.addBirdViewCamera( Const.CAMERA_FAR_CLIP, ballPosition[1]);
        if (ball[2] != null)
            this.birdViewCamera2 = MapPrepare.addBirdViewCamera( Const.CAMERA_FAR_CLIP, ballPosition[2]);
        //subScene3D.setCamera ( this.defaultCamera );
        cameraActive = 1;

        remainingLifepoints = terrain.getRemainingLifePoints();
        remainingTime = terrain.getRemainingTime();
        remainingFreezeTime = terrain.getRemainingFreezeTime();
        frozen = remainingFreezeTime > 0 ? true : false;
        this.addTimerScale( timerScale, rootSubScene );
        this.addFreezeTimerScale( timerFreezeScale, rootSubScene );
        lifepoints = new Circle[Const.MAX_LIFE_POINTS];
        MapPrepare.addLifePoints( remainingLifepoints, Const.LIFE_POINT_RADIUS, rootSubScene, lifepoints);
        for (int i = 0; i < Const.MAX_LIFE_POINTS; i++) {
            lifepoints[i].setFill(new ImagePattern(Const.IMAGE_BALL));
        }
        vectorLine = new Line( 0, 0, VECTOR_LINE_LENGTH, VECTOR_LINE_LENGTH );
        vectorLine.setStroke( Const.TITLE_COLOR );
        vectorLine.setStrokeWidth(5);
        MapPrepare.addVector2( VECTOR_WIDTH, Main.getCurrent_window_height() - TIMER_BAR_HEIGHT, rootSubScene, vectorLine);
        addTimer();

        this.stage.getScene().addEventHandler(KeyEvent.ANY, arenaHandler);
        this.stage.getScene().addEventHandler(KeyEvent.ANY, keyHandler);
        this.stage.getScene().addEventHandler(MouseEvent.ANY, cameraHandler);
        this.stage.getScene().addEventHandler(ScrollEvent.ANY, cameraScrollHandler);

        rootSubScene3D.getChildren().add( terrain.getCurrentLevel().getArena() );
    }

    private void addTimer () {
        this.timer = new Timer (
                deltaSeconds -> {

                    if (gamePaused || gameFinished || levelDone) return;

                    updateVectorLine();
                    this.arena.update( Const.ARENA_DAMP );
                    updateTimerBar( deltaSeconds );
                    updateTimerFreezeBar( deltaSeconds );

                    if (remainingTime > 0) {

                        boolean []wallHit = new boolean[Const.MAX_BALL_CNT];
                        boolean []isInBadHole = new boolean[Const.MAX_BALL_CNT];
                        boolean []isHitBySpike = new boolean[Const.MAX_BALL_CNT];
                        boolean []outOfArena = new boolean[Const.MAX_BALL_CNT];
                        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                            wallHit[i] = false;
                            isInBadHole[i] = false;
                            isHitBySpike[i] = false;
                            outOfArena[i] = false;
                        }

                        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                            if (ball[i] == null) continue;

                            for (int j = 0; j < Const.MAX_BALL_CNT; j++) {
                                if (ball[j] == null || j == i) continue;
                                if (ball[i].getLastHit() == ball[j]) continue;
                                if(ball[i].handleCollision(ball[j])){
                                    ball[i].setLastHit(ball[j]);
                                    ball[j].setLastHit(ball[i]);
                                }
                            }

                            for (Wall w : this.walls) {
                                if (this.ball[i].getLastHit() == w)
                                    continue;
                                if (w.handleCollision(this.ball[i])) {
                                    Main.getMediaPlayer().playWallHitSound();
                                    this.ball[i].setLastHit(w);
                                }
                            }

                            for (Coin c: this.coins) {
                                if (c.handleCollision( this.ball[i] )) {
                                    Main.getMediaPlayer().playCoinSound();
                                    this.arena.getChildren().remove(c);
                                    coins.remove(c);
                                    terrain.setPoints(points += (int)(Const.POINTS_COIN * factor));
                                    pointsText.setText( String.valueOf(points) );
                                    break;
                                }
                            }
                            for (Stopwatch sw: this.stopwatches) {
                                if (sw.handleCollision( this.ball[i] )) {
                                    this.arena.getChildren().remove(sw);
                                    stopwatches.remove(sw);
                                    addExtraTime();
                                    break;
                                }
                            }
                            for (BonusBall bb: this.bonusBalls) {
                                if (bb.handleCollision( this.ball[i] )) {
                                    this.arena.getChildren().remove(bb);
                                    bonusBalls.remove(bb);
                                    addExtraLife();
                                    break;
                                }
                            }
                            for (IceToken it: this.iceTokens) {
                                if (it.handleCollision( this.ball[i] )) {
                                    this.arena.getChildren().remove(it);
                                    iceTokens.remove(it);
                                    iceTokenFreeze();
                                    break;
                                }
                            }

                            for (RoundWall r : this.roundWalls) {
                                if (this.ball[i].getLastHit() == r) continue;
                                if (r.handleCollision(this.ball[i])) {
                                    Main.getMediaPlayer().playWallHitSound();
                                    this.ball[i].setLastHit(r);
                                    break;
                                }
                            }

                            for (BounceWall b: this.bounceWalls) {
                                if (this.ball[i].getLastHit() == b) continue;
                                if (b.handleCollision(this.ball[i])){
                                    Main.getMediaPlayer().playBounceSound();
                                    this.ball[i].setLastHit(b);
                                    break;
                                }
                            }

                            for (BadHole h: this.badHoles) {
                                if (frozen) {
                                    break;
                                }
                                if (h.handleCollision(this.ball[i])) {
                                    isInBadHole[i] = true;
                                    break;
                                }
                            }

                            for (SpikyBall s: this.spikyBalls) {
                                if (s.handleCollision(this.ball[i])) {
                                    isHitBySpike[i] = true;
                                    break;
                                }
                            }

                            outOfArena[i] = ball[i].update (
                                    deltaSeconds,
                                    podium_depth / 2,
                                    -podium_depth / 2,
                                    -podium_width / 2,
                                    podium_width / 2,
                                    this.arena.getXAngle ( ),
                                    this.arena.getZAngle ( ),
                                    MAX_ANGLE_OFFSET,
                                    Const.MAX_ACCELERATION,
                                    Const.BALL_DAMP,
                                    factor_index,
                                    terrain.getStageFactor()
                            );
                        }

                        for (int i = 0; i < spikyBalls.size(); i++) {
                            if (frozen) break;

                            for (Wall w : this.walls) {
                                if (w == spikyBalls.get(i).getLastHit()) continue;
                                if (w.handleCollision(spikyBalls.get(i))) {
                                    spikyBalls.get(i).setLastHit(w);
                                }
                            }
                            for (RoundWall r : this.roundWalls) {
                                if (r == spikyBalls.get(i).getLastHit()) continue;
                                if (r.handleCollision(spikyBalls.get(i))) {
                                    spikyBalls.get(i).setLastHit(r);
                                }
                            }
                            for (BounceWall b : this.bounceWalls) {
                                if (b == spikyBalls.get(i).getLastHit()) continue;
                                if (b.handleCollision(spikyBalls.get(i))) {
                                    spikyBalls.get(i).setLastHit(b);
                                }
                            }
                            for (SpikyBall sb : this.spikyBalls) {
                                if (sb == spikyBalls.get(i)) continue;
                                if (sb.getSphere() == spikyBalls.get(i).getLastHit()) continue;
                                if (sb.handleCollision(spikyBalls.get(i))) {
                                    spikyBalls.get(i).setLastHit(sb.getSphere());
                                }
                            }

                            spikyBalls.get(i).update(
                              deltaSeconds,
                                podium_depth / 2,
                                -podium_depth / 2,
                                -podium_width / 2,
                                podium_width / 2
                            );
                        }

                        boolean []isInHole = new boolean[Const.MAX_BALL_CNT];
                        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                            if (this.ball[i] == null) continue;
                            for (int j = 0; j < Const.MAX_BALL_CNT; j++) {
                                if (this.hole[j] == null) continue;
                                if (j == i){
                                    isInHole[i] = this.hole[j].handleCollision ( this.ball[i] );
                                } else {
                                    boolean isBad = this.hole[j].handleCollision ( this.ball[i] );
                                    if (isBad && !frozen)
                                        isInBadHole[i] = true;
                                }
                                if (isInHole[i]) break;
                            }

                            if ( (outOfArena[i] || isInHole[i] || isInBadHole[i] || isHitBySpike[i]) && !wallHit[i] ) {
                                handleBallDrop(isInHole[i], isInBadHole[i], isHitBySpike[i], i);
                            }
                        }
                    }
                }
        );
        timer.start ( );
    }

    private void updateVectorLine () {
        this.vectorLine.setEndY( (arena.getXAngle() / MAX_ANGLE_OFFSET ) * VECTOR_LINE_LENGTH );
        this.vectorLine.setEndX( (arena.getZAngle() / MAX_ANGLE_OFFSET ) * VECTOR_LINE_LENGTH );
    }

    private void addTimerScale(Scale scale, Group root) {
        timerBar = new Rectangle(Main.getCurrent_window_width(), TIMER_BAR_HEIGHT, Const.TITLE_COLOR);
        root.getChildren().add(timerBar);

        timerBar.getTransforms().addAll(
                new Translate( 0, Main.getCurrent_window_height() - TIMER_BAR_HEIGHT ),
                scale
        );

        timerText = new Text(15, 15, "Time remaining: " + (int) remainingTime);
        timerText.setFont(Const.FONT);
        timerText.setFill(Const.TITLE_COLOR);
        root.getChildren().add( timerText );
        timerText.getTransforms().add(
                new Translate( Main.getCurrent_window_width() - Main.getCurrent_window_width() / 4 - Const.VGAP,
                        Main.getCurrent_window_height() - FONT_SIZE_TITLE)
        );
    }
    private void addFreezeTimerScale(Scale scale, Group root) {
        timerFreezeBar = new Rectangle(Main.getCurrent_window_width() * Const.TIMER_FREEZE_SCALE,
                TIMER_BAR_HEIGHT , Color.CYAN);
        root.getChildren().add(timerFreezeBar);

        timerFreezeBar.getTransforms().addAll(
                new Translate( VECTOR_WIDTH + 1, Main.getCurrent_window_height() - 2 * TIMER_BAR_HEIGHT ),
                scale
        );

        timerFreezeText = new Text(15, 15, "");
        timerFreezeText.setFont(Const.FONT);
        timerFreezeText.setFill( Color.CYAN );
        root.getChildren().add( timerFreezeText );
        timerFreezeText.getTransforms().add(
                new Translate( VECTOR_WIDTH + 1,
                        Main.getCurrent_window_height() - TIMER_BAR_HEIGHT - FONT_SIZE_TITLE)
        );
    }

    private void updateTimerBar(double deltaSeconds) {
        if (remainingTime > 0){
            terrain.setRemainingTime(remainingTime -= deltaSeconds);
            timerScale.setX(remainingTime / Const.TIME_LIMIT);
            timerText.setText( "Time remaining: " + (int) (remainingTime + 1));
        } else {
            showGameOverMenu();
        }
    }
    private void updateTimerFreezeBar(double deltaSeconds) {
        if (remainingFreezeTime > 0){
            terrain.setRemainingFreezeTime(remainingFreezeTime -= deltaSeconds);
            timerFreezeScale.setX(remainingFreezeTime / Const.TIME_FREEZE);
            if (remainingFreezeTime > 0) {
                timerFreezeText.setText( "Freeze time remaining: " + (int) (remainingFreezeTime + 1));
            } else {
                timerFreezeText.setText("");
            }
        } else if (frozen) {
            for (BadHole b: badHoles) {
                b.unFreeze();
            }
            frozen = false;
        }
    }

    private void handleBallDrop( boolean isInHole, boolean isInBadHole, boolean isHitBySpike, int index) {
        if (this.ball[index] == null) return;
        this.arena.getChildren ( ).remove ( this.ball[index] );
        this.ball[index] = null;
        if ( isInHole ) {
            Main.getMediaPlayer().playInHoleSound();
            terrain.setPoints(points += (int)(Const.POINTS_HOLE * factor));
            pointsText.setText( String.valueOf(points) );
            boolean levelDone = true;
            for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                if (this.ball[i] != null) levelDone = false;
            }
            if (levelDone) showLevelDoneMenu();
        } else {
            Main.getMediaPlayer().playInBadHoleSound();
            terrain.setRemainingLifePoints(--remainingLifepoints);
            this.rootSubScene.getChildren().remove(lifepoints [remainingLifepoints]);
            //lifepoints [remainingLifepoints] = null;
            if (isInBadHole){
                terrain.setPoints(points -= Const.POINTS_BAD_HOLE);
                pointsText.setText( String.valueOf(points) );
            }
            if ( remainingLifepoints > 0 ) {
                for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                    if (ballPosition[i] == null) continue;
                    this.ballPosition[i].setX( terrain.getCurrentLevel().getBallStartPosition(i).getX());
                    this.ballPosition[i].setZ( terrain.getCurrentLevel().getBallStartPosition(i).getZ());
                    if (arena.getChildren().contains(this.ball[i])){
                        arena.getChildren().remove(this.ball[i]);
                    }
                    this.ball[i] = MapPrepare.addBall ( BALL_RADIUS, ballPosition[i], i,factor_index);
                    this.arena.getChildren().add(this.ball[i]);
                }
                this.terrain.getCurrentLevel().setBall(this.ball);
                this.arena.resetRotate();
            } else {
                showGameOverMenu();
            }
        }
    }

    private void addExtraTime() {
        Main.getMediaPlayer().playStopwatchSound();
        int stageFactor = Main.getCurrentPlayer().getCurrentTerrain().getStageFactor();
        double factor = stageFactor > 0 ? Math.pow(Const.STAGE_TIME_FACTOR, stageFactor) : 1;
        double time = Const.EXTRA_TIME * factor;
        if (stageFactor > 0) {
            time = time < 5 ? 5 : time;
            time = (((int)time / 5) * 5);
        }
        remainingTime = Utilities.clamp(remainingTime + time, remainingTime, Const.TIME_LIMIT);
    }
    private void addExtraLife() {
        Main.getMediaPlayer().playBonusBallSound();
        if (remainingLifepoints < Const.MAX_LIFE_POINTS){
            this.rootSubScene.getChildren().add(lifepoints [remainingLifepoints]);
            terrain.setRemainingLifePoints(++remainingLifepoints);
        }
    }
    private void iceTokenFreeze() {
        Main.getMediaPlayer().playIceSound();
        int stageFactor = Main.getCurrentPlayer().getCurrentTerrain().getStageFactor();
        double factor = stageFactor > 0 ? Math.pow(Const.STAGE_TIME_FACTOR, stageFactor) : 1;
        double time = Const.TIME_FREEZE * factor;
        if (stageFactor > 0) {
            time = time < 1 ? 1 : time;
            time = (((int)time / 5) * 5);
        }
        remainingFreezeTime = time;
        for (BadHole b: badHoles) {
            b.freeze();
        }
        frozen = true;
    }

    private void handleKeyEvent ( KeyEvent event ) {
        if ( event.getEventType().equals( KeyEvent.KEY_PRESSED)){
            if (event.getCode().equals(KeyCode.DIGIT1) || event.getCode().equals(KeyCode.NUMPAD1)) {
                if (gamePaused || levelDone) return;
                this.subScene3D.setCamera( this.defaultCamera );
                cameraActive = 1;
            } else if (event.getCode().equals(KeyCode.DIGIT2) || event.getCode().equals(KeyCode.NUMPAD2)) {
                if (gamePaused || levelDone || birdViewCamera0 == null) return;
                this.subScene3D.setCamera( this.birdViewCamera0 );
                cameraActive = 2;
            } else if (event.getCode().equals(KeyCode.DIGIT3) || event.getCode().equals(KeyCode.NUMPAD3)) {
                if (gamePaused || levelDone || birdViewCamera1 == null) return;
                this.subScene3D.setCamera( this.birdViewCamera1 );
                cameraActive = 3;
            } else if (event.getCode().equals(KeyCode.DIGIT4) || event.getCode().equals(KeyCode.NUMPAD4)) {
                if (gamePaused || levelDone || birdViewCamera2 == null) return;
                this.subScene3D.setCamera( this.birdViewCamera2 );
                cameraActive = 4;
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                if (gamePaused || gameFinished || levelDone) return;
                if (cameraActive == 1){
                    cameraRotateY.setAngle(0);
                    cameraRotateX.setAngle(0);
                    cameraDistance.setZ(Const.CAMERA_Z);
                    cameraDistance2D.setZ(Const.CAMERA_Z / 2);
                }
            } else if (event.getCode().equals(KeyCode.ESCAPE) && !levelDone) {
                Main.getMediaPlayer().playPauseSound();
                if (!gamePaused) {
                    showPauseMenu();
                } else {
                    hidePauseMenu();
                }
            }  else if (event.getCode().equals(KeyCode.DIGIT0) || event.getCode().equals(KeyCode.NUMPAD0)) {
                Main.getMediaPlayer().playButton4Sound();
                if ( lightOn ) {
                    terrain.getCurrentLevel().getArena().getChildren().remove(
                            terrain.getCurrentLevel().getLight()
                    );
                } else {
                    terrain.getCurrentLevel().getArena().getChildren().add(
                            terrain.getCurrentLevel().getLight()
                    );
                }
                this.lamp.changeLight( lightOn );
                lightOn = !lightOn;
            } else if (event.getCode().equals(KeyCode.F1)) {
                if (remainingLifepoints > 0)
                    if (ball[0] != null){
                        handleBallDrop(false, false, false, 0);
                    } else if (ball[1] != null){
                        handleBallDrop(false, false, false, 1);
                    } else {
                        handleBallDrop(false, false, false, 2);
                    }
            } else if (event.getCode().equals(KeyCode.F2)) {
                for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
                    if (ball[i] == null || hole[i] == null) continue;
                    ball[i].getPosition().setX(hole[i].getPosition().getX());
                    ball[i].getPosition().setZ(hole[i].getPosition().getZ());
                }
            }
        }
    }

    private void handleMouseEvent (MouseEvent event) {
        if (cameraActive == 1) {
            if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                if (event.isPrimaryButtonDown()) {
                    if (previousX == 0) {
                        previousX = event.getSceneX();
                    }
                    if (previousY == 0) {
                        previousY = event.getSceneY();
                    }

                    double x = event.getSceneX();
                    double dx = x - previousX;

                    double y = event.getSceneY();
                    double dy = y - previousY;

                    int signX = dx >= 0 ? 1 : -1;
                    int signY = dy >= 0 ? -1 : 1;

                    if (Math.abs(dx) > 3) {
                        previousX = x;
                        cameraRotateY.setAngle(cameraRotateY.getAngle() + signX);
                    }
                    if (Math.abs(dy) > 2) {
                        previousY = y;
                        cameraRotateX.setAngle(Utilities.clamp(cameraRotateX.getAngle() + signY * 1. / 2, -45, 45));
                    }
                }
            }
        }
    }

    private void handleScrollEvent (ScrollEvent event){
        if (cameraActive == 1) {
            if (event.getDeltaY() < 0) {
                cameraDistance.setZ(cameraDistance.getZ() - 50);
                cameraDistance2D.setZ(cameraDistance2D.getZ() - 50);
            } else {
                cameraDistance.setZ(cameraDistance.getZ() + 50);
                cameraDistance2D.setZ(cameraDistance2D.getZ() + 50);
            }
        }
    }

    @Override
    public void setSubScene3D(SubScene subScene) {
        this.subScene3D = subScene;
        this.subScene3D.setWidth(Main.getCurrent_window_width());
        this.subScene3D.setHeight(Main.getCurrent_window_height());
        if (!subScene3D.getTransforms().isEmpty())
            this.subScene3D.getTransforms().remove(0);
        //this.subScene3D.getTransforms().add( new Translate(Main2.getCurrent_window_width() / 4, 0));
        this.subScene3D.setCamera ( this.defaultCamera );
    }

    private void showGameOverMenu() {
        Main.getMediaPlayer().playGameDoneSound();
        root.getChildren().add(gameOverSubScene);
        gameFinished = true;
        Main.getCurrentPlayer().setActiveGame(false);
        Main.updateLeaderboard();
        if (PauseGameOverMenu.newRecord){
            PauseGameOverMenu.newRecord = false;
            gameOverMenu.updateLabelNewRecord();
        }
        gameOverMenu.setFocus();
    }

    private void hideGameOverMenu() {
        root.getChildren().remove(gameOverSubScene);
    }

    public void showPauseMenu() {
        if (root.getChildren().contains(levelDoneSubScene) ||
                root.getChildren().contains(gameOverSubScene)) return;
        root.getChildren().add(pauseSubScene);
        pauseGameMenu.setFocus();
        gamePaused = true;
    }

    public void hidePauseMenu() {
        root.getChildren().remove(pauseSubScene);
        gamePaused = false;
    }

    public void showLevelDoneMenu() {
        Main.getMediaPlayer().playLevelDoneSound();
        root.getChildren().add(levelDoneSubScene);
        levelDoneMenu.setFocus();
        levelDone = true;
    }

    public void hideLevelDoneMenu() {
        root.getChildren().remove(levelDoneSubScene);
    }

    public void handleContinueButton() {
        hidePauseMenu();
    }

    public void handleNewGameButton() {
        hidePauseMenu();
        if (levelDone) {
            Main.getCurrentPlayer().advanceLevel();
        }
        gamePaused = true;
        finishGame();
        navigateNewGameSubscene();
    }

    public void handleLevelContinueButton() {
        hideLevelDoneMenu();
        Main.getCurrentPlayer().advanceLevel();
        finishGame();
        navigateGameSubscene();
    }

    public void handleLeaderboardsButton() {
        hideGameOverMenu();
        //Main2.getCurrentPlayer().advanceLevel();
        finishGame();
        navigateLeaderboardsSubscene();
    }

    public void handleMainMenuButton() {
        hidePauseMenu();
        if (levelDone) {
            Main.getCurrentPlayer().advanceLevel();
        }
        gamePaused = true;
        finishGame();
        navigateMainMenuSubscene();
    }

    private void finishGame() {
        gameFinished = true;
        timer = null;
        rootPauseSubScene.getChildren().clear();
        rootLevelDoneSubScene.getChildren().clear();
        rootSubScene3D.getChildren().clear();
        rootGameOverSubScene.getChildren().clear();
        stage.getScene().removeEventHandler(KeyEvent.ANY, keyHandler);
        stage.getScene().removeEventHandler(MouseEvent.ANY, cameraHandler);
        stage.getScene().removeEventHandler(ScrollEvent.ANY, cameraScrollHandler);
        stage.getScene().removeEventHandler(KeyEvent.ANY, arenaHandler);
        root.getChildren().remove(subScene3D);
        root.getChildren().remove(pauseSubScene);
        root.getChildren().remove(levelDoneSubScene);
        root.getChildren().remove(gameOverSubScene);
    }

}

