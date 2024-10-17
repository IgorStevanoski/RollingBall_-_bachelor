package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Translate;

public class Grid extends Group {

    public static final double   SPACE    = Const.GRID_LINE_SPACE;
    public static final Material material = new PhongMaterial(Color.LIGHTGREEN);

    public Grid(double width, double depth) {
        int cntX = (int)(width / SPACE);
        int cntZ = (int)(depth / SPACE);

        for (int i = 1; i < cntX; i++) {
            Translate position = new Translate(i * SPACE - width / 2, -Const.PODIUM_HEIGHT, 0);
            this.getChildren().add(
                    new GridLine(Const.WALL_DEPTH / 2, 2, depth, material, position, i)
            );
        }
        for (int i = 1; i < cntZ; i++) {
            Translate position = new Translate(0, -Const.PODIUM_HEIGHT, i * SPACE - depth / 2);
            this.getChildren().add(
                    new GridLine(width, 2, Const.WALL_DEPTH / 2, material, position, i)
            );
        }
    }

    public void reset(double width, double depth){
        int cntX = (int)(width / SPACE);
        int cntZ = (int)(depth / SPACE);

        this.getChildren().clear();

        for (int i = 1; i < cntX; i++) {
            Translate position = new Translate(i * SPACE - width / 2, -Const.PODIUM_HEIGHT, 0);
            this.getChildren().add(
                    new GridLine(Const.WALL_DEPTH, 2, depth, material, position, i)
            );
        }
        for (int i = 1; i < cntZ; i++) {
            Translate position = new Translate(0, -Const.PODIUM_HEIGHT, i * SPACE - depth / 2);
            this.getChildren().add(
                    new GridLine(width, 2, Const.WALL_DEPTH, material, position, i)
            );
        }
    }

}
