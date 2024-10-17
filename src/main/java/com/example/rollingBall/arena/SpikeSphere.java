package com.example.rollingBall.arena;

import javafx.scene.shape.Sphere;

public class SpikeSphere extends Sphere {

    SpikyBall spikyBall; // SpikyBall koji sadrzi ovaj SpikeSphere

    public SpikeSphere(double radius) {
        super(radius);
        spikyBall = null;
    }

    public SpikyBall getSpikyBall() {
        return spikyBall;
    }

    public void setSpikyBall(SpikyBall spikyBall) {
        this.spikyBall = spikyBall;
    }
}
