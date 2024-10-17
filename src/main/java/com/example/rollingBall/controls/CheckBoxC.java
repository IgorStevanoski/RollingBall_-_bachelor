package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

public class CheckBoxC extends CheckBox {


    private Border border;
    private Border focusedBorder;
    private Border pressedBorder;
    private static Color color = Const.BUTTON_INVERSE_COLOR;
    private static Color focusedColor = Const.BUTTON_FOCUSED_COLOR;
    private static Color clickedColor = Const.BUTTON_CLICKED_COLOR;

    public CheckBoxC() {
        this("");
    }
    public CheckBoxC(String string) {
        super(string);

        setPrefWidth(Const.MENU_BUTTON_WIDTH);
        setPrefHeight(Const.MENU_BUTTON_HEIGHT);
        setFont(Const.SIGNBOARD_FONT);
        setTextFill(color);

//        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (isPressed()){
//                setTextFill(focusedColor);
//                setBorder(pressedBorder);
//            } else if (newValue) {
//                // Button is now focused
//                setTextFill(focusedColor);
//                setBorder(focusedBorder);
//            } else {
//                // Button lost focus
//                setTextFill(color);
//                setBorder(border);
//            }
//        });
//        this.pressedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                setTextFill(focusedColor);
//                setBorder(pressedBorder);
//            } else {
//                if(isFocused()){
//                    setTextFill(clickedColor);
//                    setBorder(focusedBorder);
//                } else {
//                    setTextFill(clickedColor);
//                    setBorder(border);
//                }
//            }
//        });
//        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
//            requestFocus();
//            if (isPressed()){
//                setTextFill(focusedColor);
//                setBorder(pressedBorder);
//            } else if (newValue) {
//                // Button is now focused
//                setTextFill(focusedColor);
//                setBorder(focusedBorder);
//            } else {
//                setTextFill(color);
//                setBorder(border);
//            }
//        });
    }

}
