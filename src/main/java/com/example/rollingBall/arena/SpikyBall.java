package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class SpikyBall extends Group implements Positioned {

    private SpikeSphere sphere;
    private Translate position;
    private Point3D speed;
    private Rotate rotate;
    private double radius;
    private double angleSign;
    private Shape3D lastHit;

    public SpikyBall() {
        this(new Translate(0,- (Const.PODIUM_HEIGHT + (Const.SPIKE_HEIGHT + Const.SPIKY_BALL_RADIUS)),0));
    }
    public SpikyBall(SpikyBall ball) {
        this(new Translate(ball.getPosition().getX(), ball.getPosition().getY(), ball.getPosition().getZ()));
        this.speed = new Point3D(ball.getSpeed().getX(), ball.getSpeed().getY(), ball.getSpeed().getZ());
        this.angleSign = ball.angleSign;
    }
    public SpikyBall(Translate position) {
        this(position, new Point3D(Const.SPIKY_BALL_SPEED,0,Const.SPIKY_BALL_SPEED));
    }
    public SpikyBall(Translate position, Point3D speed) {
        sphere = new SpikeSphere(Const.SPIKY_BALL_RADIUS);
        radius = Const.SPIKY_BALL_RADIUS + Const.SPIKY_BALL_HEIGHT;
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(Const.METAL_TEXTURE);
        sphere.setMaterial(mat);
        sphere.setSpikyBall(this);

        this.position = position;
        if (speed != null){
            this.speed = new Point3D(
                    speed.getX(),
                    speed.getY(),
                    speed.getZ()
            );
        } else {
            this.speed = new Point3D(Const.SPIKY_BALL_SPEED, 0, 0);
        }
        this.rotate = new Rotate(0, Rotate.X_AXIS);
        this.angleSign = 1;
        this.radius = Const.SPIKY_BALL_RADIUS + Const.SPIKE_HEIGHT;
        this.lastHit = null;

        addSpikes();
        this.getChildren().add(sphere);

        this.getTransforms().addAll(this.position, rotate);
    }

    @Override
    public Translate getPosition() {
        return position;
    }

    @Override
    public void setPosition(Translate position) {
        this.position = position;
    }

    public Point3D getSpeed() {
        return speed;
    }

    public void setSpeed(Point3D speed) {
        this.speed = speed;
    }

    public double getAngle() {
        return this.speed.angle(1, 0, 0) * angleSign;
    }

    public void setSpeed(double angle) {
        angleSign = angle < 0 ? -1 : 1;
        double speedVal = Const.SPIKY_BALL_SPEED; // * Math.pow(2, 0.5);
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));
        double x = speedVal * cos;
        double z = -speedVal * sin;
        this.speed = new Point3D(x, 0, z);
    }

    public void updateSpeed(double val) {
        double speedFactor;
        if (Math.abs(speed.getX()) > Math.abs(speed.getZ())){
            speedFactor = Math.abs(val / speed.getX());
        } else {
            speedFactor = Math.abs(val / speed.getZ());
        }
        this.speed = new Point3D(
                this.speed.getX() * speedFactor,
                this.speed.getY() * speedFactor,
                this.speed.getZ() * speedFactor
        );
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Sphere getSphere() {
        return sphere;
    }

    public void setSphere(SpikeSphere sphere) {
        this.sphere = sphere;
    }

    public boolean update (
            double deltaSeconds,
            double top,
            double bottom,
            double left,
            double right
    ) {
        double newPositionX = this.position.getX ( ) + this.speed.getX ( ) * deltaSeconds;
        double newPositionZ = this.position.getZ ( ) + this.speed.getZ ( ) * deltaSeconds;

        Point3D speedVector = speed.normalize();
        Point3D normalVector = new Point3D(0, 1, 0);
        Point3D horizontalVector = speedVector.crossProduct(normalVector);
//        horizontalVector = new Point3D(
//                Math.abs(horizontalVector.getX()),
//                Math.abs(horizontalVector.getY()),
//                horizontalVector.getZ()
//        );

        double sign = 1;
        //sign *= speed.getX() != 0 ? Math.signum(speed.getX()) : 1;
        //sign *= speed.getZ() != 0 ? Math.signum(speed.getZ()) : 1;

        rotate.setAxis(horizontalVector);
        rotate.setAngle(rotate.getAngle() + sign *
                (this.speed.magnitude() * deltaSeconds * 180.) /
                ((Const.SPIKY_BALL_RADIUS + Const.SPIKE_HEIGHT) * Math.PI));

        this.position.setX ( newPositionX );
        this.position.setZ ( newPositionZ );

        boolean xOutOfBounds = ( newPositionX > right ) || ( newPositionX < left );
        boolean zOutOfBounds = ( newPositionZ > top ) || ( newPositionZ < bottom );

        if (xOutOfBounds || zOutOfBounds) {
            if (xOutOfBounds) {
                changeDirection(-1, 1);
                this.position.setX ( this.position.getX ( ) + 2 * this.speed.getX ( ) * deltaSeconds ); // da se ne baguje na obodu
            }
            if (zOutOfBounds) {
                changeDirection(1, -1);
                this.position.setZ ( this.position.getZ ( ) + 2 * this.speed.getZ ( ) * deltaSeconds ); // da se ne baguje na obodu
            }
            lastHit = null;
        }

        return xOutOfBounds || zOutOfBounds;
    }

    public void changeDirection (int x, int z){
        double newSpeedX = this.speed.getX ( ) * Math.signum( x );
        double newSpeedZ = this.speed.getZ ( ) * Math.signum( z );

        this.speed = new Point3D ( newSpeedX, 0, newSpeedZ );
    }

    public boolean handleCollision ( Sphere ball ) {
        Bounds ballBounds = ball.getBoundsInParent ( );

        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );

        Bounds spikeBounds = super.getBoundsInParent ( );
        double spikeX      = spikeBounds.getCenterX ( );
        double spikeZ      = spikeBounds.getCenterZ ( );
        double spikeRadius = Const.SPIKY_BALL_RADIUS + Const.SPIKE_HEIGHT;

        double dx = spikeX - ballX;
        double dz = spikeZ - ballZ;

        double distance = dx * dx + dz * dz;

        boolean isHit = distance < spikeRadius * spikeRadius;

        return isHit;
    }

    public boolean handleCollision ( SpikyBall ball ) {
        Bounds ballBounds = ball.getBoundsInParent ( );

        double ballX = ballBounds.getCenterX ( );
        double ballZ = ballBounds.getCenterZ ( );
        double ballRadius = Const.SPIKY_BALL_RADIUS + Const.SPIKE_HEIGHT;

        Bounds wallBounds = super.getBoundsInParent ( );
        double wallX      = wallBounds.getCenterX ( );
        double wallZ      = wallBounds.getCenterZ ( );
        double wallRadius = Const.SPIKY_BALL_RADIUS + Const.SPIKE_HEIGHT;

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


    private void addSpikes(){
        Spike spike1 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike2 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike3 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike4 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike5 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike6 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike7 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike8 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike9 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike10 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike11 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike12 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike13 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        Spike spike14 = new Spike(Const.SPIKE_HEIGHT, Const.SPIKE_LENGHT);
        spike1.setSpikyBall(this);
        spike2.setSpikyBall(this);
        spike3.setSpikyBall(this);
        spike4.setSpikyBall(this);
        spike5.setSpikyBall(this);
        spike6.setSpikyBall(this);
        spike7.setSpikyBall(this);
        spike8.setSpikyBall(this);
        spike9.setSpikyBall(this);
        spike10.setSpikyBall(this);
        spike11.setSpikyBall(this);
        spike12.setSpikyBall(this);
        spike13.setSpikyBall(this);
        spike14.setSpikyBall(this);

        spike1.getTransforms().add(new Translate(0, -Const.SPIKY_BALL_RADIUS, 0));
        spike2.getTransforms().addAll(
                new Rotate(180, Rotate.X_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike3.getTransforms().addAll(
                new Rotate(90, Rotate.X_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike4.getTransforms().addAll(
                new Rotate(-90, Rotate.X_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike5.getTransforms().addAll(
                new Rotate(90, Rotate.Z_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike6.getTransforms().addAll(
                new Rotate(-90, Rotate.Z_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike7.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(45, Rotate.Z_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike8.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(-45, Rotate.Z_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike9.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(135, Rotate.Z_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike10.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(-135, Rotate.Z_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike11.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(45, Rotate.X_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike12.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(-45, Rotate.X_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike13.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(135, Rotate.X_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );
        spike14.getTransforms().addAll(
                new Rotate(45, Rotate.Y_AXIS),
                new Rotate(-135, Rotate.X_AXIS),
                new Translate(0, -Const.SPIKY_BALL_RADIUS, 0)
        );

        this.getChildren().addAll(
                spike1, spike2, spike3, spike4, spike5, spike6, spike7,
                spike8, spike9, spike10, spike11, spike12, spike13, spike14
        );
    }

    public Shape3D getLastHit() {
        return lastHit;
    }

    public void setLastHit(Shape3D lastHit) {
        this.lastHit = lastHit;
    }
}
