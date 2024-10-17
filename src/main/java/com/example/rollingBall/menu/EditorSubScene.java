package com.example.rollingBall.menu;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.Utilities;
import com.example.rollingBall.arena.*;
import com.example.rollingBall.controls.*;
import com.example.rollingBall.entities.Level;
import com.example.rollingBall.entities.Terrain;
import com.example.rollingBall.prepare.MapPrepare;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EditorSubScene extends SubSceneMenu{

    private static final double BOTTOM_SPACE = (Main.getCurrent_window_height() -
            4 * (VGAP + BUTTON_HEIGHT)) / 2;
    private int temp = 0;

    private FlowPane   flowPaneRight;
    private FlowPane   flowPaneBottom;
    private Label      labelTitle;
    private Label      labelCurrentTerrain;
    private TextField  changeNameTextField;
    private Label      labelPaletteTerrain;
    private Label      labelHover;
    private CheckBox   checkboxGrid;
    private ComboBox   comboBox;
    private Button     newLevelButton;
    private Button     changeNameButton;
    private Button     saveButton;
    private Button     deleteTerrainButton;
    private Button     deleteLevelButton;
    private Button     backButton;
    private Label labelPickedItem;
    private Label labelPositionX;
    private Label labelPositionZ;
    private Label labelWidth;
    private Label labelDepth;
    private Label labelRadius;
    private TextField textFieldPosX;
    private TextField textFieldPosZ;
    private TextField textFieldWidth;
    private TextField textFieldDepth;
    private TextField textFieldRadius;
    private Button rotateButton;
    private Button deleteButton;
    private Button applyButton;
    private Label labelErrorMessage;

    private List<String> levels = new ArrayList<>();

    private Translate cameraDistance;
    private Translate cameraDistanceParallel;
    private Rotate cameraRotateY;
    private Rotate cameraRotateX;
    private Camera defaultCamera;
    private Camera parallelCamera;
    private int cameraActive;
    private EventHandler<ScrollEvent> scrollHandler;
    private EventHandler<ActionEvent> event;
    private EventHandler<MouseEvent> podiumHandler;

    private double podium_width;
    private double podium_height;
    private double podium_depth;

    private Terrain          terrain;
    private Arena            arena;
    private Box              podium;
    private Ball[]           ball;
    private Translate[]      ballPosition;
    private Hole[]           hole;
    private Lamp             lamp;
    private PointLight       light;
    private List<Wall>       walls;
    private List<RoundWall>  roundWalls;
    private List<Coin>       coins;
    private List<BounceWall> bounceWalls;
    private List<BadHole>    badHoles;
    private Arrow arrow;
    private Translate arrowPosition;
    private Rotate arrowRotation;

    private ImageView    ballChoice0;
    private ImageView    holeChoice0;
    private ImageView    ballChoice1;
    private ImageView    holeChoice1;
    private ImageView    ballChoice2;
    private ImageView    holeChoice2;
    private ImageView    lampChoice;
    private ImageView    wallChoice;
    private ImageView    roundWallChoice;
    private ImageView    coinChoice;
    private ImageView    bounceWallChoice;
    private ImageView    badHoleChoice;
    private ImageView    spikeChoice;
    private ImageView    stopwatchChoice;
    private ImageView    bonusballChoice;
    private ImageView    iceTokenChoice;
    private boolean      choicePressed  = false; // za prevlacenje iz palete
    private int          choiceCode;
    private Translate    pickedPosition = null;
    private Shape3D      pickedBody     = null; // za prevlacenje preko arene
    private Shape3D      selectedBody   = null; // za selektovanje komponente u cilju menjanja atributa u okviru flowPaneBottom
    private Shape3D      copiedObject   = null; // za selektovanje komponente u cilju menjanja atributa u okviru flowPaneBottom
    private AmbientLight ambientLight;
    private Grid         grid;
    private boolean      gridOn         = false;
    private static double lastCursorPositionX = 0.;
    private static double lastCursorPositionZ = 0.;
    private static final double CHOICE_SIZE = 72.;

    public EditorSubScene(Group root, Stage stage){
        super(root, stage);

        this.stage = stage;
        rootSubScene3D = new Group();
        rootSubScene = new Group();

        // 3D scena
        podiumHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handlePodiumEvent(event);
            }
        };

        terrain = new Terrain(Main.getCurrentTerrain());
        this.arrow = new Arrow(Const.ARROW_HEIGHT, Const.ARROW_LENGHT, Const.ARROW_WIDTH);
        this.arrowPosition = new Translate(0, -Const.SPIKY_BALL_RADIUS * 2, 0);
        this.arrowRotation = new Rotate(0, Rotate.Y_AXIS);
        this.arrow.getTransforms().addAll(
                arrowPosition,
                arrowRotation,
                new Translate(0, 0, -Const.SPIKY_BALL_RADIUS * 2)
        );
        fillTerrain();

        double distance = podium_width > podium_depth ? -podium_width * 5 / 2 : -podium_depth * 5 / 2;
        this.cameraDistance = new Translate( 0, 0, distance);
        this.cameraRotateY = new Rotate(0, Rotate.Y_AXIS);
        this.cameraRotateX = new Rotate(0, Rotate.X_AXIS);
        this.defaultCamera = MapPrepare.addDefaultCamera ( Const.CAMERA_FAR_CLIP, Const.CAMERA_X_ANGLE,
                cameraDistance, cameraRotateY, cameraRotateX, rootSubScene3D);

        this.cameraDistanceParallel = new Translate( 0, 0, distance);
        this.parallelCamera = MapPrepare.addDefaultCamera2 ( Const.CAMERA_FAR_CLIP, Const.CAMERA_X_ANGLE * 2,
                cameraDistanceParallel, rootSubScene3D);
        cameraActive = 1;

        scrollHandler = new EventHandler<>() {
            @Override
            public void handle(ScrollEvent event) {
                handleScrollEvent(event);
            }
        };
        this.stage.getScene().addEventHandler(ScrollEvent.ANY, scrollHandler);

        ambientLight = new AmbientLight(Color.CYAN);
        // 2D scena
        anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(Main.getCurrent_window_width());
        anchorPane.setPrefHeight(Main.getCurrent_window_height());

