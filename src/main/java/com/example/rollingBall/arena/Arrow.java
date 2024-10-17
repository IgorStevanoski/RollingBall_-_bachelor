package com.example.rollingBall.arena;

import com.example.rollingBall.Const;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Arrow extends MeshView { //samo za editor
    private float height;
    private float length;
    private float width;

    public Arrow(float height, float length, float width) {
        this.height = height;
        this.length = length;
        this.width = width;
        setPickOnBounds(false);

        //                   .4                       .9
        //
        //               .2      .3               .7      .8
        //
        //
        //               .0      .1               .5      .6

        float[] vertices = {
                -length / 2, 0, 0,          //0
                length / 2, 0, 0,           //1
                -length / 2, 0, -height,    //2
                length / 2, -0, -height,    //3
                0, 0, -height * 3 / 2,      //4
                -length / 2, width, 0,      //5
                length / 2, width, 0,       //6
                -length / 2, width, -height,//7
                length / 2, width, -height, //8
                0, width, -height * 3 / 2,  //9
        };

        float[] texCoords = {
                0, 0,
                0, 1,
                1, 0,
                1, 1
        };

        int[] faces = {
                0, 0, 1, 1, 2, 2, //lice 1
                1, 1, 3, 3, 2, 2,
                2, 2, 3, 3, 4, 0,
                5, 0, 7, 1, 6, 2, //lice 2
                6, 1, 7, 3, 8, 2,
                7, 2, 9, 3, 8, 0,
                0, 0, 5, 1, 1, 2, //dno
                5, 0, 6, 1, 1, 2,
                1, 0, 6, 1, 3, 2, //desna strana
                6, 0, 8, 1, 3, 2,
                0, 0, 2, 1, 5, 2, //leva strana
                5, 0, 2, 1, 7, 2,
                3, 0, 8, 1, 4, 2, //desna strana strelice
                8, 0, 9, 1, 4, 2,
                2, 0, 4, 1, 7, 2, //leva strana strelice
                4, 0, 9, 1, 7, 2,
        };

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(vertices);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(faces);
        this.setMesh(mesh);

        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseMap(Const.ARROW_TEXTURE);
        this.setMaterial(mat);
    }
}
