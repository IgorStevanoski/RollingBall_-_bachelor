package com.example.rollingBall.gsonAdapters;

import com.example.rollingBall.arena.SpikyBall;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.geometry.Point3D;
import javafx.scene.transform.Translate;

import java.io.IOException;

public class SpikyBallAdapter extends TypeAdapter<SpikyBall> {

    @Override
    public void write(JsonWriter writer, SpikyBall ball) throws IOException {
        writer.beginObject();
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
        writer.endObject();
    }

    @Override
    public SpikyBall read(JsonReader reader) throws IOException {
        double positionX = 10;
        double positionY = 10;
        double positionZ = 10;
        double speedX = 0;
        double speedY = 0;
        double speedZ = 0;
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
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
        }
        reader.endObject();
        SpikyBall ball = new SpikyBall(new Translate(positionX, positionY, positionZ));
        ball.setSpeed(new Point3D(speedX, speedY, speedZ));
        return ball;
    }
}