//        flowPane = new FlowPane(Orientation.VERTICAL, 0, Const.MENU_VGAP);
//        flowPane.setAlignment(Pos.CENTER);
//        flowPane.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        flowPane.setVgap(Const.MENU_VGAP - 10);
        flowPane.setMaxWidth(Main.getCurrent_window_width() / 4);
        flowPane.setPrefHeight(Main.getCurrent_window_height());
        flowPane.setBorder(Const.BORDER);
        flowPane.setBackground(Const.PANE_BACKGROUND);
        AnchorPane.setLeftAnchor(flowPane, Main.getCurrent_window_width() / 8 - flowPane.getMaxWidth() / 2);
        AnchorPane.setTopAnchor(flowPane, .0);

        flowPaneRight = new FlowPane(Orientation.HORIZONTAL, Const.VGAP, Const.VGAP / 5);
        flowPaneRight.setAlignment(Pos.CENTER);
        flowPaneRight.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        flowPaneRight.setPrefHeight(Main.getCurrent_window_height());
        AnchorPaneC anchorPaneRight = new AnchorPaneC();
        anchorPaneRight.setBorder(Const.BORDER);
        anchorPaneRight.setBackground(Const.PANE_BACKGROUND);
        anchorPaneRight.setMaxWidth(Main.getCurrent_window_width() / 4);
        anchorPaneRight.setPrefWidth(Main.getCurrent_window_width() / 4);
        AnchorPane.setLeftAnchor(anchorPaneRight,  Main.getCurrent_window_width() * 3 / 4);
        AnchorPane.setLeftAnchor(flowPaneRight,  (Main.getCurrent_window_width() / 4 - flowPaneRight.getPrefWidth()) / 2);
        AnchorPane.setTopAnchor(flowPaneRight, .0);
        anchorPaneRight.getChildren().add(flowPaneRight);

        double flowPaneWidth = Const.FLOWPANEBOTTOM_WIDTH;
        flowPaneBottom = new FlowPane(Orientation.HORIZONTAL, 0, Const.HGAP / 2);
        flowPaneBottom.setAlignment(Pos.CENTER_LEFT);
        flowPaneBottom.setPrefWidth(flowPaneWidth);
        flowPaneBottom.setPrefWrapLength(flowPaneWidth);
        flowPaneBottom.setPrefHeight(Main.getCurrent_window_height() / 4);
        AnchorPaneC anchorPaneBottom = new AnchorPaneC();
        anchorPaneBottom.setBorder(new Border(new BorderStroke(Const.BUTTON_COLOR,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(8, 0, 8, 0))));
        anchorPaneBottom.setBackground(Const.PANE_BACKGROUND);
        anchorPaneBottom.setMaxWidth(Main.getCurrent_window_width() / 2);
        anchorPaneBottom.setPrefWidth(Main.getCurrent_window_width() / 2);
        AnchorPane.setLeftAnchor(anchorPaneBottom,  Main.getCurrent_window_width() / 4);
        AnchorPane.setTopAnchor(anchorPaneBottom,  Main.getCurrent_window_height() * 3 / 4);
        AnchorPane.setLeftAnchor(flowPaneBottom, (Main.getCurrent_window_width() / 2 - flowPaneWidth) / 2);
        AnchorPane.setTopAnchor(flowPaneBottom, 0.);
        anchorPaneBottom.getChildren().add(flowPaneBottom);

        for (int i = 0; i < terrain.getLevels().size(); i++) {
            levels.add( Integer.toString(i + 1));
        }

        labelTitle = new LabelTitleC("Editor");
        labelTitle.setPrefWidth(Const.MENU_BUTTON_WIDTH);

        labelCurrentTerrain = new LabelC("Terrain: \n" + terrain.getName());
        labelCurrentTerrain.setWrapText(false);

        changeNameTextField = new TextFieldC(terrain.getName());
        changeNameTextField.setOnAction(this::handleChangeNameAction);

        checkboxGrid = new CheckBoxC("Grid");
        checkboxGrid.setPrefWidth(Const.BUTTON_WIDTH);
        checkboxGrid.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        checkboxGrid.addEventHandler(ActionEvent.ANY, this::handleShowGrid);

        comboBox = new ComboBoxC(FXCollections.observableArrayList(levels));
        comboBox.setValue(String.valueOf(terrain.getCurrentLevelNUM() + 1));
        event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        Main.getMediaPlayer().playButton4Sound();
                        try {
                            String str = (String)comboBox.getValue();
                            terrain.setCurrentLevel(terrain.getLevels().get(Integer.parseInt(str) - 1));
                        } catch (NumberFormatException exception) {
                            terrain.setCurrentLevel(terrain.getLevels().get(0));
                        }
                        rootSubScene3D.getChildren().remove( arena );
                        fillTerrain();
                    }
                };
        comboBox.setOnAction(event);

        newLevelButton = new ButtonC("Add new level");
        newLevelButton.addEventHandler( MouseEvent.ANY, this::handleNewLevelButton);
        newLevelButton.addEventHandler( KeyEvent.ANY, this::handleNewLevelButton);

        changeNameButton = new ButtonC("Change terrain name");
        changeNameButton.addEventHandler( MouseEvent.ANY, this::handleChangeNameButton);
        changeNameButton.addEventHandler( KeyEvent.ANY, this::handleChangeNameButton);

        saveButton = new ButtonC("Save");
        saveButton.addEventHandler( MouseEvent.ANY, this::handleSaveButton);
        saveButton.addEventHandler( KeyEvent.ANY, this::handleSaveButton);
        saveButton.setDisable(true);

        deleteTerrainButton = new ButtonC("Delete terrain");
        deleteTerrainButton.addEventHandler( MouseEvent.ANY, this::handleDeleteTerrainButton);
        deleteTerrainButton.addEventHandler( KeyEvent.ANY, this::handleDeleteTerrainButton);

        deleteLevelButton = new ButtonC("Delete level");
        deleteLevelButton.addEventHandler( MouseEvent.ANY, this::handleDeleteLevelButton);
        deleteLevelButton.addEventHandler( KeyEvent.ANY, this::handleDeleteLevelButton);
        if (levels.size() <= 1) deleteLevelButton.setDisable(true);

        backButton = new ButtonC("Back");
        backButton.addEventHandler( MouseEvent.ANY, this::handleBackButton);
        backButton.addEventHandler( KeyEvent.ANY, this::handleBackButton2);

        // flowPaneRight
        labelPaletteTerrain = new LabelC("Palette");
        labelPaletteTerrain.setPrefWidth(BUTTON_WIDTH);
        ballChoice0 = new ImageView(Const.IMAGE_BALL);
        ballChoice0.setFitWidth(CHOICE_SIZE);
        ballChoice0.setFitHeight(CHOICE_SIZE);
        ballChoice0.addEventHandler( MouseEvent.ANY, this::handleAddBallChoice0);
        holeChoice0 = new ImageView(Const.IMAGE_HOLE);
        holeChoice0.setFitWidth(CHOICE_SIZE);
        holeChoice0.setFitHeight(CHOICE_SIZE);
        holeChoice0.addEventHandler( MouseEvent.ANY, this::handleAddHoleChoice0);
        ballChoice1 = new ImageView(Const.IMAGE_BALL2);
        ballChoice1.setFitWidth(CHOICE_SIZE);
        ballChoice1.setFitHeight(CHOICE_SIZE);
        ballChoice1.addEventHandler( MouseEvent.ANY, this::handleAddBallChoice1);
        holeChoice1 = new ImageView(Const.IMAGE_HOLE2);
        holeChoice1.setFitWidth(CHOICE_SIZE);
        holeChoice1.setFitHeight(CHOICE_SIZE);
        holeChoice1.addEventHandler( MouseEvent.ANY, this::handleAddHoleChoice1);
        ballChoice2 = new ImageView(Const.IMAGE_BALL3);
        ballChoice2.setFitWidth(CHOICE_SIZE);
        ballChoice2.setFitHeight(CHOICE_SIZE);
        ballChoice2.addEventHandler( MouseEvent.ANY, this::handleAddBallChoice2);
        holeChoice2 = new ImageView(Const.IMAGE_HOLE3);
        holeChoice2.setFitWidth(CHOICE_SIZE);
        holeChoice2.setFitHeight(CHOICE_SIZE);
        holeChoice2.addEventHandler( MouseEvent.ANY, this::handleAddHoleChoice2);
        lampChoice = new ImageView(Const.IMAGE_LAMP);
        lampChoice.setFitWidth(CHOICE_SIZE);
        lampChoice.setFitHeight(CHOICE_SIZE);
        lampChoice.addEventHandler( MouseEvent.ANY, this::handleAddLampChoice);
        wallChoice = new ImageView(Const.IMAGE_WALL);
        wallChoice.setFitWidth(CHOICE_SIZE);
        wallChoice.setFitHeight(CHOICE_SIZE);
        wallChoice.addEventHandler( MouseEvent.ANY, this::handleAddWallChoice);
        roundWallChoice = new ImageView(Const.IMAGE_ROUNDWALL);
        roundWallChoice.setFitWidth(CHOICE_SIZE);
        roundWallChoice.setFitHeight(CHOICE_SIZE);
        roundWallChoice.addEventHandler( MouseEvent.ANY, this::handleAddRoundWallChoice);
        coinChoice = new ImageView(Const.IMAGE_COIN);
        coinChoice.setFitWidth(CHOICE_SIZE);
        coinChoice.setFitHeight(CHOICE_SIZE);
        coinChoice.addEventHandler( MouseEvent.ANY, this::handleAddCoinChoice);
        bounceWallChoice = new ImageView(Const.IMAGE_BOUNCEWALL);
        bounceWallChoice.setFitWidth(CHOICE_SIZE);
        bounceWallChoice.setFitHeight(CHOICE_SIZE);
        bounceWallChoice.addEventHandler( MouseEvent.ANY, this::handleAddBounceWallChoice);
        badHoleChoice = new ImageView(Const.IMAGE_BADHOLE);
        badHoleChoice.setFitWidth(CHOICE_SIZE);
        badHoleChoice.setFitHeight(CHOICE_SIZE);
        badHoleChoice.addEventHandler( MouseEvent.ANY, this::handleAddBadHoleChoice);
        spikeChoice = new ImageView(Const.IMAGE_SPIKE);
        spikeChoice.setFitWidth(CHOICE_SIZE);
        spikeChoice.setFitHeight(CHOICE_SIZE);
        spikeChoice.addEventHandler( MouseEvent.ANY, this::handleAddSpikeChoice);
        stopwatchChoice = new ImageView(Const.IMAGE_STOPWATCH);
        stopwatchChoice.setFitWidth(CHOICE_SIZE);
        stopwatchChoice.setFitHeight(CHOICE_SIZE);
        stopwatchChoice.addEventHandler( MouseEvent.ANY, this::handleAddStopwatchChoice);
        bonusballChoice = new ImageView(Const.IMAGE_BONUSHOLE);
        bonusballChoice.setFitWidth(CHOICE_SIZE);
        bonusballChoice.setFitHeight(CHOICE_SIZE);
        bonusballChoice.addEventHandler( MouseEvent.ANY, this::handleAddBonusBallChoice);
        iceTokenChoice = new ImageView(Const.IMAGE_ICETOKEN);
        iceTokenChoice.setFitWidth(CHOICE_SIZE);
        iceTokenChoice.setFitHeight(CHOICE_SIZE);
        iceTokenChoice.addEventHandler( MouseEvent.ANY, this::handleAddIceTokenChoice);
        labelHover = new LabelSmallC("");
        labelHover.setPrefWidth(BUTTON_WIDTH);
        labelHover.setAlignment(Pos.CENTER);

        // flowPaneBottom
        labelPickedItem = new LabelC("Picked: None");
        labelPickedItem.setPrefWidth(flowPaneWidth);
        labelPositionX = new LabelSmallC("Position X:");
        labelPositionX.setPrefWidth(BUTTON_WIDTH);
        labelPositionZ = new LabelSmallC("Position Z:");
        labelPositionZ.setPrefWidth(BUTTON_WIDTH);
        labelWidth = new LabelSmallC("Width:");
        labelWidth.setPrefWidth(BUTTON_WIDTH);
        labelDepth = new LabelSmallC("Depth:");
        labelDepth.setPrefWidth(BUTTON_WIDTH);
        labelRadius = new LabelSmallC("Radius:");
        labelRadius.setPrefWidth(BUTTON_WIDTH);

        double textBoxHeight = 20;
        textFieldPosX = new TextFieldC();
        textFieldPosX.setPrefWidth(BUTTON_WIDTH);
        textFieldPosX.setMaxHeight(textBoxHeight);
        textFieldPosX.setFont(Const.VERY_SMALL_FONT);
        textFieldPosX.setDisable(true);
        textFieldPosX.addEventHandler(KeyEvent.ANY, this::handleApplyButton);
        textFieldPosZ = new TextFieldC();
        textFieldPosZ.setPrefWidth(BUTTON_WIDTH);
        textFieldPosZ.setMaxHeight(textBoxHeight);
        textFieldPosZ.setFont(Const.VERY_SMALL_FONT);
        textFieldPosZ.setDisable(true);
        textFieldPosZ.addEventHandler(KeyEvent.ANY, this::handleApplyButton);
        textFieldWidth = new TextFieldC();
        textFieldWidth.setPrefWidth(BUTTON_WIDTH);
        textFieldWidth.setMaxHeight(textBoxHeight);
        textFieldWidth.setFont(Const.VERY_SMALL_FONT);
        textFieldWidth.setDisable(true);
        textFieldWidth.addEventHandler(KeyEvent.ANY, this::handleApplyButton);
        textFieldDepth = new TextFieldC();
        textFieldDepth.setPrefWidth(BUTTON_WIDTH);
        textFieldDepth.setMaxHeight(textBoxHeight);
        textFieldDepth.setFont(Const.VERY_SMALL_FONT);
        textFieldDepth.setDisable(true);
        textFieldDepth.addEventHandler(KeyEvent.ANY, this::handleApplyButton);
        textFieldRadius = new TextFieldC();
        textFieldRadius.setPrefWidth(BUTTON_WIDTH);
        textFieldRadius.setMaxHeight(textBoxHeight);
        textFieldRadius.setFont(Const.VERY_SMALL_FONT);
        textFieldRadius.setDisable(true);
        textFieldRadius.addEventHandler(KeyEvent.ANY, this::handleApplyButton);

        rotateButton = new ButtonC("Rotate");
        rotateButton.addEventHandler( MouseEvent.ANY, this::handleRotateButton);
        rotateButton.setPrefWidth(BUTTON_WIDTH / 2);
        rotateButton.setMaxHeight(textBoxHeight);
        rotateButton.setFont(Const.VERY_SMALL_FONT);
        rotateButton.setDisable(true);
        deleteButton = new ButtonC("Delete");
        deleteButton.addEventHandler( MouseEvent.ANY, this::handleDeleteButton);
        deleteButton.setPrefWidth(BUTTON_WIDTH / 2);
        deleteButton.setMaxHeight(textBoxHeight);
        deleteButton.setFont(Const.VERY_SMALL_FONT);
        deleteButton.setDisable(true);
        applyButton = new ButtonC("Apply");
        applyButton.addEventHandler( MouseEvent.ANY, this::handleApplyButton);
        applyButton.setPrefWidth(BUTTON_WIDTH / 2);
        applyButton.setMaxHeight(textBoxHeight);
        applyButton.setFont(Const.VERY_SMALL_FONT);
        applyButton.setDisable(true);
        Label labelSpace = new Label("");
        labelSpace.setPrefWidth(Const.HGAP);
        Label labelSpace2 = new Label("");
        labelSpace2.setPrefWidth(Const.HGAP);
        Label labelSpace3 = new Label("");
        labelSpace3.setPrefWidth(Const.HGAP);

        labelErrorMessage = new LabelSmallC("", true);
        labelErrorMessage.setMaxWidth(Const.MENU_BUTTON_WIDTH - 12);
        labelErrorMessage.setTextFill(Color.DARKRED);
        labelErrorMessage.setPadding(new Insets(10, 10, 10, 10));
        AnchorPaneC anchorPaneC = new AnchorPaneC(true, Const.MENU_BUTTON_WIDTH - 12, 2 * Const.MENU_BUTTON_HEIGHT);
        anchorPaneC.setBackground(Const.PLANK_HUGE_BACKGROUND);
        anchorPaneC.addToFlowPane(labelErrorMessage);

        // 8BUTTONHEIGHT 8VGAP
        flowPane.getChildren().addAll(
                //labelTitle,
                labelCurrentTerrain,
                checkboxGrid,
                comboBox,
                newLevelButton,
                changeNameButton,
                saveButton,
                deleteTerrainButton,
                deleteLevelButton,
                backButton,
                anchorPaneC
        );
        Label fillLabel = new Label("");
        fillLabel.setMinWidth(Const.MENU_BUTTON_WIDTH);
        flowPaneRight.getChildren().addAll(
                labelPaletteTerrain,
                fillLabel,
                ballChoice0, holeChoice0,
                ballChoice1, holeChoice1,
                ballChoice2, holeChoice2,
                lampChoice, wallChoice,
                roundWallChoice, coinChoice,
                bounceWallChoice, badHoleChoice,
                spikeChoice, stopwatchChoice,
                bonusballChoice, iceTokenChoice,
                labelHover
        );
        flowPaneBottom.getChildren().addAll(
                labelPickedItem,
                labelPositionX, textFieldPosX, labelPositionZ, textFieldPosZ,
                labelWidth, textFieldWidth, labelDepth, textFieldDepth,
                labelRadius, textFieldRadius,
                labelSpace, rotateButton, labelSpace2, deleteButton, labelSpace3, applyButton
        );
        anchorPane.getChildren().add(anchorPaneRight);
        anchorPane.getChildren().add(anchorPaneBottom);
        anchorPane.getChildren().add(flowPane);

        rootSubScene = new Group();
        rootSubScene.getChildren().addAll(
                anchorPane
        );
        //-------------------------------
        this.stage.getScene().addEventHandler(MouseEvent.ANY, this::handleMouseReleased);
        //this.stage.getScene().addEventHandler(MouseEvent.ANY, this::handleMouseMoved);
        this.stage.getScene().addEventHandler( KeyEvent.ANY, this::handleKeyboard);

        focusedButton = backButton;
    }

    @Override
    public void setSubScene3D(SubScene subScene) {
        this.subScene3D = subScene;
        this.subScene3D.setWidth(Main.getCurrent_window_width() / 2);
        this.subScene3D.setHeight(Main.getCurrent_window_height() * 3 / 4);
        if (!subScene3D.getTransforms().isEmpty())
            this.subScene3D.getTransforms().remove(0);
        this.subScene3D.getTransforms().add( new Translate(Main.getCurrent_window_width() / 4, 0));
        this.subScene3D.setCamera ( this.defaultCamera );
    }

    private void handleNewLevelButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(0);
        }
    }
    private void handleNewLevelButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(0);
        }
    }

    // Za promenu imena terena
    private void handleChangeNameButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(1);
        }
    }
    private void handleChangeNameButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(1);
        }
    }

    private void handleChangeNameAction(ActionEvent event) {
        changeName();
    }
    private void changeName(){
        String newName = changeNameTextField.getText().toUpperCase();
        if (!newName.equals("")){
            if(Main.changeTerrainName(terrain.getName(), newName) || newName.equals(terrain.getName())){
                terrain.setName(newName);
                labelCurrentTerrain.setText("Terrain: \n" + terrain.getName());
                flowPane.getChildren().remove(changeNameTextField);
                flowPane.getChildren().add(0, labelCurrentTerrain);
                updateSaveButton();
                changeNameButton.requestFocus();
            } else {
                labelErrorMessage.setText("Terrain with that name already exists!");
            }
        }
    }

    private void handleSaveButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(2);
        }
    }
    private void handleSaveButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(2);
        }
    }

    private void updateSaveButton() {
        labelErrorMessage.setText("");
        saveButton.setDisable(false);
    }

    private void handleDeleteTerrainButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(3);
        }
    }
    private void handleDeleteTerrainButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(3);
        }
    }

    private void handleDeleteLevelButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(4);
        }
    }
    private void handleDeleteLevelButton(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(4);
        }
    }

    private void handleBackButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            handleButton(5);
        }
    }
    private void handleBackButton2(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            handleButton(5);
        }
    }

    private void handleButton(int code){
        if (code == 0){ // newLevel
            Main.getMediaPlayer().playButtonSound();
            comboBox.setOnAction(null);
            Level newLevel = new Level();
            terrain.addLevel(newLevel);
            terrain.setCurrentLevel(newLevel);
            levels.add(Integer.toString(levels.size() + 1));
            rootSubScene3D.getChildren().remove( arena );
            fillTerrain();
            comboBox.setItems(FXCollections.observableArrayList(levels));
            comboBox.setValue(Integer.toString(terrain.getCurrentLevelNUM() + 1));
            comboBox.setOnAction(this.event);
            deleteLevelButton.setDisable(false);
            updateSaveButton();
        } else if (code == 1){ // changeTerrainName
            Main.getMediaPlayer().playButtonSound();
            if (flowPane.getChildren().contains(labelCurrentTerrain)){
                flowPane.getChildren().remove(labelCurrentTerrain);
                flowPane.getChildren().add(0, changeNameTextField);
                changeNameTextField.requestFocus();
                updateSaveButton();
            } else {
                changeName();
            }
        } else if (code == 2){ // Save
            Main.getMediaPlayer().playButtonSound();
            for (int i = 0; i < terrain.getLevels().size(); i++) {
                Ball[] balls = terrain.getLevels().get(i).getBall();
                Hole[] holes = terrain.getLevels().get(i).getHole();
                boolean ballExists = false;
                boolean holeExists = false;
                boolean validBallsAndHoles = true;
                for (int j = 0; j < Const.MAX_BALL_CNT; j++) {
                    if (balls[j] != null) ballExists = true;
                    if (holes[j] != null) holeExists = true;
                    if ((balls[j] != null && holes[j] == null) || (balls[j] == null && holes[j] != null)){
                        validBallsAndHoles = false;
                    }
                }
                if (!ballExists || !holeExists) {
                    labelErrorMessage.setText("Each level must contain a ball and a hole before saving!");
                    comboBox.setValue(Integer.toString(i + 1));
                    return;
                }
                if (!validBallsAndHoles) {
                    labelErrorMessage.setText("Each ball requires appropriate hole and vice versa!");
                    comboBox.setValue(Integer.toString(i + 1));
                    return;
                }
                if (terrain.getLevels().get(i).getLamp() == null) {
                    labelErrorMessage.setText("Each level must contain a lamp!");
                    comboBox.setValue(Integer.toString(i + 1));
                    return;
                }
            }

            terrain.setDate(LocalDate.now());
            deselectBody();
            for (int i = 0; i < Main.getTerrains().size(); i++) {
                if (Main.getTerrains().get(i).getName().equals(terrain.getName())){
                    Main.getTerrains().remove(i);
                    Main.getTerrains().add(i, new Terrain(terrain));
                    Main.setCurrentTerrain(Main.getTerrains().get(i).getName());
                    navigateEditorSubscene();
                }
            }
        } else if (code == 3){ // Delete Terrain
            Main.getMediaPlayer().playButtonSound();
            if (Main.isDeletableTerrain(terrain.getName())){
                String text = "Are you sure you want to delete this terrain?";
                if (!labelErrorMessage.getText().equals(text)) {
                    labelErrorMessage.setText(text);
                    return;
                }
                finishEdit();
                this.stage.getScene().removeEventHandler(ScrollEvent.ANY, scrollHandler);
                Main.deleteTerrain(terrain.getName());
                navigateEditorChooseSubscene();
            } else {
                labelErrorMessage.setText("This terrain can't be deleted because there is an active game on this terrain!");
            }
        } else if (code == 4){ // Delete Level
            Main.getMediaPlayer().playButtonSound();
            String text = "Are you sure you want to delete this level?";
            if (!labelErrorMessage.getText().equals(text)) {
                labelErrorMessage.setText(text);
                return;
            }
            int index = Integer.parseInt((String)comboBox.getValue()) - 1;
            terrain.deleteLevel(index);
            rootSubScene3D.getChildren().remove( arena );
            fillTerrain();
            levels.clear();
            for (int i = 0; i < terrain.getLevels().size(); i++) {
                levels.add( Integer.toString(i + 1));
            }
            comboBox.setItems(FXCollections.observableArrayList(levels));
            comboBox.setValue(String.valueOf(terrain.getCurrentLevelNUM() + 1));
            if (levels.size() <= 1) deleteLevelButton.setDisable(true);
            updateSaveButton();
        } else if (code == 5){ // Back
            Main.getMediaPlayer().playButtonSound();
            String text = "Are you sure you want to leave without saving?";
            if (!saveButton.isDisabled() && !labelErrorMessage.getText().equals(text)) {
                labelErrorMessage.setText(text);
                return;
            }
            finishEdit();
            this.stage.getScene().removeEventHandler(ScrollEvent.ANY, scrollHandler);
            navigateEditorChooseSubscene();
            labelErrorMessage.setText("");
        }
    }

    private void handleKeyboard(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.G) {
            this.checkboxGrid.setSelected(!gridOn);
            handleShowGrid(new ActionEvent());
        } else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ESCAPE) {
            deselectBody();
        } else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.DELETE) {
            Main.getMediaPlayer().playRemoveSound();
            deleteSelectedBody();
        } else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.DIGIT1) {
            if (cameraActive == 2) {
                cameraActive = 1;
                this.subScene3D.setCamera ( this.defaultCamera );
            }
        } else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.DIGIT2) {
            if (cameraActive == 1) {
                cameraActive = 2;
                this.subScene3D.setCamera ( this.parallelCamera );
            }
        }  else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.LEFT) {
            if (cameraActive == 1) {
                cameraDistance.setX(cameraDistance.getX() - 50);
            } else {
                cameraDistanceParallel.setX(cameraDistanceParallel.getX() - 50);
            }
        }  else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.RIGHT) {
            if (cameraActive == 1) {
                cameraDistance.setX(cameraDistance.getX() + 50);
            } else {
                cameraDistanceParallel.setX(cameraDistanceParallel.getX() + 50);
            }
        }  else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.DOWN) {
            if (cameraActive == 1) {
                cameraDistance.setY(cameraDistance.getY() + 50);
            } else {
                cameraDistanceParallel.setY(cameraDistanceParallel.getY() + 50);
            }
        }  else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.UP) {
            if (cameraActive == 1) {
                cameraDistance.setY(cameraDistance.getY() - 50);
            } else {
                cameraDistanceParallel.setY(cameraDistanceParallel.getY() - 50);
            }
        }  else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.C) {
            if (event.isControlDown()){
                copyObject();
            }
        }  else if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.V) {
            if (event.isControlDown()){
                pasteObject();
            }
        }
    }

    private void handleShowGrid(ActionEvent event) {
        //Main2.getMediaPlayer().playButtonSound();
        this.gridOn = !this.gridOn;

        if (gridOn) {
            this.rootSubScene3D.getChildren().add(grid);
            this.rootSubScene3D.getChildren().add(ambientLight);
            ambientLight.getScope().add(grid);
        } else {
            this.rootSubScene3D.getChildren().remove(grid);
            this.rootSubScene3D.getChildren().remove(ambientLight);
        }
    }

    private void finishEdit() {
        rootSubScene3D.getChildren().clear();
        root.getChildren().remove(subScene3D);
    }

    private void fillTerrain() {
        pickedBody = null;
        pickedPosition = null;

        arena = terrain.getCurrentLevel().getArena();
        this.arena.removeEventHandler(MouseEvent.ANY, podiumHandler);
        this.arena.addEventHandler(MouseEvent.ANY, podiumHandler);
        podium = terrain.getCurrentLevel().getPodium();
        podium_width = podium.getWidth();
        podium_height = podium.getHeight();
        podium_depth = podium.getDepth();
        ball = terrain.getCurrentLevel().getBall();
        ballPosition = new Translate[Const.MAX_BALL_CNT];
        for (int i = 0; i < Const.MAX_BALL_CNT; i++) {
            if (ball[i] != null) {
                ballPosition[i] = ball[i].getPosition();
            }
        }
        hole = terrain.getCurrentLevel().getHole();
        lamp = terrain.getCurrentLevel().getLamp();
        light = terrain.getCurrentLevel().getLight();
        walls = terrain.getCurrentLevel().getWalls();
        roundWalls = terrain.getCurrentLevel().getRoundWalls();
        coins = terrain.getCurrentLevel().getCoins();
        bounceWalls = terrain.getCurrentLevel().getBounceWalls();
        badHoles = terrain.getCurrentLevel().getBadHoles();

        for (Node n: this.arena.getChildren()) {
            if (n instanceof Positioned) {
                n.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
            } else if (n instanceof Podium) {
                n.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
            }
        }

        double distance = podium_width > podium_depth ? -podium_width * 5 / 2 : -podium_depth * 5 / 2;
        if (cameraDistance != null){
            cameraDistance.setZ(distance);
        }
        if (cameraDistanceParallel != null){
            cameraDistanceParallel.setZ(distance);
        }
        rootSubScene3D.getChildren().add( this.arena );

        if (rootSubScene3D.getChildren().contains(grid)){
            rootSubScene3D.getChildren().remove( this.grid );
            rootSubScene3D.getChildren().remove( this.ambientLight );
        }
        grid = new Grid(podium.getWidth(),podium.getDepth());
        if (gridOn){
            rootSubScene3D.getChildren().add( this.grid );
            rootSubScene3D.getChildren().add( this.ambientLight );
            ambientLight.getScope().add(grid);
        }
    }

    private void handleScrollEvent (ScrollEvent event){
        if (cameraActive == 1) {
            if (event.getDeltaY() < 0) {
                cameraDistance.setZ(cameraDistance.getZ() - 100);
            } else {
                cameraDistance.setZ(cameraDistance.getZ() + 100);
            }
        } else if (cameraActive == 2) {
            if (event.getDeltaY() < 0) {
                cameraDistanceParallel.setZ(cameraDistanceParallel.getZ() - 100);
            } else {
                cameraDistanceParallel.setZ(cameraDistanceParallel.getZ() + 100);
            }
        }
    }

    private void handleAddBallChoice0(MouseEvent event) {
        handleChoice(event, Const.BALL_CHOICE0);
    }
    private void handleAddBallChoice1(MouseEvent event) {
        handleChoice(event, Const.BALL_CHOICE1);
    }
    private void handleAddBallChoice2(MouseEvent event) {
        handleChoice(event, Const.BALL_CHOICE2);
    }

    private void handleAddHoleChoice0(MouseEvent event) {
        handleChoice(event, Const.HOLE_CHOICE0);
    }
    private void handleAddHoleChoice1(MouseEvent event) {
        handleChoice(event, Const.HOLE_CHOICE1);
    }
    private void handleAddHoleChoice2(MouseEvent event) {
        handleChoice(event, Const.HOLE_CHOICE2);
    }
    private void handleAddLampChoice(MouseEvent event) {
        handleChoice(event, Const.LAMP_CHOICE);
    }
    private void handleAddWallChoice(MouseEvent event) {
        handleChoice(event, Const.WALL_CHOICE);
    }
    private void handleAddRoundWallChoice(MouseEvent event) {
        handleChoice(event, Const.ROUNDWALL_CHOICE);
    }
    private void handleAddCoinChoice(MouseEvent event) {
        handleChoice(event, Const.COIN_CHOICE);
    }
    private void handleAddBounceWallChoice(MouseEvent event) {
        handleChoice(event, Const.BOUNCEWALL_CHOICE);
    }
    private void handleAddBadHoleChoice(MouseEvent event) {
        handleChoice(event, Const.BADHOLE_CHOICE);
    }
    private void handleAddSpikeChoice(MouseEvent event) {handleChoice(event, Const.SPIKE_CHOICE);}
    private void handleAddStopwatchChoice(MouseEvent event) {handleChoice(event, Const.STOPWATCH_CHOICE);}
    private void handleAddBonusBallChoice(MouseEvent event) {handleChoice(event, Const.BONUSBALL_CHOICE);}
    private void handleAddIceTokenChoice(MouseEvent event) {handleChoice(event, Const.ICETOKEN_CHOICE);}
    private void handleChoice(MouseEvent event, int code) {
        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED) && event.getButton() == MouseButton.PRIMARY) {
            choicePressed = true;
            changeCursor(code);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            changeHoverLabel(code);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)){
            changeHoverLabel(Const.NONE);
        }
    }

