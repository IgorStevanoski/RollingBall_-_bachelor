package com.example.rollingBall;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Const {

    public static final double WINDOW_WIDTH  = 1280;
    public static final double WINDOW_HEIGHT = 800;
    public static final int MAX_CHARACTERS_IN_NAME = 32;
    public static final int VISIBLE_ROW_CNT = 6;

    public static final double BUTTON_WIDTH = 150;
    public static final double BUTTON_HEIGHT = 30;
    public static final double MENU_BUTTON_WIDTH = 300;
    public static final double MENU_BUTTON_HEIGHT = 50;
    public static final double MENU_VGAP = 30;
    public static final double MENU_PANE_WIDTH = MENU_BUTTON_WIDTH;
    public static final double MENU_PANE_HEIGHT = 200; // u settings subscene
    public static final double MENU_HELP_WIDTH = 4 * MENU_BUTTON_WIDTH; // u settings subscene
    public static final double MENU_HELP_HEIGHT = 2 * MENU_BUTTON_WIDTH; // u settings subscene
    public static final double FONT_SIZE_TITLE = 40;
    public static final double FONT_SIZE = 30;
    public static final double FONT_SIZE_SMALL = 20;
    public static final double HGAP         = 20;
    public static final double VGAP         = 50;
    public static final double FLOWPANEBOTTOM_WIDTH = 640;
    public static final double GRID_LINE_SPACE = 100;
    public static final double TIMER_BAR_HEIGHT = 20;
    public static final int MAX_BALL_CNT = 3;
    public static final double[] DIFFICULTY_FACTOR = {1.0, 1.2, 1.5};

    public static final double STAGE_TIME_FACTOR = 0.67; // za vreme, stopwatch i icetoken
    public static final double STAGE_BALL_FACTOR = 1.2;
    public static final double STAGE_BOUNCE_FACTOR = 1.2;

    public static double TIME_LIMIT             = 60;
    public static double TIME_FREEZE            = 15;
    public static double TIMER_FREEZE_SCALE     = TIME_FREEZE / TIME_LIMIT;
    public static int EXTRA_TIME                = 15;
    public static int MAX_LIFE_POINTS           = 5;
    public static double LIFE_POINT_RADIUS      = 20;
    public static int MAX_BALL_SPEED            = 2000;
    public static int SPIKY_BALL_SPEED          = 200;
    public static double SPIKY_BALL_SPEED_INCREASE = 1.5;
    public static double BALL_DAMP              = 0.999;
    public static double ARENA_DAMP             = 0.995;
    public static double MAX_ACCELERATION       = 400;
    public static int    POINTS_COIN            = 5;
    public static int    POINTS_BAD_HOLE        = 5;
    public static int    POINTS_HOLE            = 10;
    public static int    BOUNCEWALL_FACTOR      = 2;

    public static final double MAX_PODIUM_WIDTH  = 4000;
    public static final double MAX_PODIUM_DEPTH  = 4000;
    public static final double MIN_PODIUM_WIDTH  = 1000;
    public static final double MIN_PODIUM_DEPTH  = 1000;
    public static final double PODIUM_WIDTH      = 2000;
    public static final double PODIUM_HEIGHT     = 10;
    public static final double PODIUM_DEPTH      = 2000;
    public static final double BALL_RADIUS       = 50;
    public static final double HOLE_RADIUS       = 2 * BALL_RADIUS;
    public static final double HOLE_HEIGHT       = 30;
    public static final double HOLE_POSITION_Y   = -30;
    public static final double LAMP_WIDTH        = BALL_RADIUS * 2;
    public static final double LAMP_POSITION_Y   = -1200;
    public static final double WALL_WIDTH        = PODIUM_WIDTH / 2;
    public static final double WALL_HEIGHT       = 100;
    public static final double WALL_DEPTH        = 30;
    public static final double ROUNDWALL_HEIGHT  = 2 * WALL_HEIGHT;
    public static final double ROUNDWALL_RADIUS  = BALL_RADIUS;
    public static final double COIN_RADIUS       = BALL_RADIUS;
    public static final double COIN_HEIGHT       = 5; // debljina novcica
    public static final double BOUNCEWALL_WIDTH  = BALL_RADIUS * 3;
    public static final double BOUNCEWALL_DEPTH  = BOUNCEWALL_WIDTH;
    public static final double BOUNCEWALL_HEIGHT = WALL_HEIGHT;
    public static final double SPIKY_BALL_RADIUS = BALL_RADIUS;
    public static final double STOPWATCH_RADIUS  = BALL_RADIUS;
    public static final double STOPWATCH_HEIGHT  = 15;
    public static final double ICETOKEN_RADIUS   = BALL_RADIUS;
    public static final double ICETOKEN_HEIGHT   = 15;
    public static final double BONUSBALL_RADIUS  = BALL_RADIUS * 2 / 3;
    public static final double BONUSBALL_HEIGHT  = BONUSBALL_RADIUS + PODIUM_HEIGHT;

    public static final float SPIKE_HEIGHT = (float)SPIKY_BALL_RADIUS;
    public static final float SPIKE_LENGHT = (float)SPIKY_BALL_RADIUS / 4;
    public static final float ARROW_HEIGHT = (float)SPIKY_BALL_RADIUS * 3 / 2;
    public static final float ARROW_LENGHT = (float)SPIKY_BALL_RADIUS;
    public static final float ARROW_WIDTH  = (float)SPIKY_BALL_RADIUS / 4;
    public static final double SPIKY_BALL_HEIGHT = -((double)SPIKE_HEIGHT + SPIKY_BALL_RADIUS + PODIUM_HEIGHT / 2);

    public static final double CAMERA_FAR_CLIP = 100000;
    public static final double CAMERA_Z        = -5000;
    public static final double CAMERA_X_ANGLE  = -45;

    public static final int NONE              = 0;
    public static final int BALL_CHOICE0      = 1;
    public static final int BALL_CHOICE1      = 2;
    public static final int BALL_CHOICE2      = 3;
    public static final int HOLE_CHOICE0      = 4;
    public static final int HOLE_CHOICE1      = 5;
    public static final int HOLE_CHOICE2      = 6;
    public static final int LAMP_CHOICE       = 7;
    public static final int WALL_CHOICE       = 8;
    public static final int ROUNDWALL_CHOICE  = 9;
    public static final int COIN_CHOICE       = 10;
    public static final int BOUNCEWALL_CHOICE = 11;
    public static final int BADHOLE_CHOICE    = 12;
    public static final int SPIKE_CHOICE      = 13;
    public static final int STOPWATCH_CHOICE  = 14;
    public static final int BONUSBALL_CHOICE  = 15;
    public static final int ICETOKEN_CHOICE   = 16;

    public static final String[] RESOLUTIONS = {
            "1280x800",
            "1280x960",
            "1600x900",
            "1600x1024",
            "1680x1050",
            "1920x1080"
    };

    //public static final String css = Main.class.getResourceAsStream("application.css").toExternalForm();

    //cursor images
    public static final Image IMAGE_BALL = new Image(Main.class.getClassLoader().getResourceAsStream("CursorBall1.png"));
    public static final Image IMAGE_BALL2= new Image(Main.class.getClassLoader().getResourceAsStream("CursorBall2.png"));
    public static final Image IMAGE_BALL3 = new Image(Main.class.getClassLoader().getResourceAsStream("CursorBall3.png"));
    public static final Image IMAGE_HOLE = new Image(Main.class.getClassLoader().getResourceAsStream("CursorHole1.png"));
    public static final Image IMAGE_HOLE2 = new Image(Main.class.getClassLoader().getResourceAsStream("CursorHole2.png"));
    public static final Image IMAGE_HOLE3 = new Image(Main.class.getClassLoader().getResourceAsStream("CursorHole3.png"));
    public static final Image IMAGE_LAMP = new Image(Main.class.getClassLoader().getResourceAsStream("CursorLamp.png"));
    public static final Image IMAGE_WALL = new Image(Main.class.getClassLoader().getResourceAsStream("CursorWall.png"));
    public static final Image IMAGE_ROUNDWALL = new Image(Main.class.getClassLoader().getResourceAsStream("CursorRoundWall.png"));
    public static final Image IMAGE_COIN = new Image(Main.class.getClassLoader().getResourceAsStream("CoinCursor.png"));
    public static final Image IMAGE_STOPWATCH = new Image(Main.class.getClassLoader().getResourceAsStream("CursorStopwatch.png"));
    public static final Image IMAGE_ICETOKEN = new Image(Main.class.getClassLoader().getResourceAsStream("CursorIceToken.png"));
    public static final Image IMAGE_BOUNCEWALL = new Image(Main.class.getClassLoader().getResourceAsStream("CursorBounce.png"));
    public static final Image IMAGE_BADHOLE = new Image(Main.class.getClassLoader().getResourceAsStream("CursorHole.png"));
    public static final Image IMAGE_BONUSHOLE = new Image(Main.class.getClassLoader().getResourceAsStream("CursorBonusBall.png"));
    public static final Image IMAGE_SPIKE = new Image(Main.class.getClassLoader().getResourceAsStream("CursorSpikyBall.png"));
    public static final Image IMAGE_BALL_TYPE2 = new Image(Main.class.getClassLoader().getResourceAsStream("BallType2.png"));
    public static final Image IMAGE_BALL_TYPE3 = new Image(Main.class.getClassLoader().getResourceAsStream("BallType3.png"));

    //commands images
    public static final Image CommandsImage = new Image(Main.class.getClassLoader().getResourceAsStream("CommandsPNG.png"));
    public static final Image CommandsEditorImage = new Image(Main.class.getClassLoader().getResourceAsStream("CommandsEditorPNG.png"));

    //texture images
    public static final Image METAL_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Metal2.png"));
    public static final Image BACKGROUND_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("background.jpg"));
    public static final Image PANE_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("PaneBackground.png"));
    public static final Image PANE2_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("PaneBackground2.png"));
    public static final Image GREENWALL_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("GreenWall.jpg"));
    public static final Image PLANK_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Plank.png"));
    public static final Image PLANK_HUGE_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("PlankHuge.png"));
    public static final Image PLANK_TEXT_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("PlankText.png"));
    public static final Image STOPWATCH_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Stopwatch.png"));
    public static final Image ICETOKEN_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("IceToken.png"));
    public static final Image ROUNDWALL_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("obstacle.jpg"));
    public static final Image LAMPSELFILL_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("selfIllumination.png"));
    public static final Image ARROW_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("ArrowColor.png"));
    public static final Image PODIUM_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Podium2.jpg"));
    public static final Image HOLE_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Hole.png"));
    public static final Image LAMP_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Lamp.png"));
    public static final Image LAMPOFF_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("LampOff.png"));
    public static final Image BOUNCE_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Bounce.jpg"));
    public static final Image KEG_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("RoundWall.jpg"));
    public static final Image COIN_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Coin.jpg"));
    public static final Image LIGHT_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("LightTexture.jpg"));
    public static final Image BALL1_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("PATTERN1.jpg"));
    public static final Image BALL2_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Stripes.png"));
    public static final Image BALL3_TEXTURE = new Image(Main.class.getClassLoader().getResourceAsStream("Pattern3.png"));
    public static final Image[] BALL_TEXTURE = {BALL1_TEXTURE, BALL2_TEXTURE, BALL3_TEXTURE};

    //controls
    public static Color BUTTON_COLOR = Color.rgb(72, 60, 50); // DARKBROWN
    public static Color BUTTON_INVERSE_COLOR = Color.rgb(214, 172, 114); // LIGHTBROWN
    public static Color BUTTON_FOCUSED_COLOR = Color.DARKRED;
    public static Color BUTTON_CLICKED_COLOR = Color.RED;
    public static Color TITLE_COLOR = BUTTON_FOCUSED_COLOR;

    //font
    public static final Font SIGNBOARD_FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("Signboard.ttf"), FONT_SIZE_SMALL);
    public static final Font ROLLINGBALL_FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("Signboard.ttf"), 2 * FONT_SIZE_TITLE);
    public static final Font TITLE_FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("Signboard.ttf"), FONT_SIZE_TITLE);
    public static final Font FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("Signboard.ttf"), FONT_SIZE);
    public static final Font SMALL_FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("Signboard.ttf"), FONT_SIZE_SMALL);
    public static final Font VERY_SMALL_FONT = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("Signboard.ttf"), 12);
    public static final Font ROLLINGBALL_FONT_SMALL = Font.loadFont(Main.class.getClassLoader().getResourceAsStream("Signboard.ttf"), 4 / 3 * FONT_SIZE_TITLE);

    //other
    public static final Background BUTTON_BACKGROUND = new Background(new BackgroundImage(
            Const.PLANK_TEXTURE,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT
    ));
    public static final Background PANE_BACKGROUND = new Background(new BackgroundImage(
            Const.PANE_TEXTURE,
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT
    ));
    public static final Background PANE_LIGHT_BACKGROUND = new Background(new BackgroundImage(
            Const.PANE2_TEXTURE,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT
    ));
    public static final Background PLANK_HUGE_BACKGROUND = new Background(new BackgroundImage(
            Const.PLANK_HUGE_TEXTURE,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(1, 1, true, true, true, true)
    ));
    public static final Border BORDER = new Border(new BorderStroke(Const.BUTTON_COLOR,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(8, 8, 8, 8)));
}
