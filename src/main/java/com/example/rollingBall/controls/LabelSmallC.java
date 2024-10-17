package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class LabelSmallC extends Label {

    public LabelSmallC() {
        this("");
    }
    public LabelSmallC(String string, boolean allignCenter){
        this(string);
        setTextAlignment(TextAlignment.CENTER);
    }
    public LabelSmallC(String string) {
        super(string);
        setFont(Const.SMALL_FONT);
        setTextFill(Const.BUTTON_INVERSE_COLOR);
        setAlignment(Pos.BASELINE_CENTER);
        setWrapText(true);
    }
}
