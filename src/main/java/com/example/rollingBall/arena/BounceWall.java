package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class BounceWall extends Box implements Positioned{

    private Translate position;

    public BounceWall(double width, double height, double depth, Material material, Translate position){
        this(width, height, depth, position);
    }
    public BounceWall(double width, double height, double depth, Translate position){
        super(width, height, depth);

        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(Const.BOUNCE_TEXTURE);
        super.setMaterial(mat);

        this.position = position;
        super.getTransforms().addAll( this.position );
    }
    public BounceWall(Translate position){
        this(Const.BOUNCEWALL_WIDTH, Const.BOUNCEWALL_HEIGHT, Const.BOUNCEWALL_DEPTH, position);
    }

    public BounceWall(BounceWall bounceWall){
        this(bounceWall.getWidth(),
                bounceWall.getHeight(),
                bounceWall.getDepth(),
                new Translate(
                        bounceWall.getPosition().getX(),
                        bounceWall.getPosition().getY(),
                        bounceWall.getPosition().getZ()
                ));
    }

    public boolean handleCollision (Ball ball) {
        Bounds ballBounds = ball.getBoundsInParent ( );
        double ballRadius = Const.BALL_RADIUS;
        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );

        double ballMaxX      = ballX + ballRadius;
        double ballMinX      = ballX - ballRadius;
        double ballMaxZ      = ballZ + ballRadius;
        double ballMinZ      = ballZ - ballRadius;

        Bounds wallBounds = super.getBoundsInParent ( );
        double wallMaxX      = wallBounds.getMaxX ( );
        double wallMinX      = wallBounds.getMinX ( );
        double wallMaxZ      = wallBounds.getMaxZ ( );
        double wallMinZ      = wallBounds.getMinZ ( );

        double wallX = wallBounds.getCenterX ( );
        double wallZ = wallBounds.getCenterZ ( );

        boolean collision = false;

        if ((ballMaxX >= wallMinX && ballMaxX <= wallMaxX
                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMaxZ - wallMinZ))
                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMinZ - wallMaxZ))
        ) {
            ball.changeDirection( -1, 1); collision = true;
        }else if ((ballMinX >= wallMinX && ballMinX <= wallMaxX
                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMaxZ - wallMinZ))
                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMinZ - wallMaxZ))
        )  {
            ball.changeDirection( -1, 1); collision = true;
        }else if ((ballMaxZ >= wallMinZ && ballMaxZ <= wallMaxZ
                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMaxX - wallMinX))
                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMinX - wallMaxX))
        ) {
            ball.changeDirection( 1, -1); collision = true;
        } else if ((ballMinZ >= wallMinZ && ballMinZ <= wallMaxZ
                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMaxX - wallMinX))
                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMinX - wallMaxX))
        )  {
            ball.changeDirection( 1, -1); collision = true;
        }

        if ( collision ) {
            int stage = Main.getCurrentPlayer().getCurrentTerrain().getStageFactor();
            double stage_factor = stage == 0 ? 1. : Math.pow(Const.STAGE_BOUNCE_FACTOR, stage);
            Point3D speed = ball.getSpeed();
            double x = speed.getX() * Const.BOUNCEWALL_FACTOR * stage_factor;
            double z = speed.getZ() * Const.BOUNCEWALL_FACTOR * stage_factor;

            speed = new Point3D( x, 0, z);
            ball.setSpeed( speed );
        }

        return collision;
    }

    public boolean handleCollision (SpikyBall ball) {
        Bounds ballBounds = ball.getBoundsInParent ( );
        double ballRadius = Const.SPIKY_BALL_RADIUS + Const.SPIKE_HEIGHT;
        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );

        double ballMaxX      = ballX + ballRadius;
        double ballMinX      = ballX - ballRadius;
        double ballMaxZ      = ballZ + ballRadius;
        double ballMinZ      = ballZ - ballRadius;

        Bounds wallBounds = super.getBoundsInParent ( );
        double wallMaxX      = wallBounds.getMaxX ( );
        double wallMinX      = wallBounds.getMinX ( );
        double wallMaxZ      = wallBounds.getMaxZ ( );
        double wallMinZ      = wallBounds.getMinZ ( );

        double wallX = wallBounds.getCenterX ( );
        double wallZ = wallBounds.getCenterZ ( );

        int isInHole = 0;

        if ((ballMaxX >= wallMinX && ballMaxX <= wallMaxX
                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMaxZ - wallMinZ))
                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMinZ - wallMaxZ))
        ) {
            isInHole = 1;
        }else if ((ballMinX >= wallMinX && ballMinX <= wallMaxX
                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMaxZ - wallMinZ))
                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMinZ - wallMaxZ))
        )  {
            isInHole = 2;
        }else if ((ballMaxZ >= wallMinZ && ballMaxZ <= wallMaxZ
                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMaxX - wallMinX))
                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMinX - wallMaxX))
        ) {
            isInHole = 3;
        } else if ((ballMinZ >= wallMinZ && ballMinZ <= wallMaxZ
                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMaxX - wallMinX))
                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMinX - wallMaxX))
        )  {
            isInHole = 4;
        }

        if (isInHole != 0) {
            Point2D d  = new Point2D( ball.getSpeed().getX(), ball.getSpeed().getZ());
            Point2D r ;
            double x = ballX - wallX;
            double y = ballZ - wallZ;
            if (Math.abs(x) > Math.abs(y)) {
                y = 0;
            } else {
                x = 0;
            }
            Point2D n  = new Point2D( x, y);
            if (Math.abs(n.getX()) > Math.abs(n.getY())){

            }
            n = n.normalize();

            r = d.subtract(n.multiply(2 * d.dotProduct(n)));
            ball.setSpeed( new Point3D(
                    r.getX(),
                    0,
                    r.getY()
            ));
            return true;
        }


        return false;
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
