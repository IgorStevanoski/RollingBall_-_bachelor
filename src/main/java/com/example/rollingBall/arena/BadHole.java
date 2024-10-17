package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class BadHole extends Cylinder implements Positioned {

    private Translate position;
    private PhongMaterial material;
    private int colorIndex; // 0 = Color.Black ; 1 = Color.CYAN

    public BadHole (double radius, double height, Material mat, Translate position ) {
        super ( radius, height );

        this.material = new PhongMaterial(Color.DARKGREY);
        material.setDiffuseMap(Const.HOLE_TEXTURE);
        super.setMaterial ( material );
        this.colorIndex = 0;

        this.position = position;
        super.getTransforms ( ).add ( this.position );
    }
    public BadHole (Translate position ) {
        this(Const.HOLE_RADIUS, Const.HOLE_HEIGHT, position);
    }
    public BadHole (double radius, double height, Translate position ) {
        this(radius, height, null, position);
    }

    public BadHole(BadHole badHole){
        this( badHole.getRadius(),
                badHole.getHeight(),
                badHole.getMaterial(),
                new Translate(
                        badHole.getPosition().getX(),
                        badHole.getPosition().getY(),
                        badHole.getPosition().getZ()
                ));
    }

    public boolean handleCollision ( Sphere ball ) {
        Bounds ballBounds = ball.getBoundsInParent ( );

        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );

        Bounds holeBounds = super.getBoundsInParent ( );
        double holeX      = holeBounds.getCenterX ( );
        double holeZ      = holeBounds.getCenterZ ( );
        double holeRadius = super.getRadius ( );

        double dx = holeX - ballX;
        double dz = holeZ - ballZ;

        double distance = dx * dx + dz * dz;

        boolean isInHole = distance < holeRadius * holeRadius;

        return isInHole;
    }

    @Override
    public Translate getPosition() {
        return position;
    }

    @Override
    public void setPosition(Translate position) {
        this.position = position;
    }

    public void freeze() {
        Timeline animation = new Timeline(
                new KeyFrame( Duration.seconds(0), new KeyValue(((PhongMaterial)material).diffuseColorProperty() , Color.DARKGREY, Interpolator.LINEAR)),
                new KeyFrame( Duration.seconds(2), new KeyValue(((PhongMaterial)material).diffuseColorProperty() , Color.CYAN, Interpolator.LINEAR))
        );
        animation.setCycleCount( 1 );
        animation.setAutoReverse( false );
        colorIndex = 1;
        animation.play();

    }
    public void unFreeze() {
        Timeline animation = new Timeline(
                new KeyFrame( Duration.seconds(0), new KeyValue((material).diffuseColorProperty() , Color.CYAN, Interpolator.LINEAR)),
                new KeyFrame( Duration.seconds(2), new KeyValue((material).diffuseColorProperty() , Color.DARKGREY, Interpolator.LINEAR))
        );
        animation.setCycleCount( 1 );
        animation.setAutoReverse( false );
        colorIndex = 0;
        animation.play();
    }

    // KORISTI SE ISKLJUCIVO U BadHoleAdapter-u
    public int getColorINDEX() {
        return colorIndex;
    }
    public void setColor(int color) {

        if (color == 0) {
            (material).setDiffuseColor(Color.DARKGREY);
        } else {
            (material).setDiffuseColor(Color.CYAN);
        }
    }
    //-------------------------------------
}