//    private void handleMouseMoved(MouseEvent event){
//        if (event.getEventType().equals(MouseEvent.MOUSE_MOVED)){
//            lastCursorPositionX = event.getX();
//            lastCursorPositionZ = event.getY();
//            System.out.println("last x: " + (int)lastCursorPositionX + ", last y: " + (int)lastCursorPositionZ);
//
//        }
//    }

    private void handleMouseReleased(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED) && event.getButton() == MouseButton.PRIMARY) {
            if (choicePressed) {
                PickResult res = event.getPickResult();
                Point3D position = res.getIntersectedPoint();

                if (res != null && (res.getIntersectedNode() instanceof Shape3D)){
                    Shape3D body = (Shape3D) res.getIntersectedNode();
                    if (body instanceof Podium) {
                        double x = position.getX();
                        double y = position.getY();
                        double z = position.getZ();
                        addComponent(new Translate(x, y, z));
                    } else if (body instanceof GridLine) {
                        double x = position.getX();
                        double y = position.getY();
                        double z = position.getZ();
                        if (Math.abs(((GridLine)body).getWidth()) > Math.abs(((GridLine)body).getDepth())){
                            z = ((GridLine)body).getIndex() * Const.GRID_LINE_SPACE - podium.getDepth() / 2;
                        } else {
                            x = ((GridLine)body).getIndex() * Const.GRID_LINE_SPACE - podium.getWidth() / 2;
                        }
                        addComponent(new Translate(x, y, z));
                    }
                }
                choicePressed = false;
                changeCursor(Const.NONE);
            }
        }
    }
    private void handlePodiumEvent(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            PickResult res = event.getPickResult();
            Point3D position = res.getIntersectedPoint();
            if (res != null  && (res.getIntersectedNode() instanceof Shape3D)) {
                Shape3D body = (Shape3D) res.getIntersectedNode();
                if (body instanceof Podium) {
                    double x = position.getX();
                    double y = position.getY();
                    double z = position.getZ();
                    if (pickedPosition != null && !(pickedBody instanceof Lamp)) {
                        pickedPosition.setX(Utilities.clamp(x, -podium_width / 2, podium_width / 2));
                        pickedPosition.setZ(Utilities.clamp(z, -podium_depth / 2, podium_depth / 2));
                    }
                } else if (body == pickedBody) {
                    double x = position.getX();
                    double y = position.getY();
                    double z = position.getZ();
                    if (pickedPosition != null) {
                        x += pickedPosition.getX();
                        z += pickedPosition.getZ();
                        pickedPosition.setX(Utilities.clamp(x, -podium_width / 2, podium_width / 2));
                        pickedPosition.setZ(Utilities.clamp(z, -podium_depth / 2, podium_depth / 2));
                    }
                } else if (body instanceof GridLine) {
                    double x = position.getX();
                    double y = position.getY();
                    double z = position.getZ();
                    if (Math.abs(((GridLine)body).getWidth()) > Math.abs(((GridLine)body).getDepth())){
                        z = ((GridLine)body).getIndex() * Const.GRID_LINE_SPACE - podium.getDepth() / 2;
                    } else {
                        x = ((GridLine)body).getIndex() * Const.GRID_LINE_SPACE - podium.getWidth() / 2;
                    }
                    if (pickedPosition != null && !(pickedBody instanceof Lamp)) {
                        pickedPosition.setX(x);
                        pickedPosition.setZ(z);
                    }
                }
            }
            if (pickedBody instanceof Spike || pickedBody instanceof SpikeSphere){
                arrowPosition.setX(pickedPosition.getX());
                arrowPosition.setZ(pickedPosition.getZ());
            }
            updateSelectedItem(); // za azuriranje donjeg menija
        } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            updateSaveButton();
            pickedPosition = null;
            pickedBody = null;
        }
    }
    private void handlePositionEvent(MouseEvent event){
        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            PickResult res = event.getPickResult();

            if (res != null  && (res.getIntersectedNode() instanceof Shape3D)){
                Shape3D body = (Shape3D) res.getIntersectedNode();
                if (body instanceof Positioned) {
                    pickedPosition = ((Positioned) body).getPosition();
                    pickedBody = body;
                    selectBody(body);
                } else if ( body instanceof Podium) {
                    selectBody(body);
                } else if ( body instanceof SpikeSphere) {
                    pickedPosition = ((SpikeSphere) body).getSpikyBall().getPosition();
                    pickedBody = body;
                    selectBody(body);
                } else if ( body instanceof Spike) {
                    pickedPosition = ((Spike) body).getSpikyBall().getPosition();
                    pickedBody = body;
                    selectBody(body);
                }
            }
        }
    }

    // DONJI MENI --------------------------
    private void selectBody(Shape3D body) {
        deselectBody();
        selectedBody = body;
        String pickedName = "";
        if (selectedBody instanceof Spike || selectedBody instanceof SpikeSphere) {
            pickedName = "SpikyBall";
            this.arena.getChildren().add(this.arrow);
            if ( body instanceof SpikeSphere) {
                arrowPosition.setX(((SpikeSphere) body).getSpikyBall().getPosition().getX());
                arrowPosition.setZ(((SpikeSphere) body).getSpikyBall().getPosition().getZ());
                arrowRotation.setAngle(((SpikeSphere) body).getSpikyBall().getAngle() - 90);
            } else if ( body instanceof Spike) {
                arrowPosition.setX(((Spike) body).getSpikyBall().getPosition().getX());
                arrowPosition.setZ(((Spike) body).getSpikyBall().getPosition().getZ());
                arrowRotation.setAngle(((Spike) body).getSpikyBall().getAngle() - 90);
            }
        } else {
            pickedName = selectedBody.getClass().getSimpleName();
        }
        labelPickedItem.setText("Picked: " + pickedName);
        ((PhongMaterial)selectedBody.getMaterial()).setSelfIlluminationMap(Const.LIGHT_TEXTURE);
        updateSelectedItem();
    }
    private void deselectBody() {
        removeGlow();
        selectedBody = null;
        labelPickedItem.setText("Picked: None");
        textFieldPosX.setDisable(true);
        textFieldPosX.setText("");
        textFieldPosZ.setDisable(true);
        textFieldPosZ.setText("");
        textFieldDepth.setDisable(true);
        textFieldDepth.setText("");
        textFieldWidth.setDisable(true);
        textFieldWidth.setText("");
        textFieldRadius.setDisable(true);
        textFieldRadius.setText("");
        rotateButton.setDisable(true);
        deleteButton.setDisable(true);
        applyButton.setDisable(true);
        if (labelRadius.getText().equals("Direction:")){
            labelRadius.setText("Radius:");
        }
        if (arena.getChildren().contains(arrow)){
            arena.getChildren().remove(arrow);
        }
        arena.requestFocus();
    }
    private void removeGlow() {
        if (selectedBody != null){
            if (selectedBody instanceof Lamp) {
                ((PhongMaterial)selectedBody.getMaterial()).setSelfIlluminationMap(Const.LAMP_TEXTURE);
            } else if (selectedBody instanceof BonusBall) {
                ((PhongMaterial)selectedBody.getMaterial()).setSelfIlluminationMap(Const.ROUNDWALL_TEXTURE);
            } else if (selectedBody instanceof IceToken) {
                ((PhongMaterial)selectedBody.getMaterial()).setSelfIlluminationMap(Const.ICETOKEN_TEXTURE);
            } else if (selectedBody instanceof Stopwatch) {
                ((PhongMaterial)selectedBody.getMaterial()).setSelfIlluminationMap(Const.STOPWATCH_TEXTURE);
            }  else if (selectedBody instanceof Coin) {
                ((PhongMaterial)selectedBody.getMaterial()).setSelfIlluminationMap(Const.COIN_TEXTURE);
            } else {
                try {
                    ((PhongMaterial)selectedBody.getMaterial()).setSelfIlluminationMap(null);
                } catch (Exception e) {

                }
            }
        }
    }
    private void deleteSelectedBody() {
        if (selectedBody == null) return;
        if (!(selectedBody instanceof Podium)) {
            if (selectedBody instanceof Spike){
                this.arena.getChildren().remove(((Spike)selectedBody).getSpikyBall());
                this.terrain.getCurrentLevel().remove(((Spike)selectedBody).getSpikyBall());
            } else if (selectedBody instanceof SpikeSphere){
                this.arena.getChildren().remove(((SpikeSphere)selectedBody).getSpikyBall());
                this.terrain.getCurrentLevel().remove(((SpikeSphere)selectedBody).getSpikyBall());
            } else if (selectedBody instanceof Lamp){
                this.arena.getChildren().remove(light);
            }
            this.arena.getChildren().remove(selectedBody);
            this.terrain.getCurrentLevel().remove(selectedBody);
        }
        deselectBody();
        updateSaveButton();
    }
    private void updateSelectedItem() {
        if (selectedBody == null) return;
        deleteButton.setDisable(false);
        if (selectedBody instanceof Positioned){
            textFieldPosX.setDisable(false);
            textFieldPosZ.setDisable(false);
            applyButton.setDisable(false);
            textFieldPosX.setText(Integer.toString((int)(((Positioned)selectedBody).getPosition().getX())));
            textFieldPosZ.setText(Integer.toString((int)(((Positioned)selectedBody).getPosition().getZ())));
        } else if (selectedBody instanceof SpikeSphere){
            textFieldPosX.setDisable(false);
            textFieldPosZ.setDisable(false);
            textFieldRadius.setDisable(false);
            labelRadius.setText("Direction:");
            applyButton.setDisable(false);
            textFieldPosX.setText(Integer.toString((int)(((SpikeSphere)selectedBody).getSpikyBall().getPosition().getX())));
            textFieldPosZ.setText(Integer.toString((int)(((SpikeSphere)selectedBody).getSpikyBall().getPosition().getZ())));
            textFieldRadius.setText(Integer.toString((int)(((SpikeSphere)selectedBody).getSpikyBall().getAngle())));
        } else if (selectedBody instanceof Spike){
            textFieldPosX.setDisable(false);
            textFieldPosZ.setDisable(false);
            applyButton.setDisable(false);
            textFieldPosZ.setText(Integer.toString((int)(((Spike)selectedBody).getSpikyBall().getPosition().getZ())));
            textFieldPosX.setText(Integer.toString((int)(((Spike)selectedBody).getSpikyBall().getPosition().getX())));
        }

        if (selectedBody instanceof Wall || selectedBody instanceof BounceWall){
            textFieldWidth.setDisable(false);
            textFieldDepth.setDisable(false);
            textFieldWidth.setText(Integer.toString((int)(((Box)selectedBody).getWidth())));
            textFieldDepth.setText(Integer.toString((int)(((Box)selectedBody).getDepth())));
            rotateButton.setDisable(false);
        } else if (selectedBody instanceof Podium) {
            textFieldWidth.setDisable(false);
            textFieldDepth.setDisable(false);
            textFieldWidth.setText(Integer.toString((int)(((Box)selectedBody).getWidth())));
            textFieldDepth.setText(Integer.toString((int)(((Box)selectedBody).getDepth())));
            applyButton.setDisable(false);
            deleteButton.setDisable(true);
        } else if (selectedBody instanceof RoundWall || selectedBody instanceof BadHole) {
            textFieldRadius.setDisable(false);
            textFieldRadius.setText(Integer.toString((int)(((Cylinder)selectedBody).getRadius())));
        }
    }

    private void handleApplyButton(KeyEvent event){
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.getCode() == KeyCode.ENTER) {
            Main.getMediaPlayer().playButtonSound();
            applyChange();
        }
    }
    private void handleApplyButton(MouseEvent event){
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            Main.getMediaPlayer().playButtonSound();
            applyChange();
        }
    }
    private void applyChange() {
        boolean dontUpdateSaveButton = false; // Ovo je true samo kada ne uspe checkAllComponents kako se ne bi brisala poruka
        if (selectedBody == null ) return;
        try {
            if (selectedBody instanceof Positioned) {
                double x = Double.parseDouble(textFieldPosX.getText());
                double z = Double.parseDouble(textFieldPosZ.getText());
                ((Positioned) selectedBody).getPosition().setX(Utilities.clamp(x, -podium_width / 2, podium_width / 2));
                ((Positioned) selectedBody).getPosition().setZ(Utilities.clamp(z, -podium_depth / 2, podium_depth / 2));
            } else if (selectedBody instanceof SpikeSphere) {
                double x = Double.parseDouble(textFieldPosX.getText());
                double z = Double.parseDouble(textFieldPosZ.getText());
                double angle = Double.parseDouble(textFieldRadius.getText());
                ((SpikeSphere) selectedBody).getSpikyBall().getPosition().setX(Utilities.clamp(x, -podium_width / 2, podium_width / 2));
                ((SpikeSphere) selectedBody).getSpikyBall().getPosition().setZ(Utilities.clamp(z, -podium_depth / 2, podium_depth / 2));
                ((SpikeSphere) selectedBody).getSpikyBall().setSpeed(angle);
                arrowRotation.setAngle(angle - 90);
            } else if (selectedBody instanceof Spike) {
                double x = Double.parseDouble(textFieldPosX.getText());
                double z = Double.parseDouble(textFieldPosZ.getText());
                double angle = Double.parseDouble(textFieldRadius.getText());
                ((Spike) selectedBody).getSpikyBall().getPosition().setX(Utilities.clamp(x, -podium_width / 2, podium_width / 2));
                ((Spike) selectedBody).getSpikyBall().getPosition().setZ(Utilities.clamp(z, -podium_depth / 2, podium_depth / 2));
                ((SpikeSphere) selectedBody).getSpikyBall().setSpeed(angle);
                arrowRotation.setAngle(angle - 90);
            }
            if (selectedBody instanceof Wall || selectedBody instanceof BounceWall) {
                double width = Double.parseDouble(textFieldWidth.getText());
                double depth = Double.parseDouble(textFieldDepth.getText());
                ((Box) selectedBody).setWidth(Utilities.clamp(width, Const.WALL_DEPTH, podium_width));
                ((Box) selectedBody).setDepth(Utilities.clamp(depth, Const.WALL_DEPTH, podium_depth));
            } else if (selectedBody instanceof Podium) {
                double width = Double.parseDouble(textFieldWidth.getText());
                double depth = Double.parseDouble(textFieldDepth.getText());
                width = Utilities.clamp(width, Const.MIN_PODIUM_WIDTH, Const.MAX_PODIUM_WIDTH);
                depth = Utilities.clamp(depth, Const.MIN_PODIUM_WIDTH, Const.MAX_PODIUM_WIDTH);
                //boolean shouldCheck = width > podium_width && depth > podium_depth; // ne mora provera ako se povecavaju dimenzije
                dontUpdateSaveButton = !checkAllComponents(width, depth);
                if (!dontUpdateSaveButton) {
                    this.podium.setWidth(width);
                    this.podium.setDepth(depth);
                    this.podium_width = width;
                    this.podium_depth = depth;
                    grid.reset(width, depth);
                }
            } else if (selectedBody instanceof RoundWall || selectedBody instanceof BadHole) {
                double radius = Double.parseDouble(textFieldRadius.getText());
                double max = podium_depth > podium_width ? podium_width / 2 : podium_depth / 2;
                ((Cylinder) selectedBody).setRadius(Utilities.clamp(radius, BALL_RADIUS, max));
            }
            if (!dontUpdateSaveButton){
                updateSaveButton();
            }
        } catch (IllegalArgumentException e) {
            labelErrorMessage.setText("Invalid values!");
        }
    }

    // vraca true ako moze da se promeni
    public boolean checkAllComponents(double podium_width, double podium_depth){
        String message = "There are elements which will be left outside of podium!";
        if (labelErrorMessage.getText().equals(message)){
            boolean bodyDeleted = true;
            while (bodyDeleted){
                bodyDeleted = false;
                for (Node node: arena.getChildren()) {
                    if (node instanceof Positioned) {
                        double centerX = ((Positioned)node).getPosition().getX();
                        double centerZ = ((Positioned)node).getPosition().getZ();
                        if (centerX < -podium_width / 2 || centerX > podium_width / 2 ||
                                centerZ < -podium_depth / 2 || centerZ > podium_depth / 2){
                            if (node instanceof Ball || node instanceof Lamp || node instanceof Hole){
                                ((Positioned) node).getPosition().setX(Utilities.clamp(0, -podium_width / 2, podium_width / 2));
                                ((Positioned) node).getPosition().setZ(Utilities.clamp(0, -podium_depth / 2, podium_depth / 2));
                            } else {
                                if (node instanceof SpikyBall){
                                    selectedBody = ((SpikyBall)node).getSphere();
                                } else {
                                    selectedBody = (Shape3D) node;
                                }
                                deleteSelectedBody();
                                bodyDeleted = true;
                                break;
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            for (Node node: arena.getChildren()) {
                if (node instanceof Positioned) {
                    double centerX = ((Positioned)node).getPosition().getX();
                    double centerZ = ((Positioned)node).getPosition().getZ();
                    if (centerX < -podium_width / 2 || centerX > podium_width / 2 ||
                            centerZ < -podium_depth / 2 || centerZ > podium_depth / 2){
                        labelErrorMessage.setText(message);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void handleRotateButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            Main.getMediaPlayer().playButtonSound();
            if (selectedBody == null ) return;
            if (selectedBody instanceof Wall || selectedBody instanceof BounceWall) {
                double width = Utilities.clamp(((Box)selectedBody).getDepth(), 1, podium_width);
                double depth = Utilities.clamp(((Box)selectedBody).getWidth(), 1, podium_depth);
                ((Box) selectedBody).setWidth(Utilities.clamp(width, 1, podium_width));
                ((Box) selectedBody).setDepth(Utilities.clamp(depth, 1, podium_depth));
                textFieldWidth.setText(Integer.toString((int)(width)));
                textFieldDepth.setText(Integer.toString((int)(depth)));
            }
        }
    }
    private void handleDeleteButton(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton() == MouseButton.PRIMARY) {
            Main.getMediaPlayer().playButtonSound();
            deleteSelectedBody();
        }
    }

    private void copyObject(){
        copiedObject = selectedBody;
    }
    private void pasteObject(){
        if (copiedObject instanceof Wall){
            choiceCode = Const.WALL_CHOICE;
            addComponent(new Translate(0, 0, 0));
            ((Wall)selectedBody).setWidth(((Wall) copiedObject).getWidth());
            ((Wall)selectedBody).setDepth(((Wall) copiedObject).getDepth());
        } else if (copiedObject instanceof RoundWall){
            choiceCode = Const.ROUNDWALL_CHOICE;
            addComponent(new Translate(0, 0, 0));
            ((RoundWall)selectedBody).setRadius(((RoundWall)copiedObject).getRadius());
        } else if (copiedObject instanceof BounceWall){
            choiceCode = Const.BOUNCEWALL_CHOICE;
            addComponent(new Translate(0, 0, 0));
            ((BounceWall)selectedBody).setWidth(((BounceWall) copiedObject).getWidth());
            ((BounceWall)selectedBody).setDepth(((BounceWall) copiedObject).getDepth());
        } else if (copiedObject instanceof BadHole){
            choiceCode = Const.BADHOLE_CHOICE;
            addComponent(new Translate(0, 0, 0));
            ((BadHole)selectedBody).setRadius(((BadHole)copiedObject).getRadius());
        } else if (copiedObject instanceof Coin){
            choiceCode = Const.COIN_CHOICE;
            addComponent(new Translate(0, 0, 0));
        } else if (copiedObject instanceof SpikeSphere || copiedObject instanceof Spike){
            choiceCode = Const.SPIKE_CHOICE;
            addComponent(new Translate(0, 0, 0));
        } else if (copiedObject instanceof Stopwatch){
            choiceCode = Const.STOPWATCH_CHOICE;
            addComponent(new Translate(0, 0, 0));
        } else if (copiedObject instanceof BonusBall){
            choiceCode = Const.BONUSBALL_CHOICE;
            addComponent(new Translate(0, 0, 0));
        } else if (copiedObject instanceof IceToken){
            choiceCode = Const.ICETOKEN_CHOICE;
            addComponent(new Translate(0, 0, 0));
        }
    }

    private void changeHoverLabel(int code) {
        String text = "";
        switch (code) {
            case Const.NONE:
                break;
            case Const.BALL_CHOICE0:
                text = "Ball 1";
                break;
            case Const.BALL_CHOICE1:
                text = "Ball 2";
                break;
            case Const.BALL_CHOICE2:
                text = "Ball 3";
                break;
            case Const.HOLE_CHOICE0:
                 text = "Hole 1";
                break;
            case Const.HOLE_CHOICE1:
                 text = "Hole 2";
                break;
            case Const.HOLE_CHOICE2:
                 text = "Hole 3";
                break;
            case Const.LAMP_CHOICE:
                 text = "Lamp";
                break;
            case Const.WALL_CHOICE:
                 text = "Wall";
                break;
            case Const.ROUNDWALL_CHOICE:
                 text = "RoundWall";
                break;
            case Const.COIN_CHOICE:
                 text = "Coin";
                break;
            case Const.BOUNCEWALL_CHOICE:
                 text = "BounceWall";
                break;
            case Const.BADHOLE_CHOICE:
                 text = "BadHole";
                break;
            case Const.SPIKE_CHOICE:
                 text = "SpikyBall";
                break;
            case Const.STOPWATCH_CHOICE:
                 text = "Stopwatch";
                break;
            case Const.BONUSBALL_CHOICE:
                 text = "BonusBall";
                break;
            case Const.ICETOKEN_CHOICE:
                 text = "IceToken";
                break;
            default:
                break;
        }
        labelHover.setText(text);
    }

    // PREVLACENJE KOMPONENTI --------------
    private void changeCursor(int code) {
        if (code == Const.NONE) {
            this.stage.getScene().setCursor(Cursor.DEFAULT);
            return;
        }
        Image image = Const.IMAGE_BALL;
        switch (code) {
            case Const.BALL_CHOICE0:
                break;
            case Const.BALL_CHOICE1:
                image = Const.IMAGE_BALL2;
                break;
            case Const.BALL_CHOICE2:
                image = Const.IMAGE_BALL3;
                break;
            case Const.HOLE_CHOICE0:
                image = Const.IMAGE_HOLE;
                break;
            case Const.HOLE_CHOICE1:
                image = Const.IMAGE_HOLE2;
                break;
            case Const.HOLE_CHOICE2:
                image = Const.IMAGE_HOLE3;
                break;
            case Const.LAMP_CHOICE:
                image = Const.IMAGE_LAMP;
                break;
            case Const.WALL_CHOICE:
                image = Const.IMAGE_WALL;
                break;
            case Const.ROUNDWALL_CHOICE:
                image = Const.IMAGE_ROUNDWALL;
                break;
            case Const.COIN_CHOICE:
                image = Const.IMAGE_COIN;
                break;
            case Const.BOUNCEWALL_CHOICE:
                image = Const.IMAGE_BOUNCEWALL;
                break;
            case Const.BADHOLE_CHOICE:
                image = Const.IMAGE_BADHOLE;
                break;
            case Const.SPIKE_CHOICE:
                image = Const.IMAGE_SPIKE;
                break;
            case Const.STOPWATCH_CHOICE:
                image = Const.IMAGE_STOPWATCH;
                break;
            case Const.BONUSBALL_CHOICE:
                image = Const.IMAGE_BONUSHOLE;
                break;
            case Const.ICETOKEN_CHOICE:
                image = Const.IMAGE_ICETOKEN;
                break;
            default:
                break;
        }
        this.choiceCode = code;
        this.stage.getScene().setCursor(new ImageCursor(image));
    }

    private void addComponent(Translate position) {
        Main.getMediaPlayer().playPutSound();
        Shape3D body = null;
        switch (choiceCode) {
            case Const.BALL_CHOICE0:
                body = addBall(position, 0);
                break;
            case Const.BALL_CHOICE1:
                body = addBall(position, 1);
                break;
            case Const.BALL_CHOICE2:
                body = addBall(position, 2);
                break;
            case Const.HOLE_CHOICE0:
                body = addHole(position, 0);
                break;
            case Const.HOLE_CHOICE1:
                body = addHole(position, 1);
                break;
            case Const.HOLE_CHOICE2:
                body = addHole(position, 2);
                break;
            case Const.LAMP_CHOICE:
                body = addLamp(position);
                break;
            case Const.WALL_CHOICE:
                body = addWall(position);
                break;
            case Const.ROUNDWALL_CHOICE:
                body = addRoundWall(position);
                break;
            case Const.COIN_CHOICE:
                body = addCoin(position);
                break;
            case Const.BOUNCEWALL_CHOICE:
                body = addBounceWall(position);
                break;
            case Const.BADHOLE_CHOICE:
                body = addBadHole(position);
                break;
            case Const.SPIKE_CHOICE:
                body = addSpike(position);
                break;
            case Const.STOPWATCH_CHOICE:
                body = addStopwatch(position);
                break;
            case Const.BONUSBALL_CHOICE:
                body = addBonusBall(position);
                break;
            case Const.ICETOKEN_CHOICE:
                body = addIceToken(position);
                break;
            default:
                break;
        }
        if (body != null)
            updateSaveButton();
            selectBody(body);
    }

    private Ball addBall(Translate position, int index) {
        if (ball[index] != null) {
            this.arena.getChildren().remove(ball[index]);
            ball[index] = null;
        }
        if (position == null) {
            this.ballPosition[index] = new Translate(
                    0,
                    -(BALL_RADIUS + Const.PODIUM_HEIGHT / 2),
                    0
            );
        } else {
            this.ballPosition[index] = new Translate(
                    position.getX(),
                    -(BALL_RADIUS + Const.PODIUM_HEIGHT / 2),
                    position.getZ()
            );
        }
        this.ball[index] = MapPrepare.addBall(BALL_RADIUS, ballPosition[index], index, 0);
        this.ball[index].addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().setBall(ball[index], index);
        this.terrain.getCurrentLevel().getArena().getChildren().add(ball[index]);

        return ball[index];
    }
    private Hole addHole(Translate position, int index) {
        if (hole[index] != null) {
            this.arena.getChildren().remove(hole[index]);
            hole[index] = null;
        }
        position.setY(Const.HOLE_POSITION_Y);
        this.hole[index] = MapPrepare.addHole2(position, Const.HOLE_HEIGHT, Const.HOLE_RADIUS, index);
        this.hole[index].addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().setHole(hole[index], index);
        this.terrain.getCurrentLevel().getArena().getChildren().add(hole[index]);

        return hole[index];
    }

    private Lamp addLamp(Translate position) {
        if (lamp != null) {
            this.arena.getChildren().remove(lamp);
            lamp = null;
        }
        position.setY(Const.LAMP_POSITION_Y);
        this.lamp = MapPrepare.addLamp2(Const.LAMP_WIDTH, position);
        this.lamp.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().setLamp(lamp);
        this.terrain.getCurrentLevel().getArena().getChildren().add(lamp);
        light.getTransforms().clear();
        light.getTransforms().add(position);
        try {
            this.arena.getChildren().add(light);
        } catch (Exception e) {

        }

        return lamp;
    }
    private Wall addWall(Translate position) {
        position.setY(- Const.WALL_HEIGHT / 2);
        Wall wall = new Wall( Const.WALL_WIDTH, Const.WALL_HEIGHT, Const.WALL_DEPTH, position );

        wall.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getWalls().add(wall);
        this.terrain.getCurrentLevel().getArena().getChildren().add(wall);

        return wall;
    }
    private RoundWall addRoundWall(Translate position) {
        position.setY(- Const.ROUNDWALL_HEIGHT / 2);
        RoundWall roundWall = new RoundWall( Const.ROUNDWALL_RADIUS, Const.ROUNDWALL_HEIGHT, position);

        roundWall.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getRoundWalls().add(roundWall);
        this.terrain.getCurrentLevel().getArena().getChildren().add(roundWall);

        return roundWall;
    }
    private Coin addCoin(Translate position) {
        position.setY(-(Const.COIN_RADIUS + podium_height / 2));
        Coin coin = new Coin(position);

        coin.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getCoins().add(coin);
        this.terrain.getCurrentLevel().getArena().getChildren().add(coin);

        return coin;
    }

    private BounceWall addBounceWall(Translate position) {
        position.setY(- Const.ROUNDWALL_HEIGHT / 2);
        BounceWall bounceWall = new BounceWall(position);

        bounceWall.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getBounceWalls().add(bounceWall);
        this.terrain.getCurrentLevel().getArena().getChildren().add(bounceWall);

        return bounceWall;
    }
    private BadHole addBadHole(Translate position) {
        position.setY(Const.HOLE_POSITION_Y);
        BadHole badHole = new BadHole(position);

        badHole.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getBadHoles().add(badHole);
        this.terrain.getCurrentLevel().getArena().getChildren().add(badHole);

        return badHole;
    }
    private Sphere addSpike(Translate position) {
        position.setY(Const.SPIKY_BALL_HEIGHT);
        SpikyBall spikyBall = new SpikyBall(position);

        spikyBall.getSphere().addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getSpikyBalls().add(spikyBall);
        this.terrain.getCurrentLevel().getArena().getChildren().add(spikyBall);

        return spikyBall.getSphere();
    }
    private Stopwatch addStopwatch(Translate position) {
        position.setY(-(Const.PODIUM_HEIGHT / 2 + Const.STOPWATCH_RADIUS));
        Stopwatch stopwatch = new Stopwatch(position);

        stopwatch.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getStopwatches().add(stopwatch);
        this.terrain.getCurrentLevel().getArena().getChildren().add(stopwatch);

        return stopwatch;
    }
    private BonusBall addBonusBall(Translate position) {
        position.setY(-Const.BONUSBALL_HEIGHT);
        BonusBall ball = new BonusBall(position);

        ball.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getBonusBalls().add(ball);
        this.terrain.getCurrentLevel().getArena().getChildren().add(ball);

        return ball;
    }
    private IceToken addIceToken(Translate position) {
        position.setY(-(Const.PODIUM_HEIGHT / 2 + Const.ICETOKEN_RADIUS));
        IceToken iceToken = new IceToken(position);

        iceToken.addEventHandler(MouseEvent.ANY, this::handlePositionEvent);
        this.terrain.getCurrentLevel().getIceTokens().add(iceToken);
        this.terrain.getCurrentLevel().getArena().getChildren().add(iceToken);

        return iceToken;
    }
}
