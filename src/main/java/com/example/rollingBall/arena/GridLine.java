package com.example.rollingBall.arena;

import javafx.scene.paint.Material;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class GridLine extends Box implements Positioned{

    private Translate position;
    private int index;

    public GridLine(double width, double height, double depth, Material material, Translate position, int index){
        super(width, height, depth);

        super.setMaterial(material);

        this.index = index;
        this.position = position;
        super.getTransforms().addAll( this.position );
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Translate getPosition() {
        return position;
    }

    @Override
    public void setPosition(Translate position) {
        this.position = position;
    }
}
