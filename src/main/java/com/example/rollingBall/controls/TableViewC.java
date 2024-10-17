package com.example.rollingBall.controls;

import com.example.rollingBall.Const;
import com.example.rollingBall.entities.LeaderboardEntry;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TableViewC<S> extends TableView<S> {

    private Border border;
    private static Color color = Const.BUTTON_COLOR;

    public TableViewC(){

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setPrefWidth(Const.MENU_BUTTON_WIDTH * 2);
        setPrefHeight(Const.MENU_BUTTON_HEIGHT * 11);

        border = new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(6, 6, 6, 6)));
        setBorder(border);
        setBackground(new Background(
                new BackgroundFill(
                        Const.BUTTON_COLOR, null, null
                )
        ));

        TableColumn playerColumn = new TableColumn("Player");
        playerColumn.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        playerColumn.setCellValueFactory(
                new PropertyValueFactory<LeaderboardEntry, String>("playerName"));
        playerColumn.setResizable(false);
        playerColumn.setSortable(false);
        playerColumn.setEditable(false);
        TableColumn scoreColumn = new TableColumn("Score");
        scoreColumn.setPrefWidth(Const.MENU_BUTTON_WIDTH);
        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<LeaderboardEntry, String>("score"));
        scoreColumn.setResizable(false);
        scoreColumn.setSortable(false);
        scoreColumn.setEditable(false);

        setFocusTraversable(false);
        addEventFilter(ScrollEvent.ANY, event -> {
            event.consume(); // Consume the event to prevent scrolling
        });

        getColumns().addAll(playerColumn, scoreColumn);
        setFixedCellSize(Const.MENU_BUTTON_HEIGHT);
    }

}
