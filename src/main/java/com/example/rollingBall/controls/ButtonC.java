package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ButtonC extends Button {

    private Border border;
    private Border focusedBorder;
    private Border pressedBorder;
    private static Color color = Const.BUTTON_COLOR;
    private static Color focusedColor = Const.BUTTON_FOCUSED_COLOR;
    private static Color clickedColor = Const.BUTTON_CLICKED_COLOR;

    public ButtonC() {
        this("");
    }
    public ButtonC(String string) {
        super(string);

        setPrefWidth(Const.MENU_BUTTON_WIDTH);
        setPrefHeight(Const.MENU_BUTTON_HEIGHT);
        setFont(Const.SIGNBOARD_FONT);
        setTextFill(color);
        border = new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(0, 0, 6, 0)));
        setBorder(border);
        focusedBorder = new Border(new BorderStroke(focusedColor,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(0, 0, 6, 0)));
        pressedBorder = new Border(new BorderStroke(clickedColor,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2, 0, 4, 0)));

        this.setBackground(new Background(new BackgroundImage(
                Const.PLANK_TEXTURE,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (isPressed()){
                setTextFill(focusedColor);
                setBorder(pressedBorder);
            } else if (newValue) {
                // Button is now focused
                setTextFill(focusedColor);
                setBorder(focusedBorder);
            } else {
                // Button lost focus
                setTextFill(color);
                setBorder(border);
            }
        });
        this.pressedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setTextFill(clickedColor);
                setBorder(pressedBorder);
            } else {
                if(isFocused()){
                    setTextFill(focusedColor);
                    setBorder(focusedBorder);
                } else {
                    setTextFill(color);
                    setBorder(border);
                }
            }
        });
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            requestFocus();
            if (isPressed()){
                setTextFill(focusedColor);
                setBorder(pressedBorder);
            } else if (newValue) {
                // Button is now focused
                setTextFill(focusedColor);
                setBorder(focusedBorder);
            }
        });
    }

}
