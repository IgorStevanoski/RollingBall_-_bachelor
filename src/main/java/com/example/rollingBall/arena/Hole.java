package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;

public class Hole extends Cylinder implements Positioned{

	private Translate position;
	private int index = 0; // Za boju rupe

	public Hole ( double radius, double height, Material material, Translate position ) {
		super ( radius, height );
		
		super.setMaterial ( material );
		this.position = position;

		super.getTransforms ( ).add ( position );
	}
	public Hole ( double radius, double height, Translate position ) {
		this(radius, height, position, 0);
	}
	public Hole ( double radius, double height, Translate position, int index) {
		super ( radius, height );
		this.index = index;

		PhongMaterial holeMaterial;
		if (index == 0) {
			holeMaterial = new PhongMaterial ( Color.RED );
		} else if (index == 1) {
			holeMaterial = new PhongMaterial ( Color.GREEN );
		} else {
			holeMaterial = new PhongMaterial ( Color.YELLOW );
		}
		holeMaterial.setDiffuseMap(Const.HOLE_TEXTURE);

		super.setMaterial ( holeMaterial );
		this.position = position;

		super.getTransforms ( ).add ( position );
	}

	public Hole() {
		this(Const.HOLE_RADIUS, Const.HOLE_HEIGHT,
				new Translate((Const.PODIUM_WIDTH / 2 - 2 * Const.HOLE_RADIUS),
						-30,
						-(Const.PODIUM_DEPTH / 2 - 2 * Const.HOLE_RADIUS)),
				0);
//		super (Const.HOLE_RADIUS, Const.HOLE_HEIGHT);

//		double x = ( Const.PODIUM_WIDTH / 2 - 2 * Const.HOLE_RADIUS );
//		double z = - ( Const.PODIUM_DEPTH / 2 - 2 * Const.HOLE_RADIUS );
//
//		super.setMaterial ( new PhongMaterial( Color.RED ) );
//		this.position = new Translate ( x, -30, z );
//
//		super.getTransforms ( ).add ( position );
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

	public int getIndex() {
		return index;
	}
}
