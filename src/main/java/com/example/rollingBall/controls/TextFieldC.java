package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TextFieldC extends TextField {

    private Border border;
    private Border focusedBorder;
    private Border pressedBorder;
    private static Color color = Const.BUTTON_COLOR;
    private static Color focusedColor = Const.BUTTON_FOCUSED_COLOR;

    public TextFieldC() {
        this("");
    }
    public TextFieldC(String string) {
        super(string);

        setPrefWidth(Const.MENU_BUTTON_WIDTH);
        setMaxWidth(Const.MENU_BUTTON_WIDTH);
        setPrefHeight(Const.MENU_BUTTON_HEIGHT);
        setFont(Const.SIGNBOARD_FONT);
        setAlignment(Pos.BASELINE_CENTER);
        border = new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(0, 0, 6, 0)));
        setBorder(border);
        focusedBorder = new Border(new BorderStroke(focusedColor,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(0, 0, 6, 0)));
        pressedBorder = new Border(new BorderStroke(focusedColor,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2, 0, 4, 0)));

        this.setBackground(new Background(new BackgroundImage(
                Const.PLANK_TEXT_TEXTURE,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (isPressed()){
                setBorder(pressedBorder);
            } else if (newValue) {
                // Button is now focused
                setBorder(focusedBorder);
            } else {
                // Button lost focus
                setBorder(border);
            }
        });
        /*this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            requestFocus();
            if (isPressed()){
                setBorder(pressedBorder);
            } else if (newValue) {
                // Button is now focused
                setBorder(focusedBorder);
            }
        });*/
    }
}
