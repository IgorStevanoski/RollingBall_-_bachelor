package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import com.example.rollingBall.Utilities;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Ball extends Sphere implements Positioned{
	private Translate position;
	private Point3D speed;
//	private Rotate rotateX;
//	private Rotate rotateZ;
	private Rotate rotate;
	private int index = 0; // Za boju loptice
	private static Point3D XAXIS = Rotate.X_AXIS;
	private Shape3D lastHit;
	private static final int LASTHITCNT = 50;
	private int lastHitcnt = LASTHITCNT;

	public Point3D getSpeed() {
		return speed;
	}

	public void setSpeed(Point3D speed) {
		this.speed = speed;
	}

	@Override
	public Translate getPosition() {
		return position;
	}

	@Override
	public void setPosition(Translate position) {
		this.position = position;
	}

	public int getIndex() {
		return index;
	}

	public Ball (double radius, Material material, Translate position) {
		this(radius, position);
		super.setMaterial ( material );
	}
	public Ball (double radius, Translate position, int index, int chosen) {
		this(radius, position);
		this.index = index;

		Material material;
		if ( index == 1 ) material = new PhongMaterial ( Color.GREEN );
		else if ( index == 2 ) material = new PhongMaterial ( Color.YELLOW );
		else material = new PhongMaterial ( Color.RED );

		((PhongMaterial)material).setDiffuseMap(Const.BALL_TEXTURE[chosen]);

		super.setMaterial ( material );
	}
	public Ball (double radius, Translate position ) {
		super ( radius );
		super.setMaterial ( new PhongMaterial(Color.RED, Const.BALL1_TEXTURE, null, null, null));

		this.position = position;
//		this.rotateX = new Rotate(0, Rotate.Z_AXIS);
//		this.rotateZ = new Rotate(0, Rotate.X_AXIS);
		this.rotate = new Rotate(0, Rotate.X_AXIS);

		//super.getTransforms ( ).addAll ( this.position, rotateX, rotateZ);
		super.getTransforms().addAll(this.position, rotate);

		this.speed = new Point3D ( 0, 0, 0 );
	}

	public Ball () {
		this(Const.BALL_RADIUS, new Translate (
				- ( Const.PODIUM_WIDTH / 2 - 2 * Const.BALL_RADIUS ),
				- ( Const.BALL_RADIUS + Const.PODIUM_HEIGHT / 2 ),
				Const.PODIUM_DEPTH / 2 - 2 * Const.BALL_RADIUS));
	}
	
	public boolean update (
			double deltaSeconds,
			double top,
			double bottom,
			double left,
			double right,
			double xAngle,
			double zAngle,
			double maxAngleOffset,
			double maxAcceleration,
			double damp,
			int difficulty,
			int stage
	) {
		if (--lastHitcnt < 0) {
			lastHit = null;
			lastHitcnt = LASTHITCNT;
		}

		double newPositionX = this.position.getX ( ) + this.speed.getX ( ) * deltaSeconds;
		double newPositionZ = this.position.getZ ( ) + this.speed.getZ ( ) * deltaSeconds;
		
		this.position.setX ( newPositionX );
		this.position.setZ ( newPositionZ );

		Point3D speedVector = speed.normalize();
		Point3D normalVector = new Point3D(0, 1, 0);
		Point3D horizontalVector = speedVector.crossProduct(normalVector);
//		horizontalVector = new Point3D(
//				Math.abs(horizontalVector.getX()),
//				Math.abs(horizontalVector.getY()),
//				horizontalVector.getZ()
//		);

		double sign = 1;
		//sign *= speed.getX() != 0 ? Math.signum(speed.getX()) : 1;
		//sign *= speed.getZ() != 0 ? Math.signum(speed.getZ()) : 1;

		rotate.setAxis(horizontalVector);
		rotate.setAngle(rotate.getAngle() + sign *
				(this.speed.magnitude() * deltaSeconds * 180.) /
						((Const.SPIKY_BALL_RADIUS + Const.SPIKE_HEIGHT) * Math.PI));

//		rotateX.setAngle(rotateX.getAngle() + (180. * (this.speed.getX() * deltaSeconds) /
//				(2 * (Const.SPIKY_BALL_RADIUS + (double)Const.SPIKE_HEIGHT * Math.PI))));
//		rotateZ.setAngle(rotateZ.getAngle() - (180. * (this.speed.getZ() * deltaSeconds) /
//				(2 * (Const.SPIKY_BALL_RADIUS + (double)Const.SPIKE_HEIGHT * Math.PI))));

		double difficulty_factor = Const.DIFFICULTY_FACTOR[difficulty];
		double stage_factor = stage == 0 ? 1. : Math.pow(Const.STAGE_BALL_FACTOR, stage);

		double accelerationX = maxAcceleration * zAngle / maxAngleOffset  * difficulty_factor * stage_factor;
		double accelerationZ = -maxAcceleration * xAngle / maxAngleOffset  * difficulty_factor * stage_factor;

		double newSpeedX = ( this.speed.getX ( ) + accelerationX * deltaSeconds ) * damp;
		double newSpeedZ = ( this.speed.getZ ( ) + accelerationZ * deltaSeconds ) * damp;

		newSpeedX = Utilities.clamp(newSpeedX, -Const.MAX_BALL_SPEED, Const.MAX_BALL_SPEED);
		newSpeedZ = Utilities.clamp(newSpeedZ, -Const.MAX_BALL_SPEED, Const.MAX_BALL_SPEED);

		this.speed = new Point3D ( newSpeedX, 0, newSpeedZ );
		
		boolean xOutOfBounds = ( newPositionX > right ) || ( newPositionX < left );
		boolean zOutOfBounds = ( newPositionZ > top ) || ( newPositionZ < bottom );
		
		return xOutOfBounds || zOutOfBounds;
	}

	public void changeDirection (int x, int z){
		double newSpeedX = this.speed.getX ( ) * Math.signum( x );
		double newSpeedZ = this.speed.getZ ( ) * Math.signum( z );

		this.speed = new Point3D ( newSpeedX, 0, newSpeedZ );
	}

	// prekopirano od RoundWall
	public boolean handleCollision ( Ball ball ) {
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

	public Shape3D getLastHit() {
		return lastHit;
	}

	public void setLastHit(Shape3D lastHit) {
		this.lastHit = lastHit;
		lastHitcnt = LASTHITCNT;
	}
}
