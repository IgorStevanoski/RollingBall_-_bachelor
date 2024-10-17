package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class BonusBall extends Sphere implements Positioned {
    private Translate upndown;
    private Translate position;

    public BonusBall() {
        this(new Translate(-Const.BONUSBALL_HEIGHT,0));
    }
    public BonusBall(BonusBall ball) {
        this(new Translate(
                ball.position.getX(),
                ball.position.getY(),
                ball.position.getZ()
        ));
    }
    public BonusBall(Translate position) {
        super (Const.BONUSBALL_RADIUS);

        upndown = new Translate( 0, 0, 0 );
        this.position = position;

        PhongMaterial material = new PhongMaterial(Color.RED);
        //material.setDiffuseMap(Const.STOPWATCH_TEXTURE);
        material.setSelfIlluminationMap(Const.ROUNDWALL_TEXTURE);
        super.setMaterial ( material );

        super.getTransforms ( ).addAll (
                this.position,
                upndown,
                new Rotate( 90 , Rotate.Z_AXIS),
                new Rotate( 90, Rotate.Y_AXIS)
        );

        Timeline animation2 = new Timeline(
                new KeyFrame( Duration.seconds(0), new KeyValue( upndown.yProperty(), 0, Interpolator.LINEAR)),
                new KeyFrame( Duration.seconds(1), new KeyValue( upndown.yProperty(), -Const.COIN_RADIUS, Interpolator.LINEAR))
        );
        animation2.setCycleCount( Animation.INDEFINITE );
        animation2.setAutoReverse( true );
        animation2.play();
    }

    public boolean handleCollision ( Sphere ball ) {
        Bounds ballBounds = ball.getBoundsInParent ( );

        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );
        double ballRadius = ball.getRadius();

        Bounds holeBounds = super.getBoundsInParent ( );
        double holeX      = holeBounds.getCenterX ( );
        double holeZ      = holeBounds.getCenterZ ( );
        double holeRadius = super.getRadius ( );

        double dx = holeX - ballX;
        double dz = holeZ - ballZ;

        double distance = dx * dx + dz * dz;

        boolean isInHole = distance < (holeRadius + ballRadius) * (holeRadius + ballRadius);

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
}
