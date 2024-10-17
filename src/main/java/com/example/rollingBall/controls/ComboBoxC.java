package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class ComboBoxC extends ComboBox {

    private Border border;
    private Border focusedBorder;
    private Border pressedBorder;
    private static Color color = Const.BUTTON_COLOR;
    private static Color focusedColor = Const.BUTTON_FOCUSED_COLOR;
    private static Color clickedColor = Const.BUTTON_CLICKED_COLOR;

    public ComboBoxC(ObservableList list) {
        super(list);

        Background background = Const.BUTTON_BACKGROUND;
        setVisibleRowCount(Const.VISIBLE_ROW_CNT);

        setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    if (item == null || empty) {
                        setBackground(background);
                    } else {
                        setPrefHeight(Const.MENU_BUTTON_HEIGHT);
                        setFont(Const.SMALL_FONT);
                        setTextAlignment(TextAlignment.CENTER);
                        setAlignment(Pos.CENTER);
                        setBackground(background);
                        setText(item);
                        setTextFill(color);
                    }
                }
            }});

        setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> l) {
                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setBackground(background);
                        } else {
                            setPrefHeight(Const.MENU_BUTTON_HEIGHT);
                            setFont(Const.SMALL_FONT);
                            setTextAlignment(TextAlignment.CENTER);
                            setAlignment(Pos.CENTER);
                            setBackground(background);
                            setText(item);
                            hoverProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue) {
                                    setTextFill(focusedColor);
                                } else {
                                    setTextFill(color);
                                }
                            });
                            this.focusedProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue) {
                                    setTextFill(focusedColor);
                                } else {
                                    setTextFill(color);
                                }
                            });
                        }
                    }
                } ;
            }
        });

        setPrefWidth(Const.MENU_BUTTON_WIDTH);
        setPrefHeight(Const.MENU_BUTTON_HEIGHT);
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

        this.setBackground(background);

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (isPressed()){
                //setTextFill(focusedColor);
                setBorder(pressedBorder);
            } else if (newValue) {
                // Button is now focused
                setBorder(focusedBorder);
            } else {
                // Button lost focus
                setBorder(border);
            }
        });
        this.pressedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setBorder(pressedBorder);
            } else {
                if(isFocused()){
                    setBorder(focusedBorder);
                } else {
                    setBorder(border);
                }
            }
        });
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            //requestFocus();
            if (isPressed()){
                setBorder(pressedBorder);
            } else if (newValue) {
                setBorder(focusedBorder);
            } else {
                setBorder(border);
            }
        });
    }
}
