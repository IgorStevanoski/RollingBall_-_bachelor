package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class LabelTitleC extends Label {

    public LabelTitleC() {
        this("");
    }
    public LabelTitleC(String string) {
        super(string);
        setFont(Const.TITLE_FONT);

        //setEffect(new DropShadow(BlurType.ONE_PASS_BOX, Const.BUTTON_COLOR, 5, 5, 2, 2));

        setTextFill(Const.TITLE_COLOR);
        setAlignment(Pos.BASELINE_CENTER);
    }

}
