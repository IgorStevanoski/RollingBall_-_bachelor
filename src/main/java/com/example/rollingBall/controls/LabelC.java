package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class LabelC extends Label {

    public LabelC() {
        this("");
    }
    /** color == 0 -> BUTTON_COLOR, color != 0 -> BUTTON_INVERSE_COLOR*/
    public LabelC(String string, int color) {
        this(string);
        if (color == 0) {
            setTextFill(Const.BUTTON_COLOR);
        }
    }
    public LabelC(String string) {
        super(string);
        setFont(Const.FONT);
        setTextFill(Const.BUTTON_INVERSE_COLOR);
        setAlignment(Pos.CENTER);
        setTextAlignment(TextAlignment.CENTER);
        setWrapText(true);
        setPrefWidth(Const.MENU_BUTTON_WIDTH);
        setMinWidth(Const.MENU_BUTTON_WIDTH);
    }
}
