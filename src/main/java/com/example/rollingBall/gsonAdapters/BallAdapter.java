package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.Const;
import com.example.rollingBall.arena.Ball;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.geometry.Point3D;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class BallAdapter extends TypeAdapter<Ball> {

    @Override
    public void write(JsonWriter writer, Ball ball) throws IOException {
        if (ball != null) {
            writer.beginObject();
            writer.name("radius");
            writer.value(ball.getRadius());
            writer.name("positionX");
            writer.value(ball.getPosition().getX());
            writer.name("positionY");
            writer.value(ball.getPosition().getY());
            writer.name("positionZ");
            writer.value(ball.getPosition().getZ());
            writer.name("speedX");
            writer.value(ball.getSpeed().getX());
            writer.name("speedY");
            writer.value(ball.getSpeed().getY());
            writer.name("speedZ");
            writer.value(ball.getSpeed().getZ());
            writer.name("index");
            writer.value(ball.getIndex());
            writer.endObject();
        } else {
            writer.beginObject();
            writer.name("radius");
            writer.value(0);
            writer.name("positionX");
            writer.value(0);
            writer.name("positionY");
            writer.value(0);
            writer.name("positionZ");
            writer.value(0);
            writer.name("speedX");
            writer.value(0);
            writer.name("speedY");
            writer.value(0);
            writer.name("speedZ");
            writer.value(0);
            writer.name("index");
            writer.value(0);
            writer.endObject();
        }
    }

    @Override
    public Ball read(JsonReader reader) throws IOException {
        double radius = Const.BALL_RADIUS;
        double positionX = 10;
        double positionY = 10;
        double positionZ = 10;
        double speedX = 0;
        double speedY = 0;
        double speedZ = 0;
        int index = 0; //boja loptice
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if ("radius".equals(fieldname)) {
                token = reader.peek();
                radius = reader.nextDouble();
            }
            if ("positionX".equals(fieldname)) {
                token = reader.peek();
                positionX = reader.nextDouble();
            }
            if ("positionY".equals(fieldname)) {
                token = reader.peek();
                positionY = reader.nextDouble();
            }
            if ("positionZ".equals(fieldname)) {
                token = reader.peek();
                positionZ = reader.nextDouble();
            }
            if ("speedX".equals(fieldname)) {
                token = reader.peek();
                speedX = reader.nextDouble();
            }
            if ("speedY".equals(fieldname)) {
                token = reader.peek();
                speedY = reader.nextDouble();
            }
            if ("speedZ".equals(fieldname)) {
                token = reader.peek();
                speedZ = reader.nextDouble();
            }
            if ("index".equals(fieldname)) {
                token = reader.peek();
                index = reader.nextInt();
            }
        }
        reader.endObject();
        if (radius == 0) return null;
        Ball ball = new Ball(radius, new Translate(positionX, positionY, positionZ), index, 0);
        ball.setSpeed(new Point3D(speedX, speedY, speedZ));
        return ball;
    }
}
