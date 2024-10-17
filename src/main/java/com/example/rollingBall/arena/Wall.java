package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.geometry.Bounds;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;

public class Wall extends Box implements Positioned{

    private Translate position;
    private static int testCNT = 0;

    public Wall(double width, double height, double depth, Material material, Translate position){
        this(width, height, depth, position);
    }

    public Wall(double width, double height, double depth, Translate position){
        super(width, height, depth);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(Const.PLANK_HUGE_TEXTURE);
        super.setMaterial(material);

        this.position = position;
        super.getTransforms().addAll( this.position );
    }

    public Wall(Wall wall){
        super(wall.getWidth(), wall.getHeight(), wall.getDepth());

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(Const.PLANK_HUGE_TEXTURE);
        super.setMaterial(material);

        this.position = new Translate(
          wall.getPosition().getX(),
          wall.getPosition().getY(),
          wall.getPosition().getZ()
        );
        super.getTransforms().addAll( this.position );
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

        // TESTIRANJE
        Sphere boundingSphere = new Sphere(Const.BALL_RADIUS); // Adjust the radius
        boundingSphere.setTranslateX(ball.getPosition().getX());
        boundingSphere.setTranslateY(ball.getPosition().getY());
        boundingSphere.setTranslateZ(ball.getPosition().getZ());

        Box boundingBox = new Box(this.getWidth(), this.getHeight(), this.getDepth()); // Adjust dimensions
        boundingBox.setTranslateX(this.getPosition().getX());
        boundingBox.setTranslateY(this.getPosition().getY());
        boundingBox.setTranslateZ(this.getPosition().getZ());

        boolean isIntersecting = boundingSphere.getBoundsInParent().intersects(boundingBox.getBoundsInParent());
        // --------------------
//        double ballRadius = ballBounds.getWidth() / 2;
//        double ballX = ballBounds.getCenterX ( );
//        double ballZ = ballBounds.getCenterZ ( );

        Bounds wallBounds = super.getBoundsInParent ( );
        double wallMaxX      = wallBounds.getMaxX ( );
        double wallMinX      = wallBounds.getMinX ( );
        double wallMaxZ      = wallBounds.getMaxZ ( );
        double wallMinZ      = wallBounds.getMinZ ( );

        double wallX = wallBounds.getCenterX ( );
        double wallZ = wallBounds.getCenterZ ( );

        if (isIntersecting){
            if (ballX < wallMinX || ballX > wallMaxX){
                if (ballZ < wallMinZ || ballZ > wallMaxZ){
                    if (ballX < wallX && ball.getSpeed().getX() < 0 ||
                        ballX > wallX && ball.getSpeed().getX() > 0
                    ) {
                        ball.changeDirection( 1, -1);
                    } else {
                        ball.changeDirection( -1, 1);
                        ball.changeDirection( 1, -1);
                    }
                }
                ball.changeDirection( -1, 1);
            }
            if (ballZ < wallMinZ || ballZ > wallMaxZ){
                if (ballX < wallMinX || ballX > wallMaxX){
                    if (ballZ < wallZ && ball.getSpeed().getZ() > 0 ||
                            ballZ > wallZ && ball.getSpeed().getZ() < 0
                    ){
                        ball.changeDirection( -1, 1);
                    } else {
                        ball.changeDirection( -1, 1);
                        ball.changeDirection( 1, -1);
                    }
                }
                ball.changeDirection( 1, -1);
            }
            return true;
        }

//        if ((ballMaxX >= wallMinX && ballMaxX <= wallMaxX
//                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
//                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMaxZ - wallMinZ))
//                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMinZ - wallMaxZ))
//        ) {
//            ball.changeDirection( -1, 1); return true;
//        }else if ((ballMinX >= wallMinX && ballMinX <= wallMaxX
//                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
//                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMaxZ - wallMinZ))
//                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMinZ - wallMaxZ))
//        )  {
//            ball.changeDirection( -1, 1); return true;
//        }else if ((ballMaxZ >= wallMinZ && ballMaxZ <= wallMaxZ
//                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
//                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMaxX - wallMinX))
//                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMinX - wallMaxX))
//        ) {
//            ball.changeDirection( 1, -1); return true;
//        } else if ((ballMinZ >= wallMinZ && ballMinZ <= wallMaxZ
//                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
//                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMaxX - wallMinX))
//                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMinX - wallMaxX))
//        )  {
//            ball.changeDirection( 1, -1); return true;
//        }

        return false;
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

        if ((ballMaxX >= wallMinX && ballMaxX <= wallMaxX
                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMaxZ - wallMinZ))
                && (Math.abs(ballMaxX - wallMinX) < Math.abs(ballMinZ - wallMaxZ))
        ) {
            ball.changeDirection( -1, 1); return true;
        }else if ((ballMinX >= wallMinX && ballMinX <= wallMaxX
                && (Math.abs(ballZ - wallZ) < ballRadius + this.getDepth() / 2))
                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMaxZ - wallMinZ))
                && (Math.abs(ballMinX - wallMaxX) < Math.abs(ballMinZ - wallMaxZ))
        )  {
            ball.changeDirection( -1, 1); return true;
        }else if ((ballMaxZ >= wallMinZ && ballMaxZ <= wallMaxZ
                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMaxX - wallMinX))
                && (Math.abs(ballMaxZ - wallMinZ) < Math.abs(ballMinX - wallMaxX))
        ) {
            ball.changeDirection( 1, -1); return true;
        } else if ((ballMinZ >= wallMinZ && ballMinZ <= wallMaxZ
                && (Math.abs(ballX - wallX) < ballRadius + this.getWidth() / 2))
                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMaxX - wallMinX))
                && (Math.abs(ballMinZ - wallMaxZ) < Math.abs(ballMinX - wallMaxX))
        )  {
            ball.changeDirection( 1, -1); return true;
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
