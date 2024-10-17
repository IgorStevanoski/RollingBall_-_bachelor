package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Stopwatch extends Cylinder implements Positioned {
    private Rotate rotation;
    private Translate upndown;
    private Translate position;

    public Stopwatch() {
        this(new Translate(0,- (Const.PODIUM_HEIGHT / 2 + Const.STOPWATCH_RADIUS),0));
    }
    public Stopwatch(Stopwatch stopwatch) {
        this(new Translate(
                stopwatch.position.getX(),
                stopwatch.position.getY(),
                stopwatch.position.getZ()
        ));
    }
    public Stopwatch(Translate position) {
        super (Const.STOPWATCH_RADIUS, Const.STOPWATCH_HEIGHT );

        rotation = new Rotate( 0, Rotate.Y_AXIS );
        upndown = new Translate( 0, 0, 0 );
        this.position = position;

        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseMap(Const.STOPWATCH_TEXTURE);
        material.setDiffuseMap(Const.STOPWATCH_TEXTURE);
        material.setSelfIlluminationMap(Const.STOPWATCH_TEXTURE);
        material.setSpecularColor( Color.WHITE );
        super.setMaterial ( material );

        super.getTransforms ( ).addAll (
                this.position,
                rotation,
                upndown,
                new Rotate( 90 , Rotate.Z_AXIS),
                new Rotate( 90, Rotate.Y_AXIS)
        );

        Timeline animation = new Timeline(
                new KeyFrame( Duration.seconds(0), new KeyValue( rotation.angleProperty(), 0, Interpolator.LINEAR)),
                new KeyFrame( Duration.seconds(4), new KeyValue( rotation.angleProperty(), 360, Interpolator.LINEAR))
        );
        animation.setCycleCount( Animation.INDEFINITE );
        animation.setAutoReverse( false );
        animation.play();

        Timeline animation2 = new Timeline(
                new KeyFrame( Duration.seconds(0), new KeyValue( upndown.yProperty(), 0, Interpolator.LINEAR)),
                new KeyFrame( Duration.seconds(2), new KeyValue( upndown.yProperty(), -Const.COIN_RADIUS, Interpolator.LINEAR))
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
