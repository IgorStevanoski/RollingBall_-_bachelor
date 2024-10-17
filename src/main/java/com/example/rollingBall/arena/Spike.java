package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Spike extends MeshView {
    private float height;
    private float length;
    private SpikyBall spikyBall;

    public Spike(float height, float length) {
        this.height = height;
        this.length = length;
        this.spikyBall = null;
        setPickOnBounds(false);

        float[] vertices = {
                0, -height, 0,
                length, height, -length,
                -length, height, -length,
                0, height, length
        };

        float[] texCoords = {
                0, 0,
                0, 1,
                1, 0,
                1, 1
        };

        int[] faces = {
                0, 3, 2, 2, 1, 1,
                0, 3, 1, 1, 2, 2,
                1, 1, 3, 3, 0, 0,
                1, 1, 0, 0, 3, 3,
                0, 0, 3, 3, 2, 2,
                0, 0, 2, 2, 3, 3,
                1, 1, 2, 2, 3, 3,
                1, 1, 3, 3, 2, 2
        };

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(vertices);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(faces);
        this.setMesh(mesh);

        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(Const.METAL_TEXTURE);
        this.setMaterial(mat);
    }

    public SpikyBall getSpikyBall() {
        return spikyBall;
    }

    public void setSpikyBall(SpikyBall spikyBall) {
        this.spikyBall = spikyBall;
    }
}
