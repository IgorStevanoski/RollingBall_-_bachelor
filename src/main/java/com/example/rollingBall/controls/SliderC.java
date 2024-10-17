package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.scene.control.Slider;

public class SliderC extends Slider {

    public SliderC(double min, double max, double val) {
        super(min, max, val);

        setPrefWidth(Const.MENU_BUTTON_WIDTH * 2 / 3);
        setPrefHeight(Const.MENU_BUTTON_HEIGHT);
    }
}
