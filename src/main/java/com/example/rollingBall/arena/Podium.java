package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Podium extends Box {

    public Podium(double width, double height, double depth, Material material){
        super(width, height, depth);

        super.setMaterial(material);
    }

    public Podium(double width, double height, double depth){
        super(width, height, depth);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(Const.PODIUM_TEXTURE);
        super.setMaterial(material);
    }

    public Podium() {
        this(Const.PODIUM_WIDTH, Const.PODIUM_HEIGHT, Const.PODIUM_DEPTH, new PhongMaterial(Color.BLUE));
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(Const.PODIUM_TEXTURE);
        super.setMaterial(material);
    }
}
