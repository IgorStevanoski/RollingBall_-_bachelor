package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;

public class RoundWall extends Cylinder implements Positioned{

    private Translate position;

    public RoundWall (double radius, double height, Material material, Translate position ) {
        super ( radius, height );

        super.setMaterial ( material );

        this.position = position;
        super.getTransforms ( ).add ( this.position );
    }

    public RoundWall (double radius, double height, Translate position ) {
        super ( radius, height );

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(Const.KEG_TEXTURE);

        super.setMaterial ( material );

        this.position = position;
        super.getTransforms ( ).add ( this.position );
    }

    public RoundWall(RoundWall roundWall){
        super(roundWall.getRadius(), roundWall.getHeight());

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(Const.KEG_TEXTURE);

        super.setMaterial ( material );

        this.position = new Translate(
                roundWall.getPosition().getX(),
                roundWall.getPosition().getY(),
                roundWall.getPosition().getZ()
        );
        super.getTransforms().addAll( this.position );
    }


    public boolean handleCollision ( Ball ball ) {
        Bounds ballBounds = ball.getBoundsInParent ( );

        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );
        //double ballRadius = ball.getRadius();
        double ballRadius = Const.BALL_RADIUS;

        Bounds wallBounds = super.getBoundsInParent ( );
        double wallX      = wallBounds.getCenterX ( );
        double wallZ      = wallBounds.getCenterZ ( );
        double wallRadius = super.getRadius ( );

        double dx = wallX - ballX;
        double dz = wallZ - ballZ;

        double distance = dx * dx + dz * dz;

        boolean isInHole = distance < (wallRadius + ballRadius) * (wallRadius + ballRadius);

        if (isInHole) {
            Point2D d  = new Point2D( ball.getSpeed().getX(), ball.getSpeed().getZ());
            Point2D r ;
            Point2D n  = new Point2D( ballX - wallX, ballZ - wallZ);
            n = n.normalize();

            r = d.subtract(n.multiply(2 * d.dotProduct(n)));
            ball.setSpeed( new Point3D(
                    r.getX(),
                    0,
                    r.getY()
            ));
        }

        return isInHole;
    }

    public boolean handleCollision ( SpikyBall ball ) {
        Bounds ballBounds = ball.getBoundsInParent ( );

        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );
        double ballRadius = ball.getRadius();

        Bounds wallBounds = super.getBoundsInParent ( );
        double wallX      = wallBounds.getCenterX ( );
        double wallZ      = wallBounds.getCenterZ ( );
        double wallRadius = super.getRadius ( );

        double dx = wallX - ballX;
        double dz = wallZ - ballZ;

        double distance = dx * dx + dz * dz;

        boolean isInHole = distance < (wallRadius + ballRadius) * (wallRadius + ballRadius);

        if (isInHole) {
            Point2D d  = new Point2D( ball.getSpeed().getX(), ball.getSpeed().getZ());
            Point2D r ;
            Point2D n  = new Point2D( ballX - wallX, ballZ - wallZ);
            n = n.normalize();

            r = d.subtract(n.multiply(2 * d.dotProduct(n)));
            ball.setSpeed( new Point3D(
                    r.getX(),
                    0,
                    r.getY()
            ));
        }

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
