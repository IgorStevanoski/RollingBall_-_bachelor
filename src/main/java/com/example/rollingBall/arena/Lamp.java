package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class Lamp extends Box implements Positioned{

    private Translate position;
    private static PhongMaterial material = new PhongMaterial( );
    private static PhongMaterial materialOFF = new PhongMaterial( );
    static {
        material.setSelfIlluminationMap( Const.LAMP_TEXTURE );
        material.setDiffuseMap(Const.LAMP_TEXTURE);
        material.setSpecularColor( Color.WHITE );
        material.setSpecularPower( 1 );

        //material.setSelfIlluminationMap( Const.LAMP_TEXTURE );
        materialOFF.setDiffuseMap(Const.LAMPOFF_TEXTURE);
//        material.setSpecularColor( Color.WHITE );
//        material.setSpecularPower( 1 );
    }

    public Lamp(double width, double height, double depth, Material material, Translate position){
        this(width, height, depth, position);
    }

    public Lamp(double width, double height, double depth, Translate position){
        super(width, height, depth);

        super.setMaterial(material);
        this.position = position;

        super.getTransforms().addAll( position );
    }

    public Lamp(Translate position){
        this(Const.LAMP_WIDTH, Const.LAMP_WIDTH, Const.LAMP_WIDTH, position);
    }

    public void changeLight ( boolean change ) {
        if (change) {
            super.setMaterial(materialOFF);
        } else {
            super.setMaterial(material);
        }
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
