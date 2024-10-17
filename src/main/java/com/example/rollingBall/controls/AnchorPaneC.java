package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class AnchorPaneC extends AnchorPane {
    Border border;
    FlowPane flowPane;

    public AnchorPaneC(boolean isVertical, double maxWidth, double maxHeight){
        this(isVertical);
        this.setPrefWidth(maxWidth);
        this.setMaxWidth(maxWidth);
        this.setMaxHeight(maxHeight);
        flowPane.setMaxHeight(maxHeight);
        flowPane.setPrefWidth(maxWidth);
    }

    public AnchorPaneC(boolean isVertical){
        this();
        if (isVertical) {
            flowPane = new FlowPane(Orientation.VERTICAL, Const.VGAP, Const.MENU_VGAP);
        } else {
            flowPane = new FlowPane(Orientation.HORIZONTAL, Const.VGAP, Const.MENU_VGAP);
        }
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPrefWrapLength(Const.MENU_PANE_WIDTH);
        flowPane.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        AnchorPane.setLeftAnchor(flowPane, (Const.MENU_PANE_WIDTH - Const.MENU_BUTTON_WIDTH) / 2);
        AnchorPane.setTopAnchor(flowPane, 0.);

        this.getChildren().add(flowPane);
    }

    public AnchorPaneC(){
        super();
        setPrefWidth(Const.MENU_PANE_WIDTH);
        setPrefHeight(Const.MENU_PANE_WIDTH);

        border = Const.BORDER;
        setBorder(border);

        this.setBackground(new Background(new BackgroundImage(
                Const.PANE_TEXTURE,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1, 1, true, true, true, true)
        )));
    }

    public void addToFlowPane(Node node) {
        this.flowPane.getChildren().add(node);
    }
    public void addAllToFlowPane(Node... node) {
        this.flowPane.getChildren().addAll(node);
    }
    public FlowPane getFlowPane() {
        return flowPane;
    }
}
